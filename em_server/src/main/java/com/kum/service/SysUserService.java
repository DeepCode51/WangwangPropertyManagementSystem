package com.kum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.constant.UserType;
import com.kum.domain.entity.*;
import com.kum.mapper.SysUserMapper;
import com.kum.utils.RequestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/19-4:41 PM
 */

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SysUser loadUserByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        List<SysUser> list = sysUserMapper.selectList(wrapper);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);

    }

    /**
     * 用户列表
     * @return
     */
    public List<SysUser> list() {
        return sysUserMapper.selectList(null);
    }

    public List<SysUser> findByUserRole(int roleId) {
        return sysUserMapper.findByUserRole(roleId);
    }

    public SysUser findById(String id) {
        return sysUserMapper.selectById(id);
    }

    public void save(SysUser sysUser) {
        if (sysUser.getId() != null) {
            sysUserMapper.updateById(sysUser);
            return;
        }
        sysUserMapper.insert(sysUser);
    }

    /**
     * 判断当前用户是不是管理员
     * @param userId
     * @return
     */
    public boolean isAdmin(String userId) {
        try {
            SysRole userRole = sysUserRoleService.findUserRole(userId);
            System.out.println(userRole);
            if (userRole.getRoleKey().equals(UserType.ROLE_ADMIN_KEY)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 注册账号
     *
     * @param sysUser
     * @return 返回用户ID
     */
    public String register(SysUser sysUser) {
        sysUser.setPassword(
                bCryptPasswordEncoder.encode(sysUser.getPassword()));
        save(sysUser);
        String userId = loadUserByUsername(sysUser.getUserName()).getId();
        sysUserRoleService.save(new SysUserRole().toBuilder().userId(userId).roleId(1).build());
        return userId;

    }

    public List<String> findUserAcl(String userId) {
        List<String> userAcl = sysUserMapper.findUserAcl(userId);
        return userAcl;
    }

    /**
     * 重置当前用户的密码
     * @param newPassWord
     */
    public void resetPwd(String newPassWord){
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        SysUser sysUser = findById(userId);
        sysUser.setPassword(bCryptPasswordEncoder.encode(newPassWord));
        save(sysUser);
    }

    /**
     * 重置用户密码
     * @param sysUser
     */
    public void resetPwd(SysUser sysUser){
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        save(sysUser);
    }



    public List<SysUserInfoData> HouseholdInfoList() {
        return sysUserMapper.householdInfoList();
    }

    public void HouseholdInfoSave(SysUserInfoData sysUserInfoData) {
        //更新用户数据
        SysUser sysUser = findById(sysUserInfoData.getUserId());
        if (sysUser != null) {
            sysUser.setFullName(sysUserInfoData.getFullName());
            sysUser.setPhone(sysUserInfoData.getPhone());
            save(sysUser);
        }
        //如果该房间已被其他住户绑定，那么就删除现绑定者
        SysUserInfo byColumnVal = sysUserInfoService.findByColumnVal("room_id", sysUserInfoData.getRoomId());
        if (byColumnVal != null) {
            sysUserInfoService.delete(byColumnVal.getId());
        }
        //处理数据并保存
        SysUserInfo sysUserInfo = new SysUserInfo();
        BeanUtils.copyProperties(sysUserInfoData, sysUserInfo);
        System.out.println(sysUserInfo.toString());
        sysUserInfoService.save(sysUserInfo);
    }

    public void HouseholdInfoDelete(SysUserInfo sysUserInfo) {
    }
}
