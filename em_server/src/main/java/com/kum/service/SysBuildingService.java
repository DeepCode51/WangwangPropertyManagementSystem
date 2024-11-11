package com.kum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.entity.SysBuilding;
import com.kum.mapper.SysBuildingMapper;
import com.kum.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/15-5:43 PM
 */

@Service
public class SysBuildingService {

    @Resource
    private SysBuildingMapper sysBuildingMapper;

    public SysBuilding findById(String id){
        return sysBuildingMapper.selectById(id);
    }

    public List<SysBuilding> list(){
        return sysBuildingMapper.selectList(null);
    }

    public List<SysBuilding> list(String colName){
        QueryWrapper<SysBuilding> wrapper = new QueryWrapper<>();
        wrapper.select(StringUtils.format("DISTINCT {}" ,colName));
        return sysBuildingMapper.selectList(wrapper);
    }

    public List<SysBuilding> nameList(){
        return list("name");
    }


    public void save(SysBuilding sysBuilding){
        if(findById(sysBuilding.getId()) != null){
            sysBuildingMapper.updateById(sysBuilding);
            return;
        }
        sysBuildingMapper.insert(sysBuilding);
    }

    public boolean delete(String id){
        return sysBuildingMapper.deleteById(id) > 0;
    }


}
