package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.HttpTool;
import cn.xiaocool.wxtteacher.view.WxtApplication;

/**
 * Created by wzh on 2016/1/25.
 */
public class ResetPasswordActivity extends Activity implements View.OnClickListener {
    private EditText edit_password1;
    private EditText edit_password2;
    private Button btn_finish;
    private ImageView btn_back;
    private String result_data;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    if (msg.obj!=null){
                        try {
                            JSONObject status = new JSONObject(result_data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    Toast.makeText(ResetPasswordActivity.this,"设置成功，正在跳转...！",Toast.LENGTH_SHORT).show();
//                    Intent intent_finsh = new Intent(ResetPasswordActivity.this,MainActivity.class);
//                    startActivity(intent_finsh);
                    finish();
            }

        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_password);
        init();
    }

    // 实例化控件
    private void init() {
        edit_password1 = (EditText) findViewById(R.id.edit_password1);
        edit_password2 = (EditText) findViewById(R.id.edit_password2);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_back = (ImageView) findViewById(R.id.forget_btn_back);
        btn_finish.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_finish:


                String firstPassword = edit_password1.getText().toString();
                String secondPassword = edit_password2.getText().toString();
                if (firstPassword.length()<1||secondPassword.length()<1){
                    Toast.makeText(this, "密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (firstPassword.equals(secondPassword) ) {


                    new Thread() {
                        @Override
                        public void run() {

                            String firstPassword = edit_password1.getText().toString();
                            result_data =  HttpTool.ResetPassword( WxtApplication.UID,firstPassword);
                            handler.sendEmptyMessage(1);
                        }
                    }.start();
                }
                else
                    Toast.makeText(ResetPasswordActivity.this,"请检查输入的密码是否正确！",Toast.LENGTH_SHORT).show();

            case R.id.forget_btn_back:
                finish();
                break;
        }


    }
}
