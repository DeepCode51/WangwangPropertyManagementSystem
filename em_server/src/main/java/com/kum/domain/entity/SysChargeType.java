package com.kum.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sun.org.apache.bcel.internal.generic.INEG;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/3/2-8:03 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysChargeType {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 收费类型名称
     */
    private String chargeName;
    /**
     * 收费金额
     */
    private Integer chargeMoney;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
