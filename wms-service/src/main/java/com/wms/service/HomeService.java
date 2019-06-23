package com.wms.service;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.utils.FileUtil;

@Service("homeService")
public class HomeService {

	@Autowired
	private FTPService myftpService;
	
	public void testFtp() throws Exception{
		String filename = "a.txt";
		try {
			myftpService.connect();
			myftpService.uploadFile(new ByteArrayInputStream(FileUtil.fileToByteArray("E:\\文件\\a.txt")), "great", filename);
		} finally {
			myftpService.disconnect();
		}
	}
}
