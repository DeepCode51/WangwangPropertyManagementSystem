package com.kum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/3/19-12:43 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogin {

    /**
     * 用户在Session之中存储的key名称
     */
    public static final String LOGIN_USER_SESSION_KEY = "user_permissions";

    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String passWord;
    /**
     * 验证码
     */
    private String code;
//    /**
//     * 登录令牌
//     * <p>根据令牌进行请求验证码,然后登录之时进行匹配令牌对应的验证码</p>
//     */
//    private String token;

}




