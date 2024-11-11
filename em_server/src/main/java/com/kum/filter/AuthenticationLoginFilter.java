package com.kum.filter;

import com.alibaba.fastjson.JSON;
import com.kum.domain.AjaxResult;
import com.kum.service.security.LoginUser;
import com.kum.utils.RequestUtils;
import com.kum.utils.ServletUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version V1.0
 * @Package com.kum.filter
 * @auhter 枯木Kum
 * @date 2021/3/26-12:44 PM
 */
public class AuthenticationLoginFilter extends BasicAuthenticationFilter {
    public AuthenticationLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        if (loginUser == null) {
            RequestUtils.Forbidden();
            return;
        }

        super.doFilterInternal(request, response, chain);


    }
}
