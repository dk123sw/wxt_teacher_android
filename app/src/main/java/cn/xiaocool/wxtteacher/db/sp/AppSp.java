package cn.xiaocool.wxtteacher.db.sp;

import android.content.Context;
import android.content.SharedPreferences;

import cn.xiaocool.wxtteacher.bean.AppInfo;

/**
 * Created by wzh on 2016/2/20.
 */
public class AppSp extends BaseSp<AppInfo> {

    public AppSp(Context context) {
        super(context, "app_sp");
    }

    @Override
    public void read(AppInfo app) {
        // 安全检查
        if (app == null) {
            app = new AppInfo();
        }
        if (getSP().contains("updateCity")) {
            app.setUpdateCity(getSP().getBoolean("updateCity", true));
        }
        if (getSP().contains("umengDeivceToken")) {
            app.setUmengDeivceToken(getSP().getString("umengDeivceToken", ""));
        }
        if (getSP().contains("deviceState")) {
            app.setDeviceState(getSP().getString("deviceState", "1"));
        }
        if (getSP().contains("lastVersionCode")) {
            app.setLastVersionCode(getSP().getString("lastVersionCode", ""));
        }
        if (getSP().contains("lastVersionPath")) {
            app.setLastVersionPath(getSP().getString("lastVersionPath", ""));
        }
    }

    @Override
    public AppInfo read() {
        AppInfo result = new AppInfo();
        read(result);
        return result;
    }

    @Override
    public void write(AppInfo app) {
        SharedPreferences.Editor editor = getSP().edit();
        if (!app.getUpdateCity()) {
            editor.putBoolean("updateCity", app.getUpdateCity());
        }
        if (!app.getUmengDeivceToken().equals("")) {
            editor.putString("umengDeivceToken", app.getUmengDeivceToken());
        }
        if (!app.getDeviceState().equals("")) {
            editor.putString("deviceState", app.getDeviceState());
        }
        if (!app.getLastVersionCode().equals("")) {
            editor.putString("lastVersionCode", app.getLastVersionCode());
        }
        if (!app.getLastVersionPath().equals("")) {
            editor.putString("lastVersionPath", app.getLastVersionPath());
        }
        editor.commit();
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        SharedPreferences.Editor editor = getSP().edit();
        editor.clear();
        editor.commit();
    }
}
