package cn.xiaocool.wxtteacher.main;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jauker.widget.BadgeView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.wxtteacher.Constant;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.app.ExitApplication;
import cn.xiaocool.wxtteacher.bean.CheckVersionModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.fragment.FunctionFragment;
import cn.xiaocool.wxtteacher.fragment.MyFragment;
import cn.xiaocool.wxtteacher.fragment.NewsFragment;
import cn.xiaocool.wxtteacher.fragment.ParadiseFragment;
import cn.xiaocool.wxtteacher.fragment.TrendsFragment;
import cn.xiaocool.wxtteacher.service.update.UpdateService;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.view.NiceDialog;
import cn.xiaocool.wxtteacher.view.WxtApplication;


/**
 * Created by wzh on 2016/2/21.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private NewsFragment newsFragment;
    private ParadiseFragment addressFragment;
    private FunctionFragment functionFragment;
    private TrendsFragment trendsFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private Button[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private UserInfo user = new UserInfo();
    private TextView tv_unread_space_msg_number;
    private SharedPreferences sp;
    private LinearLayout main_bottom;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private Context mContext;
    //记录Fragment的位置
    private int position = 0;
    private static final int REQUEST_WRITE_STORAGE = 111;
    private static final String JPUSHMESSAGE = "JPUSHMESSAGE";
    private static final String JPUSHTRUST = "JPUSHTRUST";
    private static final String JPUSHNOTICE = "JPUSHNOTICE";
    private static final String JPUSHBACKLOG = "JPUSHBACKLOG";
    private static final String JPUSHDAIJIE = "JPUSHDAIJIE";
    private static final String JPUSHLEAVE = "JPUSHLEAVE";
    private Receiver receiver;
    public static MainActivity mInstace = null;
    private BadgeView news,function;
    private CheckVersionModel versionModel;
    private NiceDialog mDialog = null;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LogUtils.e("mainActivity", "onNewIntent");
        if (intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        LogUtils.e("mainActivity", "onCreate");
        mContext = this;
        mInstace = this;


        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }

        Context context = getApplicationContext();
        user.readData(context);


        //初始化组件实例
        initView();

        //创建Fragment的实例
        newsFragment = new NewsFragment();
        addressFragment = new ParadiseFragment();
        functionFragment = new FunctionFragment();
        trendsFragment = new TrendsFragment();
        myFragment = new MyFragment();

        fragments = new Fragment[]{newsFragment, functionFragment, trendsFragment, addressFragment, myFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, newsFragment);
        transaction.commit();

        receiver = new Receiver();
        IntentFilter filter = new IntentFilter("com.USER_ACTION");
        registerReceiver(receiver, filter);
        mDialog = new NiceDialog(MainActivity.this);

        checkVersion();

    }
    /**
     * 检查版本更新
     */
    private void checkVersion() {
        String versionId = getResources().getString(R.string.versionid).toString();

        String url =  "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=CheckVersion&type=android&versionid="+versionId;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                Log.d("onResponse", result);
                if (JSONparser(mContext,result)){
                    versionModel = getBeanFromJson(result);
                    showDialogByYorNo(versionModel.getVersionid());
                    ProgressViewUtil.dismiss();
                }else {

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ProgressViewUtil.dismiss();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    //展示dialog
    private void showDialogByYorNo(String versionid) {

        if (Integer.valueOf(versionid)>Integer.valueOf(getResources().getString(R.string.versionid).toString())){
            mDialog.setTitle("发现新版本");
            mDialog.setContent(versionModel.getDescription());
            mDialog.setOKButton("立即更新", new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //请求存储权限
                    boolean hasPermission = (ContextCompat.checkSelfPermission(mContext,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                    if (!hasPermission) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                        ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else {
                        //下载
                        startDownload();
                    }

                }
            });
            mDialog.show();
        }else {
            mDialog.setTitle("已经是最新版本");
            mDialog.setContent("感谢您的使用！");
            mDialog.setOKButton("确定", new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            mDialog.show();
        }
    }

    /**
     * 启动下载
     */
    private void startDownload() {
        Intent it = new Intent(MainActivity.this, UpdateService.class);
        //下载地址
        it.putExtra("apkUrl", versionModel.getUrl());
        startService(it);
        mDialog.dismiss();
    }
    /**
     * 判断返回成功失败
     *
     * @param context
     * @param result
     * @return
     */
    public static Boolean JSONparser(Context context, String result) {
        Boolean flag = true;
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("status").equals("success")) {
                flag = true;
            } else if (json.getString("status").equals("error")) {
                flag = false;
            }

        } catch (JSONException e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 字符串转模型
     * @param result
     * @return
     */
    private CheckVersionModel getBeanFromJson(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<CheckVersionModel>() {
        }.getType());
    }
    private void initView() {
        main_bottom = (LinearLayout) findViewById(R.id.main_bottom);
        tv_unread_space_msg_number = (TextView) findViewById(R.id.tv_unread_space_msg_number);
        //初始化底部5个按钮

        mTabs = new Button[5];
        mTabs[0] = (Button) findViewById(R.id.btn_news);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_function);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) findViewById(R.id.btn_trends);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) findViewById(R.id.btn_address);
        mTabs[3].setOnClickListener(this);
        mTabs[4] = (Button) findViewById(R.id.btn_my);
        mTabs[4].setOnClickListener(this);
        //设置第一个按钮为选中状态
        mTabs[0].setSelected(true);
        news = new BadgeView(this);
        news.setTargetView(findViewById(R.id.btn_container_space));
        function = new BadgeView(this);
        function.setTargetView(findViewById(R.id.btn_container_news));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_news:
                index = 0;
                break;
            case R.id.btn_function:
                index = 1;
                break;
            case R.id.btn_trends:
                index = 2;
                break;
            case R.id.btn_address:
                index = 3;
                break;
            case R.id.btn_my:
                index = 4;
                break;

        }

        if (currentTabIndex != index) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                transaction.add(R.id.fragment_container, fragments[index]);

            }
            transaction.show(fragments[index]);
            transaction.commit();

        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;

    }

    /**
     * show the dialog when user logged into another device
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;

        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        LogUtils.e("删除前", "111");
                        JPushInterface.stopPush(mContext);
                        sp = mContext.getSharedPreferences("list", mContext.MODE_PRIVATE);
                        user.clearDataExceptPhone(mContext);
                        SharedPreferences.Editor e = sp.edit();
                        LogUtils.e("删除前", e.toString());
                        e.clear();
                        e.commit();
                        WxtApplication.UID = 0;
                        LogUtils.e("删除后", e.toString());
                        IntentUtils.getIntents(mContext, LoginActivity.class);
                        finish();
                        ExitApplication.getInstance().exit();

                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();

            } catch (Exception e) {

            }

        }



    }

    /**
     * show the dialog if user account is removed
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage("聊天账号已被移除");
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();

            } catch (Exception e) {
//                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }

    /**
     * 设置小红点
     */
    public  void setRedPoint(){
        //从本地取出各个小红点的个数
        int message = (int) SPUtils.get(this,JPUSHMESSAGE,0);
        int trust = (int) SPUtils.get(this,JPUSHTRUST,0);
        int notice = (int) SPUtils.get(this,JPUSHNOTICE,0);
        int backlogNum = (int) SPUtils.get(this,JPUSHBACKLOG,0);
        int daijieNum = (int) SPUtils.get(this,JPUSHDAIJIE,0);
        int leaveNum = (int) SPUtils.get(this,JPUSHLEAVE,0);

        setBadgeView(daijieNum, functionFragment.btn_daijie);
        setBadgeView(leaveNum, functionFragment.btn_leave);
        if (message+trust+notice+backlogNum>0){
            news.setVisibility(View.VISIBLE);
            news.setText("...");
        }else {
            news.setVisibility(View.GONE);
        }
        if (daijieNum+leaveNum>0){
            function.setVisibility(View.VISIBLE);
            function.setText("...");
        }else {
            function.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("mainActivity", "onResume");
        setRedPoint();
    }

    /**
     * 设置背景
     * @param message
     * @param message1
     */
    private void setBadgeView(int message, BadgeView message1) {
        if (message1==null)return;
        if (message==0){
            message1.setVisibility(View.GONE);
        }else {
            message1.setVisibility(View.VISIBLE);
            message1.setBadgeCount(message);
        }
    }


    /**
     * 接受推送通知并通知页面添加小红点
     */
    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Recevier1", "接收到:");
            setRedPoint();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("mainActivity", "onDestroy");
        unregisterReceiver(receiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("mainActivity","onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("mainActivity","onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e("mainActivity", "onStart");
    }



}
