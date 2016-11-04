package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.TeacherReviewDetailAdapter;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class TeacherReviewDetailActivity extends Activity implements View.OnClickListener{

    private RelativeLayout up_jiantou;
    private RelativeLayout me_teacher;
    private Context mContext;
    private TextView ac_title,tvName,tvDate;
    private ListView listView;
    private String studentId,year,month,userid;
    private TeacherReviewDetailAdapter adapter;
    private ArrayList<ClassList.ClassStudentData.TeacherComment> arrayList;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_review_detail);
        mContext = this;
        ProgressViewUtil.show(this);
        arrayList = new ArrayList<>();
        initView();


    }

    private void initView() {

        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");
        userid = getIntent().getStringExtra("userid");



        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        me_teacher= (RelativeLayout) findViewById(R.id.me_teacher_remark_on);
        me_teacher.setOnClickListener(this);
        ac_title = (TextView) findViewById(R.id.ac_title);
        listView = (ListView) findViewById(R.id.teacher_review_listcontent);
//        new NewsRequest(mContext,handler).teacherReview();

//        ArrayList<ClassList.ClassStudentData.TeacherComment> arrayList1 = (ArrayList<ClassList.ClassStudentData.TeacherComment>) getIntent().getSerializableExtra("comments");
//        arrayList.addAll();
        String title = getIntent().getStringExtra("name");
        ac_title.setText(title);
        studentId = getIntent().getStringExtra("studentid");
        adapter = new TeacherReviewDetailAdapter(mContext, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.me_teacher_remark_on:
                Intent intent=new Intent(mContext,TeacherSingleReviewActivity.class);
                intent.putExtra("studentid",studentId);
                intent.putExtra("name",ac_title.getText().toString());
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        volleyRefrsh();
    }

    private void volleyRefrsh() {


        long begintime = TimeToolUtils.getMonthBeginTimestamp(Integer.valueOf(year), Integer.valueOf(month))/1000;
        long endtime = TimeToolUtils.getMonthEndTimestamp(Integer.valueOf(year), Integer.valueOf(month))/1000;
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetTeacherComment&teacherid="+userid+"&begintime="+begintime+"&endtime="+endtime;
        Log.e("uuuurrrrll", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {



            @Override
            public void onResponse(String arg0) {
//                ToastUtils.ToastShort(mContext, arg0);
                Log.d("onResponse", arg0);
                ProgressViewUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {
                        JSONArray dataArray = jsonObject.optJSONArray("data");


                        for (int k =0;k<dataArray.length();k++){

                            JSONObject classDataObject = dataArray.optJSONObject(k);
                            ClassList.ClassListData  classListData = new ClassList.ClassListData();
                            classListData.setClassid(classDataObject.optString("classid"));
                            classListData.setClassname(classDataObject.optString("classname"));
                            JSONArray studentArray = classDataObject.optJSONArray("studentlist");
                            ArrayList<ClassList.ClassStudentData> classStudentDataArrayList = new ArrayList<>();
                            for (int i = 0; i < studentArray.length(); i++) {
                                JSONObject itemObject = studentArray.optJSONObject(i);

                                ClassList.ClassStudentData stuListData = new ClassList.ClassStudentData();
                                if (itemObject.optString("id").equals(studentId)){
                                    arrayList.clear();
                                    stuListData.setName(itemObject.optString("name"));
                                    stuListData.setPhone(itemObject.optString("phone"));
                                    stuListData.setId(itemObject.optString("id"));
                                    stuListData.setSex(itemObject.optString("sex"));
                                    stuListData.setPhoto(itemObject.optString("photo"));


                                    JSONArray commentArray = itemObject.optJSONArray("comments");



                                    if (commentArray!=null){
                                        ArrayList<ClassList.ClassStudentData.TeacherComment> commentArrayList = new ArrayList<>();
                                        for (int j=0;j<commentArray.length();j++){
                                            JSONObject commentObject = commentArray.optJSONObject(commentArray.length()-j-1);
                                            ClassList.ClassStudentData.TeacherComment commentData = new ClassList.ClassStudentData.TeacherComment();
                                            commentData.setId(commentObject.optString("studentid"));
                                            commentData.setComment_time(commentObject.optString("comment_time"));
                                            commentData.setComment_status(commentObject.optString("comment_status"));
                                            commentData.setLearn(commentObject.optString("learn"));
                                            commentData.setWork(commentObject.optString("work"));
                                            commentData.setSing(commentObject.optString("sing"));
                                            commentData.setLabour(commentObject.optString("labour"));
                                            commentData.setStrain(commentObject.optString("strain"));
                                            commentData.setComment_content(commentObject.optString("comment_content"));
                                            commentData.setName(commentObject.optString("name"));
                                            commentData.setPhoto(commentObject.optString("photo"));
                                            commentArrayList.add(commentData);
                                        }

                                        arrayList.addAll(commentArrayList);
                                        stuListData.setTeacherComments(commentArrayList);
                                    }
                                    classStudentDataArrayList.add(stuListData);
                                }


                            }

                            Log.e("dataArrayList", jsonObject.optJSONArray("data").toString());
                            classListData.setStudentlist(classStudentDataArrayList);


                            if (adapter!=null){
                                adapter.notifyDataSetChanged();
                            }else {
                                adapter = new TeacherReviewDetailAdapter(mContext, arrayList);
                                listView.setAdapter(adapter);
                            }

                        }





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ToastUtils.ToastShort(mContext, arg0.toString());
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        Volley.newRequestQueue(mContext).add(request);
    }

}
