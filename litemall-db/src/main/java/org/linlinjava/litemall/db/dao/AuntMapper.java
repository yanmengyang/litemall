package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.Aunt;

import java.util.List;


/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 数据持久化层 
 */

public interface AuntMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Aunt record);

    int insertSelective(Aunt record);

    Aunt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Aunt record);

    int updateByPrimaryKey(Aunt record);

    List<Aunt> getList(Aunt dict);
}

