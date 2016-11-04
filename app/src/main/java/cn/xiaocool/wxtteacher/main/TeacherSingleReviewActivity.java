package cn.xiaocool.wxtteacher.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class TeacherSingleReviewActivity extends FragmentActivity implements View.OnClickListener {


    private UserInfo user = new UserInfo();
    private String studentId,titlename;
    private Context mContext;
    private RelativeLayout rl_back;
    private TextView tv_complete,ac_title;
    private LinearLayout xuexi_1,xuexi_2,xuexi_3,dongshou_1,dongshou_2,dongshou_3,sing_1,sing_2,sing_3,
            laodong_1,laodong_2,laodong_3,yingbian_1,yingbian_2,yingbian_3;

    private LinearLayout xuexiTabs[],dongshou[],sing[],laodong[],yiban[];
    private EditText content_text;

    private int index1=0;
    private int currentTabIndex1=0;
    private int index2=0;
    private int currentTabIndex2=0;
    private int index3=0;
    private int currentTabIndex3=0;
    private int index4=0;
    private int currentTabIndex4=0;
    private int index5=0;
    private int currentTabIndex5=0;
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 123:
                    if ( cn.xiaocool.wxtteacher.utils.JsonParser.JSONparser(TeacherSingleReviewActivity.this,msg.obj.toString())) {

                        ToastUtils.ToastShort(TeacherSingleReviewActivity.this,"点评成功");
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_single_review);
        mContext = this;
        user.readData(this);
        studentId = getIntent().getStringExtra("studentid");
        titlename = getIntent().getStringExtra("name");
        initView();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.up_jiantou);
        rl_back.setOnClickListener(this);
        tv_complete = (TextView) findViewById(R.id.complete_comment);
        tv_complete.setOnClickListener(this);
        ac_title = (TextView) findViewById(R.id.ac_title);
        ac_title.setText(titlename);
        content_text = (EditText) findViewById(R.id.content_text);

        xuexi_1 = (LinearLayout) findViewById(R.id.xuexi_1);
        xuexi_2 = (LinearLayout) findViewById(R.id.xuexi_2);
        xuexi_3= (LinearLayout) findViewById(R.id.xuexi_3);
        xuexi_1.setOnClickListener(this);
        xuexi_2.setOnClickListener(this);
        xuexi_3.setOnClickListener(this);
        xuexiTabs = new LinearLayout[]{xuexi_1,xuexi_2,xuexi_3};

        dongshou_1 = (LinearLayout) findViewById(R.id.dongshou_1);
        dongshou_2 = (LinearLayout) findViewById(R.id.dongshou_2);
        dongshou_3 = (LinearLayout) findViewById(R.id.dongshou_3);
        dongshou_1.setOnClickListener(this);
        dongshou_2.setOnClickListener(this);
        dongshou_3.setOnClickListener(this);
        dongshou = new LinearLayout[]{dongshou_1,dongshou_2,dongshou_3};

        sing_1 = (LinearLayout) findViewById(R.id.sing_1);
        sing_2 = (LinearLayout) findViewById(R.id.sing_2);
        sing_3 = (LinearLayout) findViewById(R.id.sing_3);
        sing_1.setOnClickListener(this);
        sing_2.setOnClickListener(this);
        sing_3.setOnClickListener(this);
        sing = new LinearLayout[]{sing_1,sing_2,sing_3};

        laodong_1 = (LinearLayout) findViewById(R.id.laodong_1);
        laodong_2 = (LinearLayout) findViewById(R.id.laodong_2);
        laodong_3 = (LinearLayout) findViewById(R.id.laodong_3);
        laodong_1.setOnClickListener(this);
        laodong_2.setOnClickListener(this);
        laodong_3.setOnClickListener(this);
        laodong = new LinearLayout[]{laodong_1,laodong_2,laodong_3};

        yingbian_1 = (LinearLayout) findViewById(R.id.yingbian_1);
        yingbian_2 = (LinearLayout) findViewById(R.id.yingbian_2);
        yingbian_3 = (LinearLayout) findViewById(R.id.yingbian_3);
        yingbian_1.setOnClickListener(this);
        yingbian_2.setOnClickListener(this);
        yingbian_3.setOnClickListener(this);
        yiban = new LinearLayout[]{yingbian_1,yingbian_2,yingbian_3};


        xuexiTabs[index1].setSelected(true);
        dongshou[index2].setSelected(true);
        sing[index3].setSelected(true);
        laodong[index4].setSelected(true);
        yiban[index5].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.complete_comment:
                commitReview();
                break;

            case R.id.xuexi_1:
                index1 = 0;
                break;
            case R.id.xuexi_2:
                index1 = 1;
                break;
            case R.id.xuexi_3:
                index1 = 2;
                break;

            case R.id.dongshou_1:
                index2 = 0;
                break;
            case R.id.dongshou_2:
                index2 = 1;
                break;
            case R.id.dongshou_3:
                index2 = 2;
                break;

            case R.id.sing_1:
                index3 = 0;
                break;
            case R.id.sing_2:
                index3 = 1;
                break;
            case R.id.sing_3:
                index3 = 2;
                break;

            case R.id.laodong_1:
                index4 = 0;
                break;
            case R.id.laodong_2:
                index4 = 1;
                break;
            case R.id.laodong_3:
                index4 = 2;
                break;

            case R.id.yingbian_1:
                index5 = 0;
                break;
            case R.id.yingbian_2:
                index5 = 1;
                break;
            case R.id.yingbian_3:
                index5 = 2;
                break;

        }


        xuexiTabs[currentTabIndex1].setSelected(false);
        xuexiTabs[index1].setSelected(true);
        currentTabIndex1 = index1;

        dongshou[currentTabIndex2].setSelected(false);
        dongshou[index2].setSelected(true);
        currentTabIndex2 = index2;

        sing[currentTabIndex3].setSelected(false);
        sing[index3].setSelected(true);
        currentTabIndex3 = index3;

        laodong[currentTabIndex4].setSelected(false);
        laodong[index4].setSelected(true);
        currentTabIndex4 = index4;

        yiban[currentTabIndex5].setSelected(false);
        yiban[index5].setSelected(true);
        currentTabIndex5 = index5;


    }

    /**
     * 提交点评
     * */
    private void commitReview() {
        if (content_text.getText().length()>0){

            new NewsRequest(this, handler).send_teacherRemark(studentId, content_text.getText().toString(), (currentTabIndex1 + 1) + "", (currentTabIndex2 + 1) + "", (currentTabIndex3 + 1) + "",
                    (currentTabIndex4 + 1) + "", (currentTabIndex5 + 1) + "", 123);

//            RequestQueue mQueue = Volley.newRequestQueue(this);
////            &learn=1&work=1&sing=1&labour=1&strain=1<>
//            String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=AddTeacherComment"+"&teacherid="+user.getUserId()
//                    +"&studentid="+studentId+"&content="+content_text.getText().toString()+"&learn="+(currentTabIndex1+1)+"&work="+
//                    (currentTabIndex2+1)+"&sing="+(currentTabIndex3+1)+"&labour="+(currentTabIndex4+1)+"&strain="+(currentTabIndex5+1);
//            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//
//                @Override
//                public void onResponse(String arg0) {
//                    Log.d("onResponse", arg0);
//                    ToastUtils.ToastShort(TeacherSingleReviewActivity.this,"发送成功");
//                    finish();
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError arg0) {
//                    Log.d("onErrorResponse", arg0.toString());
//                }
//            });
//            mQueue.add(request);

        }else {
            ToastUtils.ToastShort(TeacherSingleReviewActivity.this,"评价内容不能为空！");
        }


    }
}
