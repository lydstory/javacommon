package base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期
 */
public class DateUtil {

	public static String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static String yyyyMMddHHmmss_ = "yyyyMMddHHmmss";
	public static String yyyyMMdd = "yyyyMMdd";
	public static String HHmmss = "HH:mm:ss";
	public static String yyyy_MM_dd = "yyyy-MM-dd" ;
	/**
	 * 日期转换字符(动态格式转换)
	 * @param date 日期
	 * @param type 类型
	 * @return 字符串时间
	 */
	public static String getDateStr(Date date, String type) {
		SimpleDateFormat dateformat = new SimpleDateFormat(type);
		return dateformat.format(date);
	}

	/**
	 * 返回当前时间字符串
	 */
	public static String getDateStr() {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		Date data = new Date();
		return format.format(data);
	}

	/**
	 * 根据传入时间字符串、时间转化类型 返回Date
	 * @param value 时间字符串
	 * @param type 时间转化类型
	 * @return Date
	 */
	public static Date getDate(String value, String type) {
		SimpleDateFormat format = new SimpleDateFormat(type);
		Date date = null;
		try {
			date = format.parse(value);
		} catch(Exception e) {
			System.out.println(" =============== 日期转换异常  ============== " + value);
			return null;
		}
		return date;
	}

	/**
	 * 根据传入时间字符串、时间转化类型为yyyy-MM-dd HH:mm:ss 返回Date
	 * @param value 时间字符串
	 * @param type 时间转化类型
	 * @return Date
	 */
	public static Date getDate(String value) {
		return getDate(value, DateUtil.yyyyMMddHHmmss);
	}

	/**
	 *时间格式化
	 * @param value 时间字符串
	 * @return Date
	 * @throws ParseException
	 */
	public static String parseDate(Date date) throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat(DateUtil.yyyyMMddHHmmss);
		return dateformat.format(date);
	}

	/**
	 * java 13位时间戳1
	 * @param date
	 * @param type
	 * @return
	 */
	public static long getTime(String date,String type){
		return getDate(date,type).getTime();
	}

	/**
	 * java 13位时间戳2
	 * @param date
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static long getTime(Date date,String type) throws ParseException{
		SimpleDateFormat dateformat = new SimpleDateFormat(type);
		return dateformat.parse(dateformat.format(date)).getTime();
	}

	/**
	 * java 10位时间戳1
	 * @param date
	 * @param type
	 * @return
	 */
	public static int getTenTime(String date,String type){
		return (int) (getDate(date,type).getTime()/1000);
	}

	/**
	 * java 10位时间戳2
	 * @param date
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static int getTenTime(Date date,String type) throws ParseException{
		SimpleDateFormat dateformat = new SimpleDateFormat(type);
		return (int) (dateformat.parse(dateformat.format(date)).getTime()/1000);
	}

	/**
	 * 时间转化时间戳（13位）
	 * @param date
	 * @return
	 */
	public static synchronized long getTime(Date date){
		return date.getTime();
	}

	/**
	 * 10时间戳转时间
	 * @param times
	 * @return
	 * @throws ParseException
	 */
	public static String getDateOfTimes(long times,String type) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(type);
		return sdf.format(new Date(times * 1000));
	}

	/**
	 * 时间戳转时间
	 * @param times
	 * @return
	 * @throws ParseException
	 */
	public static String getDateOf13Times(long times,String type) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(type);
		return sdf.format(new Date(times));
	}
	/**
	 * 时间转化时间戳（10位）
	 * @param date
	 * @return
	 */
	public static int getTenTime(Date date){
		return (int) (date.getTime()/1000);
	}
	/**
	 * 日期比较  日期格式为('yyyy-MM-dd')
	 * @param dstr1  日期字符串 ()
	 * @param dstr2 日期字符串 ()
	 * @return
	 */
	public static boolean compareToDate(String dstr1,String dstr2){
		try {
			SimpleDateFormat oFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date=oFormat.parse(dstr1);
			Date date2=oFormat.parse(dstr2);
			if(date2.compareTo(date)>=0){
				return true;
			}
		} catch (Exception e) {
			System.out.print("时间格式化错误！");
			return false;
		}

		return false;
	}


	/**
	 * 返回当前小年字符串如09,10
	 */
	@SuppressWarnings("static-access")
	public static String getShortYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		String year_str = String.valueOf(year);

		return year_str.substring(2);
	}

	/**
	 * 增加天/小时/分钟/秒
	 *
	 * @param date			时间
	 * @param days			天
	 * @param hours			小时
	 * @param mins			分钟
	 * @param second		秒
	 * @return Date
	 * @author:何志鸣
	 * @date:Dec 26, 2013
	 */
	public static Date addDate(Date date, int days,int hours,int mins,int second) {
		if (date == null) return date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		cal.add(Calendar.HOUR, hours);
		cal.add(Calendar.MINUTE, mins);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

}
