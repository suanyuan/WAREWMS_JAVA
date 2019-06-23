package com.wms.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 產生隨機資料工具類
 * @Date 2012/6/5
 * @author OwenHuang
 */
public class RandomUtil {

	private final static char[] CHAR_ARRAY = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
											  'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
											  '0','1','2','3','4','5','6','7','8','9'};
	
	/**
	 * 產生隨機密碼
	 * @param length 密碼長度
	 * @return
	 */
	public static String genPassword(int length){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		while(sb.length() < length){
			sb.append(CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)]);
		}
		return sb.toString();
	}
	
	/**
	 * 產生 SERVER 唯一標示符，長度36
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args){
		for(int i = 0 ; i< 10000 ; i ++){
			System.out.println(RandomUtil.genCode(8));
		}
	}

	public static String genCode(int digits) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		while(sb.length() < digits){
			if(sb.length() == 1 && sb.toString().equals("0")){
				sb.setLength(0);
			}
			sb.append(random.nextInt(9));
		}
		return sb.toString();
	}
}
