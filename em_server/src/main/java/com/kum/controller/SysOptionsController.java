package com.kum.controller;

import com.kum.domain.AjaxResult;
import com.kum.domain.entity.SysOptions;
import com.kum.service.SysOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Package com.kum
 * @auhter 枯木Kum
 * @date 2021/2/10-4:05 PM
 */

@RestController
@RequestMapping("/system/options")
public class SysOptionsController {

    @Autowired
    private SysOptionsService sysOptionsService;

    @GetMapping("/get/{id}")
    public AjaxResult getOptions(@PathVariable("id") String id) {
        return AjaxResult.success(sysOptionsService.findById(id));
    }

    @PostMapping("/save")
    public AjaxResult saveOptions(@RequestBody SysOptions sysOptions) {
        sysOptionsService.save(sysOptions);
        return AjaxResult.success();
    }


}
