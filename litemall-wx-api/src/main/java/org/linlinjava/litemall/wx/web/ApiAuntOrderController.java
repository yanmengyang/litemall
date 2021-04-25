package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.util.*;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.AuntOrder;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.exection.BizException;
import org.linlinjava.litemall.db.service.AuntOrderService;
import org.linlinjava.litemall.db.service.AuntService;
import org.linlinjava.litemall.db.service.DictService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.WxUOrderService;
import org.linlinjava.litemall.wx.vo.AuntOrderVo;
import org.linlinjava.litemall.wx.vo.AuntVo;
import org.linlinjava.litemall.wx.vo.DictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 阿姨-订单服务
 */
//@CrossOrigin
@RestController
@RequestMapping("/wx/aunt/uorder")
@Validated
public class ApiAuntOrderController {
    private final Log logger = LogFactory.getLog(ApiAuntOrderController.class);

    @Autowired
    private LitemallUserService userService;




    @Autowired
    private AuntService auntService;
    @Autowired
    private AuntOrderService orderService;

    @Autowired
    private DictService dictService;
    @Autowired
    WxUOrderService wxUOrderService;

    /**
     * 预约下单
     * @param order
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody AuntOrder order) throws BizException {
        if (null==order){
            return ResponseUtil.badArgument();
        }
        if (null==order.getAuntId()){
            return ResponseUtil.badArgument();
        }

        Aunt aunt=auntService.selectById(order.getAuntId());

        order.setAuntMobile(aunt.getMobile());
        order.setAuntName(aunt.getNickName());
        Integer cunt =orderService.save(order);
        if (cunt==null||cunt==0){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(order.getId());

    }

    /***
     * 阿姨列表
     * @param aunt
     * @return
     */
    @PostMapping("auntList")
    public Object auntList(@RequestBody AuntVo aunt) {

        return ResponseUtil.okList(auntService.getListPage(aunt.getPage(),aunt.getLimit(),aunt));
    }

    /**
     * 订单列表
     * @param order
     * @return
     */
    @PostMapping("orderList")
    public Object orderList(@RequestBody AuntOrderVo order) {
        return ResponseUtil.okList(orderService.getListPage( order.getPage(),order.getLimit(),  order));
    }

    /**
     * 字典列表
     * @param dict
     * @return
     */
    @PostMapping("dictList")
    public Object orderList(@RequestBody DictVo dict) {
        return ResponseUtil.okList(dictService.getListPage(dict.getPage(),dict.getLimit(),dict));
    }






    /**
     * 付款订单的预支付会话标识
     *

     * @return 支付订单ID
     */
    @PostMapping("prepay")
    public Object prepay(@RequestBody AuntOrder order,HttpServletRequest request) {
        return wxUOrderService.prepay(order, request);
    }

    /**
     * 微信H5支付
     * @param
     * @param
     * @param request
     * @return
     */
    @PostMapping("h5pay")
    public Object h5pay(@RequestBody AuntOrder order,HttpServletRequest request) {
        return wxUOrderService.h5pay(order, request);
    }

    /**
     * 微信付款成功或失败回调接口
     * <p>
     *  TODO
     *  注意，这里pay-notify是示例地址，建议开发者应该设立一个隐蔽的回调地址
     *
     * @param request 请求内容
     * @param response 响应内容
     * @return 操作结果
     */
    @PostMapping("pay-notify")
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        return wxUOrderService.payNotify(request, response);
    }

}
