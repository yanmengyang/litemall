package org.linlinjava.litemall.db.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * litemall_aunt
 * @author 
 */
@Data
public class Aunt implements Serializable {
    private Integer id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String headUrl;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 从业经验时长
     */
    private String experience;

    /**
     * 擅长内容多个，区分，来源于字典
     */
    private String expertin;

    /**
     * 学历
     */
    private String education;

    /**
     * 简介
     */
    private String des;

    /**
     * 审核状态
     */
    private Boolean auditStatus;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 是否上架
     */
    private Boolean saleStatus;

    /**
     * 认证状态
     */
    private Boolean realStatus;

    /**
     * 属相
     */
    private String sign;

    private static final long serialVersionUID = 1L;
}