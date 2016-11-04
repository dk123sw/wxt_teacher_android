package cn.xiaocool.wxtteacher.ui;

import android.content.Context;
import android.os.Handler;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ProgressViewUtil {

    private static KProgressHUD hud;
    public static KProgressHUD show(Context context){
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        scheduleDismiss(context);
        return hud;
    }


    public static void dismiss(){
        if (hud!=null){
            hud.dismiss();
        }
    }

    private static void scheduleDismiss(final Context context) {
        if (hud!=null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hud.dismiss();
                }
            }, 5000);
        }

    }
}
