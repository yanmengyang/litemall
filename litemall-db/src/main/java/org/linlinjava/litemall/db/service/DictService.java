package org.linlinjava.litemall.db.service;
import org.linlinjava.litemall.db.domain.Dict;


/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层接口  字典表
 */
public interface DictService  {

    Dict getById(Long id);

    boolean updateById(Dict bean);

    Integer save(Dict bean);

    boolean removeById(Long id);
}
