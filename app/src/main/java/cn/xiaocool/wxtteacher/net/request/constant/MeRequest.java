package cn.xiaocool.wxtteacher.net.request.constant;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by wzh on 2016/2/27.
 */
public class MeRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;
    public static String id;

    public MeRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo();
        user.readData(mContext);
        id = user.getUserId();
    }

    //    public MeRequest(Context context) {
//        super();
//        this.mContext = context;
//        user = new UserInfo();
//        user.readData(mContext);
//
//    }
    //服务状态
    public void serviceStatus() {
        LogUtils.d("weixiaotong", "getCircleList");
        Log.e("id is this", id);
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                try {
                    String data = "&stuid=" + id;
                    LogUtils.d("weixiaotong", data);
                    String result_data = NetUtil.getResponse(WebHome.ME_GET_SERVICE_STATUS, data);
                    LogUtils.e("announcement", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GETSERVICESTATUS;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的宝宝信息
    public void MyBabyAll() {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                try {
                    String data = "&userid=" + id;
                    String result_data = NetUtil.getResponse(WebHome.WEB_GET_BABY_INFO, data);
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BABY_INFO;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取客服联系方式
    public void onlineService() {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                try {
//                    String data = "&userid=" + id;
                    String data = "schoolid=" + user.getSchoolId();
                    String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=service_phone", data);
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ONLINE_SERVICE;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //编辑教师资料
    public void editTeacherData(){
        new Thread(){
            Message msg = Message.obtain();
            public void run(){
                try {

                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }

        }.start();
    }

}
