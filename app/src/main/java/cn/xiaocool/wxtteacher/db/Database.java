package cn.xiaocool.wxtteacher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wzh on 2016/2/20.
 */
public class Database extends SQLiteOpenHelper {
    private Context mContext;

    public Database(Context context) {
        super(context.getApplicationContext(), Const_DB.DB_NAME, null, Const_DB.DB_VERSION);
        this.mContext = context.getApplicationContext();
    }

    // onCreate 数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Const_DB.DATABASE_CREATE_PLACE);// 创建Place表
        db.execSQL(Const_DB.DATABASE_CREATE_CHANNEL);// 创建Channel表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    protected Context getContext() {
        return mContext;
    }

    protected Context getApplicationContext() {
        return getContext().getApplicationContext();
    }
}
