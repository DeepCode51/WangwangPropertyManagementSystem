package com.kum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @Package com.kum.domain.entity
 * @auhter 枯木Kum
 * @date 2021/3/20-3:26 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {
    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色Key
     */
    private String roleKey;


}
