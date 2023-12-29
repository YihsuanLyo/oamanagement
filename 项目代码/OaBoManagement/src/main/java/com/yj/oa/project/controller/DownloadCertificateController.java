package com.yj.oa.project.controller;

import com.yj.oa.project.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/download")
public class DownloadCertificateController {

    @Value("${ylrc.upload.file.path}")
    private String filePath;//文件保存位置

    @GetMapping("/certificate")
    public void downloadFile(String fileSrc, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName=filePath+fileSrc;
        String s = fileName.substring(fileName.lastIndexOf("/") + 1);//文件名
        if (fileName != null) { //设置文件路径
            File file = new File(fileName); // 如果文件名存在，则进行下载
            if (file.exists()) { // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream"); // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, s)); // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);//读取文件对象，返回输入流
                    bis = new BufferedInputStream(fis);//转化这个输入流，变成可以写到缓冲区的对象
                    OutputStream os = response.getOutputStream();//新建一个输出流对象
                    int i = bis.read(buffer);//将输入流里的东西写到缓冲区
                    while (i != -1) {
                        os.write(buffer, 0, i); i = bis.read(buffer);//通过输出流把缓冲区的东西输出到浏览器
                    }
                } catch (Exception e) {
                    System.out.println("Download the song failed!");
                } finally {
                    if (bis != null) {
                        try { bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}
