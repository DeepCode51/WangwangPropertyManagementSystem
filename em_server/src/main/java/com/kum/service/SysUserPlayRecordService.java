package com.kum.service;

import com.alibaba.fastjson.JSONObject;
import com.kum.domain.entity.SysChargeType;
import com.kum.domain.entity.SysUserPlayRecord;
import com.kum.mapper.SysUserPlayRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/3/21-7:58 PM
 */

@Service
public class SysUserPlayRecordService {

    @Resource
    private SysUserPlayRecordMapper sysUserPlayRecordMapper;
    @Autowired
    private SysChargeTypeService sysChargeTypeService;

    public List<SysUserPlayRecord> list() {
        return sysUserPlayRecordMapper.selectList(null);
    }


    public List<JSONObject> findByOfMonth(String userId) {
        List<JSONObject> result = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        int currentMonth = instance.get(Calendar.MONTH) + 1;
        List<SysChargeType> list = sysChargeTypeService.list();
        list.forEach(item -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chargeTypeId", item.getId());
            jsonObject.put("chargeName", item.getChargeName());
            jsonObject.put("chargeMoney", item.getChargeMoney());
            jsonObject.put("createTime", item.getCreateTime());
            boolean isPayment = false;
            if (findByChargeTypeIdAndNowMonth(userId,item.getId(),currentMonth)) {
                isPayment = true;
            }
            jsonObject.put("isPayment", isPayment);
            result.add(jsonObject);

        });

        return result;

    }

    public boolean findByChargeTypeIdAndNowMonth(String userId, int chargeTypeId, int month) {
        List<JSONObject> list = sysUserPlayRecordMapper.findByChargeTypeIdAndNowMonth(userId, chargeTypeId, month);
        return list.size() > 0;

    }

    public void paymentFees(String userId,Integer chargeTypeId){
        SysUserPlayRecord playRecord = new SysUserPlayRecord()
                .builder()
                .userId(userId)
                .chargeTypeId(chargeTypeId)
                .createTime(new Date())
                .build();
        sysUserPlayRecordMapper.insert(playRecord);
    }


}
