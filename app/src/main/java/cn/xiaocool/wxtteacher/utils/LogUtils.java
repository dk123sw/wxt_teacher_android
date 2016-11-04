package cn.xiaocool.wxtteacher.utils;

import android.util.Log;

/**
 * Created by wzh on 2016/2/20.
 */
public class LogUtils {
    private static final boolean d = true;

    public static void i(String tag, String message) {
        if (d) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (d) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (d) {
            Log.e(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (d) {
            Log.v(tag, message);
        }
    }

    public static void w(String tag, String message) {
        // TODO Auto-generated method stub
        if (d) {
            Log.w(tag, message);
        }
    }
}

