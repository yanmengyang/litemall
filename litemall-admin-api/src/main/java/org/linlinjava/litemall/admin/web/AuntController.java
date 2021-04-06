package org.linlinjava.litemall.admin.web;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
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
@RequestMapping("/api/House")
public class AuntController {

    @Autowired
    AuntService service;




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
    @RequiresPermissions("admin:house:read")
    @RequiresPermissionsDesc(menu = {"家政管理", "阿姨管理"}, button = "详情")
    @GetMapping(value = "/read/{id}")
    public Object read(HttpServletRequest request, @PathVariable("id") Integer id){
        return service.selectById(id);
    }


    /***
     * 普通更新
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:house:update")
    @RequiresPermissionsDesc(menu = {"家政管理", "阿姨管理"}, button = "编辑")
    @PostMapping(value = "/update")
    public Object update(@RequestBody  Aunt bean){
        return service.updateById(bean);
    }


    /**
     * 新增
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:house:insertBatich")
    @RequiresPermissionsDesc(menu = {"家政管理", "阿姨管理"}, button = "导入")
    @PostMapping(value = "/insertBatich")
    public Object insertBatch(MultipartFile file){
        return service.insertBatch(file);
    }


    /**
     * 新增
     * @param bean
     * @return
     */
    @RequiresPermissions("admin:house:create")
    @RequiresPermissionsDesc(menu = {"家政管理", "阿姨管理"}, button = "添加")
    @PostMapping(value = "/create")
    public Object create(@RequestBody  Aunt bean){
        return service.save(bean);
    }

 /***
     * 删除
     * @param request
     * @param id
     * @return
     */
    @RequiresPermissions("admin:admin:delete")
    @RequiresPermissionsDesc(menu = {"家政管理", "阿姨管理"}, button = "删除")
    @PostMapping(value = "/delete/{id}")
    public Object delete(HttpServletRequest request, @PathVariable("id") Integer id){
        return service.removeById(id);
    }



    }
