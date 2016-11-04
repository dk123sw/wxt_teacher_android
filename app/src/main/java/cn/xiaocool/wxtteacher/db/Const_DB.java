package cn.xiaocool.wxtteacher.db;

/**
 * Created by wzh on 2016/2/20.
 */
/**
 * 数据库常量
 */
public interface Const_DB {
    /**
     * 数据库名字
     */
    public final String DB_NAME = "WxtParent";
    /**
     * 数据库的版本
     */
    public final int DB_VERSION = 1;
    /**
     * Place表
     */
    public static final String DATABASE_TABLE_PLACE = "Place";// 表名
    public static final String DATABASE_TABLE_PLACE_KEY = "_id";// 主键
    public static final String DATABASE_TABLE_PLACE_PROVINCE_ID = "provinceId";//
    public static final String DATABASE_TABLE_PLACE_PROVINCE_NAME = "provinceName";//
    public static final String DATABASE_TABLE_PLACE_CITY_ID = "cityId";//
    public static final String DATABASE_TABLE_PLACE_CITY_NAME = "cityName";//
    public static final String DATABASE_TABLE_PLACE_DISTRICT_ID = "districtId";//
    public static final String DATABASE_TABLE_PLACE_DISTRICT_NAME = "districtName";//

    // 获取Place表
    public static final String[] PLACE_PROJECTION = new String[] { DATABASE_TABLE_PLACE_KEY, DATABASE_TABLE_PLACE_PROVINCE_ID, DATABASE_TABLE_PLACE_PROVINCE_NAME, DATABASE_TABLE_PLACE_CITY_ID,
            DATABASE_TABLE_PLACE_CITY_NAME, DATABASE_TABLE_PLACE_DISTRICT_ID, DATABASE_TABLE_PLACE_DISTRICT_NAME };
    // 创建Place表的语句
    public static final String DATABASE_CREATE_PLACE = "create table Place (_id integer primary key autoincrement," + "provinceId text," + "provinceName text," + "cityId text," + "cityName text,"
            + "districtId integer unique," + "districtName text);";
    /**
     * 推荐频道Channel表
     */
    public static final String DATABASE_TABLE_CHANNEL = "Channel";// 表名
    public static final String DATABASE_TABLE_CHANNEL_KEY = "_id";// 主键
    public static final String DATABASE_TABLE_CHANNEL_ID = "channelId";// 频道ID
    public static final String DATABASE_TABLE_CHANNEL_NAME = "channelName";// 频道名称
    public static final String DATABASE_TABLE_CHANNEL_ORDERID = "orderId";// 频道顺序
    public static final String DATABASE_TABLE_CHANNEL_SELECTED = "selected";// "1"为用户频道,"0"为其他频道
    // 获Channel表
    public static final String[] CHANNEL_PROJECTION = new String[] { DATABASE_TABLE_CHANNEL_KEY, DATABASE_TABLE_CHANNEL_ID, DATABASE_TABLE_CHANNEL_NAME, DATABASE_TABLE_CHANNEL_ORDERID,
            DATABASE_TABLE_CHANNEL_SELECTED };
    // 创建Channel表的语句
    public static final String DATABASE_CREATE_CHANNEL = "create table Channel (_id integer primary key autoincrement," + "channelId text," + "channelName text," + "orderId text," + "selected text);";

}
