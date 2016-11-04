package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.SpaceRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;


/**
 * Created by Administrator on 2016/4/6.
 */
public class MySendMessageToTeacherActivity extends Activity implements View.OnClickListener {
    private EditText send_message;
    private Button send_btn;
    private Button clear_btn;
    private ImageView btn_exit;
    private String data;
    private String teacherID;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SENDTOTEACJER:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                JSONObject dataOject = obj.getJSONObject("data");
                                String receive_user_id = dataOject.getString("receive_user_id");
                                String send_user_id = dataOject.getString("send_user_id");
                                String message_type = dataOject.getString("message_type");
                                String message_time = dataOject.getString("message_time");
                                String message_content = dataOject.getString("message_content");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        setContentView(R.layout.send_to_teacher);
        Intent intent = getIntent();
        teacherID = intent.getStringExtra("teacherID");
        initView();
    }

    private void initView() {
        send_message = (EditText) findViewById(R.id.send_message);
        send_btn = (Button) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        clear_btn = (Button) findViewById(R.id.clear_btn);
        clear_btn.setOnClickListener(this);
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                finish();
                break;
            case R.id.send_btn:
                String sendMessage = send_message.getText().toString();
                if (TextUtils.isEmpty(sendMessage)) {
                    ToastUtils.ToastShort(this, "不能发送空消息");
                } else if (TextUtils.getTrimmedLength(sendMessage) >= 200) {
                    ToastUtils.ToastShort(this, "消息长度不能超过200字哦");
                } else {
                    new SpaceRequest(MySendMessageToTeacherActivity.this, handler).sendMessageToTeacher(sendMessage,teacherID);
                    Log.i("Info",teacherID);
                }
                ToastUtils.ToastShort(this, "发送成功");
                break;
            case R.id.clear_btn:
                send_message.setText("");
                break;
        }
    }
}
