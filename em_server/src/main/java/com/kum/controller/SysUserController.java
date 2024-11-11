package com.kum.controller;

import com.alibaba.fastjson.JSONObject;
import com.kum.domain.AjaxResult;
import com.kum.domain.constant.UserType;
import com.kum.domain.entity.SysUser;
import com.kum.domain.entity.SysUserInfo;
import com.kum.domain.entity.SysUserInfoData;
import com.kum.service.SysUserPlayRecordService;
import com.kum.service.SysUserService;
import com.kum.service.security.LoginUser;
import com.kum.utils.IpUtils;
import com.kum.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @version V1.0
 * @Package com.kum.controller
 * @auhter 枯木Kum
 * @date 2021/3/3-4:36 PM
 */

@RestController
@RequestMapping("/system/user")
public  class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserPlayRecordService sysUserPlayRecordService;
    /**
     * 用户列表
     * @return
     */
    @GetMapping("/list")

    public AjaxResult list() {
        return AjaxResult.success(sysUserService.list());
    }

    @GetMapping("/admin/list")
    public AjaxResult listOfAdmin() {
        return AjaxResult.success(sysUserService.findByUserRole(UserType.ADMIN));
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUser sysUser, HttpServletRequest req) {
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(new Date());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", sysUserService.register(sysUser));
        return AjaxResult.success(jsonObject);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody SysUser sysUser, HttpServletRequest req) {
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(new Date());
        sysUserService.save(sysUser);
        return AjaxResult.success();
    }

    @GetMapping("/isAdmin")
    public AjaxResult role() {
        boolean isAdmin = false;
        try {
            LoginUser loginUser = RequestUtils.getCurrentLoginUser();
            isAdmin = sysUserService.isAdmin(loginUser.getUser().getId());
        } catch (Exception e) {
            RequestUtils.Forbidden();
        }
        return AjaxResult.success(isAdmin);
    }

    /**
     * 业主信息列表
     */
    @GetMapping("/household/list")
    @PreAuthorize("@ps.hasPermi('system:user_householdInfo:list')")
    public AjaxResult HouseholdInfoList() {
        return AjaxResult.success(sysUserService.HouseholdInfoList());
    }

    /**
     * 业主信息的增加或更新
     *
     * @param sysUserInfoData
     */
    @PostMapping("/household/save")
    public AjaxResult HouseholdInfoSave(@RequestBody SysUserInfoData sysUserInfoData) {
        sysUserService.HouseholdInfoSave(sysUserInfoData);
        return AjaxResult.success();
    }

    @PostMapping("/household/delete")
    public AjaxResult HouseholdInfoDelete(@RequestBody SysUserInfo sysUserInfo) {
        sysUserService.HouseholdInfoDelete(sysUserInfo);
        return AjaxResult.success();
    }

    @GetMapping("/pay/record/month")
    public AjaxResult getPayRecordOfMonth(){
        LoginUser user = RequestUtils.getCurrentLoginUser();
        return AjaxResult.success(sysUserPlayRecordService.findByOfMonth(user.getUser().getId()));
    }
    @GetMapping("/pay/fess/{chargeTypeId}")
    public AjaxResult paymentFees(@PathVariable("chargeTypeId") Integer chargeTypeId){
        LoginUser user = RequestUtils.getCurrentLoginUser();
        sysUserPlayRecordService.paymentFees(user.getUser().getId(),chargeTypeId);
        return AjaxResult.success();
    }


}
