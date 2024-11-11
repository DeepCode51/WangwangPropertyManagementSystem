package com.kum.domain.funInterface;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @version V1.0
 * @Package com.kum.domain.funInterface
 * @auhter 枯木Kum
 * @date 2021/2/14-9:13 PM
 */
@FunctionalInterface
public interface CustomCellWrite {
    void operation(WriteSheetHolder writeSheetHolder, Cell cell, Boolean isHead);
}
