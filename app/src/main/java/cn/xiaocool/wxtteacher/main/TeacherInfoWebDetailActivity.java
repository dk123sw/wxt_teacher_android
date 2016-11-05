package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.SharePopupWindow;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.view.WxtApplication;


public class TeacherInfoWebDetailActivity extends Activity {

    private String itemid, title,type,content;
    private TextView title_bar_name;
    private WebView webView;
    private ImageView btn_exit;
    //private RelativeLayout right_share;
    private RelativeLayout tv_share;
    private   String a = "";
    private   int flag=0;
    private String name;
    SharePopupWindow takePhotoPopWin;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 666:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        String state = obj.optString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            JSONObject data = obj.optJSONObject("data");
                            String url = data.optString("url");
                            webView.loadUrl(url);
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
        setContentView(R.layout.activity_teacher_info_web_detail);

        initview();
    }


    public void showPopFormBottom(View view) {
        takePhotoPopWin = new SharePopupWindow(this, onClickListener);
        //SharePopupWindow takePhotoPopWin = new SharePopupWindow(this, onClickListener);
        takePhotoPopWin.showAtLocation(findViewById(R.id.webView), Gravity.TOP, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.haoyou:
                    ToastUtils.ToastShort(TeacherInfoWebDetailActivity.this, "微信好友");
                    setting();
                    break;
                case R.id.dongtai:
                    ToastUtils.ToastShort(TeacherInfoWebDetailActivity.this, "微信朋友圈");
                    history();
                    break;
            }
        }
    };
    private void initview() {

        name = getIntent().getStringExtra("name");
        tv_share = (RelativeLayout) findViewById(R.id.share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopFormBottom(v);
            }
        });
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        itemid = getIntent().getStringExtra("itemid");
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        content = getIntent().getStringExtra("content");
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        webView = (WebView) findViewById(R.id.webView);
        title_bar_name.setText(title);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        if (type!=null){

            //判断拼接网址
            if (type.equals("1")){
                a="teacher";
            }else if (type.equals("2")){
                a="baby";
            }else if (type.equals("3")){
                a="job";
            }else if (type.equals("4")){
                a = "Interest";
            }else if (type.equals("5")){
                a = "school";
            }else if (type.equals("6")){
                a= "notice";
            }else if (type.equals("7")){
                a= "news";
            }else if (type.equals("8")){
                a= "parentsthings";
            }



            if (type.equals("9")){
                webView.loadUrl("http://wxt.xiaocool.net/index.php?g=portal&m=article&a=system&id="+itemid);
            }else {
                webView.loadUrl(NetBaseConstant.NET_H5_HOST + "&a="+a+"&id="+itemid);
            }
        }


        if (name!=null){
            new NewsRequest(TeacherInfoWebDetailActivity.this,handler).getUrl(name, 666);
        }



        Log.e("webView",NetBaseConstant.NET_H5_HOST + "&a="+a+"&id="+itemid);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //返回的是true，说明是使用webview打开的网页。否则使用系统默认的浏览器
                return true;
            }
        });


       /* right_share = (RelativeLayout) findViewById(R.id.right_share);
        right_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu();
            }
        });*/

    }



    /**
     * 微信分享网页
     * */
    private void shareWX() {
        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage =new WXWebpageObject();
        webpage.webpageUrl=NetBaseConstant.NET_H5_HOST + "&a="+a+"&id="+itemid;
        WXMediaMessage msg =new WXMediaMessage(webpage);
        msg.title=title;
        msg.description=content;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share_wx);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "weiyi";
        req.message = msg;
        req.scene = flag==0? SendMessageToWX.Req.WXSceneSession: SendMessageToWX.Req.WXSceneTimeline;
        WxtApplication wxtApplication = WxtApplication.getInstance();
        wxtApplication.api.sendReq(req);
    }

    /**
     * 显示选择菜单
     */
    private void showPopupMenu() {
        View layout = LayoutInflater.from(this).inflate(R.layout.classattend_menu, null);
        PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(webView, Gravity.BOTTOM, 0, 0);

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

        final TextView qiandao_histroy = (TextView) layout.findViewById(R.id.qiandao_histroy);
        TextView quanxian_setting = (TextView) layout.findViewById(R.id.quanxian_setting);
        qiandao_histroy.setText("分享到微信朋友圈");
        quanxian_setting.setText("分享到微信好友");
        qiandao_histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history();
            }
        });
        quanxian_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });

    }
        /**
         * 分享到微信好友
         */
    private void setting() {
        //ToastUtils.ToastShort(this, "分享到微信好友");
        flag = 0;
        shareWX();
        takePhotoPopWin.dismiss();

    }

    /**
     * 分享到微信朋友圈
     */
    private void history() {
       // ToastUtils.ToastShort(this, "分享到微信朋友圈");
        flag = 1;
        shareWX();
        takePhotoPopWin.dismiss();
    }

}