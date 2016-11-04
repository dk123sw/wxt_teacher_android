package cn.xiaocool.wxtteacher.utils.find;

/**
 * Created by wzh on 2016/2/20.
 */

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算时间
 */
@SuppressLint("SimpleDateFormat")
public class CountTimeUtils {
    private static CountTimeUtils ct;
    private static String time;
    public CountTimeUtils(String time) {
        CountTimeUtils.time=time;
    }

    public static CountTimeUtils getInstance() {
        if (ct == null) {
            ct = new CountTimeUtils(time);
        }
        return ct;
    }

    /**
     * // 此方法计算时间毫秒
     *
     * @param inVal
     * @return
     */
    public long fromDateStringToLong(String inVal) {
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:ss");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }

    /**
     * // 此方法用于获得当前系统时间（格式类型2015-12-31 17:22:58）
     *
     * @return
     */
    @SuppressWarnings({ "unused", "deprecation" })
    private static String dqsj() {
        Date date = new Date(); // 实例化日期类型
        String today = date.toLocaleString(); // 获取当前时间
        System.out.println("获得当前系统时间 " + today); // 显示
        return today; // 返回当前时间
    }



}

