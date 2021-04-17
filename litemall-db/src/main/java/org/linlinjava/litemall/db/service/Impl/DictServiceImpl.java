package org.linlinjava.litemall.db.service.Impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.DictMapper;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层 字典表
 */
@Service
public class DictServiceImpl  implements DictService {
    @Autowired DictMapper dictMapper;

    @Override
    public Dict getById(Long id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateById(Dict bean) {
        return false;
    }

    @Override
    public Integer save(Dict bean) {
        return dictMapper.insertSelective(bean);
    }

    @Override
    public Integer removeById(Long id) {
        return dictMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<LitemallAdmin> getListPage(Integer page, Integer limit, Dict dict) {

        PageHelper.startPage(page, limit);
        return dictMapper.getList(dict);
    }
}

