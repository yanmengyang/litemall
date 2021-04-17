package org.linlinjava.litemall.wx.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.Aunt;
import org.springframework.web.bind.annotation.RequestParam;

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
