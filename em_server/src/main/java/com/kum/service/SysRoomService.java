package com.kum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.entity.SysRoom;
import com.kum.mapper.SysRoomMapper;
import com.kum.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/14-6:09 PM
 */

@Service
public class SysRoomService {

    @Resource
    private SysRoomMapper sysRoomMapper;

    public SysRoom findById(String id){
        return sysRoomMapper.selectById(id);
    }

    public List<SysRoom> list(){
        return sysRoomMapper.selectList(null);
    }
    public List<SysRoom> list(String colName,String buildingName){
        QueryWrapper<SysRoom> wrapper = new QueryWrapper<>();
        wrapper
                .select(StringUtils.format("DISTINCT {}" ,colName))
                .eq("building_name",buildingName);
        return sysRoomMapper.selectList(wrapper);
    }

    public List<SysRoom> unitNameList(String buildingName){
        return list("unit_name",buildingName);
    }
    public void save(SysRoom sysRoom){
        if(findById(sysRoom.getId()) != null){
            sysRoomMapper.updateById(sysRoom);
            return;
        }
        sysRoomMapper.insert(sysRoom);
    }

    public boolean delete(String id){
        return sysRoomMapper.deleteById(id) > 0;
    }







}
