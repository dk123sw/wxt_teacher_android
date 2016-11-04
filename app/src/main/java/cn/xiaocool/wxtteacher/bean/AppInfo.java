package cn.xiaocool.wxtteacher.bean;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.Serializable;

import cn.xiaocool.wxtteacher.db.sp.AppSp;

/**
 * Created by wzh on 2016/2/20.
 */
public class AppInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean updateCity;
    private String umengDeivceToken;
    private String deviceState;
    private String lastVersionCode;
    private String lastVersionPath;

    public boolean isFristLogin(Context context) {
        AppInfo app = new AppInfo();
        app.readData(context);
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String currentVersion = "" + pi.versionCode;
            if (currentVersion.equals(app.getLastVersionCode())) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void readData(Context context) {
        AppSp sp = new AppSp(context);
        sp.read(this);
    }

    public void writeData(Context context) {
        AppSp sp = new AppSp(context);
        sp.write(this);
    }

    public Boolean getUpdateCity() {
        if (updateCity == null) {
            return true;
        } else if (updateCity.equals("null")) {
            return true;
        }
        return updateCity;
    }

    public void setUpdateCity(Boolean updateCity) {
        this.updateCity = updateCity;
    }

    public String getUmengDeivceToken() {
        if (umengDeivceToken == null) {
            return "";
        } else if (umengDeivceToken.equals("null")) {
            return "";
        }
        return umengDeivceToken;
    }

    public void setUmengDeivceToken(String umengDeivceToken) {
        this.umengDeivceToken = umengDeivceToken;
    }

    public String getDeviceState() {
        return "1";
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }


    public String getLastVersionCode() {
        if (lastVersionCode == null) {
            return "";
        } else if (lastVersionCode.equals("null")) {
            return "";
        }
        return lastVersionCode;
    }

    public void setLastVersionCode(String lastVersionCode) {
        this.lastVersionCode = lastVersionCode;
    }

    public String getLastVersionPath() {
        if (lastVersionPath == null) {
            return "";
        } else if (lastVersionPath.equals("null")) {
            return "";
        }
        return lastVersionPath;
    }

    public void setLastVersionPath(String lastVersionPath) {
        this.lastVersionPath = lastVersionPath;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "AppInfo [updateCity=" + updateCity + ", umengDeivceToken=" + umengDeivceToken + ", deviceState=" + deviceState + ", lastVersionCode=" + lastVersionCode + ", lastVersionPath="
                + lastVersionPath + "]";
    }

}
