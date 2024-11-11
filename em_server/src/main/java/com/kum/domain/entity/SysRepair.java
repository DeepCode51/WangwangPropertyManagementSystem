package com.kum.domain.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/2/18-9:42 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRepair {
    @TableId(type = IdType.AUTO)

    private Integer id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户报修单标题
     */
    private String title;
    /**
     * 用户联系方式
     */
    private String phone;
    /**
     * 用户住址
     */
    private String address;
    /**
     * 申请时间
     */
    private Date date;
    /**
     * 用户报修单详细内容
     */
    private String text;
    /**
     * 管理员是否审核
     */
    private Integer isExamine;
    /**
     * 管理员处理回执数据
     */
    private String examineData;
}
