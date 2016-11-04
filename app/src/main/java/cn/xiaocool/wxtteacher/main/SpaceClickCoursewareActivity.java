package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.CoursewareTypeListAdapter;
import cn.xiaocool.wxtteacher.bean.ClassCourseWare;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.SpaceRequest;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

;


/**
 * Created by wzh on 2016/1/29.
 */
public class SpaceClickCoursewareActivity extends Activity implements View.OnClickListener {

    private RelativeLayout up_jiantou;
    private ListView listView;
    private RelativeLayout btn_add;
    private Context mContext;
    private List<ClassCourseWare> classCourseWareList;
    private int length;
    private String [] title;
    private String [] content;
    private String [] name;
    private String [] date;
    private CoursewareTypeListAdapter coursewareTypeListAdapter;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            switch(msg.what){
                case CommunalInterfaces.CLASS_COURSEWARE:
                    JSONObject courseObj = (JSONObject) msg.obj;
                    ProgressViewUtil.dismiss();
                    try {
                        if (courseObj.optString("status").equals("success")){

                            classCourseWareList.clear();
                            Gson gson = new Gson();
                           List<ClassCourseWare> classCourseWares = gson.fromJson(courseObj.optString("data"), new TypeToken<List<ClassCourseWare>>() {
                            }.getType());
                            classCourseWareList.addAll(classCourseWares);
                        }

                       if (coursewareTypeListAdapter!=null){
                           coursewareTypeListAdapter.notifyDataSetChanged();
                       }else {
                           coursewareTypeListAdapter = new CoursewareTypeListAdapter(mContext,classCourseWareList);
                           listView.setAdapter(coursewareTypeListAdapter);
                       }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_baby_courseware);
        mContext = this;
        ProgressViewUtil.show(mContext);
        initView();
        classCourseWareList = new ArrayList<>();
        //绑定ListView
        listView = (ListView) findViewById(R.id.baby_course_listcontent);
        listView.setAdapter(coursewareTypeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (classCourseWareList.get(position).getCourseware_info().size()>0){
                    Intent intent = new Intent(mContext,CoursewareDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coursewareinfo", (Serializable) classCourseWareList.get(position).getCourseware_info());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    ToastUtils.ToastShort(mContext,"没有该课程的课件的数据！");
                }

            }
        });

    }

    private void initView() {
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        btn_add = (RelativeLayout) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.btn_add:
                IntentUtils.getIntents(SpaceClickCoursewareActivity.this, AddCoursewareActivity.class);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //调用网络请求
        new SpaceRequest(mContext,handler).classCourseware();
    }
}
