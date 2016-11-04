package cn.xiaocool.wxtteacher.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by THB on 2016/6/20.
 */
public class DateUtils {
    public static Date nextWeekMonday(Date date){
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //下周星期一
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    public static Date lastWeekMonday(Date date){
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.WEEK_OF_YEAR,-1);
        return cal.getTime();
    }
    public static Date currentWeekMonday(Date date){
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }
    public static Date currentWeekSunday(Date date){
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }
    public static Date lastDayWholePointDate(Date date) {
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }
    public static Date nextDayWholePointDate(Date date) {
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }
}
