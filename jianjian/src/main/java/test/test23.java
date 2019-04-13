package test;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class test23 {

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow((short) 0);
        Cell cell = row.createCell((short) 0);
        cell.setCellValue("调色板");

        // 创建一个单元格样式
        XSSFCellStyle style = workbook.createCellStyle();
        cell.setCellStyle(style);

        /*****************************使用默认颜色**************************************************/
        // 填充色
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 字体颜色
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        /**************************************************************************************/

        /*****************************自定义颜色**************************************************/
        XSSFColor color = new XSSFColor(new Color(255, 0, 0));
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        /**************************************************************************************/


        File file = new File("C:/Users/Administrator/Desktop/joe/result5.xlsx");
        try(FileOutputStream fOut = new FileOutputStream(file)){

            // 将创建的内容写到指定的Excel文件中
            workbook.write(fOut);
            fOut.flush();
            fOut.close();// 操作结束，关闭文件
            System.out.println("Excel文件创建成功！\nExcel文件的存放路径为："
                    + file.getAbsolutePath());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
