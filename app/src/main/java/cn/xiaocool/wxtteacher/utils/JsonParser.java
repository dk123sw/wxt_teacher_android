package cn.xiaocool.wxtteacher.utils;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.xiaocool.wxtteacher.bean.CommunicateListModel;
import cn.xiaocool.wxtteacher.bean.CommunicateModel;
import cn.xiaocool.wxtteacher.bean.SuggestModel;


public class JsonParser {
    public JsonParser() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断返回成功失败
     * @param context
     * @param result
     * @return
     */
    public static Boolean JSONparser(Context context, String result) {
        Boolean flag = true;
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("status").equals("success")) {
                flag = true;
            } else if (json.getString("status").equals("error")) {
                flag = false;
            }

        } catch (JSONException e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 字符串转模型（接收）
     * @param result
     * @return
     */
        public static List<CommunicateModel> getBeanFromJsonCommunicateModel(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<CommunicateModel>>() {
        }.getType());
    }


    /**
     * 字符串转模型（接收）
     * @param result
     * @return
     */
    public static List<CommunicateListModel> getBeanFromJsonCommunicateListModel(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<CommunicateListModel>>() {
        }.getType());
    }
    /**
     * 字符串转模型（接收）
     * @param result
     * @return
     */
    public static List<SuggestModel> getBeanFromJsonSuggestModel(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<SuggestModel>>() {
        }.getType());
    }
}
