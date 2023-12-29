package com.yj.oa.project.controller;

import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Design;
import com.yj.oa.project.service.design.IDesignService;
import com.yj.oa.project.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.yj.oa.common.utils.file.UploadFile.getFileTimeId;
import static com.yj.oa.common.utils.shiro.ShiroUtils.getUser;
import static com.yj.oa.framework.web.po.AjaxResult.error;
import static com.yj.oa.framework.web.po.AjaxResult.success;
import static com.yj.oa.project.controller.DesignController.getFileBean;

@Controller
@RequestMapping("/design/add/uploadDesign")
public class UploadDesignController {

    private static final Logger log = LoggerFactory.getLogger(UploadDesignController.class);

    @Value("${ylrc.upload.file.path}")
    private String uploadPath;//文件保存位置

    @Autowired
    private IDesignService iDesignService;

    @RequestMapping(value = "/design",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") MultipartFile design, Design fileBean) {


        // 获取文件名
        String fileName = design.getOriginalFilename();
        if (!fileName.isEmpty()) {
            String fileId = getFileTimeId();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            fileId  += suffixName;
            // 设置文件存储路径
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                //若不存在文件夹，则创建一个文件夹
                filePath.mkdir();
                log.info("创建1成功");
            }
            filePath = new File(uploadPath + "/" + StringUtil.getFormatterDate(new Date(), "yyyyMMdd"));
            /*String path = filePath +"/"+ fileName;
            File dest = new File(path);*/
            // 检测是否存在目录
            if (!filePath.exists()) {
                filePath.mkdir();// 新建文件夹
                log.info("创建2成功");
            }
            String filename = StringUtil.getFormatterDate(new Date(), "yyyyMMdd") + "/" + System.currentTimeMillis() + suffixName;
            try {
                design.transferTo(new File(uploadPath+"/"+filename));// 文件写入
                log.info(fileBean.getDescr());

                log.info(Integer.toString(fileBean.getDownloadCount()));

            } catch (IOException e) {
                e.printStackTrace();

            }
            iDesignService.insertSelective(getFileBean(design, filename, fileBean,getUser().getName()));
            return success();

        }
        return error("上传失败！请再试试！");
    }

}
