package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pojo.ServiceStatus;
import service.ExcelParserService;
import service.UploadAndDownLoadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExcelController {


    @Autowired
    private UploadAndDownLoadService uploadAndDownLoadService;

    @Autowired
    private ExcelParserService excelService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/excel/upload")
    public ServiceStatus upload(@RequestParam(value = "file") MultipartFile mFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
         return uploadAndDownLoadService.upload(mFile, request,response);

    }

    @RequestMapping("/excel/download")
    public  void download(@RequestParam(value = "fileUrl") String fileUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
         uploadAndDownLoadService.download(fileUrl, request,response);

    }
}
