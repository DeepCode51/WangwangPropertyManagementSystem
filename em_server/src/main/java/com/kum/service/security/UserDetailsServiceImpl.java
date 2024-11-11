package com.kum.service.security;

import com.kum.domain.entity.SysLogin;
import com.kum.domain.entity.SysUser;
import com.kum.service.SysUserService;
import com.kum.utils.AddressUtils;
import com.kum.utils.IpUtils;
import com.kum.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Package com.kum.service.security
 * @auhter 枯木Kum
 * @date 2021/2/19-5:29 PM
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.loadUserByUsername(username);
        if(sysUser == null){
            return null;
        }
        String ipAddr = IpUtils.getIpAddr();
        return new LoginUser(sysUser);
    }

}
