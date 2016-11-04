package cn.xiaocool.wxtteacher.app;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wzh on 2016/2/20.
 */
public class ExitApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitApplication instance;

    private ExitApplication() {
    }

    // 单例模式获取唯一的exitapplication
    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;
    }

    // 添加activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 当Activity退出销毁时卸掉添加当前的Activity 防止OOM
     * @param activity
     */
    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    // 遍历所有的Activiy并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
