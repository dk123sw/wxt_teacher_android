package cn.xiaocool.wxtteacher.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SortAdapter;
import cn.xiaocool.wxtteacher.bean.SortModel;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.ClearEditText;
import cn.xiaocool.wxtteacher.ui.SideBar;
import cn.xiaocool.wxtteacher.utils.CharacterParser;
import cn.xiaocool.wxtteacher.utils.PinyinComparator;

/**
 * Created by 潘 on 2016/4/2.
 */
public class AddressGardenerFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private ListView sortListView;
    private SideBar sideBar;
    /**
     * 显示字母的TextView
     */
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private SQLiteDatabase db;  //数据库对象
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.TEACHER:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        String state = obj.optString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            JSONArray jsonArray = obj.optJSONArray("data");
                            SourceDateList = new ArrayList<>();
                            JSONObject itemObject;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                itemObject = jsonArray.optJSONObject(i);
                                SortModel sortModel = new SortModel();
                                sortModel.setId(itemObject.optString("id"));
                                sortModel.setPhoto("http://wxt.xiaocool.net/uploads/avatar/" + itemObject.optString("photo"));
                                sortModel.setName(itemObject.optString("name"));
                                sortModel.setPhone(itemObject.optString("phone"));
                                sortModel.setSortLetters(getCase(itemObject.optString("name")));
                                Log.e("hello------",sortModel.getPhoto());
                                insertDataToTable(sortModel.getId(),sortModel.getName(),sortModel.getPhoto());
                                SourceDateList.add(sortModel);
                            }
                            Collections.sort(SourceDateList, pinyinComparator);
                            adapter = new SortAdapter(getActivity(), SourceDateList);
                            sortListView.setAdapter(adapter);
                        }

                    }

                    break;


            }

        }
    };
    /*private ExpandableListView expandableListView;
    *//**
     * 创建一级条目容器
     *//*
    List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
    */

    /**
     * 存放内容, 以便显示在列表中
     *//*
    List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
    List<List<Map<String, String>>> childs2 = new ArrayList<List<Map<String, String>>>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MESSAGEADDRESS:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String status = obj.getString("status");
                            if (status.equals("success")) {
                                expandableListView = (ExpandableListView) getView().findViewById(R.id.address_class);
                                JSONArray dataArray = obj.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    Map<String, String> title = new HashMap<String, String>();
                                    title.put("group", dataArray.getJSONObject(i).getString("classid"));
                                    gruops.add(title);
                                    JSONArray dayaArray2 = dataArray.getJSONObject(i).getJSONArray("teacherinfo");
                                    List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
                                    for (int j = 0; j < dayaArray2.length(); j++) {
                                        //   childs.get(i).get(j).put("child",dayaArray2.getJSONObject(j).getString("name"));
                                        Map<String, String> title_1_content_1 = new HashMap<String, String>();
                                        title_1_content_1.put("child", dayaArray2.getJSONObject(j).getString("name"));
                                        LogUtils.e("111", title_1_content_1.toString());
                                        childs_1.add(title_1_content_1);
                                    }
                                    childs.add(childs_1);
                                }
                                SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
                                        mContext, gruops, R.layout.groups, new String[]{"group"},
                                        new int[]{R.id.textGroup}, childs, R.layout.childs,
                                        new String[]{"child"}, new int[]{R.id.textChild});
                                // 加入列表
                                expandableListView.setAdapter(sela);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }


    };*/
    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_address_teacher, container, false);
        //打开或者创建数据库, 这里是创建数据库
        db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().toString() + "/users.db", null);
        System.out.println(getActivity().getFilesDir().toString() + "/users.db");
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) view.findViewById(R.id.country_lvcountry);
        /*sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getActivity(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });*/
        new NewsRequest(getActivity(), handler).getTeacher();
        //SourceDateList = filledData(getResources().getStringArray(R.array.date));

        // 根据a-z进行排序源数据



        mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        expandableListView = (ExpandableListView)getView().findViewById(R.id.android_list);
        mContext = getActivity();

    }

    /*
     * 插入或更新数据到数据库中
     */
    public void insertDataToTable(String userid, String username, String useravatar) {


        try {
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
    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            String pic = "http://wxt.xiaocool.net/uploads/avatar/weixiaotong.png";
            sortModel.setName(date[i]);
            sortModel.setPhoto(pic);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
    //汉字得到首字母
    private String getCase(String name){
        //汉字转换成拼音
        String pinyin = characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase();

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            return sortString.toUpperCase();
        } else {
            return "#";
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}
