package cn.xiaocool.wxtteacher.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.MyExpandableListViewAdapter;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressTeacherFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private ExpandableListView address_class;
    private String teacherID;
    private List<Map<String, String>> classAddresses = new ArrayList<>();
    private List<List<Map<String, String>>> teachersAddresses = new ArrayList<>();
    private SQLiteDatabase db;  //数据库对象
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.TEACHER:
                    if (msg.obj != null) {
                        ProgressViewUtil.dismiss();
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                address_class = (ExpandableListView) getView().findViewById(R.id.address_class);
                                address_class.setGroupIndicator(null);
                                JSONArray dataArray = obj.optJSONArray("data");
                                for (int i = 0; i < 1; i++) {
                                    Map<String, String> title = new HashMap<>();
                                    title.put("group", "老师");
                                    classAddresses.add(title);
                                    List<Map<String, String>> teacherInfo = new ArrayList<>();
                                    for (int j = 0; j < dataArray.length(); j++) {

                                        if (dataArray.optJSONObject(j) != null) {
                                            Map<String, String> title_1_content_1 = new HashMap<String, String>();
                                            title_1_content_1.put("teacherName", dataArray.optJSONObject(j).optString("name"));
                                            title_1_content_1.put("teacherPhone", dataArray.optJSONObject(j).optString("phone"));
                                            title_1_content_1.put("teacherID", dataArray.optJSONObject(j).optString("id"));
                                            title_1_content_1.put("teacherAvatar", NetBaseConstant.NET_CIRCLEPIC_HOST+dataArray.optJSONObject(j).optString("photo"));
                                            teacherID = dataArray.optJSONObject(j).optString("id");
                                            LogUtils.e("Infoteacherid", teacherID);
                                            Log.e("group", classAddresses.get(0).get("group"));
                                            teacherInfo.add(title_1_content_1);
                                            insertDataToTable(title_1_content_1.get("teacherID"), title_1_content_1.get("teacherName"), title_1_content_1.get("teacherAvatar"));
                                        }

                                    }
                                    Log.e("============", classAddresses.get(0).get("group"));
                                    teachersAddresses.add(teacherInfo);

                                }
                                // 加入列表
                                Log.e("group", classAddresses.get(0).get("group"));
                                Log.e("group", teachersAddresses.get(0).get(0).get("teacherName"));

                                MyExpandableListViewAdapter sela = new MyExpandableListViewAdapter(classAddresses, teachersAddresses, context);
                                address_class.setAdapter(sela);
//                                address_class.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                                    @Override
//                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                                        return false;
//                                    }
//                                });
                                int groupCount = address_class.getCount();
                                for (int i = 0; i < groupCount; i++) {
                                    address_class.expandGroup(i);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("============", classAddresses.get(0).get("group"));
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_address_parent, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProgressViewUtil.show(getActivity());
        address_class = (ExpandableListView) getView().findViewById(R.id.address_class);
        context = getActivity();
        //打开或者创建数据库, 这里是创建数据库
        db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().toString() + "/users.db", null);
        System.out.println(getActivity().getFilesDir().toString() + "/users.db");

        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table';", null);
        while (cursor.moveToNext()) {
            //遍历出表名
            String name = cursor.getString(0);
            Log.i("System.out", name);
        }
        news();
//        setListData();
    }

    private void news() {
        new NewsRequest(getActivity(), handler).getTeacher();
    }


    /*
     * 插入或更新数据到数据库中
     */
    public void insertDataToTable(String userid, String username, String useravatar) {


        try {
            db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().toString() + "/users.db", null);
            Cursor cursor = db.rawQuery("select * from users_table where user_id=?", new String[]{userid});
            if (cursor.moveToFirst()) {
                updateData(userid, username, useravatar);
            } else {
                insertData(userid, username, useravatar);
            }
        } catch (SQLiteException exception) {
            db.execSQL("create table users_table (" +
                    "_id integer primary key autoincrement, " + "user_id varchar(50)," +
                    "user_name varchar(50), " +
                    "user_avatar varchar(500))");
            Log.e("数据库操作异常","生生世世是生生世世是三三三三三三三");
            insertData(userid, username, useravatar);


        }

    }

    /*
     * 向数据库中更新数据
     */
    private void updateData(String userid, String username, String useravatar) {
        db.execSQL("update users_table set user_name=? , user_avatar=? where user_id=?", new Object[]{username, useravatar, userid});

    }


    /*
     * 向数据库中插入数据
	 */
    private void insertData(String userid, String username, String useravatar) {
        db.execSQL("insert into users_table values(null,?, ?, ?)", new String[]{userid, username, useravatar});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db.isOpen())db.close();
    }

}
