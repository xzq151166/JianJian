package Util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import pojo.ExcelVo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static Map<String, Object> getStreamAndFilename(String fileUrl) throws FileNotFoundException {
        Map<String, Object> resultMap = new HashMap<>();
        File file = new File(fileUrl);
        FileInputStream fis = new FileInputStream(file);
        String fileName = fileUrl.split("\\\\")[fileUrl.split("\\\\").length - 1];
        resultMap.put("stream", fis);
        resultMap.put("fileName", fileName);
        return resultMap;
    }


    /*获取到excel准备解析*/
    public static Map<String, Object> parseExcel(Map<String, Object> streamAndFilename) throws IOException {
        //String fileName = (String) streamAndFilename.get("fileName");
        FileInputStream fis = (FileInputStream) streamAndFilename.get("stream");
        /*检验文件名是否符合规范，校验方法，后续再写*/
        if (false) {
            return null;
        }
        return getExcelInfo(fis);
    }

    private static Map<String, Object> getExcelInfo(FileInputStream fis) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        Workbook wb = new XSSFWorkbook(fis);
        List<ExcelVo> dataList = new ArrayList<>();
        Sheet bSheet = wb.getSheetAt(0);
        Sheet sSheet = wb.getSheetAt(1);
        resultMap.put("bList", readvalue(bSheet, "bank"));
        resultMap.put("sList", readvalue(sSheet, "store"));
        return resultMap;
    }


    private static List<ExcelVo> readvalue(Sheet sheet, String dataType) {
        List<ExcelVo> dataList = new ArrayList<>();
        //总共有多少行
        int rows = sheet.getPhysicalNumberOfRows();
        //从第几行开始
        int firstRow = sheet.getFirstRowNum();
        //单元格
        int totallCells = 0;
        if (rows > +1 && sheet.getRow(firstRow) != null) {
            totallCells = sheet.getRow(firstRow).getPhysicalNumberOfCells();
        }

        //循环行数获取数据
        for (int r = firstRow; r <= rows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            ExcelVo excelVo = new ExcelVo();
            for (int c = 0; c < totallCells; c++) {
                Cell cell = row.getCell(c);
                switch (c) {
                    case 0:
                        //编码，也是数字？？
                        if (dataType.equalsIgnoreCase("bank")) {
                            //银行交易时间
                            excelVo.setTime((String) readUtil(cell));
                            excelVo.setDataResource("bank");
                        } else {
                            //门店对账时间
                            excelVo.setCheckTime((String) readUtil(cell));
                            excelVo.setDataResource("store");
                        }
                        break;
                    case 1:
                        if (dataType.equalsIgnoreCase("bank")) {
                            //银行流水凭证号码
                            excelVo.setCredential((String) readUtil(cell));
                        } else {
                            //门店名称
                            excelVo.setStore((String) readUtil(cell));
                        }
                        break;
                    case 2:
                        if (dataType.equalsIgnoreCase("bank")) {
                            //银行流水门店编码
                            excelVo.setCode((String) readUtil(cell));
                        } else {
                            //门店编码
                            excelVo.setCode((String) readUtil(cell));
                        }
                        break;
                    case 3:
                        if (dataType.equalsIgnoreCase("bank")) {
                            //银行流水
                            excelVo.setStore((String) readUtil(cell));
                        } else {
                            //门店凭证
                            excelVo.setCredential((String) readUtil(cell));
                        }
                        break;
                    case 4:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setAmount((String) readUtil(cell));
                        } else {
                            //门店短摘要
                            excelVo.setSummary((String) readUtil(cell));
                        }
                        break;
                    case 5:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setAccountName((String) readUtil(cell));
                        } else {
                            //门店摘要
                            excelVo.setBigSummary((String) readUtil(cell));

                        }
                        break;
                    case 6:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setAccount((String) readUtil(cell));
                        } else {
                            //门店金额
                            excelVo.setAmount((String) readUtil(cell));

                        }
                        break;
                    case 7:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setBankType((String) readUtil(cell));
                        } else {
                            //门店凭证日期
                            excelVo.setCredentialDate((String) readUtil(cell));
                        }
                        break;
                    case 8:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setAccountDate((String) readUtil(cell));
                        } else {
                            break;
                        }
                        break;
                    case 9:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setSummary((String) readUtil(cell));
                        } else {
                            break;
                        }
                        break;
                    case 10:
                        if (dataType.equalsIgnoreCase("bank")) {
                            excelVo.setRemark((String) readUtil(cell));
                        } else {
                            //门店凭证日期
                            break;
                        }
                        break;
                }
            }

            excelVo.setIsResolved("N");
            excelVo.setRowNum(r);
            dataList.add(excelVo);
        }
        return dataList;
    }

    /**
     * 读取数据的公共类方法
     */

    private static Object readUtil(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    /**
     * 写入数据的公共类方法
     */

    public static void writeUtil(String excelValue, Row row, int cellNum, String dataType, String isDraw,XSSFWorkbook workbook) {

        if(StringUtils.isEmpty(excelValue)){
            excelValue ="";
        }
        Cell cell = row.createCell(cellNum);
        switch (dataType) {
            case "String":
                cell.setCellValue(excelValue);
                break;

            case "BigDecimal":
                excelValue = excelValue ==""?"0":excelValue;
                if(excelValue=="0"){
                    cell.setCellValue("");
                    break;
                }
                cell.setCellValue(Double.parseDouble(excelValue));
                break;

            default:
                break;
        }

        if (isDraw.equalsIgnoreCase("Y")) {
            ExcelUtil.getMatchCellStyle(workbook,cell,"G");
        }else{
            ExcelUtil.getNoMatchCellStyle(workbook,cell);
        }

    }


    /**
     * 去掉换行符号和空格
     */

    public static String removeBadStr(String str) {
        if(StringUtils.isEmpty(str)){
            str="";
        }
        return str.trim().replace("\n", "").replace("\r", "");
    }

    /**
     * 差异相减得出结果
     *
     * @param bAmount
     * @param sAmount
     * @return
     */
    public static String subtractAmount(String bAmount, String sAmount,Cell cell,XSSFWorkbook workbook) {

        BigDecimal sDecimal = sAmount!=null?new BigDecimal(sAmount):new BigDecimal("0");
        BigDecimal bDecimal = bAmount!=null?new BigDecimal(bAmount):new BigDecimal("0");
        BigDecimal subtract = sDecimal.subtract(bDecimal);
        getMatchCellStyle(workbook,cell,"B");
        return subtract.toString();
    }


    /**
     * 渲染成绿色
     *
     * @param workbook
     */
    public static void   getMatchCellStyle(XSSFWorkbook workbook,Cell cell,String colortype) {
        // 创建一个单元格样式
        XSSFCellStyle style = workbook.createCellStyle();
        cell.setCellStyle(style);

        /*****************************使用默认颜色**************************************************/
        // 填充色
        /*style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);*/

        // 字体颜色
        /*Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);*/
        /**************************************************************************************/

        /*****************************自定义颜色**************************************************/

        XSSFColor color = null;

        if("G"==colortype){
            color = new XSSFColor(new java.awt.Color(123, 203, 51));
        }else{
            color = new XSSFColor(new java.awt.Color(146, 205, 220));
        }


        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFColor color1 = new XSSFColor(new java.awt.Color(0, 0, 0));
        // 字体颜色
        XSSFFont font1 = workbook.createFont();
        font1.setColor(color1);
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short)10);
        style.setFont(font1);
    }

    private static void getNoMatchCellStyle(XSSFWorkbook workbook, Cell cell) {
        /*没有匹配上的的字体样式*/
        // 创建一个单元格样式
        XSSFCellStyle style = workbook.createCellStyle();
        cell.setCellStyle(style);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)10);
        style.setFont(font);

    }

}
