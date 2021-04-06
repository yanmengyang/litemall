package org.linlinjava.litemall.admin.web;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层 字典表
 */
@RestController
@RequestMapping("/api/Dict")
public class DictController {

    @Autowired
    DictService service;

    @RequiresPermissions("admin:dict:list")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       String dictType) {
        Dict dict=new Dict();
        dict.setDictType(dictType);
        List<LitemallAdmin> adminList =service.getListPage(page,limit,dict);
        return ResponseUtil.okList(adminList);
    }



    /***
     * 根据id获取详情
     * @param request
     * @param id
     * @return
     */
    @RequiresPermissions("admin:dict:read")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "详情")
    @GetMapping(value = "/get/{id}")
    public Object get(HttpServletRequest request, @PathVariable("id") Long id){
        return service.getById(id);
    }


    /***
     * 普通更新
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:dict:update")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "编辑")
    @PostMapping(value = "/update")
    public Object update(@RequestBody  Dict bean){
        return service.updateById(bean);
    }


    /**
     * 新增
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:dict:create")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "添加")
    @PostMapping(value = "/insert")
    public Object insert(@RequestBody  Dict bean){
        return service.save(bean);
    }

 /***
     * 删除
     * @param request
     * @param id
     * @return
     */
 @RequiresPermissions("admin:dict:delete")
 @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "删除")
    @PostMapping(value = "/del/{id}")
    public Object del(HttpServletRequest request, @PathVariable("id") Long id){
        return service.removeById(id);
    }



    }
