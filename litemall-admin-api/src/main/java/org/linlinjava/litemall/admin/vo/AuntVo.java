package org.linlinjava.litemall.admin.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.Aunt;

/**
 * @Auther: honglei
 * @Date: 2021/4/11 13:59
 * @Description:
 */
@Data
public class AuntVo extends Aunt {
    int page=1;
    int limit=10;
}
