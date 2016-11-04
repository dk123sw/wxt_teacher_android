package cn.xiaocool.wxtteacher.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by wzh on 2016/2/20.
 */
public class HttpTool {
    //    private static String baseurl = "http://www.xiaocool.cn:8016/index.php?g=apps&m=index&";
    private static final String TAG = "NetUtil";
    /**
     * 网络连接是否可用
     */
    public static boolean isConnnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

            if (null != networkInfo) {
                for (NetworkInfo info : networkInfo) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        LogUtils.e(TAG, "the net is ok");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     *
     * @param is
     * @return
     * @throws IOException
     */
    private static String getStringFromInputStream(InputStream is)
            throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代碼 必須熟練
        byte[] buffer = new byte[1024];
        int len = -1;
        // 一定要寫len=is.read(buffer)
        // 如果while((is.read(buffer))!=-1)則無法將數據寫入buffer中
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 將流中的數據轉換成字串,採用的編碼是utf-8(模擬器默認編碼)
        os.close();
        return state;
    }
    public static String getResponse(String url,String data){
        String result = "";
        HttpURLConnection conn = null;
        try{
            // 創建一個url對象

            URL mURL = new URL(url);
            // 調用URL的openConnection()方法,獲得HttpURLConnection對象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");       // 設置請求方法post
            conn.setReadTimeout(5000);           // 設置讀取超時
            conn.setConnectTimeout(10000);       // 設置網路連結超時
            conn.setDoInput(true);               // 開啟輸入流
            conn.setDoOutput(true);              // 開啟輸出流
            conn.setUseCaches(false);            // 使用Post方式不能使用暫存

            // post請求參數

            // 獲得一個輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();// 調用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                result = getStringFromInputStream(is);
                return result;
            } else {
                // Log.i(TAG, "訪問失敗" + responseCode);
                return result = "请求失败，请检查网络";
            }
        }catch (Exception e){
            return result = "请求失败，请检查网络";
        }
    }
    public static String Login(String phone, String password,String jpushid) {

        String url = NetBaseConstant.NET_API_HOST + "a=applogin&";
        String data = "phone="+phone+"&password="+password+"&jgtoken=" + jpushid;
        String result = "";
        result = getResponse(url,data);
        return  result;
    }

    public static String UserVerify(String phone,String code){
        String url = NetBaseConstant.NET_API_HOST + "a=UserVerify&";
        String data = "phone="+phone+"&code="+code;
        String result = "";
        result = getResponse(url,data);
        return  result;
    }
    public static  String SendVerifycode(String phone){
        String url = NetBaseConstant.NET_API_HOST + "a=";
        String data= "phone="+phone;
        String result="";
        result = getResponse(url,data);
        return result;

    }
    //注册设置密码
    public static String SetPassword(int userid,String pass){
        String url = NetBaseConstant.NET_API_HOST + "a=UserActivate&";
        String data = "userid="+userid+"&pass="+pass;
        String result = "";
        result = getResponse(url,data);
        Log.i("data", result);
        return result;
    }
    //未激活的手机号发送验证码
    public static String SendVerifyCode(String phone){
        String url = NetBaseConstant.NET_API_HOST + "a=SendMobileCode&";
        String data = "phone="+phone;
        String result = "";
        result = getResponse(url,data);
        Log.e("verify", result);
        return result;
    }
    //忘记密码（已激活手机号）验证验证码
    public static String ForgetPasswordSendVerify(String phone,String code){
        String url = NetBaseConstant.NET_API_HOST + "a=forgetPass_Verify&";
        String data = "phone="+phone+"&code="+code;
        String result = "";
        result = getResponse(url,data);
        Log.i("data", result);
        Log.e("wzh",data);
        return result;
    }

    //忘记密码（已激活手机号）写入数据库手机号和密码
    public static String ResetPassword(int userid,String password){
        String url = NetBaseConstant.NET_API_HOST + " a=forgetPass_Activate&";
        String data = "userid="+userid+"&pass="+password;
        //Log.e("wzhtest", String.valueOf(userid));
        // Log.e("wzhtest",password);
        String result = "";
        result = getResponse(url,data);
        return result;

    }
    /**
     *获取三张轮播图片
     * @return
     */
    public static String getViewPagerData(){
        String url = NetBaseConstant.NET_API_HOST + "a=Viwepager&";
        String result = "";
        String  data = "";
        result = getResponse(url,data);
        return  result;
    }

    /**
     * 获取动态圈
     * "id":"28","name":"123","phone":"18401216028","password":"202cb962ac59075b964b07152d234b70","schoolid":"1","classid":"1","birthday":"1994.01.23","qq":"61912465","weixin":"weixin","photo":"babyteacher.png","from":"1","status":"1","user_type":"0","time":"1453804975","city":"\u70df\u53f0"}}
     * @param beginid
     * @param schoolid
     * @param classid
     * @return
     */
    public static String getMicroblog(String beginid,String schoolid,String classid){
        String url = NetBaseConstant.NET_API_HOST + "a=GetMicroblog";
        String result = "";
        String  data = "beginid="+beginid+"&schoolid="+schoolid+"&classid+"+classid;
        result = getResponse(url,data);
        return  result;
    }

    /**
     * 点赞
     * @param userid
     * @param mid
     * @param time
     * @return
     */
    public static String setLike(String userid,String mid,String time){
        String url=NetBaseConstant.NET_API_HOST+"a=SetLike";
        String result="";
        String data="userid="+userid+"&mid="+mid+"time="+time;
        result = getResponse(url,data);
        return  result;
    }

    /**
     * 取消点赞
     * @param userid
     * @param mid
     * @return
     */
    public static String resetLike(String userid,String mid){
        String url=NetBaseConstant.NET_API_HOST+"a=ResetLike";
        String result="";
        String data="userid="+userid+"&mid="+mid;
        result = getResponse(url,data);
        return  result;
    }
}
