package org.linlinjava.litemall.wx.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.Dict;

/**
 * @description:
 * @author: honglei
 * @time: 2021/4/12 0012 17:06
 */
@Data
public class DictVo extends Dict {
    int page=1;
    int limit=10;
}
