package com.kum.service;

import com.kum.domain.entity.SysRoom;
import com.kum.domain.entity.SysUserInfo;
import com.kum.domain.entity.SysUserInfoData;
import com.kum.utils.excel.ExcelUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @version V1.0
 * @Package com.kum.service
 * @auhter 枯木Kum
 * @date 2021/2/15-5:15 PM
 */

@Service
public class SysExcelService {

    @Autowired
    private ExcelUtils excelUtils;

    @Autowired
    private SysRoomService sysRoomService;
    @Autowired
    private SysUserService sysUserService;

    public void downloadSysRooms(HttpServletResponse response) {
        excelUtils.download(response, "room", SysRoom.class, sysRoomService.list(), (writeSheetHolder, cell, isHead) -> {
            if (!isHead && cell.getColumnIndex() == 8) {
                Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                short color = cell.getStringCellValue().equals("已有住户")
                        ? IndexedColors.RED.getIndex() : IndexedColors.BLUE.getIndex();
                font.setColor(color);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
            }
        });
    }

    public void downloadSysHouseholdInfos(HttpServletResponse response){
        excelUtils.download(response, "住户信息", SysUserInfoData.class, sysUserService.HouseholdInfoList(),null);
    }




}
