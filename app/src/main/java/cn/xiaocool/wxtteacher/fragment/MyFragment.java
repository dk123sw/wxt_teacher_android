
package cn.xiaocool.wxtteacher.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.app.ExitApplication;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.main.LoginActivity;
import cn.xiaocool.wxtteacher.main.SettingActivity;
import cn.xiaocool.wxtteacher.main.ShareAppActivity;
import cn.xiaocool.wxtteacher.main.SuggestListActivity;
import cn.xiaocool.wxtteacher.main.TeacherStyleActivity;
import cn.xiaocool.wxtteacher.main.WebClickEditActivity;
import cn.xiaocool.wxtteacher.net.request.constant.MeRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.view.WxtApplication;

/**
 * Created by wzh on 2016/2/23.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private UserInfo user = new UserInfo();
    private RoundImageView iv_me_fragment_avatar;
    private LinearLayout my_clock,my_my_score,my_maintain,my_online_service,system_tongzhi,app_share;
    private RelativeLayout function_teacher,settint_btn;
    private Activity mContext;
    private LinearLayout my_qrcode_dowland;
    private LinearLayout my_help;
    private RelativeLayout my_exit;
    private SharedPreferences sp;
    private TextView text1,teacher_name,phone_number;
    private String phoneNum;
    private DisplayImageOptions options;
    private Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ONLINE_SERVICE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject object = (JSONObject) jsonObject.get("data");
                            phoneNum = object.getString("phone");
                            phone_number.setText(phoneNum);
                            LogUtils.e("phone = ", phoneNum);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.ToastShort(getActivity(),"请求异常！");
                    }
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square)
                .showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
        sp = mContext.getSharedPreferences("list", mContext.MODE_PRIVATE);
        initView();
    }

    private void initView() {

        phone_number = (TextView) getView().findViewById(R.id.phone_number);
        text1=(TextView)getView().findViewById(R.id.teacher_introduce);
        //扫一扫打卡
        my_clock = (LinearLayout) getView().findViewById(R.id.my_clock);
        my_clock.setOnClickListener(this);
        //个人信息及头像
        function_teacher = (RelativeLayout) getView().findViewById(R.id.function_teacher);
        function_teacher.setOnClickListener(this);
        //积分商城
        my_my_score = (LinearLayout) getView().findViewById(R.id.my_my_score);
        my_my_score.setOnClickListener(this);
        //维护人员
        my_maintain = (LinearLayout) getView().findViewById(R.id.my_maintain);
        my_maintain.setOnClickListener(this);
        //在线客服
        my_online_service = (LinearLayout) getView().findViewById(R.id.my_online_service);
        my_online_service.setOnClickListener(this);
        //系统通知
        system_tongzhi = (LinearLayout) getView().findViewById(R.id.system_tongzhi);
        system_tongzhi.setOnClickListener(this);
        //二维码分享
        app_share = (LinearLayout) getView().findViewById(R.id.app_share);
        app_share.setOnClickListener(this);
        //设置
        settint_btn = (RelativeLayout) getView().findViewById(R.id.settint_btn);
        settint_btn.setOnClickListener(this);


        teacher_name = (TextView) getView().findViewById(R.id.teacher_name);
        iv_me_fragment_avatar = (RoundImageView) getView().findViewById(R.id.iv_me_fragment_avatar);

    }

    @Override
    public void onResume() {
        super.onResume();
        //获取客服电话
        new MeRequest(mContext, handler).onlineService();
        user.readData(mContext);
        teacher_name.setText(user.getUserName());
        ImageLoader.getInstance().displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + user.getUserImg(), iv_me_fragment_avatar, options);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //扫一扫打卡
            case R.id.my_clock:
                Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            //二维码分享
            case R.id.app_share:
                IntentUtils.getIntent(mContext, ShareAppActivity.class);
                break;
            //设置
            case R.id.settint_btn:
                IntentUtils.getIntent(mContext, SettingActivity.class);
                break;
            //在线客服
            case R.id.my_online_service:
                IntentUtils.getIntent(mContext, SuggestListActivity.class);

                break;
            case R.id.my_exit:
                getDialog();
                break;
            //个人信息及头像
            case R.id.function_teacher:
                IntentUtils.getIntent(mContext, WebClickEditActivity.class);
                break;
            //积分商城
            case R.id.my_my_score:
                ToastUtils.ToastShort(getActivity(),"暂无功能");
                break;
            //维护人员
            case R.id.my_maintain:
                serviceDialog();
                break;
            //系统通知
            case R.id.system_tongzhi:
                Intent intent1 = new Intent(mContext,TeacherStyleActivity.class);
                intent1.putExtra("type","9");
                intent1.putExtra("title","系统通知");
                startActivity(intent1);
                break;
        }
    }
//
    private void getDialog() {
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
                LogUtils.e("删除前", "111");
//				user.clearData(mContext);
                user.clearDataExceptPhone(mContext);
                SharedPreferences.Editor e = sp.edit();
                LogUtils.e("删除前", e.toString());
                e.clear();
                e.commit();
                WxtApplication.UID = 0;
                LogUtils.e("删除后", e.toString());
                IntentUtils.getIntents(mContext, LoginActivity.class);
                mContext.finish();
                ExitApplication.getInstance().exit();
            }
        });
    }

    private void serviceDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(
                R.layout.setting_service_dialog, null);

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
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNum));
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }
}


