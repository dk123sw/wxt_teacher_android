package cn.xiaocool.wxtteacher.utils;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fu
 * Volley框架Post请求方法
 */
public class StringPostRequest extends StringRequest {
    private Map<String,String> myData = new HashMap<>();


    public StringPostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }


    public StringPostRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,url, listener, errorListener);


    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return myData;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
    public  void putMap(Map<String,String> params){
        if (params!=null){
            myData.putAll(params);
        }

    }
    //自己写的方法
    public void putParams(String key,String value){
        myData.put(key,value);
    }


}
