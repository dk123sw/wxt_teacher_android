package cn.xiaocool.wxtteacher.utils;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14.
 */
public class VolleyUtil {

    public static final String URL ="VolleyUtil=====URL";
    public static final String PARAMS ="VolleyUtil=====PARAMS";
    public static final String FILES ="VolleyUtil=====FILES";
    public static final String RESULT ="VolleyUtil=====RESULT";


    /**
     * 实现网络请求数据
     *
     * @param url      请求地址
     * @param callback 返回数据的回调
     * @param params   参数
     */
    public static void VolleyPostRequest(final Context context, String url, final VolleyJsonCallback callback, Map<String, String> params) {
        LogUtils.e(URL, url);
        if (params != null) {
            LogUtils.e(PARAMS, params.toString());
        }
        StringPostRequest request = new StringPostRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.e(RESULT, s);
                callback.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError();
            }
        });
        request.putMap(params);
        Volley.newRequestQueue(context).add(request);
    }
    /**
     * 实现网络请求数据
     *
     * @param url      请求地址
     * @param callback 返回数据的回调
     * @param params   参数
     */

    public static void VolleyPostFileRequest(final Context context, final String url,
                                             final Map<String, File> files,
                                             final Map<String, String> params,
                                             final VolleyJsonCallback callback) {
        LogUtils.e(URL, url);
        if (params != null) {
            LogUtils.e(PARAMS, params.toString());
        }
        if (files != null) {
            LogUtils.e(FILES, files.toString());
        }
        MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                LogUtils.e(RESULT , s);
                callback.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError();
            }
        }) {

            @Override
            public Map<String, File> getFileUploads() {
                return files;
            }

            @Override
            public Map<String, String> getStringUploads() {
                return params;
            }

        };

        Volley.newRequestQueue(context).add(multiPartRequest);
    }

    /**
     * 实现网络请求数据
     *
     * @param url      请求地址
     * @param callback 返回数据的回调
     */
    public static void VolleyGetRequest(Context context, String url, final VolleyJsonCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.e(RESULT, s);
                callback.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError();
            }
        });

        Volley.newRequestQueue(context).add(request);
    }


    // 以下是在同一个类中定义的接口
    public interface VolleyJsonCallback {
        void onSuccess(String result);

        void onError();
    }


}
