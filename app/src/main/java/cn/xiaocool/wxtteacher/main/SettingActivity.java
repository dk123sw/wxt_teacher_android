package cn.xiaocool.wxtteacher.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.app.ExitApplication;
import cn.xiaocool.wxtteacher.bean.CheckVersionModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.service.update.UpdateService;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.AppUtils;
import cn.xiaocool.wxtteacher.utils.DataCleanManager;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.view.NiceDialog;
import cn.xiaocool.wxtteacher.view.WxtApplication;

public class SettingActivity extends Activity implements View.OnClickListener{

//    <include layout="@layout/my_help"></include>
//
//    <include layout="@layout/my_suggest"></include>
//
//    <include layout="@layout/my_announcement"></include>
//
//    <include layout="@layout/update_version"></include>
//
//    <include layout="@layout/my_clean_cache"></include>
//
//    <include layout="@layout/my_exit"></include>

    private LinearLayout my_help,my_suggest,my_announcement,update_version,my_clean_cache;
    private RelativeLayout my_exit,up_jiantou;
    private UserInfo user = new UserInfo();
    private SharedPreferences sp;
    private Activity mContext;
    private KProgressHUD hud;
    private TextView version_text,cashe_text;
    private CheckVersionModel versionModel;
    private NiceDialog mDialog = null;
    private static final int REQUEST_WRITE_STORAGE = 111;
    private static final String APK_DOWNLOAD_URL = "http://hyx.xiaocool.net/hyx.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        mContext = this;
        sp = mContext.getSharedPreferences("list", mContext.MODE_PRIVATE);
        mDialog = new NiceDialog(SettingActivity.this);
        initview();
    }

    /**
     * 初始化控件
     * */
    private void initview() {
        //使用帮助
        my_help = (LinearLayout) findViewById(R.id.my_help);
        my_help.setOnClickListener(this);
        //意见反馈
        my_suggest = (LinearLayout) findViewById(R.id.my_suggest);
        my_suggest.setOnClickListener(this);
        //关于我们
        my_announcement = (LinearLayout) findViewById(R.id.my_announcement);
        my_announcement.setOnClickListener(this);
        //版本更新
        update_version = (LinearLayout) findViewById(R.id.update_version);
        update_version.setOnClickListener(this);
        //清除缓存
        my_clean_cache = (LinearLayout) findViewById(R.id.my_clean_cache);
        my_clean_cache.setOnClickListener(this);
        //退出
        my_exit = (RelativeLayout) findViewById(R.id.my_exit);
        my_exit.setOnClickListener(this);

        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);

        version_text = (TextView) findViewById(R.id.version_text);
        version_text.setText(AppUtils.getVersionName(mContext));
        cashe_text = (TextView) findViewById(R.id.cashe_text);
        try {
            cashe_text.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //使用帮助
            case R.id.my_help:
                IntentUtils.getIntent(mContext, MeHelpBackActivity.class);
                break;
            //意见反馈
            case R.id.my_suggest:
                IntentUtils.getIntent(mContext, SuggestBackActivity.class);
                break;
            //关于我们
            case R.id.my_announcement:
                IntentUtils.getIntent(mContext, AboutUsActivity.class);
                break;
            //版本更新
            case R.id.update_version:
                checkVersion();
                break;
            //清除缓存
            case R.id.my_clean_cache:
                clearcache();
                break;
            //退出
            case R.id.my_exit:
                exitDialog();
                break;
            //退出
            case R.id.up_jiantou:
               finish();
                break;

        }
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
    /**
     * 检查版本更新
     */
    private void checkVersion() {
        ProgressViewUtil.show(mContext);
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
                    ProgressViewUtil.dismiss();
                    mDialog.setTitle("暂无最新版本");
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
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ProgressViewUtil.dismiss();
            }
        });
        Volley.newRequestQueue(this).add(request);

//        VolleyUtil.VolleyPostRequest(mContext, url, new VolleyUtil.VolleyJsonCallback() {
//            @Override
//            public void onSuccess(String result) {
//                if (JSONparser(mContext,result)){
//                    versionModel = getBeanFromJson(result);
//                    showDialogByYorNo(versionModel.getVersionid());
//                }else {
//                    mDialog.setTitle("暂无最新版本");
//                    mDialog.setContent("感谢您的使用！");
//                    mDialog.setOKButton("确定", new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            mDialog.dismiss();
//                        }
//                    });
//                    mDialog.show();
//                }
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
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
                    boolean hasPermission = (ContextCompat.checkSelfPermission(SettingActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                    if (!hasPermission) {
                        ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                        ActivityCompat.shouldShowRequestPermissionRationale(SettingActivity.this,
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获取到存储权限,进行下载
                    startDownload();
                } else {
                    Toast.makeText(SettingActivity.this, "不授予存储权限将无法进行下载!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /**
     * 清楚缓存
     * */
    private void clearcache() {
        hud = KProgressHUD.create(SettingActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("清除中")
                .setCancellable(true);
        hud.show();
        DataCleanManager.clearAllCache(mContext);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    hud.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cashe_text.setText("0.0Byte");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 启动下载
     */
    private void startDownload() {
        Intent it = new Intent(SettingActivity.this, UpdateService.class);
        //下载地址
        it.putExtra("apkUrl", APK_DOWNLOAD_URL);
        startService(it);
        mDialog.dismiss();
    }

    /**
     * 退出
     * */
    private void exitDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(
                R.layout.setting_dialog, null);

        // 对话框
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .create();
        dialog.show();
        dialog.getWindow().setContentView(layout);

        // 取消按钮
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮
        Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hud = KProgressHUD.create(SettingActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDetailsLabel("退出中")
                        .setCancellable(true);
                hud.show();
                LogUtils.e("删除前", "111");
//				user.clearData(mContext);
                user.clearDataExceptPhone(mContext);
                JPushInterface.stopPush(mContext);
                SPUtils.remove(mContext, "newsGroupRecive");
                SPUtils.remove(mContext,"receiveParentWarn");
                SPUtils.remove(mContext,"noticeRecive");
                SPUtils.remove(mContext,"backlogData");
                SPUtils.remove(mContext,"teacherCommunication");
                SPUtils.remove(mContext,"homeWork");
                SharedPreferences.Editor e = sp.edit();
                LogUtils.e("删除前", e.toString());
                e.clear();
                e.commit();

                WxtApplication.UID = 0;
                LogUtils.e("删除后", e.toString());
                MainActivity.mInstace.finish();
                IntentUtils.getIntents(mContext, LoginActivity.class);
                mContext.finish();
                ExitApplication.getInstance().exit();
            }
        });
    }
}
