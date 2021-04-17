package org.linlinjava.litemall.admin.web;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.vo.AuntOrderVo;
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
import java.util.List;

/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层 
 */
@RestController
@RequestMapping("/admin/uorder")
public class AuntOrderController {

    @Autowired
    AuntOrderService  service;




    @RequiresPermissions("admin:uorder:list")
    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "查询")
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
    @RequiresPermissions("admin:uorder:read")
    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "详情")
    @PostMapping(value = "/read/{id}")
    public Object read(HttpServletRequest request, @PathVariable("id") Integer id){
        return ResponseUtil.ok(service.selectById(id));
    }


    /***
     * 普通更新
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:uorder:update")
    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "编辑")
    @PostMapping(value = "/update")
    public Object update(@RequestBody  AuntOrder bean){
        return ResponseUtil.ok(service.updateById(bean));
    }




    /**
     * 新增
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:uorder:create")
    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "添加")
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
    @RequiresPermissions("admin:uorder:delete")
    @RequiresPermissionsDesc(menu = {"家政管理", "预约管理"}, button = "删除")
    @PostMapping(value = "/delete/{id}")
    public Object delete(HttpServletRequest request, @PathVariable("id") Integer id){
        return ResponseUtil.ok(service.removeById(id));
    }



    }
