package org.linlinjava.litemall.db.service;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.Dict;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.exection.BizException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author honglei
 * @version 1.0
 * @time: 2021/3/26 0026 15:32
 * @Description 服务实现层接口  
 */
public interface AuntService {

    Aunt selectById(Integer id);

    boolean updateById(Aunt bean);

    Integer save(Aunt bean) throws BizException;

    boolean removeById(Integer id);

    Integer insertBatch(MultipartFile file);

    List<Aunt> getListPage(Integer page, Integer limit, Aunt dict);
}
