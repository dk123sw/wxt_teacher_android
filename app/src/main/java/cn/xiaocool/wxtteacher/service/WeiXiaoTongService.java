package cn.xiaocool.wxtteacher.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by wzh on 2016/2/20.
 */
public class WeiXiaoTongService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if (intent == null) {

        } else {
            Integer instruction = intent.getIntExtra("instruction", -1);
            switch (instruction) {
                case 1000:// UPLOAD_ADDRESSBOOK 上传通讯录指令
//                    UploadContactTask uploadContactTask = new UploadContactTask(getApplicationContext());
//                    uploadContactTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                case 1001:// SYNCHRONOUS_USER_DATA 用户数据的同步指令
//                    GetUserDataTask getUserDataTask = new GetUserDataTask(getApplicationContext());
//                    getUserDataTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                case 1002:// SYNCHRONOUS_USER_TAG 1002;//同步用户标签
//                    GetUserTagTask getUserTagTask = new GetUserTagTask(getApplicationContext());
//                    getUserTagTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                case 1003:// SYNCHRONOUS_USER_EXPERIENCE 1003;//同步用户工作经历
//                    GetUserExperienceTask getUserExperienceTask = new GetUserExperienceTask(getApplicationContext());
//                    getUserExperienceTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                case 1004:// CHECK_VERSION 检查版本指令
                    String source = intent.getStringExtra("source");
//                    CheckVersionUpdateTask checkVersionUpdateTask = new CheckVersionUpdateTask(getApplicationContext());
//                    checkVersionUpdateTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, source);
                    break;
                case 1005:// SYNCHRONOUS_USER_CHANNAL 用户频道的同步指令
//                    FindUserChannalTask findUserChannalTask = new FindUserChannalTask(getApplicationContext());
//                    findUserChannalTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                case 1006:// SYNCHRONOUS_ALL_CHANNAL 推荐频道的同步指令
//                    FindRecommendChannalTask findRecommendChannalTask = new FindRecommendChannalTask(getApplicationContext());
//                    findRecommendChannalTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                case 1007:// SYNCHRONOUS_ALL_CITY 城市的同步指令
//                    FindAllCityTask findAllCityTask = new FindAllCityTask(getApplicationContext());
//                    findAllCityTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
                    break;
                default:
                    break;
            }
        }
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
}