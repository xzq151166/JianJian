package service;


import Util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pojo.DifferDesc;
import pojo.ExcelVo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 10.14  解析excel的业务类
 */
public class ExcelDateProvider {


    public String outPutResult(String bankFileUrl, String storefileUrl ) throws IOException {
        /**
         *
         * List<DifferDesc> dataList = getDifference(bankStreamAndFilename, storeStreamAndFilename);
         *         if (CollectionUtils.isEmpty(dataList)) {
         *             resultMessage = "亲爱的坚坚，并没有不同的数据.嘿嘿嘿.";
         *         } else {
         *             resultMessage = "我靠，居然还真有差异.MMP.......~~~ ";
         *         }
         *         resultMap.put("list", dataList);
         *         resultMap.put("result", resultMessage);
         */

        Map<String,Object> resultMap =getExcelData(bankFileUrl,storefileUrl);
        List<DifferDesc> dataList = (List<DifferDesc>)resultMap.get("list");
        String message = (String)resultMap.get("result");
        return packageResult(dataList,message).toString();
    }

    private StringBuffer packageResult(List<DifferDesc> dataList, String message) {
        if(CollectionUtils.isEmpty(dataList)){
            return new StringBuffer(message);
        }

        StringBuffer buffer = new StringBuffer(message).append("坚坚请往下看：\r\n").append("====================================================================\r\n").append("\r\n");

        for (DifferDesc desc:dataList) {
            buffer.append(desc.getDesc()).append("\r\n").append("====================================================================\r\n").append("\r\n");
        }
        return buffer;
    }

    /*获取到excel准备解析*/
    private List<ExcelVo> parseExcel(Map<String, Object> streamAndFilename) throws IOException {
        String fileName = (String) streamAndFilename.get("fileName");
        FileInputStream fis = (FileInputStream) streamAndFilename.get("stream");
        List<ExcelVo> dataList = new ArrayList<>();
        /*检验文件名是否符合规范，校验方法，后续再写*/
        if (false) {
            return null;
        }
        return getExcelInfo(fis);
    }

    private List<ExcelVo> getExcelInfo(FileInputStream fis) throws IOException {
        Workbook wb = new XSSFWorkbook(fis);
        return readvalue(wb);
    }

    private List<ExcelVo> readvalue(Workbook wb) {
        List<ExcelVo> dataList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(0);
        //总共有多少行
        int rows = sheet.getPhysicalNumberOfRows();
        //从第几行开始
        int firstRow = sheet.getFirstRowNum();
        //单元格
        int totallCells=0;
        if(rows>+1 && sheet.getRow(firstRow)!=null){
            totallCells = sheet.getRow(firstRow).getPhysicalNumberOfCells();
        }

        //循环行数获取数据
        for(int r = firstRow+2;r<=rows;r++){
            Row row = sheet.getRow(r);
            if(row==null){
                continue;
            }
            ExcelVo excelVo = new ExcelVo();
            for (int c=0;c<totallCells;c++){
                Cell cell = row.getCell(c);
                switch (c){
                    case 0:
                        //编码，也是数字？？
                        excelVo.setCode((String) readUtil(cell));
                        excelVo.setColumnindex(c);
                        break;
                    case 1:
                        //门店名字
                        excelVo.setStore((String)readUtil(cell));
                        excelVo.setColumnindex(c);
                        break;
                    case 2:
                        excelVo.setAmount((String)readUtil(cell));
                        excelVo.setColumnindex(c);
                        break;
                }


            }

            excelVo.setIsResolved("N");
            excelVo.setDataResource("Bank");
            excelVo.setRowNum(r);
            dataList.add(excelVo);
        }
        return dataList;
    }

    public Map<String, Object> getExcelData(String bankFileUrl, String storefileUrl) throws IOException {
        Map<String, Object> bankStreamAndFilename = ExcelUtil.getStreamAndFilename(bankFileUrl);
        Map<String, Object> storeStreamAndFilename = ExcelUtil.getStreamAndFilename(storefileUrl);
        return readExcel(bankStreamAndFilename, storeStreamAndFilename);
    }

    private Map<String, Object> readExcel(Map<String, Object> bankStreamAndFilename, Map<String, Object> storeStreamAndFilename) throws IOException {
        HashMap<String, Object> resultMap = new HashMap<>();
        String resultMessage = "";
        List<DifferDesc> dataList = getDifference(bankStreamAndFilename, storeStreamAndFilename);
        if (CollectionUtils.isEmpty(dataList)) {
            resultMessage = "亲爱的坚坚，并没有不同的数据.嘿嘿嘿.";
        } else {
            resultMessage = "我靠，居然还真有差异.MMP.......~~~ ";
        }
        resultMap.put("list", dataList);
        resultMap.put("result", resultMessage);
        return resultMap;
    }

    private List<DifferDesc> getDifference(Map<String, Object> bankStreamAndFilename, Map<String, Object> storeStreamAndFilename) throws IOException {
        List<ExcelVo> bankExcelVos = parseExcel(bankStreamAndFilename);
        List<ExcelVo> storeExcelVos = parseExcel(storeStreamAndFilename);
        if (!CollectionUtils.isEmpty(bankExcelVos) && !CollectionUtils.isEmpty(storeExcelVos)) {
            return getDifferenceUtil(bankExcelVos, storeExcelVos);
        }
        return null;
    }

    private List<DifferDesc> getDifferenceUtil(List<ExcelVo> bankExcelVos, List<ExcelVo> storeExcelVos) {
        int differCount = 0;
        List<DifferDesc> diffList = new ArrayList<>();
        for (int i = 0; i < bankExcelVos.size(); i++) {
            if("Y".equalsIgnoreCase(bankExcelVos.get(i).getIsResolved())){
                continue;
            }
                /**
                 * 找出两个数据集里面有哪些相同的记录
                 */
                //从banklist找出来有哪些相同的记录
                List<ExcelVo> sameBankDatas = findSameDataFromList(bankExcelVos.get(i), bankExcelVos);
                //把银行的集合置位为IsResolved 为"Y"
                setBankDatasIsResolved(bankExcelVos.get(i),bankExcelVos);
                List<ExcelVo> sameStoreDatas = findSameDataFromList(bankExcelVos.get(i), storeExcelVos);
                DifferDesc singleDifferDesc = recordDiffDesc(sameBankDatas, sameStoreDatas, differCount);
                if(singleDifferDesc==null){
                    continue;
                }
                diffList.add(singleDifferDesc);
        }
        return diffList;
    }

    private void setBankDatasIsResolved(ExcelVo excelVo, List<ExcelVo> bankExcelVos) {
        for (ExcelVo e:bankExcelVos) {
            if(removeBadStr(excelVo.getCode()).equalsIgnoreCase(removeBadStr(e.getCode()))){
                e.setIsResolved("Y");
            }
        }
    }


    private List<ExcelVo> findSameDataFromList(ExcelVo excelVo, List<ExcelVo> excelVos) {
        List<ExcelVo> sameList = new ArrayList<>();
        for (int k = 0; k < excelVos.size(); k++) {
            ExcelVo kData = excelVos.get(k);
            if (removeBadStr(excelVo.getCode()).equalsIgnoreCase(removeBadStr(kData.getCode()))) {
                //添加到list里面再返回
                sameList.add(kData);
            }
        }

        return sameList;
    }

    private DifferDesc recordDiffDesc(List<ExcelVo> sameBankDatas, List<ExcelVo> sameStoreDatas, int differCount) {
        DifferDesc differDesc = new DifferDesc();
        ExcelVo compareO = sameBankDatas.get(0);
        String message = "";

        if(compareO.getCode().equalsIgnoreCase("5847")){

            String message2 = "";
        }

        //1、考虑门店流水数据不全的情况
        if (CollectionUtils.isEmpty(sameStoreDatas)) {
            //门店流水数据不全，需要做出提示
            //message = differCount+"、门店流水表中不存在编码为"+compareO.getCode()+"的数据.坚坚检查一下，是不是门店流水的数据不完整？"+"\r\n";
            //使用字符化list的工具，打印出提示类
            String messages =getMessage(compareO,sameBankDatas,sameStoreDatas);
            differDesc.setDesc(messages);
            return differDesc;
        }

        //2、考虑两个数据集数量不同的比较
        if (sameBankDatas.size()!=sameStoreDatas.size()) {
            //数量不同必须打印出来
            String messages =getMessage(compareO,sameBankDatas,sameStoreDatas);
            differDesc.setDesc(messages);
            return differDesc;
        }

        //3、先比较银行流水中是否存在差异，也就是银行流水之间的比较
        for(int i=0;i<sameBankDatas.size();i++){
            if(!removeBadStr(compareO.getAmount()).equals(removeBadStr(sameBankDatas.get(i).getAmount())) || !removeBadStr(compareO.getStore()).equals(removeBadStr(sameBankDatas.get(i).getStore()))){
                //金额或者门店名称不一致
                differCount ++;
                //message = differCount+"、注意哦: 编码为"+compareO.getCode()+"的在银行流水中之间有不同的数据，请坚坚仔细检查一下下，门店和金额有不同的地方。。。"+"\r\n";
                message = getMessage(compareO,sameBankDatas,sameStoreDatas);
                differDesc.setDesc(message);
            }
        }


        //4、正常比较
        for(int i =0;i<sameBankDatas.size();i++){
            ExcelVo bank = sameBankDatas.get(i);
            for (int j=0;j<sameStoreDatas.size();j++){
                ExcelVo store = sameStoreDatas.get(j);
                if(!removeBadStr(bank.getStore()).equalsIgnoreCase(removeBadStr(store.getStore())) || !removeBadStr(bank.getAmount()).equals(removeBadStr(store.getAmount()))){
                    //有不同的地方
                    differCount++;
                    message = getMessage(bank,sameBankDatas,sameStoreDatas);
                }
            }
        }

        if(StringUtils.isEmpty(message)){
            return null;
        }
        differDesc.setDesc(message);
        return differDesc;
    }

    /*组装message*/

    private String getMessage(ExcelVo bank,List<ExcelVo> sameBankDatas, List<ExcelVo> sameStoreDatas ){

        return "编码为"+bank.getCode()+"的银行流水与门店流水存在金额、或者数量差异，详情如下："+
                "\r\n"+"《银行流水》"+"\r\n"+"( "+toStringList(sameBankDatas)+" )"+"\r\n"+"\r\n"+
                "《门店流水》\r\n"+"( "+toStringList(sameStoreDatas)+" );"+"\r\n"+"请仔细对比哦！";

    }


    /**
     * 字符化公用方法
     */
    private String toStringList(List<ExcelVo> sameDatas) {
        if(CollectionUtils.isEmpty(sameDatas)){
            return "无数据";
        }
        String messages = "";
        for (ExcelVo e:sameDatas){
            messages=messages+e.toString()+",";
        }
        return messages;
    }

    /**
     * 读取数据的公共类方法
     */

    private Object readUtil(Cell cell){
        ExcelVo excelVo = new ExcelVo();
        cell.setCellType(CellType.STRING);


      /*  if(null!=cell && cell.getCellTypeEnum()!=CellType.ERROR){
            //数字类型的
            if(cell.getCellTypeEnum()==CellType.NUMERIC){
                return cell.getNumericCellValue();

            }else{
                return cell.getStringCellValue();
            }
        }*/
        return cell.getStringCellValue();
    }

    /**
     * 去掉换行符号和空格
     */

    private String removeBadStr(String str){
        return str.trim().replace("\n","").replace("\r","");
    }

}
