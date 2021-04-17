package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.util.*;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.AuntOrder;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.service.AuntOrderService;
import org.linlinjava.litemall.db.service.AuntService;
import org.linlinjava.litemall.db.service.DictService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.vo.AuntOrderVo;
import org.linlinjava.litemall.wx.vo.AuntVo;
import org.linlinjava.litemall.wx.vo.DictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 阿姨-订单服务
 */
@CrossOrigin
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

    /**
     * 预约下单
     * @param order
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody AuntOrder order) {
        if (null==order){
            return ResponseUtil.badArgument();
        }
        if (null==order.getAuntId()){
            return ResponseUtil.badArgument();
        }
        if (null==order.getUserId()){
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(order.getStartTime())){
            return ResponseUtil.badArgument();
        }
        Aunt aunt=auntService.selectById(order.getAuntId());

        order.setAuntMobile(aunt.getMobile());
        order.setAuntName(aunt.getNickName());


        return ResponseUtil.ok(orderService.save(order));

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



}
