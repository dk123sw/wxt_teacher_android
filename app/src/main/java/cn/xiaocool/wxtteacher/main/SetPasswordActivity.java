package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.HttpTool;
import cn.xiaocool.wxtteacher.view.WxtApplication;

/**
 * Created by wzh on 2016/1/25.
 */
public class SetPasswordActivity extends Activity implements View.OnClickListener {
    private EditText edit_password1;
    private EditText edit_password2;
    private Button btn_finish;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(SetPasswordActivity.this,"设置成功，正在跳转首页！",Toast.LENGTH_SHORT).show();
                    Intent intent_finsh = new Intent(SetPasswordActivity.this,MainActivity.class);
                    startActivity(intent_finsh);
                    //finish();
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
        btn_finish.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_finish:

                String firstPassword = edit_password1.getText().toString();
                String secondPassword = edit_password2.getText().toString();
                if (firstPassword.equals(secondPassword) ) {


                    new Thread() {
                        @Override
                        public void run() {

                            String firstPassword = edit_password1.getText().toString();
                            HttpTool.SetPassword( WxtApplication.UID,firstPassword);
                            handler.sendEmptyMessage(1);
                        }
                    }.start();
                }
                else
                    Toast.makeText(SetPasswordActivity.this,"请检查输入的密码是否正确！",Toast.LENGTH_SHORT).show();

        }


    }
}
