package cn.xiaocool.wxtteacher.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SimpleTreeAdapter;
import cn.xiaocool.wxtteacher.bean.FileBean;
import cn.xiaocool.wxtteacher.bean.ParentAdressBean;
import cn.xiaocool.wxtteacher.bean.bean.Node;
import cn.xiaocool.wxtteacher.bean.bean.TreeListViewAdapter;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by 潘 on 2016/4/2.
 */
public class AddressParentFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ListView address_class;
    private String teacherID;
    private List<Map<String, String>> classAddresses = new ArrayList<>();
    private List<List<Map<String, String>>> teachersAddresses = new ArrayList<>();
    private SQLiteDatabase db;  //数据库对象
    private List<ParentAdressBean> parentAdressBeanList;
    private TreeListViewAdapter mAdapter;
    private List<FileBean> fileBeanList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MESSAGEADDRESS:
                    if (msg.obj != null) {
                        ProgressViewUtil.dismiss();
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)) {
//                                address_class = (ListView) getView().findViewById(R.id.address_class);
//                                address_class.setGroupIndicator(null);

                                JSONArray dataArray = obj.optJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    Map<String, String> title = new HashMap<>();
                                    title.put("group", dataArray.optJSONObject(i).optString("classname"));
                                    classAddresses.add(title);
                                    JSONArray teachersPhoneArray = dataArray.optJSONObject(i).optJSONArray("student_list");
                                    List<Map<String, String>> teacherInfo = new ArrayList<>();
                                    for (int j = 0; j < teachersPhoneArray.length(); j++) {

                                        if (teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").length()>0) {
                                            for (int k=0;k<teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").length();k++){

                                                Map<String, String> title_1_content_1 = new HashMap<String, String>();
                                                title_1_content_1.put("studentName",teachersPhoneArray.optJSONObject(j).optString("name"));
                                                title_1_content_1.put("pareName", teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").optJSONObject(k).optString("name"));
                                                title_1_content_1.put("teacherPhone", teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").optJSONObject(k).optString("phone"));
                                                title_1_content_1.put("teacherID", teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").optJSONObject(k).optString("id"));
                                                title_1_content_1.put("teacherAvatar", NetBaseConstant.NET_CIRCLEPIC_HOST+teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").optJSONObject(k).optString("photo"));
                                                title_1_content_1.put("teacherAvatar", NetBaseConstant.NET_CIRCLEPIC_HOST+teachersPhoneArray.optJSONObject(j).optJSONArray("parent_list").optJSONObject(k).optString("photo"));

                                                teacherID = teachersPhoneArray.optJSONObject(j).optString("id");
                                                LogUtils.e("Infoteacherid", teacherID);
                                                Log.e("group", classAddresses.get(0).get("group"));
                                                teacherInfo.add(title_1_content_1);
                                                insertDataToTable(title_1_content_1.get("teacherID"), title_1_content_1.get("pareName"), title_1_content_1.get("teacherAvatar"));
                                            }

                                        }

                                    }
                                    Log.e("============", classAddresses.get(0).get("group"));
                                    teachersAddresses.add(teacherInfo);

                                }
                                // 加入列表
//                                Log.e("group", classAddresses.get(0).get("group"));
//                                Log.e("group", teachersAddresses.get(0).get(0).get("teacherName"));
//
//                                MyExpandableListViewAdapter sela = new MyExpandableListViewAdapter(classAddresses, teachersAddresses, context);
//                                address_class.setAdapter(sela);
//                                address_class.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                                    @Override
//                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                                        return false;
//                                    }
//                                });
//                                int groupCount = address_class.getCount();
//                                for (int i = 0; i < groupCount; i++) {
//                                    address_class.expandGroup(i);
//                                }


                                //修改三级列表后重新解析
                                parentAdressBeanList.clear();
                                parentAdressBeanList.addAll(getBeanFromJson(obj));
                                for (int i =0;i<parentAdressBeanList.size();i++){
                                    parentAdressBeanList.get(i).setNparentId(0);
                                    parentAdressBeanList.get(i).setNid(Integer.parseInt(parentAdressBeanList.get(i).getClassid()));
                                   fileBeanList.add(new FileBean(parentAdressBeanList.get(i).getNid(),parentAdressBeanList.get(i).getNparentId(),parentAdressBeanList.get(i).getClassname(),"0","0"));
                                    for (int j=0;j<parentAdressBeanList.get(i).getStudent_list().size();j++){
                                        parentAdressBeanList.get(i).getStudent_list().get(j).setNparentId(Integer.parseInt(parentAdressBeanList.get(i).getClassid()));
                                        parentAdressBeanList.get(i).getStudent_list().get(j).setNid(Integer.parseInt(parentAdressBeanList.get(i).getStudent_list().get(j).getId()));
                                       fileBeanList.add(new FileBean(parentAdressBeanList.get(i).getStudent_list().get(j).getNid(),parentAdressBeanList.get(i).getStudent_list().get(j).getNparentId(),parentAdressBeanList.get(i).getStudent_list().get(j).getName(),parentAdressBeanList.get(i).getStudent_list().get(j).getPhoto(),"0"));
                                        for (int k=0;k<parentAdressBeanList.get(i).getStudent_list().get(j).getParent_list().size();k++){
                                            ParentAdressBean.StudentListBean studentListBean =  parentAdressBeanList.get(i).getStudent_list().get(j);
                                            studentListBean.getParent_list().get(k).setNparentId(Integer.parseInt(studentListBean.getId()));
                                            studentListBean.getParent_list().get(k).setNid(Integer.parseInt(studentListBean.getParent_list().get(k).getId()));
                                            fileBeanList.add(new FileBean(studentListBean.getParent_list().get(k).getNid(), studentListBean.getParent_list().get(k).getNparentId(), studentListBean.getParent_list().get(k).getName()+"("+ studentListBean.getParent_list().get(k).getAppellation()+")",studentListBean.getParent_list().get(k).getPhoto(),studentListBean.getParent_list().get(k).getPhone()));
                                        }
                                    }
                                }
                                try
                                {
                                    mAdapter = new SimpleTreeAdapter<FileBean>(address_class, context, fileBeanList,fileBeanList, 10);
                                    mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener()
                                    {
                                        @Override
                                        public void onClick(Node node, int position)
                                        {
                                            if (node.isLeaf())
                                            {
                                                Toast.makeText(context, node.getName(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });

                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                    Log.e("AddressParentFragment",e.toString());
                                }
                                address_class.setAdapter(mAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Log.e("============", classAddresses.get(0).get("group"));
                        }

                    }
                    break;
            }
        }
    };


    private List<ParentAdressBean> getBeanFromJson(JSONObject result) {
        String data = "";
        try {
//            JSONObject json = new JSONObject(result);
            data = result.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<ParentAdressBean>>() {
        }.getType());
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_address_parentnew, container, false);
        address_class = (ListView) view.findViewById(R.id.address_class);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProgressViewUtil.show(getActivity());
        context = getActivity();
        parentAdressBeanList = new ArrayList<>();
        fileBeanList = new ArrayList<>();
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
        new NewsRequest(context, handler).addressParent();
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

//    /**
//     * 设置列表内容
//     */
//    public void setListData() {
//        // 创建二个一级条目标题
//        Map<String, String> title_1 = new HashMap<String, String>();
//        Map<String, String> title_2 = new HashMap<String, String>();
//        Map<String, String> title_3 = new HashMap<String, String>();
//        title_1.put("group", "林炳文");
//        title_2.put("group", "文炳林");
//        gruops.add(title_1);
//        gruops.add(title_2);
//
//        // 创建二级条目内容
//        // 内容一
//        Map<String, String> title_1_content_1 = new HashMap<String, String>();
//        Map<String, String> title_1_content_2 = new HashMap<String, String>();
//        Map<String, String> title_1_content_3 = new HashMap<String, String>();
//        title_1_content_1.put("child", "工人");
//        title_1_content_2.put("child", "学生");
//        title_1_content_3.put("child", "农民");
//
//        List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
//        childs_1.add(title_1_content_1);
//        childs_1.add(title_1_content_2);
//        childs_1.add(title_1_content_3);
//
//        // 内容二
//        Map<String, String> title_2_content_1 = new HashMap<String, String>();
//        Map<String, String> title_2_content_2 = new HashMap<String, String>();
//        Map<String, String> title_2_content_3 = new HashMap<String, String>();
//        title_2_content_1.put("child", "猩猩");
//        title_2_content_2.put("child", "老虎");
//        title_2_content_3.put("child", "狮子");
//        List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
//        childs_2.add(title_2_content_1);
//        childs_2.add(title_2_content_2);
//        childs_2.add(title_2_content_3);
//
//        childs.add(childs_1);
//        childs.add(childs_2);
//
//        /**
//         * 创建ExpandableList的Adapter容器 参数: 1.上下文 2.一级集合 3.一级样式文件 4. 一级条目键值
//         * 5.一级显示控件名 6. 二级集合 7. 二级样式 8.二级条目键值 9.二级显示控件名
//         *
//         */
//        SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
//                mContext, gruops, R.layout.groups, new String[] { "group" },
//                new int[] { R.id.textGroup }, childs, R.layout.childs,
//                new String[] { "child" }, new int[] { R.id.textChild });
//        // 加入列表
//        expandableListView.setAdapter(sela);


//    /**
//     * 列表内容按下
//     */
//    @Override
//    public boolean onChildClick(ExpandableListView parent, View v,
//                                int groupPosition, int childPosition, long id) {
//        Toast.makeText(
//                mContext,
//                "您选择了"
//                        + gruops.get(groupPosition).toString()
//                        + "子编号"
//                        + childs.get(groupPosition).get(childPosition)
//                        .toString(), Toast.LENGTH_SHORT).show();
//        return super.onChildClick(parent, v, groupPosition, childPosition, id);
//    }
//
//    /**
//     * 二级标题按下
//     */
//    @Override
//    public boolean setSelectedChild(int groupPosition, int childPosition,
//                                    boolean shouldExpandGroup) {
//        return super.setSelectedChild(groupPosition, childPosition,
//                shouldExpandGroup);
//    }
//
//    /**
//     * 一级标题按下
//     */
//    @Override
//    public void setSelectedGroup(int groupPosition) {
//        super.setSelectedGroup(groupPosition);
//    }

