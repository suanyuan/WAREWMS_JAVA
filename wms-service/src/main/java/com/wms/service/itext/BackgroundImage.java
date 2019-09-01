package com.wms.service.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/9/1
 */
public class BackgroundImage extends PdfPageEventHelper {
    private String picPath = "";
    private String no;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public BackgroundImage(){

    }

    public BackgroundImage(String path,String no){
        this.picPath = path;
        this.no = no;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            document.add(new Paragraph(no));
            Image image = Image.getInstance(picPath);
            image.setAlignment(image.UNDERLYING);
            image.setAbsolutePosition(0,0);
            image.scaleAbsolute(500.0F,750F);
            // 设置图片旋转
            // image.setRotation((float) (-Math.PI / 6));
            document.add(image);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        super.onStartPage(writer, document);
    }
}