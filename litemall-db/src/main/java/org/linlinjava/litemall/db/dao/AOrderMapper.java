package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.AuntOrder;

import java.util.List;

public interface AOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuntOrder record);

    int insertSelective(AuntOrder record);

    AuntOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuntOrder record);

    int updateByPrimaryKey(AuntOrder record);

    List<AuntOrder> getList(AuntOrder order);
}