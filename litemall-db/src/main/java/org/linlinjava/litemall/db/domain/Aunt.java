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
    private String birthday;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 从业经验时长
     */
    private String experience;

    /**
     * 学历
     */
    private String education;




    /**
     * 富文本简介
     */
    private String des;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 是否上架
     */
    private Integer saleStatus;

    /**
     * 认证状态
     */
    private Integer realStatus;

    /**
     * 属相
     */
    private String sign;

    private String mobile;

    private String idcard;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 标签 对应字典人物标签
     */
    private String flag;
    /**
     * 擅长内容  对应字典主要技能
     */
    private String expertin;

    /**
     * 类别,间隔 对应字典类别矩阵
     */
    private String type;


    private static final long serialVersionUID = 1L;
}