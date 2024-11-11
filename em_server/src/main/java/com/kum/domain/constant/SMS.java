package com.kum.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @Package com.kum.domain.constant
 * @auhter 枯木Kum
 * @date 2021/2/17-5:03 PM
 */
@Data
public class SMS {
    /**
     *  客户维修通知模板ID
     */
    public static final String CUSTOMER_REPAIR_NOTICE_TEMPLATE_ID = "870986";
    /**
     *  客户维修回执模板ID
     */
    public static final String CUSTOMER_REPAIR_RECEIPT_TEMPLATE_ID = "871061";
    /**
     *  客户投诉通知模板ID
     */
    public static final String CUSTOMER_COMPLAINT_NOTICE_TEMPLATE_ID = "873549";
    /**
     *  客户投诉回执模板ID
     */
    public static final String CUSTOMER_COMPLAINT_RECEIPT_TEMPLATE_ID = "873550";


}
