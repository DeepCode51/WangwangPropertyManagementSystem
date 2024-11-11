package com.kum.service;

import com.kum.domain.entity.SysJob;
import com.kum.mapper.SysJobMapper;
import com.kum.utils.QuartzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/24-8:25 PM
 */

//@Service
public class SysJobService {

//    @Resource
//    private SysJobMapper sysJobMapper;
//    @Autowired
//    private QuartzUtils quartzUtils;
//
//    public SysJob findById(String id){
//        return sysJobMapper.selectById(id);
//    }
//
//    public List<SysJob> list(){
//        return sysJobMapper.selectList(null);
//    }
//
//    public boolean add(SysJob sysJob){
//        if(sysJobMapper.insert(sysJob) > 0){
//            return quartzUtils.add(sysJob);
//        }
//        return false;
//    }
//
//    public boolean update(SysJob sysJob){
//        if(findById(sysJob.getId()) != null){
//            int i = sysJobMapper.updateById(sysJob);
//            return i > 0 && quartzUtils.update(sysJob);
//        }
//        return false;
//    }
//
//    public boolean delete(SysJob sysJob){
//        int i = sysJobMapper.deleteById(sysJob.getId());
//        return i > 0 && quartzUtils.delete(sysJob.getName(),sysJob.getGroupName());
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//




}
