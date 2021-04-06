package org.linlinjava.litemall.db.service;
import org.linlinjava.litemall.db.domain.Aunt;


/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层接口  
 */
public interface AuntService {

    Aunt selectById(Integer id);

    boolean updateById(Aunt bean);

    Integer save(Aunt bean);

    boolean removeById(Integer id);
}
