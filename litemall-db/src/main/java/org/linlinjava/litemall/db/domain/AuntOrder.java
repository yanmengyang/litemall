package org.linlinjava.litemall.db.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * aunt_order
 * @author 
 */
@Data
public class AuntOrder implements Serializable {
    private Integer id;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 联系地址
     */
    private String addr;

    /**
     * 电话号
     */
    private String mobile;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 支付状态
     */
    private Boolean payStatus;

    /**
     * 订单状态
     */
    private Short status;

    /**
     * 调度状态
     */
    private Boolean dispatchStatus;

    /**
     * 备注
     */
    private String remark;

    private Date creatTime;

    private static final long serialVersionUID = 1L;
}