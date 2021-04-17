package org.linlinjava.litemall.db.service.Impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.AuntMapper;
import org.linlinjava.litemall.db.dao.AuntOrderMapper;
import org.linlinjava.litemall.db.domain.AuntOrder;
import org.linlinjava.litemall.db.service.AuntOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: honglei
 * @Date: 2021/4/11 13:17
 * @Description:
 */
@Service
public class AuntOrderServiceImpl implements AuntOrderService {
    @Autowired
    AuntOrderMapper orderMapper;

    @Override
    public AuntOrder selectById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateById(AuntOrder bean) {
        orderMapper.updateByPrimaryKey(bean);
        return true;
    }

    @Override
    public Integer save(AuntOrder bean) {
        return orderMapper.insertSelective(bean);
    }

    @Override
    public Integer removeById(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AuntOrder> getListPage(Integer page, Integer limit, AuntOrder order) {
        PageHelper.startPage(page, limit);
        return orderMapper.getList(order);
    }
}
