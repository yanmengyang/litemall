package org.linlinjava.litemall.db.service;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.AuntOrder;
import org.linlinjava.litemall.db.exection.BizException;

import java.util.List;


/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层接口  
 */
public interface AuntOrderService {

    AuntOrder selectById(Integer id);

    boolean updateById(AuntOrder bean);

    Integer save(AuntOrder bean) throws BizException;

    Integer removeById(Integer id);

    List<AuntOrder> getListPage(Integer page, Integer limit, AuntOrder order);

    AuntOrder findBySn(String orderSn);
}
