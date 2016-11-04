package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class SuggestBackActivity extends Activity {

    private EditText apply_content;
    private RelativeLayout send_btn,up_jiantou;
    private Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1001:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.ToastShort(SuggestBackActivity.this,"发送成功！");
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.ToastShort(SuggestBackActivity.this,"请求异常！");
                    }
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_suggest_back);
        initview();
    }

    private void initview() {
        apply_content = (EditText) findViewById(R.id.apply_content);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        if (apply_content.getText().toString().length()>0){

            new NewsRequest(this,handler).send_suggest(apply_content.getText().toString(),1001);

        }else {
            ToastUtils.ToastShort(this,"发送内容不能为空!");
        }
    }
}
