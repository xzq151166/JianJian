package test;


import service.ExcelDateProvider;
import service.ExcelParserService;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        /*ExcelVo excelVo = new ExcelVo();
        Stack<Object> stack = new Stack<>();
        List<Object> objects = new ArrayList<>();
        List<Object> differObjects = new ArrayList<>();
        objects.add(1);
        objects.add(1);
        objects.add(1);
        objects.add(3);
        objects.add(1);
        objects.add(1);
        objects.add(1);
        stack.push(objects.get(0));
        differObjects.add(objects.get(0));*/
        ExcelDateProvider excelDateProvider = new ExcelDateProvider();
        ExcelParserService excelParserService = new ExcelParserService();
        excelParserService.getExcelData("C:/Users/Administrator/Desktop/joe/原件/对账坚坚.xlsx");
       // excelDateProvider.outPutResult("C:/Users/Administrator/Desktop/joe/bank.xlsx","C:/Users/Administrator/Desktop/joe/store.xlsx");


    }
}
