package org.linlinjava.litemall.db.service;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;

import java.util.List;


/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层接口  字典表
 */
public interface DictService  {

    Dict getById(Long id);

    Integer updateById(Dict bean);

    Integer save(Dict bean);

    Integer removeById(Long id);

    List<LitemallAdmin> getListPage(Integer page, Integer limit, Dict dict);
}
