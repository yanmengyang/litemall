package org.linlinjava.litemall.admin.web;



import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.service.AuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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





    /***
     * 根据id获取详情
     * @param request
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public Object get(HttpServletRequest request, @PathVariable("id") Integer id){
        return service.selectById(id);
    }


    /***
     * 普通更新
     * @param bean
     * @return
     */
    @PostMapping(value = "/update")
    public Object update(@RequestBody  Aunt bean){
        return service.updateById(bean);
    }


    /**
     * 新增
     * @param bean
     * @return
     */
    @PostMapping(value = "/insertBatich")
    public Object insertBatch(MultipartFile file){
        return service.insertBatch(file);
    }


    /**
     * 新增
     * @param bean
     * @return
     */
    @PostMapping(value = "/insert")
    public Object insert(@RequestBody  Aunt bean){
        return service.save(bean);
    }

 /***
     * 删除
     * @param request
     * @param id
     * @return
     */
    @PostMapping(value = "/del/{id}")
    public Object del(HttpServletRequest request, @PathVariable("id") Integer id){
        return service.removeById(id);
    }



    }
