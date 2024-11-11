package com.kum.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.constant.Option;
import com.kum.domain.constant.SMS;
import com.kum.domain.entity.SysRepair;
import com.kum.mapper.SysRepairMapper;
import com.kum.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/18-9:44 PM
 */

@Service
public class SysRepairService {

    @Resource
    private SysRepairMapper sysRepairMapper;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SysOptionsService sysOptionsService;

    public SysRepair findById(Integer id) {
        return sysRepairMapper.selectById(id);
    }

    public List<SysRepair> list() {
        return sysRepairMapper.selectList(null);
    }

    /**
     * 根据用户ID进行查询
     * @param userId
     * @return
     */
    public List<SysRepair> findByUserId(String userId){
        QueryWrapper<SysRepair> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return sysRepairMapper.selectList(wrapper);
    }


    public void save(SysRepair sysRepair) {
        if (findById(sysRepair.getId()) != null) {
            sysRepairMapper.updateById(sysRepair);
            return;
        }
        sysRepairMapper.insert(sysRepair);
    }

    /**
     * 添加报修
     * @param sysRepair
     */
    public void add(SysRepair sysRepair) {
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        sysRepair.setUserId(userId);
        JSONObject jsonObject = JSON.parseObject(sysOptionsService.findById(Option.rq_repair_info).getText().toString());
        String contact_phone = jsonObject.getString(Option.rq_repair_info_contact_phone);
        save(sysRepair);
        smsService.sendRepair(
                SMS.CUSTOMER_REPAIR_NOTICE_TEMPLATE_ID,
                contact_phone,
                sysRepair.getUserName(),
                sysRepair.getTitle());
    }

    /**
     * 审核报修
     * @param sysRepair
     */
    public void examine(SysRepair sysRepair){
        sysRepair.setIsExamine(1);
        save(sysRepair);
        smsService.sendRepair(
                SMS.CUSTOMER_REPAIR_RECEIPT_TEMPLATE_ID,
                sysRepair.getPhone(),
                sysRepair.getUserName(),
                sysRepair.getTitle());
    }
    public boolean delete(String id) {
        return sysRepairMapper.deleteById(id) > 0;
    }


}
