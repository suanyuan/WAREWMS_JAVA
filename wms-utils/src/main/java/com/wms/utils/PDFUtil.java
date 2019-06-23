package com.wms.utils;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PushbuttonField;

/**
 * PDF 工具类
 * @author OwenHuang
 * @Date 2014/2/21
 */
public class PDFUtil {
	
	/**
	 * 取得指定档名的PDF范本
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public static PdfReader getTemplate(String templateName) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(ResourceUtil.getPdfTemplateRootPath()).append(templateName);
		return new PdfReader(sb.toString());
	}
	
	/**
	 * 将button栏位填充指定图片
	 * @param form
	 * @param filedName
	 * @param img
	 * @return
	 * @throws Exception
	 */
	public static PdfFormField genPdfButton(AcroFields form, String filedName, byte[] img) throws Exception {
		PushbuttonField button = null;
		button = form.getNewPushbuttonFromField(filedName);
		button.setLayout(PushbuttonField.LAYOUT_ICON_ONLY);
		button.setProportionalIcon(true);
		button.setImage(Image.getInstance(img));
		return button.getField();
	}
}
