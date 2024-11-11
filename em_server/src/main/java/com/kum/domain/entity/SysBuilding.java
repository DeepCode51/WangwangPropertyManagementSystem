package com.kum.domain.entity;

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
 * @date 2021/2/15-5:40 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysBuilding {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 楼宇名称
     */
    private String name;
    /**
     * 楼宇层数
     */
    private Integer layers;
    /**
     * 楼宇高度
     */
    private Integer height;
    /**
     * 楼宇面积
     */
    private Integer area;
    /**
     * 楼宇建成时间
     */
    private Date date;

}
