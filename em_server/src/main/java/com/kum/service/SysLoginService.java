package com.kum.service;

import com.google.code.kaptcha.Constants;
import com.kum.domain.entity.SysLogin;
import com.kum.domain.entity.SysUser;
import com.kum.service.security.LoginUser;
import com.kum.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/3/19-12:46 PM
 */

@Service
public class SysLoginService {

    @Autowired
    private SysUserService sysUserService;
    @Resource
    private AuthenticationManager authenticationManager;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SysUser login(SysLogin sysLogin){
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(sysLogin.getUserName(), sysLogin.getPassWord()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> userAcl = sysUserService.findUserAcl(loginUser.getUser().getId());
        loginUser.setPermissions(userAcl);
        //将登录信息存储到Session中
        setUserInfoToSession(loginUser);
        //删除验证码信息
        removeLoginCode();
        return loginUser.getUser();
    }

    public boolean checkCode(String code){
        HttpServletRequest request = RequestUtils.getCurrentRequest();
        System.out.println(request.getSession().getId());
        String sCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(sCode == null || !code.equals(sCode)){
            return false;
        }
        return true;
    }

    public void setUserInfoToSession(LoginUser loginUser){
        RequestUtils.setCurrentSessionAttribute(SysLogin.LOGIN_USER_SESSION_KEY,loginUser);
    }
    public void removeLoginCode(){
        RequestUtils.removeCurrentSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
    }


}
