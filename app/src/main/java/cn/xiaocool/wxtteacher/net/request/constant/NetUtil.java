package cn.xiaocool.wxtteacher.net.request.constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.xiaocool.wxtteacher.utils.LogUtils;


/**
 * Created by mac on 16/2/18.
 */
public class NetUtil {
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
            return result = "请求异常，请检查网络";
        }
    }
}
