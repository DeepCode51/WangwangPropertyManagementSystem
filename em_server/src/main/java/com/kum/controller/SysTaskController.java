package com.kum.controller;

import com.kum.domain.AjaxResult;
import com.kum.domain.entity.SysJob;
import com.kum.service.SysJobService;
import com.kum.utils.QuartzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version V1.0
 * @Package com.kum.controller
 * @auhter 枯木Kum
 * @date 2021/2/24-8:28 PM
 */

@RestController
@RequestMapping("system/task")
public class SysTaskController {

//    @Autowired
//    private QuartzUtils quartzUtils;
//    @Autowired
//    private SysJobService sysJobService;
//
//    @GetMapping("/list")
//    private AjaxResult list() {
//        return AjaxResult.success(sysJobService.list());
//    }
//
//    @PostMapping("/add")
//    private AjaxResult start(@RequestBody SysJob sysJob) {
//        if (sysJobService.add(sysJob)) {
//            return AjaxResult.success();
//        }
//        return AjaxResult.error("请检查类是否存在或Cron表达式是否正确");
//    }
//
//
//    @PostMapping("/delete")
//    private AjaxResult delete(@RequestBody SysJob sysJob) {
//        if (sysJobService.delete(sysJob)) {
//            return AjaxResult.success();
//        }
//        return AjaxResult.error("并不存在此任务");
//    }
//
//    @PostMapping("/update")
//    private AjaxResult update(@RequestBody SysJob sysJob) {
//        if (sysJobService.update(sysJob)) {
//            return AjaxResult.success();
//        }
//        return AjaxResult.error("并不存在此任务");
//    }


}
