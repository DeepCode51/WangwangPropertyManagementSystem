package com.kum.controller;

import com.alibaba.fastjson.JSONObject;
import com.kum.domain.AjaxResult;
import com.kum.domain.entity.SysComplaint;
import com.kum.domain.entity.SysRepair;
import com.kum.service.SysComplaintService;
import com.kum.service.SysRepairService;
import com.kum.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Package com.kum.controller
 * @auhter 枯木Kum
 * @date 2021/2/18-9:47 PM
 */


@RestController
@RequestMapping("/system/complaint")
public class SysComplaintController {

    @Autowired
    private SysComplaintService sysComplaintService;

    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysComplaintService.list());
    }
    @GetMapping("/list/user")
    public AjaxResult getListByUserId(){
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        return AjaxResult.success(sysComplaintService.findByUserId(userId));
    }
    @PostMapping("/add")
    public AjaxResult addFacilities(@RequestBody SysComplaint sysComplaint) {
        sysComplaintService.add(sysComplaint);
        return AjaxResult.success();
    }
    @PostMapping("/examine")
    public AjaxResult examineFacilities(@RequestBody SysComplaint sysComplaint) {
        sysComplaintService.examine(sysComplaint);
        return AjaxResult.success();
    }
    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if(sysComplaintService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
