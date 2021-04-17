package org.linlinjava.litemall.db.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * aunt_order
 * @author 
 */
@Data
public class AuntOrder implements Serializable {
    private Integer id;


    private String auntName;

    private Integer auntId;

    private String auntMobile;

    private Integer userId;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   // @JSONField(format="yyyy-MM-dd")
    private String endTime;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 调度状态
     */
    private Integer dispatchStatus;

    /**
     * 备注
     */
    private String remark;

    private Date creatTime;

    private static final long serialVersionUID = 1L;
}