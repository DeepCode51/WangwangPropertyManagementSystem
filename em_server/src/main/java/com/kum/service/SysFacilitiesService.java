package com.kum.service;

import com.kum.domain.entity.SysFacilities;
import com.kum.mapper.SysFacilitiesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/11-6:17 PM
 */

@Service
public class SysFacilitiesService {

    @Resource
    private SysFacilitiesMapper sysFacilitiesMapper;

    public SysFacilities findById(String id){
        return sysFacilitiesMapper.selectById(id);
    }

    public List<SysFacilities> list(){
        return sysFacilitiesMapper.selectList(null);
    }

    public void save(SysFacilities sysFacilities){
        if(findById(sysFacilities.getId()) != null){
            sysFacilitiesMapper.updateById(sysFacilities);
            return;
        }
        sysFacilitiesMapper.insert(sysFacilities);
    }

    public boolean delete(String id){
        return sysFacilitiesMapper.deleteById(id) > 0;
    }



}
