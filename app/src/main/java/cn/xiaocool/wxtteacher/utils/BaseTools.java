package cn.xiaocool.wxtteacher.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by wzh on 2016/2/20.
 */
public class BaseTools {

    /** ��ȡ��Ļ�Ŀ�� */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
