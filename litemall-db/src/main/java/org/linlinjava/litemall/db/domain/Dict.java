package org.linlinjava.litemall.db.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * litemall_dict
 * @author 
 */
@Data
public class Dict implements Serializable {
    /**
     * 字典主键ID
     */
    private Integer id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer dictSequence;

    /**
     * 上级字典ID
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 租户编码
     */
    private String tenantCode;

    private static final long serialVersionUID = 1L;
}