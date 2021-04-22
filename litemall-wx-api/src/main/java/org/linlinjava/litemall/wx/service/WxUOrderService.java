package org.linlinjava.litemall.wx.service;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.models.auth.In;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.express.ExpressService;
import org.linlinjava.litemall.core.express.dao.ExpressInfo;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.qcode.QCodeService;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.task.TaskService;
import org.linlinjava.litemall.core.util.DateTimeUtil;
import org.linlinjava.litemall.core.util.IpUtil;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.CouponUserConstant;
import org.linlinjava.litemall.db.util.GrouponConstant;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.wx.task.OrderUnpaidTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.linlinjava.litemall.wx.util.WxResponseCode.*;

/**
 * 订单服务
 *
 * <p>
 * 订单状态：
 * 101 订单生成，未支付；102，下单后未支付用户取消；103，下单后未支付超时系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，但是退款取消；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货； 402 用户没有确认收货超过一定时间，系统自动确认收货；
 *
 * <p>
 * 用户操作：
 * 当101用户未付款时，此时用户可以进行的操作是取消订单，或者付款操作
 * 当201支付完成而商家未发货时，此时用户可以取消订单并申请退款
 * 当301商家已发货时，此时用户可以有确认收货的操作
 * 当401用户确认收货以后，此时用户可以进行的操作是删除订单，评价商品，申请售后，或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除订单，评价商品，申请售后，或者再次购买
 */
@Service
public class WxUOrderService {
    private final Log logger = LogFactory.getLog(WxUOrderService.class);

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private TaskService taskService;



    @Autowired
    private AuntService auntService;
    @Autowired
    private AuntOrderService uOrderService;



    /**
     * 付款订单的预支付会话标识
     * <p>
     * 1. 检测当前订单是否能够付款
     * 2. 微信商户平台返回支付订单ID
     * 3. 设置订单付款状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 支付订单ID
     */
    @Transactional
    public Object prepay( AuntOrder order,HttpServletRequest request) {
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (null==order.getId()) {
            return ResponseUtil.badArgument();
        }
        if (null==order.getUserId()) {
            return ResponseUtil.badArgument();
        }
        AuntOrder dbOrder = uOrderService.selectById(order.getId());
        if (dbOrder == null) {
            return ResponseUtil.badArgumentValue();
        }

        if (dbOrder.getPayStatus()!=0){
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }

        LitemallUser user = userService.findById(order.getUserId());
        String openid = user.getWeixinOpenid();
        if (openid == null) {
            return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "订单不能支付");
        }
        WxPayMpOrderResult result = null;
        String transactionCode =UUID.randomUUID().toString().replace("-","");
        dbOrder.setPayStatus(1);
        dbOrder.setTransactionCode(transactionCode);

        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(dbOrder.getTransactionCode());
            orderRequest.setOpenid(openid);
            orderRequest.setBody("订单：" + dbOrder.getTransactionCode());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = dbOrder.getBlance();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));

            result = wxPayService.createOrder(orderRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(ORDER_PAY_FAIL, "订单不能支付");
        }

        uOrderService.updateById(dbOrder);

        return ResponseUtil.ok(result);
    }

    /**
     * 微信H5支付
     *
     * @param userId
     * @param body
     * @param request
     * @return
     */
    @Transactional
    public Object h5pay(AuntOrder order,HttpServletRequest request) {
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (null==order.getId()) {
            return ResponseUtil.badArgument();
        }
        if (null==order.getUserId()) {
            return ResponseUtil.badArgument();
        }

        AuntOrder dbOrder = uOrderService.selectById(order.getId());
        if (dbOrder == null) {
            return ResponseUtil.badArgumentValue();
        }

        if (dbOrder.getPayStatus()!=0){
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }

        WxPayMwebOrderResult result = null;
        String transactionCode =UUID.randomUUID().toString().replace("-","");
        dbOrder.setPayStatus(1);
        dbOrder.setTransactionCode(transactionCode);
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(dbOrder.getTransactionCode());
            orderRequest.setTradeType("MWEB");
            orderRequest.setBody("订单：" + dbOrder.getTransactionCode());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = dbOrder.getBlance();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));

            result = wxPayService.createOrder(orderRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(result);
    }

    /**
     * 微信付款成功或失败回调接口
     * <p>
     * 1. 检测当前订单是否是付款状态;
     * 2. 设置订单付款成功状态相关信息;
     * 3. 响应微信商户平台.
     *
     * @param request  请求内容
     * @param response 响应内容
     * @return 操作结果
     */
    @Transactional
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        String xmlResult = null;
        try {
            xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            logger.info("payNotify-xmlResult:"+xmlResult);
        } catch (IOException e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }

        WxPayOrderNotifyResult result = null;
        try {
            result = wxPayService.parseOrderNotifyResult(xmlResult);

            if(!WxPayConstants.ResultCode.SUCCESS.equals(result.getResultCode())){
                logger.error(xmlResult);
                throw new WxPayException("微信通知支付失败！");
            }
            if(!WxPayConstants.ResultCode.SUCCESS.equals(result.getReturnCode())){
                logger.error(xmlResult);
                throw new WxPayException("微信通知支付失败！");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }

        logger.info("处理腾讯支付平台的订单支付");
        logger.info(result);

        String orderSn = result.getOutTradeNo();
        String payId = result.getTransactionId();

        // 分转化成元
        String totalFee = BaseWxPayResult.fenToYuan(result.getTotalFee());
        AuntOrder order = uOrderService.findBySn(orderSn);
        if (order == null) {
            return WxPayNotifyResponse.fail("订单不存在 sn=" + orderSn);
        }

        // 检查这个订单是否已经处理过
        if (order.getPayStatus()==2) {
            return WxPayNotifyResponse.success("订单已经处理成功!");
        }

        // 检查支付订单金额
        if (!totalFee.equals(order.getBlance().toString())) {
            return WxPayNotifyResponse.fail(order.getTransactionCode() + " : 支付金额不符合 totalFee=" + totalFee);
        }

        order.setPayNo(payId);
        order.setPayStatus(2);
        uOrderService.updateById(order);

        // 这里微信的短信平台对参数长度有限制，所以将订单号只截取后6位
        notifyService.notifySmsTemplateSync(order.getMobile(), NotifyType.PAY_SUCCEED, new String[]{orderSn.substring(8, 14)});



        return WxPayNotifyResponse.success("处理成功!");
    }

    /**
     * 订单申请退款
     * <p>
     * 1. 检测当前订单是否能够退款；
     * 2. 设置订单申请退款状态。
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    public Object refund(AuntOrder order,HttpServletRequest request) {
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (null==order.getId()) {
            return ResponseUtil.badArgument();
        }

        AuntOrder dbOrder = uOrderService.selectById(order.getId());
        if (dbOrder == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (dbOrder.getPayStatus()!=2){
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能退款");
        }

        // 微信退款
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutTradeNo(order.getTransactionCode());
        wxPayRefundRequest.setOutRefundNo("refund_" + order.getTransactionCode());
        // 元转成分
        Integer totalFee = order.getBlance().multiply(new BigDecimal(100)).intValue();
        wxPayRefundRequest.setTotalFee(totalFee);
        wxPayRefundRequest.setRefundFee(totalFee);

        WxPayRefundResult wxPayRefundResult;
        try {
            wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
        } catch (WxPayException e) {
            logger.error(e.getMessage(), e);
            return ResponseUtil.fail(621, "订单退款失败");
        }
        if (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) {
            logger.warn("refund fail: " + wxPayRefundResult.getReturnMsg());
            return ResponseUtil.fail(621, "订单退款失败");
        }
        if (!wxPayRefundResult.getResultCode().equals("SUCCESS")) {
            logger.warn("refund fail: " + wxPayRefundResult.getReturnMsg());
            return ResponseUtil.fail(621, "订单退款失败");
        }

        LocalDateTime now = LocalDateTime.now();
        // 设置订单取消状态
        order.setPayStatus(3);
        // 记录订单退款相关信息
        uOrderService.updateById(order);


        //TODO 发送邮件和短信通知，这里采用异步发送
        // 退款成功通知用户, 例如“您申请的订单退款 [ 单号:{1} ] 已成功，请耐心等待到账。”
        // 注意订单号只发后6位
        notifyService.notifySmsTemplate(order.getMobile(), NotifyType.REFUND,
                new String[]{order.getId().toString()});

        return ResponseUtil.ok();
    }

}