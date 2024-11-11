package com.kum.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.kum.domain.entity.SysRoom;
import com.kum.domain.funInterface.CustomCellWrite;
import com.kum.handler.CustomCellWriteHandler;
import com.kum.service.SysRoomService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @version V1.0
 * @Package com.kum.utils.excel
 * @auhter 枯木Kum
 * @date 2021/2/14-10:01 PM
 */
@Component
public class ExcelUtils {


    /**
     * 创建对应数据的表格并传递给前端
     *
     * @param response        响应
     * @param fileName        文件名称
     * @param clazz           目标数据实体类
     * @param data            数据集合
     * @param customCellWrite 自定义的单元格处理类
     */
    public void download(HttpServletResponse response, String fileName, Class clazz, List data, CustomCellWrite customCellWrite) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(response.getOutputStream(), clazz)
                    .sheet("sheet1");
            // 自定义单元格处理器如果不为空就加入拦截器
            if (customCellWrite != null) {
                excelWriterSheetBuilder = excelWriterSheetBuilder.
                        registerWriteHandler(new CustomCellWriteHandler(customCellWrite));
            }
            excelWriterSheetBuilder.doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
