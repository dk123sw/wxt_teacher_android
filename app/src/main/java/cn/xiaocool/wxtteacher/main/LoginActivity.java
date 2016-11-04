package cn.xiaocool.wxtteacher.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.HttpTool;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.KeyBoardUtils;
import cn.xiaocool.wxtteacher.utils.NetBaseUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.view.WxtApplication;


/**
 * Created by mac on 16/1/23.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private String result_data;
    private Button btn_login;
    private EditText tx_phonenumber;
    private EditText tx_vertifycode;
    private String phone = null;
    private String password = null;
    private TextView tv_register;
    private TextView tv_forget;
    private Context mContext;
    private UserInfo user;
    private KProgressHUD hud;
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 3:
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            JSONObject item = new JSONObject(data);
                            WxtApplication.UID = Integer.parseInt(item.getString("id"));
                            user.setUserId(item.getString("id"));
                            user.setUserName(item.getString("name"));
                            user.setSchoolId(item.getString("schoolid"));
                            if (user.getSchoolId().equals("0")){
                                user.setSchoolId("1");
                            }
                            user.setUserCompany(item.getString("school_name"));
                            user.setClassId(item.getString("classid"));
                            user.setUserPhone(item.optString("phone"));
                            user.setUserImg(item.optString("photo"));
                            user.setUserGender(item.optString("sex"));
                            user.setUserImg(item.getString("photo"));
                            user.writeData(mContext);
                            hud.dismiss();
                            JPushInterface.resumePush(mContext);
                            JPushInterface.setAlias(getBaseContext(), user.getUserId(), mAliasCallback);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            hud.dismiss();
                            ToastUtils.ToastShort(mContext, "登录失败！");
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                case 4:
                    hud.dismiss();
                    ToastUtils.ToastShort(mContext, "请输入正确的手机号！");
                    break;
                case 5:
                    hud.dismiss();
                    ToastUtils.ToastShort(mContext, "登录聊天服务器失败！");
                    break;
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d("setAlias", "Set tags in handler.");
                    break;

                default:

                    break;
            }
        }


    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int i, String s, Set<String> set) {
            String logs;
            switch (i) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("setAlias", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("setAlias", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_ALIAS, user.getUserId()), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + i;
                    Log.e("setAlias", logs);
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        init();
    }


    private void init() {
        Log.e("login", "init");
        // TODO Auto-generated method stub
//        mDialog = MyProgressDialog.createDialog(LoginActivity.this);
        btn_login = (Button) findViewById(R.id.login_login);
        tx_phonenumber = (EditText) findViewById(R.id.login_phonenum);
        tx_vertifycode = (EditText) findViewById(R.id.login_Password);
        btn_login = (Button) findViewById(R.id.login_login);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        tv_forget = (TextView) findViewById(R.id.tv_login_forgetpassword);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        user = new UserInfo();
        user.readData(this);
        if (!user.getUserPhone().equals("")) {
            tx_phonenumber.setText(user.getUserPhone());
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = tx_phonenumber.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(tx_phonenumber, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                if (NetBaseUtils.isConnnected(mContext)){
                    getLogin();
                }else {
                    ToastUtils.ToastShort(mContext,"当前无网络！");
                }

                break;
            case R.id.tv_login_register://  忘记密码
//                IntentUtils.getIntent(LoginActivity.this, RegisterActivity.class);
                IntentUtils.getIntent(LoginActivity.this, ForgetPasswordActivity.class);
                break;
            case R.id.tv_login_forgetpassword:// 忘记密码

                break;
        }
    }


    private void getLogin() {
        phone = tx_phonenumber.getText().toString();
        password = tx_vertifycode.getText().toString();
        user.setUserPhone(phone);
        user.setUserPassword(password);
        user.writeData(mContext);

        final String jpushId = JPushInterface.getRegistrationID(mContext);
        Log.e( "getLogin: ", jpushId);
        hud = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("登陆中...")
                .setCancellable(true);
        hud.show();
        scheduleDismiss();
        //线程

        new Thread() {
            public void run() {
                //1、获取输入的手机号码
                String phonenum = tx_phonenumber.getText().toString();
                //2、获取收入的密码
                String vertifycode = tx_vertifycode.getText().toString();
                //------逻辑判断
                if (phonenum.length() == 11) {
                    if (HttpTool.isConnnected(mContext)) {
                        result_data = HttpTool.Login(phonenum, vertifycode,jpushId);
                        Log.e("ok???", result_data);
                        handler.sendEmptyMessage(3);
                    }
                } else {
                    handler.sendEmptyMessage(4);

                }
                //--调用服务器登录函数


            }

        }.start();
    }

    private void scheduleDismiss() {
        if (hud!=null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hud.dismiss();
                }
            }, 2000);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if (!JPushInterface.isPushStopped(mContext)){
            JPushInterface.stopPush(mContext);
        }

    }
}
