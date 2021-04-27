package org.linlinjava.litemall.db.service.Impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.AOrderMapper;
import org.linlinjava.litemall.db.domain.AuntOrder;
import org.linlinjava.litemall.db.exection.BeanValidator;
import org.linlinjava.litemall.db.exection.BizException;
import org.linlinjava.litemall.db.service.AuntOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: honglei
 * @Date: 2021/4/11 13:17
 * @Description:
 */
@Service
public class AuntOrderServiceImpl implements AuntOrderService {
    @Autowired
    AOrderMapper orderMapper;

    @Override
    public AuntOrder selectById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public AuntOrder findBySn(String orderSn) {
        AuntOrder order=new AuntOrder();
        order.setTransactionCode(orderSn);
        List<AuntOrder> orderList= orderMapper.getList(order);
        if (!CollectionUtils.isEmpty(orderList)){
            order=orderList.get(0);
        }
        return order;
    }

    @Override
    public boolean updateById(AuntOrder bean) {
        orderMapper.updateByPrimaryKeySelective(bean);
        return true;
    }

    @Override
    public Integer save(AuntOrder bean) throws BizException {
        BeanValidator.check(bean);
        AuntOrder order=new AuntOrder();
        BeanUtils.copyProperties(bean,order);
        List<AuntOrder> orderList= orderMapper.getList(order);
        if (!CollectionUtils.isEmpty(orderList)){
            order=orderList.get(0);
            Long time=System.currentTimeMillis();
            Long dbTime=order.getCreatTime().getTime();
            if (time-dbTime>5000){
               orderMapper.insertSelective(bean);
               return bean.getId();
            }
        }
        return order.getId();
    }

    @Override
    public Integer removeById(Integer id) {
        AuntOrder bean=selectById(id);
        bean.setIsDel(1);
        return orderMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public List<AuntOrder> getListPage(Integer page, Integer limit, AuntOrder order) {
        PageHelper.startPage(page, limit);
        return orderMapper.getList(order);
    }


}
