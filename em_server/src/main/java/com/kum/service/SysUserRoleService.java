package com.kum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.entity.SysRole;
import com.kum.domain.entity.SysUserRole;
import com.kum.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/3/20-9:23 AM
 */

@Service
public class SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleService sysRoleService;

    public SysUserRole findByUserId(String userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(wrapper);
        if(sysUserRoles.size() == 0){
            return null;
        }
        return sysUserRoles.get(0);


    }

    /**
     * 通过UserId查询对应的角色信息
     * @param userId
     * @return
     */
    public SysRole findUserRole(String userId) {
        SysUserRole byUserID = findByUserId(userId);
        System.out.println(byUserID);
        return sysRoleService.findById(byUserID.getRoleId());
    }

    public void save(SysUserRole sysUserRole) {
        if (findByUserId(sysUserRole.getUserId()) == null) {
            sysUserRoleMapper.insert(sysUserRole);
            return;
        }
        sysUserRoleMapper.updateById(sysUserRole);
    }


}
