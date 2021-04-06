package org.linlinjava.litemall.admin.web;



import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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





    /***
     * 根据id获取详情
     * @param request
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public Object get(HttpServletRequest request, @PathVariable("id") Long id){
        return service.getById(id);
    }


    /***
     * 普通更新
     * @param bean
     * @return
     */
    @PostMapping(value = "/update")
    public Object update(@RequestBody  Dict bean){
        return service.updateById(bean);
    }


    /**
     * 新增
     * @param bean
     * @return
     */
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
    @PostMapping(value = "/del/{id}")
    public Object del(HttpServletRequest request, @PathVariable("id") Long id){
        return service.removeById(id);
    }



    }
