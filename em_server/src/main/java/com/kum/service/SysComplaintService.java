package com.kum.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kum.domain.constant.Option;
import com.kum.domain.constant.SMS;
import com.kum.domain.entity.SysComplaint;
import com.kum.mapper.SysComplaintMapper;
import com.kum.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/18-9:44 PM
 */

@Service
public class SysComplaintService {

    @Resource
    private SysComplaintMapper sysComplaintMapper;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SysOptionsService sysOptionsService;

    public SysComplaint findById(Integer id) {
        return sysComplaintMapper.selectById(id);
    }

    /**
     * 根据用户ID进行查询
     * @param userId
     * @return
     */
    public List<SysComplaint> findByUserId(String userId){
        QueryWrapper<SysComplaint> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return sysComplaintMapper.selectList(wrapper);
    }

    public List<SysComplaint> list() {
        return sysComplaintMapper.selectList(null);
    }

    public void save(SysComplaint sysComplaint) {
        if (findById(sysComplaint.getId()) != null) {
            sysComplaintMapper.updateById(sysComplaint);
            return;
        }
        sysComplaintMapper.insert(sysComplaint);
    }

    /**
     * 添加投诉信息
     * <p>添加投诉信息，并发送短信通知物业管理人员</p>
     * @param sysComplaint
     */
    public void add(SysComplaint sysComplaint) {
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        sysComplaint.setUserId(userId);
        sysComplaint.setDate(new Date());
        JSONObject jsonObject = JSON.parseObject(sysOptionsService.findById(Option.rq_repair_info).getText().toString());
        String contactPhone = jsonObject.getString(Option.rq_repair_info_contact_phone);
        save(sysComplaint);
        smsService.sendRepair(
                SMS.CUSTOMER_COMPLAINT_NOTICE_TEMPLATE_ID,
                contactPhone,
                sysComplaint.getUserName(),
                sysComplaint.getTitle());

    }
    public void examine(SysComplaint sysComplaint){
        sysComplaint.setIsExamine(1);
        save(sysComplaint);
        smsService.sendRepair(
                SMS.CUSTOMER_COMPLAINT_RECEIPT_TEMPLATE_ID,
                sysComplaint.getPhone(),
                sysComplaint.getUserName(),
                sysComplaint.getTitle());
    }
    public boolean delete(String id) {
        return sysComplaintMapper.deleteById(id) > 0;
    }


}
