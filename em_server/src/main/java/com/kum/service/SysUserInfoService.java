package com.kum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.entity.SysUserInfo;
import com.kum.mapper.SysUserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/3/3-11:44 AM
 */

@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    public SysUserInfo findById(String id){
        return sysUserInfoMapper.selectById(id);
    }

    /**
     * 通过匹配列名进行寻找
     * @param column
     * @param val
     */
    public SysUserInfo findByColumnVal(String column, String val){
        QueryWrapper<SysUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq(column,val);
        return sysUserInfoMapper.selectOne(wrapper);
    }

    public List<SysUserInfo> list(){
        return sysUserInfoMapper.selectList(null);
    }

    public void save(SysUserInfo sysUserInfo){
        if(findById(sysUserInfo.getId()) != null){
            sysUserInfoMapper.updateById(sysUserInfo);
            return;
        }
        sysUserInfoMapper.insert(sysUserInfo);
    }

    public boolean delete(String id){
        return sysUserInfoMapper.deleteById(id) > 0;
    }







}
