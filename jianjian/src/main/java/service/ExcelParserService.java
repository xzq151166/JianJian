package service;

import Util.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.ExcelVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ExcelParserService {


    public String getExcelData(String fileUrl) throws IOException {
        Map<String, Object> StreamAndFilename = ExcelUtil.getStreamAndFilename(fileUrl);
        Map<String, Object> dataLists = ExcelUtil.parseExcel(StreamAndFilename);
        List<ExcelVo> bLists = (List<ExcelVo>) dataLists.get("bList");
        List<ExcelVo> sLists = (List<ExcelVo>) dataLists.get("sList");
        String downUrl= "";
        try {

             downUrl = outPutExcel(bLists, sLists);
            return downUrl;
        } catch (Exception e) {
            System.out.println("最外层的异常" + e.getMessage());
        }

        return downUrl;
    }

    private String outPutExcel(List<ExcelVo> bLists, List<ExcelVo> sLists) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("匹配");


        //处理第一行
        setValueToSheet(sheet, bLists.get(0), sLists.get(0), 0, "N", wb);

        /**
         * 对比能匹配上的
         */
        for (int i = 1; i < bLists.size(); i++) {
            ExcelVo bank = bLists.get(i);
            for (int j = 1; j < sLists.size(); j++) {
                ExcelVo store = sLists.get(j);
                if (!StringUtils.isEmpty(ExcelUtil.removeBadStr(bank.getCode())) && !StringUtils.isEmpty(ExcelUtil.removeBadStr(store.getCode()))
                        && "Y" != store.getIsWrited()) {
                    //编码不为空，要进行比较了
                    if (ExcelUtil.removeBadStr(bank.getCode()).equalsIgnoreCase(ExcelUtil.removeBadStr(store.getCode())) &&
                            ExcelUtil.removeBadStr(bank.getAmount()).equalsIgnoreCase(ExcelUtil.removeBadStr(store.getAmount()))) {
                        setValueToSheet(sheet, bank, store, i, "Y", wb);
                        bank.setIsWrited("Y");
                        store.setIsWrited("Y");
                        break;
                    }
                }

            }
        }

        /**
         *匹配不上的，也要写进去
         */

        int maxLength = bLists.size() > sLists.size() ? bLists.size() : sLists.size();

        for (int i = 1; i < maxLength; i++) {

            ExcelVo bExcelO = getExcelO(bLists, i);

            ExcelVo sExcelO = getExcelO(sLists, i);

            if ("Y" == bExcelO.getIsWrited()) {
                continue;
            }
            /**
             *获取到sList的数据，如果已经被写过，就要轮循找到没有被写过的数据
             */
            if ("Y" == sExcelO.getIsWrited()) {
                sExcelO = getNextNoWrited(i, sLists);
            }

            try {
                setValueToSheet(sheet, bExcelO, sExcelO, i, "N", wb);
                sExcelO.setIsWrited("Y");
            } catch (Exception e) {
                String i3 = "";
            }
        }

        String subpath = UUID.randomUUID().toString().replaceAll("-", "");


        String subDownUrl = "C:/Users/Administrator/Desktop/joe/原件/" + subpath ;
        File fileDir = new File(subDownUrl);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        String downUrl = subDownUrl + "/匹配表.xlsx";
        File file = new File(downUrl);

        try (FileOutputStream fOut = new FileOutputStream(file)) {
            // 将创建的内容写到指定的Excel文件中
            wb.write(fOut);
            fOut.flush();
            fOut.close();// 操作结束，关闭文件
            System.out.println("Excel文件创建成功！\nExcel文件的存放路径为："
                    + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return downUrl;

    }

    private ExcelVo getNextNoWrited(int i, List<ExcelVo> sLists) {
        for (int k = 1; k < sLists.size(); k++) {
            if ("Y" != sLists.get(k).getIsWrited()) {
                return sLists.get(k);
            }
        }
        return new ExcelVo();
    }

    private ExcelVo getExcelO(List<ExcelVo> lists, int i) {
        try {
            ExcelVo excelVo = lists.get(i);
            return excelVo;
        } catch (Exception e) {
            return new ExcelVo();
        }

    }


    private void setValueToSheet(XSSFSheet sheet, ExcelVo bank, ExcelVo store, int index, String isDraw, XSSFWorkbook wb) {

        //封装成匹配表的vo类
        XSSFRow row = sheet.createRow(index);

        /**
         * 银行流水
         *
         * */
        //交易时间
        ExcelUtil.writeUtil(bank.getTime(), row, 0, "String", isDraw, wb);
        //凭证号码
        ExcelUtil.writeUtil(bank.getCredential(), row, 1, "String", isDraw, wb);
        //对方户名
        ExcelUtil.writeUtil(bank.getAccountName(), row, 2, "String", isDraw, wb);
        //对方账号
        ExcelUtil.writeUtil(bank.getAccount(), row, 3, "String", isDraw, wb);
        //对方开户机构
        ExcelUtil.writeUtil(bank.getBankType(), row, 4, "String", isDraw, wb);
        //记账日期
        ExcelUtil.writeUtil(bank.getAccountDate(), row, 5, "String", isDraw, wb);
        //摘要
        ExcelUtil.writeUtil(bank.getSummary(), row, 6, "String", isDraw, wb);
        //备注
        ExcelUtil.writeUtil(bank.getRemark(), row, 7, "String", isDraw, wb);
        //门店编码
        ExcelUtil.writeUtil(bank.getCode(), row, 8, index == 0 ? "String" : "BigDecimal", isDraw, wb);
        //门店名称
        ExcelUtil.writeUtil(bank.getStore(), row, 9, "String", isDraw, wb);
        //贷方发生额度（收入）
        ExcelUtil.writeUtil(bank.getAmount(), row, 10, index == 0 ? "String" : "BigDecimal", isDraw, wb);

        //差异
        ExcelUtil.writeUtil(index == 0 ? "差异" : ExcelUtil.subtractAmount(bank.getAmount(), store.getAmount(), row.createCell(11), wb), row, 11, index == 0 ? "String" : "BigDecimal", isDraw, wb);


        /**
         * 门店入账
         *
         * */

        //借方金额
        ExcelUtil.writeUtil(store.getAmount(), row, 12, index == 0 ? "String" : "BigDecimal", isDraw, wb);
        //利润中心
        ExcelUtil.writeUtil(store.getStore(), row, 13, "String", isDraw, wb);
        //门店编码
        ExcelUtil.writeUtil(store.getCode(), row, 14, index == 0 ? "String" : "BigDecimal", isDraw, wb);
        //摘要
        ExcelUtil.writeUtil(index == 0 ? "摘要" : store.getBigSummary(), row, 15, "String", isDraw, wb);
        //凭证字号
        ExcelUtil.writeUtil(store.getCredential(), row, 16, "String", isDraw, wb);
        //短摘要
        ExcelUtil.writeUtil(store.getSummary(), row, 17, "String", isDraw, wb);
        //制单人
        ExcelUtil.writeUtil(index == 0 ? "制单人" : "POS_IF", row, 18, "String", isDraw, wb);
        //凭证日期
        ExcelUtil.writeUtil(store.getCredentialDate(), row, 19, "String", isDraw, wb);
    }

    /**
     * 通过code找list里面的数据
     */

    public List<ExcelVo> getDataByCode(String code, List<ExcelVo> list) {
        List<ExcelVo> resultList = new ArrayList<>();
        for (ExcelVo l : list) {
            if (code.equalsIgnoreCase(ExcelUtil.removeBadStr(l.getCode()))) {
                resultList.add(l);
            }
        }
        return resultList;

    }


}
