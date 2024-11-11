package com.kum.service;

import com.kum.mapper.SysOptionsMapper;
import com.kum.domain.entity.SysOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/10-4:00 PM
 */

@Service
public class SysOptionsService {

    @Resource
    private SysOptionsMapper sysOptionsMapper;

    public SysOptions findById(String id){
        return sysOptionsMapper.selectById(id);
    }

    public void save(SysOptions sysOptions){
        if(findById(sysOptions.getId()) != null){
            sysOptionsMapper.updateById(sysOptions);
            return;
        }
        sysOptionsMapper.insert(sysOptions);
    }


}
