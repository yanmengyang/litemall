package org.linlinjava.litemall.db.service.Impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.AuntMapper;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.exection.BeanValidator;
import org.linlinjava.litemall.db.exection.BizException;
import org.linlinjava.litemall.db.service.AuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层 
 */
@Service
public class AuntServiceImpl implements AuntService {
    @Autowired
    AuntMapper auntMapper;
    @Override
    public Aunt selectById(Integer id) {
        return auntMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateById(Aunt bean) {
        auntMapper.updateByPrimaryKeySelective(bean);
        return false;
    }

    @Override
    public Integer save(Aunt bean) throws BizException {
        BeanValidator.check(bean);

        return auntMapper.insertSelective(bean);
    }

    @Override
    public boolean removeById(Integer id) {
        auntMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public Integer insertBatch(MultipartFile file) {

        return null;
    }

    @Override
    public List<Aunt> getListPage(Integer page, Integer limit, Aunt dict) {
        PageHelper.startPage(page, limit);
        List<Aunt> list= auntMapper.getList(dict);
        return list;
    }


}

