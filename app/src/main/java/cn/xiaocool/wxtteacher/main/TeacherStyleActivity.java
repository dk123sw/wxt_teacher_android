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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.TeacherInfosAdapter;
import cn.xiaocool.wxtteacher.bean.TeacherInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;

public class TeacherStyleActivity extends Activity {

    private RelativeLayout up_jiantou;
    private ListView listView;
    private String type, title;
    private TextView vc_title;
    private ArrayList<TeacherInfo> teacherInfos = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.TEACHERINFO:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                        teacherInfos.clear();
                        JSONArray hwArray = obj.optJSONArray("data");
                        Log.e("datadddd", obj.optJSONArray("data").toString());
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            TeacherInfo teacherInfo = new TeacherInfo();
                            teacherInfo.setId(itemObject.optString("id"));
                            teacherInfo.setPost_date(itemObject.optString("post_date"));
                            teacherInfo.setPost_keywords(itemObject.optString("post_keywords"));
                            teacherInfo.setPost_title(itemObject.optString("post_title"));
                            teacherInfo.setSchoolid(itemObject.optString("create_time"));
                            teacherInfo.setPost_excerpt(itemObject.optString("post_excerpt"));
                            teacherInfo.setThumb(itemObject.optString("thumb"));
                            teacherInfo.setObject_id(itemObject.optString("object_id"));
                            teacherInfo.setTerm_id(itemObject.optString("term_id"));
                            teacherInfo.setPost_like(itemObject.optString("post_like"));
                            teacherInfo.setPost_hits(itemObject.optString("post_hits"));
                            teacherInfo.setRecommended(itemObject.optString("recommended"));

                            teacherInfos.add(teacherInfo);
                        }
                        Log.e("data", teacherInfos.toString());
                        listView.setAdapter(new TeacherInfosAdapter(teacherInfos, getApplicationContext(), "0"));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(), TeacherInfoWebDetailActivity.class);
//                                if (type.equals("9")){
//                                    intent.putExtra("itemid",teacherInfos.get(position).getObject_id());
//                                }else {
                                intent.putExtra("itemid", teacherInfos.get(position).getId());
//                                }

                                intent.putExtra("title", teacherInfos.get(position).getPost_title());
                                intent.putExtra("content", teacherInfos.get(position).getPost_excerpt());
                                intent.putExtra("type", type);
                                startActivity(intent);
                            }
                        });
                    }
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_style);
        initView();
    }

    private void initView() {
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.teacher_listcontent);
        vc_title = (TextView) findViewById(R.id.vc_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        vc_title.setText(title);
        if (type.equals("1")) {
//            new NewsRequest(getApplicationContext(),handler).getTeacherInfo("getteacherinfos");
            new NewsRequest(getApplicationContext(), handler).getSchoolListInfo("getteacherinfos", CommunalInterfaces.TEACHERINFO);
        } else if (type.equals("2")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getbabyinfos");
        } else if (type.equals("3")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getjobs");
        } else if (type.equals("4")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getInterestclass");
        } else if (type.equals("5")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getWebSchoolInfos");
        } else if (type.equals("6")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getSchoolNotices");
        } else if (type.equals("7")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getSchoolNews");
        } else if (type.equals("8")) {
            new NewsRequest(getApplicationContext(), handler).getTeacherInfo("getParentsThings");
        } else if (type.equals("9")) {
            new NewsRequest(getApplicationContext(), handler).getArticleInfo();
        }


    }
}
