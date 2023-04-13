package org.linlinjava.litemall.wx.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.AuntOrder;

/**
 * @Auther: honglei
 * @Date: 2021/4/11 13:59
 * @Description:
 */
@Data
public class AuntOrderVo extends AuntOrder {
    int page=1;
    int limit=10;
}
