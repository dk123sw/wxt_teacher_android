package cn.xiaocool.wxtteacher.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.CommunicationAdapter;
import cn.xiaocool.wxtteacher.bean.CommunicateModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.JsonParser;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.utils.VolleyUtil;

public class TeacherCommunicationActivity extends BaseActivity {


    private ListView commnicate_lv;
    private SwipeRefreshLayout swip_layout;
    private EditText ed_comment;
    private TextView tv_comment,top_title;
    private CommunicationAdapter communicationAdapter;
    private Context context;
    private UserInfo user;
    private List<CommunicateModel> communicateModelList;
    private String receive_uid;
    private int tag = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    if (JsonParser.JSONparser(context,msg.obj.toString())){
                        ed_comment.setText(null);
                        tag=0;
                        initData();
                    }else {
                        ToastUtils.ToastShort(context,"发送失败");
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_communication);
        context = this;
        user = new UserInfo(context);
        communicateModelList = new ArrayList<>();
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        receive_uid = getIntent().getStringExtra("reciver_id");
        top_title.setText(getIntent().getStringExtra("chat_name"));
        String url = "http://wxt.xiaocool.net/index.php?g=apps&m=message&a=xcGetChatData&send_uid="+user.getUserId()+"&receive_uid="+receive_uid;
        VolleyUtil.VolleyGetRequest(context, url, new VolleyUtil.VolleyJsonCallback() {
            @Override
            public void onSuccess(String result) {
                swip_layout.setRefreshing(false);
                if (JsonParser.JSONparser(context, result)) {
                    communicateModelList.clear();
                    communicateModelList.addAll(JsonParser.getBeanFromJsonCommunicateModel(result));
                    setAdapter();
                }
            }

            @Override
            public void onError() {
                swip_layout.setRefreshing(false);
            }
        });
    }

    private void setAdapter() {
        if (communicationAdapter == null){
            communicationAdapter = new CommunicationAdapter(context,communicateModelList,user.getUserId());
            commnicate_lv.setAdapter(communicationAdapter);
        }else {
            communicationAdapter.notifyDataSetChanged();
        }
        if (tag==0){
            commnicate_lv.setSelection(communicateModelList.size()-1);
            tag=1;
        }

    }

    private void initView() {
        findViewById(R.id.up_jiantou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        commnicate_lv = (ListView) findViewById(R.id.commnicate_lv);
        swip_layout = (SwipeRefreshLayout) findViewById(R.id.swip_layout);
        ed_comment = (EditText) findViewById(R.id.ed_comment);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        swip_layout.setColorSchemeResources(R.color.white);
        swip_layout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.title_bg_color));
        swip_layout.setProgressViewOffset(true, 10, 100);
        swip_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swip_layout.setRefreshing(false);
                    }
                }, 5000);
            }
        });

        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatContent();
            }
        });
        top_title = (TextView) findViewById(R.id.top_title);
    }

    /**
     * 发送聊天
     */
    private void sendChatContent() {
        if (ed_comment.getText().length()==0){
            ToastUtils.ToastShort(context,"聊天内容不能为空！");
            return;
        }

        String url = "http://wxt.xiaocool.net/index.php?g=apps&m=message&a=SendChatData";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("send_uid", user.getUserId()));
        params.add(new BasicNameValuePair("receive_uid",receive_uid));
        params.add(new BasicNameValuePair("content", ed_comment.getText().toString()));
        new NewsRequest(context,handler).send_chat(params,111);
//        VolleyUtil.VolleyPostRequest(context, url, new VolleyUtil.VolleyJsonCallback() {
//            @Override
//            public void onSuccess(String result) {
//                if (JsonParser.JSONparser(context,result)){
//                    initData();
//                    ToastUtils.ToastShort(context,"发送成功");
//                }else {
//                    ToastUtils.ToastShort(context,"发送");
//                }
//            }
//
//            @Override
//            public void onError() {
//                ToastUtils.ToastShort(context,"失败");
//            }
//        },params);
    }


}
