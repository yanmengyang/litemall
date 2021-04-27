package org.linlinjava.litemall.admin.web;



import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.vo.AuntOrderVo;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.AuntOrder;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.exection.BizException;
import org.linlinjava.litemall.db.service.AuntOrderService;
import org.linlinjava.litemall.db.service.AuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层 
 */
//@CrossOrigin
@RestController
@RequestMapping("/admin/uorder")
public class AuntOrderController {

    private final Log logger = LogFactory.getLog(AuntOrderController.class);

    @Autowired
    AuntOrderService  service;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private NotifyService notifyService;


//    @RequiresPermissions("admin:uorder:list")
//    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "查询")
    @PostMapping(value = "/list")
    public Object list(@RequestBody AuntOrderVo order) {
        return ResponseUtil.okList(service.getListPage( order.getPage(),order.getLimit(),  order));
    }


    /***
     * 根据id获取详情
     * @param request
     * @param id
     * @return
     */
//    @RequiresPermissions("admin:uorder:read")
//    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "详情")
    @PostMapping(value = "/read/{id}")
    public Object read(HttpServletRequest request, @PathVariable("id") Integer id){
        return ResponseUtil.ok(service.selectById(id));
    }


    /***
     * 普通更新
     * @param bean
     * @return
     */
//    @RequiresPermissions("admin:uorder:update")
//    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "编辑")
    @PostMapping(value = "/update")
    public Object update(@RequestBody  AuntOrder bean){
        if (null==bean){
            ResponseUtil.fail(422222,"订单不存在");
        }
        if (null!=bean.getPayStatus()){

        }


        return ResponseUtil.ok(service.updateById(bean));
    }




    /**
     * 新增
     * @param bean
     * @return
     */
//    @RequiresPermissions("admin:uorder:create")
//    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "添加")
    @PostMapping(value = "/create")
    public Object create(@RequestBody  AuntOrder bean) throws BizException {
        return ResponseUtil.ok(service.save(bean));
    }

 /***
     * 删除
     * @param request
     * @param id
     * @return
     */
//    @RequiresPermissions("admin:uorder:delete")
//    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "删除")
    @PostMapping(value = "/delete/{id}")
    public Object delete(HttpServletRequest request, @PathVariable("id") Integer id){
        AuntOrder auntOrder = service.selectById(id);
        if (auntOrder == null){
            return ResponseUtil.fail(-1,"该订单不存在");
        }
        if (auntOrder.getPayStatus() == 1){
            return ResponseUtil.fail(-1,"该订单状态不允许删除");
        }
        return ResponseUtil.ok(service.removeById(id));
    }




    /**
     * 订单申请退款
     *
     * @return 订单退款操作结果
     */
//    @RequiresPermissions("admin:uorder:refund")
//    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "退款")
    @PostMapping("refund")
    public Object refund(@RequestBody AuntOrder order,HttpServletRequest request) {
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (null==order.getId()) {
            return ResponseUtil.badArgument();
        }

        AuntOrder dbOrder = service.selectById(order.getId());
        if (dbOrder == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (dbOrder.getPayStatus()!=2){
            return ResponseUtil.fail(4725, "不是已支付订单不能退款");
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
            return ResponseUtil.fail(4621, "订单退款失败");
        }
        if (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) {
            logger.warn("refund fail: " + wxPayRefundResult.getReturnMsg());
            return ResponseUtil.fail(4621, "订单退款失败");
        }
        if (!wxPayRefundResult.getResultCode().equals("SUCCESS")) {
            logger.warn("refund fail: " + wxPayRefundResult.getReturnMsg());
            return ResponseUtil.fail(4621, "订单退款失败");
        }

        LocalDateTime now = LocalDateTime.now();
        // 设置订单取消状态
        order.setPayStatus(3);
        // 记录订单退款相关信息
        service.updateById(order);


        //TODO 发送邮件和短信通知，这里采用异步发送
        // 退款成功通知用户, 例如“您申请的订单退款 [ 单号:{1} ] 已成功，请耐心等待到账。”
        // 注意订单号只发后6位
        notifyService.notifySmsTemplate(order.getMobile(), NotifyType.REFUND,
                new String[]{order.getId().toString()});

        return ResponseUtil.ok();
    }









}
