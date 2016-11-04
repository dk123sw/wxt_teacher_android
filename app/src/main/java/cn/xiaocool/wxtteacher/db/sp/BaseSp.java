package cn.xiaocool.wxtteacher.db.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wzh on 2016/2/20.
 */
public abstract class BaseSp<T> {
    private Context context;
    private SharedPreferences sharedPreferences;
    private String spName;

    public BaseSp(Context context, String spName) {
        this.context = context;
        this.spName = spName;
        this.sharedPreferences = null;
    }

    public SharedPreferences getSP() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(spName, Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public abstract T read() throws Exception;

    public abstract void read(T bean) throws Exception;

    public abstract void write(T bean) throws Exception;

    public abstract void clear();
}
