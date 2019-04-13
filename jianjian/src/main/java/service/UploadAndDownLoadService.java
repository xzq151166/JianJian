package service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pojo.ServiceStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadAndDownLoadService {


    private final String baseCatalog = "C:/Users/Administrator/Desktop/jianjian/files";

    @Autowired
    private ExcelParserService parserService;

    public ServiceStatus upload(MultipartFile mFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String subpath = UUID.randomUUID().toString().replaceAll("-", "");
        String dPath = baseCatalog + "/" + subpath;
        File file = new File(dPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String completePath = "";
        try {
            String originalFilename = mFile.getOriginalFilename();
            completePath = dPath + "/" + originalFilename;

            File saveFile = new File(completePath);
            mFile.transferTo(saveFile);
        } catch (Exception e) {
            return new ServiceStatus(e, "程序执行失败了哦", "2");
        }

        //生成成品文件
         String downFileUrl = parserService.getExcelData(completePath);

        return new ServiceStatus(downFileUrl, "程序执行成功", "1");
    }

    public ServiceStatus delete(String filePath) {
        try {
            File file = new File(filePath);
            FileUtils.deleteQuietly(file);
        } catch (Exception e) {
            return new ServiceStatus(e, "程序执行失败了哦", "2");
        }
        return new ServiceStatus(null, "程序执行成功", "1");
    }

    public void download(String fileUrl,HttpServletRequest request, HttpServletResponse response) throws IOException {
       /* String filename = "匹配表.xlsx";
        filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");*/
      //  String filepath = request.getServletContext().getRealPath(fileUrl);
        File file = new File(fileUrl);
        if (!file.exists()) {
            response.getWriter().print("您要下载的文件不存在！");
            return;
        }
        response.addHeader("content-disposition", "attachment;filename=" + fileUrl);
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }
}
