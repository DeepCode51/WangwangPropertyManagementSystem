package com.kum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kum.domain.entity.SysUser;
import com.kum.domain.entity.SysUserInfoData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @Package com.kum.mapper
 * @auhter 枯木Kum
 * @date 2021/2/19-4:40 PM
 */

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Results({
            @Result(property = "roomId", column = "id")
    })
    @Select("SELECT u.user_name,u.full_name,u.phone,r.building_name,r.unit_name,r.id FROM sys_user_info ui LEFT JOIN sys_room r ON ui.room_id = r.id LEFT JOIN sys_user u ON ui.user_id = u.id")
    public List<SysUserInfoData> householdInfoList();

    @Select("SELECT u.id,u.user_name,u.full_name,u.`status`,u.phone," +
            "r.role_name FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.user_id " +
            "LEFT JOIN sys_role r ON ur.role_id = r.id WHERE r.id = #{roleId}")
    public List<SysUser> findByUserRole(int roleId);

    @Select("SELECT a.`name` FROM sys_user u  \n" +
            "JOIN sys_user_role ur ON u.id = ur.user_id\n" +
            "JOIN sys_role_acl ra ON ur.role_id = ra.role_id \n" +
            "JOIN sys_acl a ON ra.acl_id = a.id \n" +
            "WHERE u.id = #{userId}")
    public List<String> findUserAcl(String userId);

}
