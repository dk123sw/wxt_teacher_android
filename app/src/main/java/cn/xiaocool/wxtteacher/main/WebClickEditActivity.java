package cn.xiaocool.wxtteacher.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.actionsheet.ActionSheet;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.UserRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.view.PicassoImageLoader;
import cn.xiaocool.wxtteacher.view.PicassoPauseOnScrollListener;

/**
 * Created by wzh on 2016/1/29.
 */
public class WebClickEditActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private RoundImageView set_head_img;
    private LinearLayout reset_head_img,reset_name,reset_sex,reset_phone,reset_secert;
    private RelativeLayout rl_back;
    private TextView name_text,sex_text,phone_text;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private ArrayList<Drawable> drawables;
    private ArrayList<String> filepaths;
    private ArrayList<String> picnames=new ArrayList<>();
    private String filepath = "/sdcard/confimimg";
    private String picname = "newpic.jpg";
    private UserInfo user = new UserInfo();
    private FunctionConfig functionConfig;
    private ArrayList<PhotoInfo> mPhotoList = new ArrayList<>();
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private DisplayImageOptions options;
    private File imgFile;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 111:
                    name_text.setText(user.getUserName());
                    phone_text.setText(user.getUserPhone());
                    sex_text.setText(user.getUserGender().equals("1")?"男":"女");
                    ImageLoader.getInstance().displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+user.getUserImg(),set_head_img,options);
                    break;
                case 122:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals("success")){
                            new UserRequest(mContext,handler).updateHeadImg(picname, 123);
                            Log.e("pushhead","ok");
                        }else {
                            Log.e("pushhead","not");
                        }
                    }

                    break;
                case 123:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals("success")){
                            ToastUtils.ToastShort(WebClickEditActivity.this, "修改头像成功");
                            getData();
                        }else {
                            ToastUtils.ToastShort(WebClickEditActivity.this, "修改头像失败");
                        }

                    }

                    break;
                case 124:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals("success")){
                            ToastUtils.ToastShort(WebClickEditActivity.this, "修改姓名成功");
                            getData();
                        }else {
                            ToastUtils.ToastShort(WebClickEditActivity.this, "修改姓名失败");
                        }

                    }

                    break;

                case 125:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals("success")){
                            ToastUtils.ToastShort(WebClickEditActivity.this, "修改性别成功");
                           getData();
                        }else {
                            ToastUtils.ToastShort(WebClickEditActivity.this, "修改性别失败");

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
        setContentView(R.layout.my_teacher_edit);
        mContext = this;
        user.readData(mContext);
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square)
                .showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();

        init();
        getData();
    }

    private void getData() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
//            &learn=1&work=1&sing=1&labour=1&strain=1<>
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=GetUsers&userid="+user.getUserId();
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                JSONObject json = null;
                try {
                    json = new JSONObject(arg0);
                    String status = json.getString("status");
                    String data = json.getString("data");
                    if (status.equals("success")) {
                        JSONObject item = new JSONObject(data);
                        user.setUserName(item.getString("name"));
                        user.setUserPhone(item.optString("phone"));
                        user.setUserImg(item.optString("photo"));
                        user.setUserGender(item.optString("sex"));
                        user.writeData(mContext);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendEmptyMessage(111);
                }




            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        mQueue.add(request);
    }

    private void init() {
        //设置头像


        reset_head_img = (LinearLayout) findViewById(R.id.reset_head_img);
        reset_head_img.setOnClickListener(this);

        reset_name = (LinearLayout) findViewById(R.id.reset_name);
        reset_name.setOnClickListener(this);

        reset_sex = (LinearLayout) findViewById(R.id.reset_sex);
        reset_sex.setOnClickListener(this);

        reset_phone = (LinearLayout) findViewById(R.id.reset_phone);
        reset_phone.setOnClickListener(this);

        reset_secert = (LinearLayout) findViewById(R.id.reset_secert);
        reset_secert.setOnClickListener(this);

        set_head_img = (RoundImageView) findViewById(R.id.set_head_img);

        name_text = (TextView) findViewById(R.id.name_text);
        sex_text = (TextView) findViewById(R.id.sex_text);
        phone_text = (TextView) findViewById(R.id.phone_text);



        rl_back = (RelativeLayout) findViewById(R.id.up_jiantou);
        rl_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_head_img:
                showActionSheet();
                break;
            case R.id.reset_name:
                showDialog();
                break;
            case R.id.reset_sex:
                ShowSexDialog();
                break;
            case R.id.reset_phone:
//                Intent intent = new Intent(WebClickEditActivity.this, UpdatePhoneActivity.class);
//                startActivityForResult(intent, 101);
                break;
            case R.id.reset_secert:
                Intent intent1 = new Intent(WebClickEditActivity.this, ResetSecretActivity.class);
                startActivity(intent1);
                break;
            case R.id.up_jiantou:
                finish();
                break;
        }
    }



    private void showActionSheet() {
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;
        imageLoader = new PicassoImageLoader();
        pauseOnScrollListener = new PicassoPauseOnScrollListener(false, true);
        functionConfigBuilder.setMutiSelectMaxSize(1);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setRotateReplaceSource(true);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnablePreview(true);
        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        functionConfig = functionConfigBuilder.build();

        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor("#9BE5B4"))
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.WHITE)
                .setFabNornalColor(Color.parseColor("#9BE5B4"))
                .setFabPressedColor(Color.BLUE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.parseColor("#9BE5B4"))
                .setIconBack(R.drawable.ic_fanhui)
                .setIconRotate(R.mipmap.ic_action_repeat)
                .setIconCrop(R.mipmap.ic_action_crop)
                .build();

        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        ActionSheet.createBuilder(mContext, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("打开相册", "拍照")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {

                        switch (index) {
                            case 0:
                                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);

                                break;
                            case 1:

                                //获取拍照权限
                                if (hasPermission("android.permission.CAMERA")) {
                                    Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    // 判断存储卡是否可以用，可用进行存储
                                    String state = Environment.getExternalStorageState();
                                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                        File file = new File(path, "newpic.png");
                                        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                    }
                                    GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);

                                } else {


                                    String[] perms = {"android.permission.CAMERA"};

                                    int permsRequestCode = 200;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(perms, permsRequestCode);
                                    }

                                }

                                break;

                            default:
                                break;
                        }
                    }
                })
                .show();
    }





    private boolean canMakeSmores(){

        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    private boolean hasPermission(String permission){

        if(canMakeSmores()){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return(checkSelfPermission(permission)== PackageManager.PERMISSION_GRANTED);
            }

        }

        return true;

    }


    /**
     * 选择图片后 返回的图片数据
     */

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
//                localImgGridAdapter.notifyDataSetChanged();
                Bitmap bitmap;
                for (PhotoInfo photoInfo : resultList) {
                    bitmap = BitmapFactory.decodeFile(photoInfo.getPhotoPath(), getBitmapOption(2));
                    getImageToView(bitmap);

                }

                set_head_img.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage("file:/" + mPhotoList.get(0).getPhotoPath(), set_head_img, options);
                Log.e("mPhotoList", mPhotoList.toString());
                redsetHead();

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
    protected void ShowSexDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("男", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               if (!sex_text.getText().toString().equals("男")){
                   resetSex(1);
               }
                sex_text.setText("男");

            }
        }).setPositiveButton("女", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                if (!sex_text.getText().toString().equals("女")){
                    resetSex(0);
                }
                sex_text.setText("女");
            }
        }).show();
    }

    //重写onActivityResult以获得你需要的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 101:
                    if (data != null) {
                        String sss = data.getStringExtra("phone");
                        Log.e("phonme",sss);
                        phone_text.setText(sss);
                    }

                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 保存图片数据
     */
    private void getImageToView(Bitmap photo) {

        if (photo != null) {
            Random random=new Random();
            picname = "newsgroup" + random.nextInt(1000) + String.valueOf(new Date().getTime()) + ".jpg";
            Log.e("picname", picname);
            picnames.add(picname);
            storeImageToSDCARD(photo, picname, filepath);
        }
    }

    /**
     * storeImageToSDCARD 将bitmap存放到sdcard中
     */
    public void storeImageToSDCARD(Bitmap colorImage, String ImageName, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file, ImageName);
        try {
            imagefile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagefile);
            colorImage.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            imgFile = imagefile;
            filepath = imagefile.getPath();
            filepaths.add(imagefile.getPath());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 修改头像
     * */
    private void redsetHead() {

        Log.e("OkHttpUtils","redsetHead");

//        OkHttpUtils
//                .postFile()
//                .url("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=WriteMicroblog_upload")
//                .file(imgFile)
//                .build()
//                .execute(new cn.xiaocool.wxtteacher.utils.MyStringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("OkHttpUtils", e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (JsonParser.JSONparser(mContext, response)) {
//                            Log.e("OkHttpUtils", "成功");
//                            handler.sendEmptyMessage(122);
//                        }
//
//                    }
//                });

//        Map<String, File> files = new HashMap();
//        files.put("upfile", imgFile);
//        if (files==null){
//            Log.e("VolleyUtil", "files==null");
//        }
//        VolleyUtil.VolleyPostFileRequest(this, UserNetConstant.NET_USER_UPLOAD_HEAD_IMG, files, null, new VolleyUtil.VolleyJsonCallback() {
//            @Override
//            public void onSuccess(String result) {
//                if (JsonParser.JSONparser(getBaseContext(), result)) {
//
//                    handler.sendEmptyMessage(122);
//                }
//                Log.e("VolleyUtil", result);
//            }
//
//            @Override
//            public void onError() {
//                Log.e("VolleyUtil", "网络连接失败");
//            }
//        });
//
//        OkHttpUtils
//                .get()
//                .url("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=ParentContacts")
//                .addParams("userid", "605")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("OkHttpUtils", e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("OkHttpUtils", response.toString());
//                    }
//
//                });

//        OkHttpUtils.post()//
//                .addFile("mFile", picname, imgFile)//
//                .url("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=WriteMicroblog_upload")//
//                .build()//
//                .execute(new StringCallback(){
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("OkHttpUtils", e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (JsonParser.JSONparser(mContext, response)) {
//                            Log.e("OkHttpUtils", "成功");
//                            handler.sendEmptyMessage(122);
//                        }
//
//                    }
//
//                });
        new UserRequest(mContext,handler).pushImg(filepath, 122);

    }

    /**
     * 修改姓名
     * */
    private void resetName(String name) {
        new UserRequest(mContext,handler).updateName(name, 124);

    }

    /**
     * 修改性别
     * */
    private void resetSex(int sex) {
        new UserRequest(mContext,handler).updateSex(sex, 125);

    }

    private void showDialog() {

        // 1.创建弹出式对话框
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);    // 系统默认Dialog没有输入框

        // 获取自定义的布局
        View alertDialogView = View.inflate(mContext, R.layout.edit_and_send, null);
        final AlertDialog tempDialog = alertDialog.create();
        tempDialog.setView(alertDialogView, 0, 0, 0, 0);

        // 2.密码框-EditText。alertDialogView.findViewById(R.id.自定义布局中的文本框)
        final EditText et_dialog_confirmphoneguardpswd = (EditText) alertDialogView.findViewById(R.id.btn_discuss);

        // 确认按钮，确认验证密码
        Button btn_dialog_resolve_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_ok);
        btn_dialog_resolve_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                // 提取文本框中输入的文本密码
                if (et_dialog_confirmphoneguardpswd.getText().length() > 0) {
                    //获取到需要上传的参数
                    if (et_dialog_confirmphoneguardpswd.getText().toString().equals(name_text.getText().toString())){
                        Toast.makeText(mContext, "姓名与原名字相同，无需修改！", Toast.LENGTH_SHORT).show();
                    }else {
                        resetName(et_dialog_confirmphoneguardpswd.getText().toString());
                    }


                } else {

                    Toast.makeText(mContext, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }
                tempDialog.dismiss();
            }
        });
        // 取消按钮，不验证密码
        Button btn_dialog_cancel_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_cancel);
        btn_dialog_cancel_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                //
                tempDialog.dismiss();
            }
        });

        tempDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_dialog_confirmphoneguardpswd, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        /** 3.自动弹出软键盘 **/
        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_dialog_confirmphoneguardpswd, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
    }
}
