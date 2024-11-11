package com.kum.service;

import com.kum.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/17-5:06 PM
 */

@Service
public class SmsService {

    @Autowired
    private SmsUtils smsUtils;

    /**
     * 发送维修短信
     * @param templateId 短信模板ID
     * @param phone 目标手机号
     * @param userName 目标用户名
     * @param text 目标维修内容
     * @return  是否发送成功
     */
    public boolean sendRepair(String templateId, String phone, String userName, String text){
        return smsUtils.sendSms(templateId,phone,new String[]{userName,text});
    }




}
