package org.linlinjava.litemall.db.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty(message = "昵称不能为空")
    private String nickName;

    /**
     * 性别
     */
    @NotEmpty(message = "性别不能为空")
    private String sex;

    /**
     * 头像
     */
    @NotEmpty(message = "头像不能为空")
    private String headUrl;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /**
     * 出生日期
     */
//    @NotEmpty(message = "出生日期不能为空 例2021-01-01")
    private String birthday;

    /**
     * 籍贯
     */
    @NotEmpty(message = "籍贯不能为空")
    private String nativePlace;

    /**
     * 从业经验时长
     */
    @NotEmpty(message = "从业经验时长不能为空")
    private String experience;

    /**
     * 学历
     */
    @NotEmpty(message = "学历不能为空")
    private String education;






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
    @Deprecated
    private Integer saleStatus;

    /**
     * 认证状态
     */
    @Deprecated
    private Integer realStatus;

    /**
     * 属相
     */
//    @NotEmpty(message = "属相不能为空")
    private String sign;

    @NotEmpty(message = "电话不能为空")
    private String mobile;

    private String zodiac;

    private String nation;

    private String constellation;

    private String identityApprove;

    private String skillApprove;

    private String backgroundApprove;

    private String trainApprove;

    private String resumeApprove;

    private String examinationApprove;

    private String insuranceApprove;

    private String selfIntroduction;

    private String jobIntention;

    private String workExperience;

    private String personalPresentation;

    private String idcard;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 标签 对应字典人物标签
     */
    @NotEmpty(message = "人物标签不能为空")
    private String flag;
    /**
     * 擅长内容  对应字典主要技能
     */
    @NotEmpty(message = "交主要技能不能为空")
    private String expertin;

    /**
     * 类别,间隔 对应字典类别矩阵
     */
    @NotEmpty(message = "类别矩阵不能为空")
    private String type;

    /**
     * 富文本简介
     */

    private String des;


    private static final long serialVersionUID = 1L;
}