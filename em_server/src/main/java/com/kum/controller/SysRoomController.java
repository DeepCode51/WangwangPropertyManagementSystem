package com.kum.controller;

import com.alibaba.fastjson.JSONObject;
import com.kum.domain.AjaxResult;
import com.kum.domain.entity.SysFacilities;
import com.kum.domain.entity.SysRoom;
import com.kum.service.SysRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Package com.kum.controller
 * @auhter 枯木Kum
 * @date 2021/2/14-6:11 PM
 */

@RestController
@RequestMapping("/system/room")
public class SysRoomController {

    @Autowired
    private SysRoomService sysRoomService;

    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysRoomService.list());
    }

    @GetMapping("unitName/list/{buildingName}")
    public AjaxResult getUnitNameList(@PathVariable("buildingName") String buildingName){
        return AjaxResult.success(sysRoomService.unitNameList(buildingName));
    }


    @PostMapping("/save")
    public AjaxResult saveFacilities(@RequestBody SysRoom sysRoom) {
        sysRoomService.save(sysRoom);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if(sysRoomService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }


}
