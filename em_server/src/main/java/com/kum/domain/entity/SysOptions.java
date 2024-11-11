package com.kum.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @Package com.kum.domain
 * @auhter 枯木Kum
 * @date 2021/2/10-3:51 PM
 */

@Data
@NoArgsConstructor
public class SysOptions {
    /**
     * 小区基本信息
     */
    public static final String RQ_INFO = "rq_info";

    private String id;
    private Object text;

    public SysOptions(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
