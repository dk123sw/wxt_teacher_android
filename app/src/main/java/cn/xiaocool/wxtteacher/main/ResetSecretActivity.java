package cn.xiaocool.wxtteacher.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;
import java.util.Map;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.app.ExitApplication;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.JsonParser;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.utils.VolleyUtil;
import cn.xiaocool.wxtteacher.view.WxtApplication;

public class ResetSecretActivity extends AppCompatActivity {


    private EditText old_sec,new_sec,check_new_sec;
    private RelativeLayout send_btn,up_jiantou;
    private UserInfo user;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_secret);
        getSupportActionBar().hide();
        user = new UserInfo(this);

        old_sec = (EditText) findViewById(R.id.old_sec);
        new_sec = (EditText) findViewById(R.id.new_sec);
        check_new_sec = (EditText) findViewById(R.id.check_new_sec);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSecret();
            }
        });

        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 修改密码
     * */
    private void sendSecret() {
        if (user.getUserPassword().equals(old_sec.getText().toString())){
            if (new_sec.getText().toString().equals(check_new_sec.getText().toString())){
                sendRequset(user.getUserId(),check_new_sec.getText().toString());

            }else {
                ToastUtils.ToastShort(this,"两次密码输入不匹配!");
            }


        }else {
            ToastUtils.ToastShort(this,"输入原密码错误!");
        }
    }

    private void sendRequset(String usrid,String secret) {

        Map<String,String> params = new HashMap<>();
        params.put("userid",usrid);
        params.put("pass",secret);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        hud.show();
        VolleyUtil.VolleyPostRequest(this, "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=forgetPass_Activate", new VolleyUtil.VolleyJsonCallback() {
            @Override
            public void onSuccess(String result) {
                if (JsonParser.JSONparser(getBaseContext(),result)){
                    exitAPP();
                    ToastUtils.ToastShort(getBaseContext(), "修改成功，请重新登录!");
                }else {
                    ToastUtils.ToastShort(getBaseContext(),"修改失败");
                }
                hud.dismiss();
            }

            @Override
            public void onError() {
                ToastUtils.ToastShort(getBaseContext(),"修改失败");
                hud.dismiss();
            }
        },params);
    }

    private void exitAPP() {
         SharedPreferences sp = this.getSharedPreferences("list", this.MODE_PRIVATE);
        user.clearDataExceptPhone(this);
        SPUtils.remove(this, "newsGroupRecive");
        SPUtils.remove(this,"receiveParentWarn");
        SPUtils.remove(this,"noticeRecive");
        SPUtils.remove(this,"backlogData");
        SPUtils.remove(this,"teacherCommunication");
        SPUtils.remove(this,"homeWork");
        SharedPreferences.Editor e = sp.edit();
        LogUtils.e("删除前", e.toString());
        e.clear();
        e.commit();
        WxtApplication.UID = 0;
        LogUtils.e("删除后", e.toString());
        IntentUtils.getIntents(this, LoginActivity.class);
        this.finish();
        ExitApplication.getInstance().exit();
    }
}
