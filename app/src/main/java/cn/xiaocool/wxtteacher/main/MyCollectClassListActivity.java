package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassListAdapter;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyCollectClassListActivity extends Activity implements View.OnClickListener{
    private ImageView btn_exit;
    private ListView class_list;
    private String classID,className;
    private ClassListAdapter classListAdapter;
    private List<ClassList.ClassListData> classListDataList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CLASSLIST:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            JSONArray dataArray = obj.optJSONArray("data");
                            JSONObject itemObject;
                            for (int i = 0; i < dataArray.length(); i++) {
                                itemObject = dataArray.optJSONObject(i);
                                ClassList.ClassListData classListData = new ClassList.ClassListData();
                                classID = itemObject.optString("classid");
                                className = itemObject.optString("classname");
                                classListData.setClassid(itemObject.optString("classid"));
                                classListData.setClassname(itemObject.optString("classname"));
                                classListDataList.add(classListData);
                            }
                            classListAdapter = new ClassListAdapter(classListDataList, MyCollectClassListActivity.this);
                            class_list.setAdapter(classListAdapter);
                            class_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MyCollectClassListActivity.this, ChooseCollectActivity.class);
                                    intent.putExtra("classID", classListDataList.get(position).getClassid());
                                    Log.d("classssssssssis", classListDataList.get(position).getClassid());
                                    intent.putExtra("className", classListDataList.get(position).getClassname());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_class_list);
        initView();
    }

    private void initView() {
        classListDataList = new ArrayList<>();
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        class_list = (ListView) findViewById(R.id.listView);
        new NewsRequest(MyCollectClassListActivity.this, handler).myclass();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                finish();
                break;
        }
    }

}
