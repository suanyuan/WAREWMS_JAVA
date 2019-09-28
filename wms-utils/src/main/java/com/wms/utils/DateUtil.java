package com.wms.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * 日期工具類
 * 
 * @author OwenHuang
 * @Date 2013/5/22
 */
public class DateUtil {

	/**
	 * Date =>
	 * 
	 * @param splitPattern
	 * @return
	 */
	public static String ROCFormat(String splitPattern) {
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		StringBuilder sb = new StringBuilder();
		int year = today.get(Calendar.YEAR) - 1911;
		int month = today.get(Calendar.MONTH) + 1;
		int date = today.get(Calendar.DATE);

		sb.append(year).append(splitPattern);

		if (month < 10) {
			sb.append("0").append(month);
		} else {
			sb.append(month);
		}
		sb.append(splitPattern);

		if (date < 10) {
			sb.append("0").append(date);
		} else {
			sb.append(date);
		}

		return sb.toString();
	}

	/**
	 * Date => String：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * Date => String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * String => Date
	 * 
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateString) throws Exception {
		return parse(dateString, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * String => Date
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateString, String pattern) throws Exception {
		if (StringUtils.isEmpty(dateString)) {
			return null;
		}
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(pattern).parse(dateString);
	}

	/**
	 * @Title: DateToGetMonth
	 * @param @param
	 *            dataStart
	 * @param @param
	 *            dataEnd
	 * @param @return
	 * @return Integer
	 * @throws ParseException
	 * @Description: ( )
	 */
	public static Map<Integer, Object> DateToGetMonth(String dataStart, String dataEnd) {
		Integer monthday;
		Map<Integer, Object>map=new HashMap<Integer, Object>();
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			monthday = null;
			// long to = f.parse(dataStart).getTime();
			// long from = f.parse(dataEnd).getTime();
			// System.out.println((from-to) / (1000 * 60 * 60 * 24));
			// if((from-to) / (1000 * 60 * 60 * 24)>30){
			Integer flag=0;
			Date startDate1 = f.parse(dataStart);
			Date endDate1 = f.parse(dataEnd);
			Calendar starCal = Calendar.getInstance();
			starCal.setTime(startDate1);
			int sYear = starCal.get(Calendar.YEAR);
			int sMonth = starCal.get(Calendar.MONTH);
			int sDay = starCal.get(Calendar.DATE);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate1);
			int eYear = endCal.get(Calendar.YEAR);
			int eMonth = endCal.get(Calendar.MONTH);
			int eDay = endCal.get(Calendar.DATE);
			monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
			if (sDay < eDay) {
				monthday = monthday + 1;
			}
			if(monthday<2){
				long dataNumber=DateComparison(startDate1, endDate1);
				map.put(flag=1, dataNumber);
				return map;
			}
			map.put(flag, monthday);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }else{
		// return monthday=(int) ((from-to) / (1000 * 60 * 60 * 24));
		// }
		
		return map;
	}

	/**
	 * @Title: DateToStrQd
	 * @param @param
	 *            num
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @Description: ( 日期往后增加一天.整数往后推,负数往前移动 )
	 */
	public static String DateToStrQd(int num) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(date);
		calendar.add(calendar.DATE, num);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		String str = format.format(date);
		return str;
	}
	
	    /**
	    * @Title: DateComparison
	    * @param @return    参数
	    * @return Integer    返回类型
	    * @Description: ( 2个日期比较 返回相差天数 如果0表示今天 )
	    */
	public static Long DateComparison(Date s,Date e){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
//			Date d1 = df.parse("2016-06-24 00:00:00");
//			Date d2 = df.parse("2016-06-22 00:00:00");
			long diff = s.getTime() - e.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			System.out.println(days);
			return days;
		} catch (Exception er){
			
		}
		return null;
	}

    /**
     * 200212 -》 2020-02-12
     * @param lotatt02 效期
     * @return ~
     */
	public static String lotatt02DateFormat(String lotatt02) {
	    if (StringUtils.isEmpty(lotatt02)) {
	        return lotatt02;
        }
        String regex = "(.{2})";
        lotatt02 = lotatt02.replaceAll(regex, "$1-");
        lotatt02 = "20" + lotatt02;
        lotatt02 = lotatt02.substring(0,lotatt02.length() - 1);
        return lotatt02;
    }

    /**
     * 日期是否在开始日期和结束日期之间
     */
    public static boolean betweenOn(String target, Date startTime, Date endDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startstr = dateFormat.format(startTime);
        String endstr = dateFormat.format(endDate);

        return betweenOn(target, startstr, endstr);
    }

    public static boolean betweenOn(String target, String startTime, String endDate) {

        if(target == null || startTime == null || endDate == null) return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            long start = dateFormat.parse(startTime).getTime();
            long end = dateFormat.parse(endDate).getTime();

            long targetDate = dateFormat.parse(target).getTime();

            return targetDate >= Math.min(start, end) && targetDate <= Math.max(start, end); // start <= value <= end
        } catch (java.text.ParseException e) {

            e.printStackTrace();
            return false;
        }
    }

	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse("2016-06-24 00:00:00");
			Date d2 = df.parse("2016-06-22 00:00:00");
			long diff = d1.getTime() - d2.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			System.out.println(days);
		} catch (Exception e){
			
		}
	}

}
