package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Backlog;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class BacklogNextDetailActivity extends Activity implements View.OnClickListener{
    private Backlog.BacklogData backlogData;
    private Context context;
    private TextView tv_username,tv_content,tv_previous_user,tv_previous_advice,btn_finish,btn_send,zhuanpai_name_text;
    private RelativeLayout back,next;
    private EditText et_advice;
    private String teacherid=null;
    private static final int DO_OK = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DO_OK:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            ToastUtils.ToastShort(BacklogNextDetailActivity.this,"处理待办成功！");
                            finish();
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
        setContentView(R.layout.activity_backlog_next_detail);
        context = this;
        initData();
        initview();


    }

    private void initData() {
        Intent intent = this.getIntent();
        backlogData =(Backlog.BacklogData)intent.getSerializableExtra("backlog");


    }

    private void initview() {
        tv_username = (TextView) findViewById(R.id.username);
        zhuanpai_name_text = (TextView) findViewById(R.id.zhuanpai_name_text);
        tv_content = (TextView) findViewById(R.id.backlog_content);
        tv_previous_advice = (TextView) findViewById(R.id.previous_advice);
        tv_previous_user = (TextView) findViewById(R.id.previous_user);
        btn_finish = (TextView) findViewById(R.id.finish);
        btn_finish.setOnClickListener(this);
        btn_send = (TextView) findViewById(R.id.send);
        btn_send.setOnClickListener(this);
        back = (RelativeLayout) findViewById(R.id.up_jiantou);
        back.setOnClickListener(this);
        next = (RelativeLayout) findViewById(R.id.btn_tiaozhuan);
        next.setOnClickListener(this);
        et_advice = (EditText) findViewById(R.id.backlog_advice);
        if (backlogData.getReciverlist().size()>0){
            tv_previous_user.setText(backlogData.getReciverlist().get(0).getName());
            tv_previous_advice.setText(backlogData.getReciverlist().get(0).getFeedback());
        }
        tv_username.setText(backlogData.getUsername());
        tv_content.setText(backlogData.getContent());
    }

    @Override
    public void onClick(View v) {
        String lastid = "null";
        int count = backlogData.getReciverlist().size();
        if (count>0){
            lastid = backlogData.getReciverlist().get(count-1).getId();
        }else {
            lastid = backlogData.getUserid();
        }
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.send:

                //转派
                if (et_advice.getText().length()>0){
                    if(teacherid!=null){
                        new NewsRequest(this,handler).do_schedule(lastid,backlogData.getId(),et_advice.getText().toString(),teacherid,"0",DO_OK);
                    }else {
                        ToastUtils.ToastShort(this, "请选择转派人！");
                    }

                }else {
                    ToastUtils.ToastShort(this, "请输入您的宝贵意见！");
                }
                break;
            case R.id.finish:
                //完成办结
                if (et_advice.getText().length()>0){
                    new NewsRequest(this,handler).do_schedule(lastid,backlogData.getId(),et_advice.getText().toString(),teacherid,"1",DO_OK);
                }else {
                    ToastUtils.ToastShort(this,"请输入您的宝贵意见！");
                }
                break;
            case R.id.btn_tiaozhuan:
                //选择经办人

                Intent intent = new Intent(BacklogNextDetailActivity.this, AddBacklogChooseTeacherActivity.class);
                startActivityForResult(intent, 101);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {

                case 101:
                    if (data != null) {
                        String teachername = data.getStringExtra("teachername");
                        teacherid = data.getStringExtra("teacherid");
                        zhuanpai_name_text.setText(teachername);
                    }

                    break;
            }
        }
    }
}
