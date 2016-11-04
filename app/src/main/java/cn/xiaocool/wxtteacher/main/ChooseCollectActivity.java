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
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.StudentListAdapter;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ChooseCollectActivity extends Activity implements View.OnClickListener {
    private ImageView btn_exit;
    private GridView gv_childrenList;

    private ArrayList<ClassList.ClassStudentData> classListDataList;
    private StudentListAdapter classListAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.STUDENT_LIST:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        Log.d("ChooseCollectActivity",obj.optString("status"));
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            JSONArray dataArray = obj.optJSONArray("data");
                            Log.d("ChooseCollectActivity",obj.optString("data"));
                            JSONObject itemObject;
                            classListDataList = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                itemObject = dataArray.optJSONObject(i);
                                ClassList.ClassStudentData classListData = new ClassList.ClassStudentData();
                                classListData.setName(itemObject.optString("name"));
                                classListData.setPhone(itemObject.optString("phone"));
                                classListData.setId(itemObject.optString("id"));
                                classListDataList.add(classListData);
                            }
                            classListAdapter = new StudentListAdapter(classListDataList, ChooseCollectActivity.this);
                            gv_childrenList.setAdapter(classListAdapter);
                            gv_childrenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(ChooseCollectActivity.this, AddHomeworkActivity.class);
                                    intent.putExtra("ID", classListDataList.get(position).getId());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                        break;
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_class);
        initView();
    }

    private void initView() {
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        gv_childrenList = (GridView) findViewById(R.id.gv_childrenList);
        String classId = getIntent().getStringExtra("classID");
        Log.d("classsssis",classId);
        new NewsRequest(ChooseCollectActivity.this, handler).choosecollect(classId);
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
