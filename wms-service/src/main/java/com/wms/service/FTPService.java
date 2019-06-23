package com.wms.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.net.ftp.FileTransferOutputStream;

/**
 * FTP 服務
 * @author OwenHuang
 * @Date 2013/7/23
 */
public class FTPService {
	private static final Logger logger = Logger.getLogger(FTPService.class);
	private String host;
	private int port;
	private String username;
	private String password;
	private int timeout;
	private FileTransferClient ftpClient;
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setFtpClient(FileTransferClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	/**
	 * ftp連線
	 * @throws Exception
	 */
	public void connect() throws Exception {
		ftpClient.setRemoteHost(host);
		ftpClient.setRemotePort(port);
		ftpClient.setUserName(username);
		ftpClient.setPassword(password);
		ftpClient.setTimeout(timeout);
		ftpClient.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
		ftpClient.connect();
	}
	
	/**
	 * ftp離線
	 * @throws Exception
	 */
	public void disconnect() throws Exception {
		ftpClient.disconnect();
	}
	
	/**
	 * 新增檔案
	 * @param sourceDir 来源资料夹
	 * @param sourceFilename 来源档案
	 * @param targetDir 目标资料夹
	 * @param targetFilename 目标档案
	 * @throws Exception
	 */
	public boolean uploadFile(InputStream inputStream, String targetDir, String targetFilename) {
		FileTransferOutputStream outputStream = null;
		try {
			ftpClient.changeDirectory(new StringBuilder().append("/").append(targetDir).toString());
			outputStream = ftpClient.uploadStream(targetFilename);
			int count;
			byte[] bytes = new byte[1024];
			while (-1 != (count = inputStream.read(bytes))) {
				outputStream.write(bytes, 0, count);
			}
		} catch (Exception e) {
			logger.error("ftp upload file error",e);
			return false;
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("close inputStream error",e);
					return false;
				}
			}
			if(outputStream != null){
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					logger.error("close outputStream error",e);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 下載檔案
	 * @param sourceDir 来源资料夹
	 * @param sourceFilename 来源档案
	 * @return
	 */
	public byte[] downloadFile(String sourceDir, String sourceFilename) {
		byte[] fileBytes = null;
		try {
			if(this.fileExists(sourceDir, sourceFilename)){
				ftpClient.changeDirectory(new StringBuilder().append("/").append(sourceDir).toString());
				fileBytes = ftpClient.downloadByteArray(sourceFilename);
			}else{
				logger.info("ftp file dose not exist");
			}
		} catch (Exception e) {
			logger.error("ftp download file error",e);
		} 
		return fileBytes;
	}
	
	/**
	 * 移動檔案
	 * @param sourceDir 来源资料夹
	 * @param sourceFilename 来源档案
	 * @param targetDir 目标资料夹
	 * @param targetFilename 目标档案
	 * @throws Exception
	 */
	public boolean moveFile(String sourceDir, String sourceFilename, String targetDir, String targetFilename) {
		try {
			if(this.fileExists(sourceDir, sourceFilename)){
				ftpClient.changeDirectory(new StringBuilder().append("/").append(sourceDir).toString());
				
				StringBuilder sb = new StringBuilder();
				sb.append("/").append(targetDir)
				  .append("/").append(targetFilename);
				
				ftpClient.rename(sourceFilename, sb.toString());
			}else{
				logger.info("ftp file dose not exist");
				return false;
			}
		} catch (Exception e) {
			logger.error("ftp move file error",e);
			return false;
		} 
		return true;
	}
	
	/**
	 * 刪除檔案
	 * @param sourceDir 来源资料夹
	 * @param sourceFilename 来源档案
	 * @throws Exception
	 */
	public boolean deleteFile(String sourceDir, String sourceFilename) {
		try {
			if(this.fileExists(sourceDir, sourceFilename)){
				ftpClient.changeDirectory(new StringBuilder().append("/").append(sourceDir).toString());
				ftpClient.deleteFile(sourceFilename);
			}else{
				logger.info("ftp file dose not exist");
				return false;
			}
		} catch (Exception e) {
			logger.error("ftp delete file error",e);
			return false;
		} 
		return true;
	}
	
	/**
	 * 檔案是否存在
	 * @param sourceDir 来源资料夹
	 * @param sourceFilename 来源档案
	 * @return
	 */
	public boolean fileExists(String sourceDir, String sourceFilename) {
		boolean result = false ; 
		try {
			ftpClient.changeDirectory(new StringBuilder().append("/").append(sourceDir).toString());
			result = ftpClient.exists(sourceFilename);
		} catch (Exception e) {
			logger.error("ftp error",e);
			return false;
		} 
		return result;
	}
}
