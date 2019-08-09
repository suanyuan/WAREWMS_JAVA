package com.wms.controller;

import com.google.common.io.Files;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.result.UploadResult;
import com.wms.service.BasCodesService;
import com.wms.utils.DateUtil;
import com.wms.utils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/24
 */
@Controller
@RequestMapping("commonController")
public class CommonController {

    private static final String uploadUrl = "/Users/quendi/fileUpload";
    //private static final String uploadUrl = "/root/uploadDir";

    @Autowired
    private BasCodesService basCodesService;

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
            System.out.println("targetFile=="+targetFile);
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

    @RequestMapping(params = "fileDownLoad")
    public ResponseEntity<byte[]> fileDownLoad(
            HttpServletResponse response,
            @RequestParam String url,
            @RequestParam(defaultValue = "") String fileName) throws Exception{
        if(StringUtils.isEmpty(fileName)){
            fileName = DateUtil.format(new Date(),"yyyyMMddhh24mmss")+"."+url.substring(url.lastIndexOf(".")+1);
        }else{
            fileName = url;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File file = new File(uploadUrl+File.separator+url);
        if(file.exists()){
            long fileLength = file.length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "UTF-8"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[4096];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

        }
        return null;
    }

    /**
     * 是否启用
     * @return
     */
    @RequestMapping(params = "getIsUseCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getIsUseCombobox() {
        List<EasyuiCombobox> easyuiComboboxList = new ArrayList<>();
        EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
        easyuiCombobox.setId("");
        easyuiCombobox.setValue("");
        easyuiCombobox.setSelected(true);
        easyuiComboboxList.add(easyuiCombobox);
        EasyuiCombobox easyuiComboboxUse = new EasyuiCombobox();
        easyuiComboboxUse.setId("1");
        easyuiComboboxUse.setValue("有效");
        easyuiComboboxList.add(easyuiComboboxUse);
        EasyuiCombobox easyuiComboboxUnUse = new EasyuiCombobox();
        easyuiComboboxUnUse.setId("0");
        easyuiComboboxUnUse.setValue("失效");
        easyuiComboboxList.add(easyuiComboboxUnUse);
        return easyuiComboboxList;
    }

    /**
     * 管理分类
     * @return
     */
    @RequestMapping(params = "getCatalogClassify")
    @ResponseBody
    public List<EasyuiCombobox> getCatalogClassify(){
        return basCodesService.getBy(Constant.CODE_CATALOG_CLASSIFY);
    }

    /**
     * 版本2002/2017
     * @return
     */
    @RequestMapping(params = "getCatalogVersion")
    @ResponseBody
    public List<EasyuiCombobox> getCatalogVersion(){
        return basCodesService.getBy(Constant.CODE_CATALOG_VERSION);
    }

    /**
     * 首营状态
     * @return
     */
    @RequestMapping(params = "getCatalogFirstState")
    @ResponseBody
    public List<EasyuiCombobox> getCatalogFirstState(){
        return basCodesService.getBy(Constant.CODE_CATALOG_FIRSTSTATE);
    }

    /**
     * 是否
     * @return
     */
    @RequestMapping(params = "getYesOrNoCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getYesOrNoCombobox() {
        List<EasyuiCombobox> easyuiComboboxList = new ArrayList<>();
        EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
        easyuiCombobox.setId("");
        easyuiCombobox.setValue("");
        easyuiComboboxList.add(easyuiCombobox);
        EasyuiCombobox easyuiComboboxUse = new EasyuiCombobox();
        easyuiComboboxUse.setId(Constant.CODE_YES_OR_YES);
        easyuiComboboxUse.setValue("是");
        easyuiComboboxList.add(easyuiComboboxUse);
        EasyuiCombobox easyuiComboboxUnUse = new EasyuiCombobox();
        easyuiComboboxUnUse.setId(Constant.CODE_YES_OR_NO);
        easyuiComboboxUnUse.setValue("否");
        easyuiComboboxList.add(easyuiComboboxUnUse);
        return easyuiComboboxList;
    }

    /**
     * 企业类型
     * @return
     */
    @RequestMapping(params = "getEntType")
    @ResponseBody
    public List<EasyuiCombobox> getEntType(){
        return basCodesService.getBy(Constant.CODE_CATALOG_ENTTYPE);
    }

    /**
     * 首营状态
     * @return
     */
    @RequestMapping(params = "firstState")
    @ResponseBody
    public List<EasyuiCombobox> firstState(){
        return basCodesService.getBy(Constant.CODE_CATALOG_FIRSTSTATE);
    }


    /**
     * 首营状态
     * @return
     */
    @RequestMapping(params = "checkState")
    @ResponseBody
    public List<EasyuiCombobox> checkState(){
        return basCodesService.getBy(Constant.CODE_CATALOG_CHECKSTATE);
    }

    /**
     * 单位
     * @return
     */
    @RequestMapping(params = "getUOM")
    @ResponseBody
    public List<EasyuiCombobox> getUOM(){
        return basCodesService.getBy(Constant.CODE_CATALOG_UOM);
    }

    /**
     * 样品属性
     * @return
     */
    @RequestMapping(params = "sampleAttr")
    @ResponseBody
    public List<EasyuiCombobox> getSampleAttr(){
        return basCodesService.getBy(Constant.CODE_CATALOG_SAMPLEATTR);
    }

    /**
     * 发运方式
     * @return
     */
    @RequestMapping(params = "sendFunction")
    @ResponseBody
    public List<EasyuiCombobox> getSendFunction(){
        return basCodesService.getBy(Constant.CODE_CATALOG_SENDFUNCTION);
    }

    /**
     * 结算方式
     * @return
     */
    @RequestMapping(params = "settlement")
    @ResponseBody
    public List<EasyuiCombobox> getSettlement(){
        return basCodesService.getBy(Constant.CODE_CATALOG_SETTLEMENT);
    }

    /**
     * 质量状态
     * @return
     * */
    @RequestMapping(params = "qcState")
    @ResponseBody
    public List<EasyuiCombobox> getQcState(){
        return basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
    }/**
     * 冷链标志
     * @return
     * */
    @RequestMapping(params = "getColdHainMark")
    @ResponseBody
    public List<EasyuiCombobox> getColdHainMark(){
        return basCodesService.getBy(Constant.CODE_CATALOG_COLDHAINMARK);
    }

}