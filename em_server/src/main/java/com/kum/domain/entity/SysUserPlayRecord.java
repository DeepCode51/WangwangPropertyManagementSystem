package com.kum.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/3/21-7:43 PM
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserPlayRecord {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 费用类型ID
     */
    private Integer chargeTypeId;
    /**
     * 创建时间
     */
    private Date createTime;


}
