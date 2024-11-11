package com.kum.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/2/19-4:36 PM
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    /**
     * 用户Id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String fullName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态(0正常 1停用)
     */
    private String status;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户最后登录IP
     */
    private String loginIp;
    /**
     * 用户最后登录时间
     */
    private Date loginDate;

}
