package com.kum.utils;

import com.alibaba.fastjson.JSON;
import com.kum.domain.AjaxResult;
import com.kum.domain.constant.HttpStatus;
import com.kum.domain.entity.SysLogin;
import com.kum.service.security.LoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version V1.0
 * @Package com.kum.utils
 * @auhter 枯木Kum
 * @date 2021/3/19-4:32 PM
 */
public class RequestUtils {

    /**
     * 获取当前线程的Request对象
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        return getServletRequestAttributes().getRequest();
    }
    /**
     * 获取当前线程的Response对象
     * @return
     */
    public static HttpServletResponse getCurrentResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getServletRequestAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static void setCurrentSessionAttribute(String key,Object val){
        getCurrentRequest().getSession().setAttribute(key,val);
    }
    public static void removeCurrentSessionAttribute(String key){
        getCurrentRequest().getSession().removeAttribute(key);
    }
    public static Object getCurrentSessionAttribute(String key){
        return getCurrentRequest().getSession().getAttribute(key);
    }
    public static LoginUser getCurrentLoginUser(){
        return (LoginUser)getCurrentSessionAttribute(SysLogin.LOGIN_USER_SESSION_KEY);
    }

    /**
     * 删除当前Session
     */
    public static void invalidate(){
        getCurrentRequest().getSession().invalidate();
    }

    public static void NoPeri(){
        ServletRequestAttributes attributes = getServletRequestAttributes();
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源",
                attributes.getRequest().getRequestURI());
        ServletUtils.renderString(attributes.getResponse(), JSON.toJSONString(AjaxResult.error(HttpStatus.UNAUTHORIZED, msg)));
    }

    public static void Forbidden(){
        ServletUtils.renderString(getCurrentResponse(), JSON.toJSONString(AjaxResult.forbidden()));
    }


}
