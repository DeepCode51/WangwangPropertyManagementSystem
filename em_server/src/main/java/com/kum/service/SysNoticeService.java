package com.kum.service;

import com.kum.domain.entity.SysNotice;
import com.kum.mapper.SysNoticeMapper;
import com.kum.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/11-10:15 PM
 */

@Service
public class SysNoticeService {

    @Resource
    private SysNoticeMapper sysNoticeMapper;

    public SysNotice findById(String id) {
        return sysNoticeMapper.selectById(id);
    }

    public List<SysNotice> list() {
        return sysNoticeMapper.selectList(null);
    }

    public void save(SysNotice sysNotice) {
        if (findById(sysNotice.getId()) != null) {
            sysNoticeMapper.updateById(sysNotice);
            return;
        }
        sysNoticeMapper.insert(sysNotice);
    }

    public boolean delete(String id) {
        return sysNoticeMapper.deleteById(id) > 0;
    }


    public String getNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int noticeLength = list().size();
        String number =String.format("%d-%02d-%02dGG%05d",year, month, day, noticeLength);
        return number;
    }

}
