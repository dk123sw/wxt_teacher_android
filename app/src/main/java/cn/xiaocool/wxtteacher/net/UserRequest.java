package cn.xiaocool.wxtteacher.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.bean.AppInfo;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.net.request.constant.UserNetConstant;
import cn.xiaocool.wxtteacher.net.request.constant.WebHome;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.NetBaseUtils;


/**
 * Created by mac on 16/5/9.
 */
public class UserRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;
    private AppInfo appInfo;
    private String TAG = "UserRequest.class";

    public UserRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo();
        user.readData(mContext);
        appInfo=new AppInfo();
        appInfo.readData(mContext);
    }

    public UserRequest(Context context) {
        super();
        this.mContext = context;
        user = new UserInfo();
        user.readData(mContext);
        appInfo=new AppInfo();
        appInfo.readData(mContext);
    }

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     * @param KEY
     */
    public void Login(final String phone, final String pwd, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("phone", phone));
                params.add(new BasicNameValuePair("password", pwd));
//                params.add(new BasicNameValuePair("DeviceToken", appInfo.getUmengDeivceToken()));
//                params.add(new BasicNameValuePair("DeviceState", appInfo.getDeviceState()));
                LogUtils.i("TuiSong", "device_token" + params.toString());
                String result = NetBaseUtils.getResponseForPost(UserNetConstant.NET_USER_LOGIN, params, mContext);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            };
        }.start();
    }
    /**
     *
     * @param userIdTemp
     */
    public void obainUserData(final String userIdTemp, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("memberId", userIdTemp));
                try {
                    String result = NetBaseUtils.getResponseForPost(UserNetConstant.NET_USER_GET_DATA, params, mContext);
                    JSONObject json = new JSONObject(result);
                    LogUtils.i("UserData", "---->" + json.toString());
                    int state = json.getInt("state");
                    if (state == 1) {
                        user.setUserName(json.getString("MemberName"));
//                        user.setUserGender(json.getString("MemberSex"));
//                        user.setUserCityId(json.getString("MemberCityId"));
//                        user.setUserPosition(json.getString("MemberPostition"));
//                        user.setUserCompany(json.getString("MemberCompany"));
                        user.setUserImg(json.getString("MemberImg"));
                        user.writeData(mContext);
                    }
                    msg.what = KEY;
                    msg.obj = state;
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            };
        }.start();
    }
    /**
     * 判断是否已注
     *
     * @param phone
     * @param KEY
     */
    public void isCanRegister(final String phone, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("phone", phone));
                String result = NetBaseUtils.getResponseForPost(UserNetConstant.NET_USER_IS_REG, params, mContext);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            };
        }.start();
    }
    /**
     * 验证
     *
     * @param phone
     * @param KEY
     */
    public void getVerficationCode(final String phone, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("phone", phone));
                String result = NetBaseUtils.getResponseForPost(UserNetConstant.NET_USER_GET_VERIFICATION_CODE, params, mContext);
                LogUtils.i("TempSDFG", "json-->" + result);
                LogUtils.i("TempSDFG", "json-->" + UserNetConstant.NET_USER_GET_VERIFICATION_CODE);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            };
        }.start();
    }
    /**
     * 修改用户的密
     *
     * @param password
     * @param KEY
     */
    public void updateUserPassWord(final String password, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("phone", user.getUserPhone()));
                params.add(new BasicNameValuePair("password", password));
//                params.add(new BasicNameValuePair("MemberPass", user.getUserPassword()));
                String result = NetBaseUtils.getResponseForPost(UserNetConstant.NET_USER_UPDATE_PASS, params, mContext);
                LogUtils.i(TAG, "修改用户的密�?--->" + result);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            };
        }.start();
    }
    /**
     * 注册
     * @param pwd
     *
     * @param pwd
     * @param KEY
     */
    public void register(final String pwd, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("phone", user.getUserPhone()));
                params.add(new BasicNameValuePair("password", pwd));
                params.add(new BasicNameValuePair("sex", "1"));
                params.add(new BasicNameValuePair("avatar", user.getUserImg()));
//                params.add(new BasicNameValuePair("MemberCompany", user.getUserCompany()));
//                params.add(new BasicNameValuePair("MemberPostition", user.getUserPosition()));
                params.add(new BasicNameValuePair("name", user.getUserName()));
//                params.add(new BasicNameValuePair("MemberCityId", user.getUserCityId()));
//                params.add(new BasicNameValuePair("DeviceToken", appInfo.getUmengDeivceToken()));
//                params.add(new BasicNameValuePair("DeviceState", appInfo.getDeviceState()));
                LogUtils.i("TuiSong", "device_token" + params.toString());
//                String result = NetBaseUtils.getResponseForImg(UserNetConstant.NET_USER_REGISTER, params, mContext);
                String result = NetBaseUtils.getResponseForPost(UserNetConstant.NET_USER_REGISTER, params, mContext);
                LogUtils.i("UserRegister", "---->" + result);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            };
        }.start();
    }
    /**
     * 上传图片
     *
     * @param img
     * @param KEY
     */
    public void updateUserImg(final String img, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("upfile", img));
                String result = NetBaseUtils.getResponseForImg(UserNetConstant.NET_USER_UPLOAD_HEAD_IMG, params, mContext);
//                LogUtils.i(TAG, "修改用户的头�?--->" + img);
//                LogUtils.i(TAG, "修改用户的头�?--->" + result);
                msg.what = KEY;
                msg.obj = result;
                handler.sendMessage(msg);
            };
        }.start();
    }
//    public void AddWorkRing(final String workMatter, final String workRadio, final int KEY) {
//        new Thread() {
//            Message msg = Message.obtain();
//
//            public void run() {
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("userid", user.getUserId()));
//                params.add(new BasicNameValuePair("type", "1"));
//                params.add(new BasicNameValuePair("content", workMatter));
//                params.add(new BasicNameValuePair("reciveid", "12"));
//
//                String result = NetBaseUtils.getResponseForImg(UserNetConstant.NET_ADD_WORK_RING, params, mContext);
////                LogUtils.i(TAG, "��ӹ���Ȧ--->" + result);
//                msg.what = KEY;
//                msg.obj = result;
//                handler.sendMessage(msg);
//            };
//        }.start();
//    }
    //�ϴ�ͼƬ
    public void pushImg(final String picPath,final int KEY){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                Log.i("picPath",picPath);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("upfile",picPath));
                String result = NetBaseUtils.getResponseForImg(UserNetConstant.NET_USER_UPLOAD_HEAD_IMG, params, mContext);
                Log.e("pushhead","mmmmmmmmmmmm");
                try {
                    JSONObject obj = new JSONObject(result);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    public void addAnnounceImg(final String title,final String contents,final String picname,final int KEY){
        new Thread(){
            Message msg = Message.obtain();

            @Override
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("userid",user.getUserId()));
                params.add(new BasicNameValuePair("type","3"));
                params.add(new BasicNameValuePair("title",title));
                params.add(new BasicNameValuePair("content",contents));
                if (picname !=null){
                    params.add(new BasicNameValuePair("photo",picname));
                }
                params.add(new BasicNameValuePair("reciveid","12"));
                String result = NetBaseUtils.getResponseForPost(WebHome.WRITE_ANNOUNCEMENT, params, mContext);
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    msg.what = KEY;
                    msg.obj = object;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                        handler.sendMessage(msg);
                }

            }
        }.start();
    }

    /**
     * 更新头像
     * */
    public void updateHeadImg(final String avatar,final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid="+user.getUserId()+"&avatar="+avatar;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=UpdateUserAvatar", data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    /**
     * 更新名字
     * */
    public void updateName(final String avatar,final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid="+user.getUserId()+"&nicename="+avatar;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=UpdateUserName", data);
                Log.e("updateName","http://wxt.xiaocool.net/index.php?g=apps&m=index&a=UpdateUserName"+ data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 更新性别
     * */
    public void updateSex(final int avatar,final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid="+user.getUserId()+"&sex="+avatar;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=UpdateUserSex", data);
                Log.e("updateSex","http://wxt.xiaocool.net/index.php?g=apps&m=index&a=UpdateUserSex"+ data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 更新电话
     * */
    public void updatePhone(final String phone,final String code,final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid="+user.getUserId()+"&phone="+phone+"&code="+code;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=UpdateUserPhone", data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
