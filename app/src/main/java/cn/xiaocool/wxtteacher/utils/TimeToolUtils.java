package cn.xiaocool.wxtteacher.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by wzh on 2016/2/20.
 */
public class TimeToolUtils {
    /**
     * 获得当前时间，时间戳
     *
     * @return 当前时间的时间戳
     */
    public static long getNowTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获得当前时间，按照给定的格式格式化<br/>
     *
     * @param fromateType
     *            格式化格式
     * @return 当前时间
     */
    public static String getNowTime(String fromateType) {
        return fromateTimeShow(getNowTime(), fromateType);
    }

    /**
     * 格式化时间
     *
     * @param time
     *            时间，UNIX时间戳格式
     * @param fType
     *            格式
     * @return 格式化后的时间
     */
    public static String fromateTimeShow(long time, String fType) {
        java.util.Date date = new java.util.Date(time);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(fType, Locale.getDefault());
        return format.format(date);
    }

    /**
     * @exception
     * :rule：1分钟以内  显示“刚刚”  1小时以内    显示“几分钟前”，24小时以内  显示“几小时前” ，24*3小时以内  显示“几天前”，其他显示具体时间： 年 月  日
     * @param time
     * @return
     */
    public static String fromateTimeShowByRule(long time){
        long timeDistance=getNowTime() - time;
        long days = timeDistance / (1000 * 60 * 60 * 24);
        long hours = (timeDistance - days * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long minutes = (timeDistance-days*(1000 * 60 * 60 *24)-hours*(1000* 60 * 60))/(1000* 60);
        if (days==0) {
            if (hours==0) {
                if (minutes==0) {
                    return "刚刚";
                }else{
                    return minutes+"分钟前";
                }
            }else{
                return hours+"小时前";
            }
        }else if (days==1) {
            return "1天前";
        }else if (days==2) {
            return "2天前";
        }else if (days==3) {
            return "3天前";
        }else{
            return fromateTimeShow(time, "yyyy-MM-dd");
        }
    }
    /**
     * 判断一个UNIX时间戳是否是今天以前的时间<br/>
     * 如果异常则返回true
     *
     * @param time
     *            要判断的时间
     * @return true表示是今天以前的时间，false表示不是 @ 未知异常
     */
    public static boolean ifBeforeToday(long time) {
        boolean result = true;
        try {
            long now = getTodayTime(0, 0, 0);
            if ((now - time) > 0) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = true;
        }
        return result;
    }

    /**
     * 根据今天的时间，时、分、秒来获得一个UNIX时间戳<br/>
     * 如果异常则返回0L
     *
     * @param hours
     *            时
     * @param minute
     *            分
     * @param seconds
     *            秒
     * @return UNIX时间戳 @ 未知异常
     */
    public static long getTodayTime(int hours, int minute, int seconds) {
        long result = 0L;
        try {
            Calendar nowC = Calendar.getInstance();
            nowC.set(nowC.get(Calendar.YEAR), nowC.get(Calendar.MONTH), nowC.get(Calendar.DAY_OF_MONTH), hours, minute, seconds);
            result = nowC.getTime().getTime();
        } catch (Exception e) {
            result = 0L;
        }
        return result;
    }

    /**
     *
     * 获取今天零点的时间戳
     **/
    public Timestamp getDayBeginTimestamp() {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
                * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
                * 1000);
        return new Timestamp(date2.getTime());
    }

    /**
     *
     * 根据年月获取该月第一天的时间戳
     **/
    public static Long getMonthBeginTimestamp(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.YEAR, year); //year 为 int
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        time.set(Calendar.DAY_OF_MONTH, 1);
        time.set(Calendar.HOUR_OF_DAY,0);
        time.set(Calendar.MINUTE,0);
        time.set(Calendar.SECOND,0);
        return time.getTimeInMillis();

    }

    /**
     *
     * 根据年月获取该月最后的时间戳
     **/
    public static Long getMonthEndTimestamp(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.YEAR, year); //year 为 int
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        time.set(Calendar.DAY_OF_MONTH,day);
        time.set(Calendar.HOUR_OF_DAY,23);
        time.set(Calendar.MINUTE,59);
        time.set(Calendar.SECOND,59);

        return time.getTimeInMillis();

    }

}
