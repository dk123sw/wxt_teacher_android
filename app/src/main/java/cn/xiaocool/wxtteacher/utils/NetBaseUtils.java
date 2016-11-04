package cn.xiaocool.wxtteacher.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by mac on 16/5/9.
 */
public class NetBaseUtils {
    private static final String TAG = "NetUtil";
    private static int responsecode = 0;

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
     * 网络可用状�?�下，�?�过get方式向server端发送请求，并返回响应数�?
     *
     * @param strUrl
     *            请求网址
     * @param context
     *            上下�?
     * @return 响应数据
     */
    public static String getResponseForGet(String strUrl, Context context) {
        if (isConnnected(context)) {
            HttpGet httpRequest = new HttpGet(strUrl);
            return getRespose(httpRequest);
        }
        return null;
    }
//
//    /**
//     * 网络可用状�?�下，�?�过post方式向server端发送请求，并返回响应数�?
//     *
//     * @param market_uri
//     *            请求网址
//     * @param nameValuePairs
//     *            参数信息
//     * @param context
//     *            上下�?
//     * @return 响应数据
//     */
    public static String getResponseForPost(String market_uri, List<NameValuePair> nameValuePairs, Context context) {
        if (isConnnected(context)) {
            if (null == market_uri || "" == market_uri) {
                return null;
            }
            HttpPost request = new HttpPost(market_uri);
            try {
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                return getRespose(request);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 工作圈图像上�?
     *
     * @param market_uri
     * @param nameValuePairs
     * @param context
     * @return
     */
    public static String getResponseForImg(String market_uri, List<NameValuePair> nameValuePairs, Context context) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(market_uri);
        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < nameValuePairs.size(); index++) {
                if (nameValuePairs.get(index).getName().equalsIgnoreCase("upfile")) {
                    entity.addPart(nameValuePairs.get(index).getName(), new FileBody(new File(nameValuePairs.get(index).getValue())));
                } else {
                    entity.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                return Response;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//
//    /**
//     * 响应客户端请�?
//     *
//     * @param //
//     *            客户端请求get/post
//     * @return 响应数据
//     */
    private static String getRespose(HttpUriRequest request) {
        try {
            HttpResponse httpResponse = new DefaultHttpClient().execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
