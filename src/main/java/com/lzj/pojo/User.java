package com.lzj.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author lzj
 * @since 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId (type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名称
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 手机号码
     */
    @TableField("number")
    private Integer number;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 地址信息
     */
    @TableField("address")
    private String address;

    /**
     * 1管理员；0用户
     */
    @TableField("is_admin")
    private Integer isAdmin;

    /**
     * 性别，1-男；0-女
     */
    @TableField("gender")
    private Integer gender;   //性别 ，1-男;0-女

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;   // 驼峰命名



}
