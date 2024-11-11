package com.kum.controller;

import com.alibaba.fastjson.JSONObject;
import com.kum.domain.AjaxResult;
import com.kum.domain.entity.SysChargeType;
import com.kum.service.SysChargeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Package com.kum.controller
 * @auhter 枯木Kum
 * @date 2021/3/2-8:10 PM
 */

@RestController
@RequestMapping("/system/chargeType")
public class SysChargeTypeController {

    @Autowired
    private SysChargeTypeService sysChargeTypeService;


    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysChargeTypeService.list());
    }


    @PostMapping("/save")
    public AjaxResult saveChargeType(@RequestBody SysChargeType sysChargeType) {
        sysChargeTypeService.save(sysChargeType);
        return AjaxResult.success();
    }
    @PostMapping("/delete")
    public AjaxResult deleteChargeType(@RequestBody JSONObject jsonObject) {
        if(sysChargeTypeService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }






}
