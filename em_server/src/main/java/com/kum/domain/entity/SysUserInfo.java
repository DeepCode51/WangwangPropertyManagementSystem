package com.kum.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/3/3-11:42 AM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserInfo {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;



}
