package com.kum.controller;

import com.alibaba.fastjson.JSONObject;
import com.kum.domain.AjaxResult;
import com.kum.domain.entity.SysNotice;
import com.kum.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Package com.kum.controller
 * @auhter 枯木Kum
 * @date 2021/2/11-10:17 PM
 */

@RestController
@RequestMapping("/system/notice")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysNoticeService.list());
    }


    @PostMapping("/save")
    public AjaxResult saveFacilities(@RequestBody SysNotice sysNotice) {
        sysNoticeService.save(sysNotice);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if(sysNoticeService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }





}
