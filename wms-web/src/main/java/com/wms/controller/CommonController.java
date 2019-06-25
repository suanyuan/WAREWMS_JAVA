package com.wms.controller;

import com.google.common.io.Files;
import com.wms.result.UploadResult;
import com.wms.utils.DateUtil;
import com.wms.utils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/24
 */
@Controller
@RequestMapping("commonController")
public class CommonController {

    private static String uploadUrl = "/Users/quendi/fileUpload";

    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "uploadFileLocal", method= RequestMethod.POST, headers="Content-Type=multipart/form-data")
    @ResponseBody
    public UploadResult uploadFileLocal(HttpServletRequest request , @RequestParam(value="file",required=false) MultipartFile file) {
        UploadResult uploadResult = new UploadResult();
        InputStream stream = null;
        uploadResult.setSuccess(false);
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String file_name = UUID.randomUUID().toString().replace("-","")+"."+ ext.substring(ext.lastIndexOf(".")+1);
        try {
            stream = file.getInputStream();
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            File targetFile = new File(uploadUrl+File.separator+file_name);
            Files.write(buffer,targetFile);
            uploadResult.setSuccess(true);
            uploadResult.setComment(file_name);
        } catch (IOException e) {
            uploadResult.setSuccess(false);
            uploadResult.setComment("uploadFile 上传文件 error");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
        return uploadResult;
    }

    @RequestMapping("/get_fileLocal")
    public ResponseEntity<byte[]> fileDownLoad(
            HttpServletResponse response,
            @RequestParam String fileUrl,
            @RequestParam(defaultValue = "") String fileName) throws Exception{
        if(StringUtils.isEmpty(fileName)){
            fileName = DateUtil.format(new Date(),"yyyyMMddhh24mmnn")+"."+fileUrl.substring(fileUrl.lastIndexOf(".")+1);
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String uploadUrl = PropertyUtils.getString("uploadUrl");
        File file = new File(uploadUrl+File.separator+fileUrl);
        if(file.exists()){
            long fileLength = file.length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

        }
        return null;
    }
}