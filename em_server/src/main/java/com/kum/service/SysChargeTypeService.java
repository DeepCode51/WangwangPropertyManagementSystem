package com.kum.service;

import com.kum.domain.entity.SysChargeType;
import com.kum.mapper.SysChargeTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/3/2-8:09 PM
 */

@Service
public class SysChargeTypeService {

    @Resource
    private SysChargeTypeMapper sysChargeTypeMapper;

    public SysChargeType findById(Integer id){
        return sysChargeTypeMapper.selectById(id);
    }

    public List<SysChargeType> list(){
        return sysChargeTypeMapper.selectList(null);
    }

    public void save(SysChargeType sysChargeType){
        if(findById(sysChargeType.getId()) != null){
            sysChargeTypeMapper.updateById(sysChargeType);
            return;
        }
        sysChargeTypeMapper.insert(sysChargeType);
    }

    public boolean delete(String id){
        return sysChargeTypeMapper.deleteById(id) > 0;
    }





}
