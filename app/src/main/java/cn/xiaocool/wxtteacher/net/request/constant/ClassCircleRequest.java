package cn.xiaocool.wxtteacher.net.request.constant;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.utils.LogUtils;


/**
 * Created by mac on 16/2/18.
 */
public class ClassCircleRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;
    public ClassCircleRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo();
        user.readData(mContext);
    }

    public ClassCircleRequest(Context context) {
        super();
        this.mContext = context;
        user = new UserInfo();
        user.readData(mContext);

    }
    /**
     * 获取首页幻灯片列表
     *
     */
    public void getIndexSlideNewsList(final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                try {

                    String result_data =  NetUtil.getResponse(ClassCircle.NET_GET_INDEXSLIDENEWSLIST, data);

                    LogUtils.e("getIndexSlideNewsList", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    /**
     * 获取动态圈
     * "id":"28","name":"123","phone":"18401216028","password":"202cb962ac59075b964b07152d234b70","schoolid":"1","classid":"1","birthday":"1994.01.23","qq":"61912465","weixin":"weixin","photo":"babyteacher.png","from":"1","status":"1","user_type":"0","time":"1453804975","city":"\u70df\u53f0"}}
     * @return
     */
    public void getCircleList(final String begin_id,final String school_id,final String class_id,final String type, final int KEY){
    LogUtils.d("weixiaotong", "getCircleList");
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                try {
                    String  data = "&userid="+user.getUserId()+"&classid="+user.getClassId()+"&schoolid="+user.getSchoolId()+"&type="+type+"&beginid="+begin_id;
                    LogUtils.d("weixiaotong", data);
                    String result_data =  NetUtil.getResponse(ClassCircle.NET_GET_CIRCLELIST, data);
                    LogUtils.e("getIndexSlideNewsList", ClassCircle.NET_GET_CIRCLELIST+data);
                    LogUtils.e("getIndexSlideNewsList", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            };
        }.start();
    }
    public void Praise(final String id,final int KEY){
        LogUtils.d("weixiaotong", "getCircleList");
        new Thread() {
            Message msg = Message.obtain();
            public void run() {

                try {

                    String  data = "userid="+user.getUserId()+"&id="+id+"&type=1";
                    LogUtils.d("weixiaotong", data);
                    String result_data =  NetUtil.getResponse(ClassCircle.NET_SET_PRAISE, data);

                    LogUtils.e("getIndexSlideNewsList", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            };
        }.start();
    }
    public void DelPraise(final String id,final int KEY){
        LogUtils.d("weixiaotong", "getCircleList");
        new Thread() {
            Message msg = Message.obtain();
            public void run() {

                try {

                    String  data = "userid="+user.getUserId()+"&id="+id+"&type=1";
                    LogUtils.d("weixiaotong", data);
                    String result_data =  NetUtil.getResponse(ClassCircle.NET_DEL_PRAISE, data);

                    LogUtils.e("getIndexSlideNewsList", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            };
        }.start();
    }

}
