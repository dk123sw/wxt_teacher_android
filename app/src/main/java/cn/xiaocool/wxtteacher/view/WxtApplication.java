package cn.xiaocool.wxtteacher.view;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by wzh on 2016/2/21.
 */
public class WxtApplication extends Application {
    private UserInfo user = new UserInfo();
    private static WxtApplication mInstance;

    public static int UID;
    public static final String APP_ID = "wx9aa88ce0b796d68f";
    public IWXAPI api; //第三方app与微信通信的openapi接口


    //环信注册


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        mInstance = this;

        user.readData(getApplicationContext());

        //设置极光推送
        setJPush();


        //设置微信分享
        setWeShare();


        initImageLoader(this);

    }

    /**
     * 设置推送
     */
    private void setJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }



    private void setWeShare() {
        //微信分享
        api = WXAPIFactory.createWXAPI(this, APP_ID); //初始化api
        api.registerApp(APP_ID); //将APP_ID注册到微信中
    }





    public static WxtApplication getInstance() {
        return mInstance;
    }








    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "wxt/Cache");// 获取到缓存的目录地址
        LogUtils.d("cacheDir", cacheDir.getPath());
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(480, 800)
                // max width,max height，即保存的每个缓存文件的最大长宽default=device screen dimensions
                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null)
                        // Can slow ImageLoader, use it carefully (Better don't use
                        // it)设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1).tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                        // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
                        // 1024))
                        // You can pass your own memory cache
                        // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCache(new WeakMemoryCache())
                        // implementation你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(30 * 1024 * 1024)
                        // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5加密
                        // .discCacheFileNameGenerator(new
                        // HashCodeFileNameGenerator())// 将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(1000) // 缓存的File数量
                .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                        // connectTimeout (5s), readTimeout(30s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }


}

