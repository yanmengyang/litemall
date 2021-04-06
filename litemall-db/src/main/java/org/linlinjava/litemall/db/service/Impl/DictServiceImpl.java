package org.linlinjava.litemall.db.service.Impl;

import org.linlinjava.litemall.db.dao.DictMapper;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.service.DictService;
import org.springframework.stereotype.Service;

/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层 字典表
 */
@Service
public class DictServiceImpl  implements DictService {
    @Override
    public Dict getById(Long id) {
        return null;
    }

    @Override
    public boolean updateById(Dict bean) {
        return false;
    }

    @Override
    public Integer save(Dict bean) {
        return null;
    }

    @Override
    public boolean removeById(Long id) {
        return false;
    }
}

