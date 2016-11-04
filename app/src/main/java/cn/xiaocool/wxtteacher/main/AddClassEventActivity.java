package cn.xiaocool.wxtteacher.main;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import com.baoyz.actionsheet.ActionSheet;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.LocalImgGridAdapter;
import cn.xiaocool.wxtteacher.app.ExitApplication;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.UserRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.view.PicassoImageLoader;
import cn.xiaocool.wxtteacher.view.PicassoPauseOnScrollListener;

public class AddClassEventActivity extends BaseActivity implements View.OnClickListener {
    private TextView  tv_select_count;
    private Context mContext;
    private UserInfo user;
    private String type, contents, title;
    private String picname = "";
    private String filepath = "/sdcard/myheader";
    private String head = null;
    private RelativeLayout back;
    private TextView submit, tv_chooseclass;
    private EditText et_event_title, et_event_content;
    private static final int ADD_KEY = 4;
    private static final int ADD_IMG_KEY = 5;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private GridView homework_pic_grid;
    private LocalImgGridAdapter localImgGridAdapter;

    private ArrayList<Drawable> drawables;
    private ArrayList<String> filepaths;
    private ArrayList<String> picnames;

    private EditText contact_name, contact_phone;
    private RelativeLayout start_layout, finish_layout,start_attend_layout,finish_attend_layout;
    private LinearLayout attend_layout;
    private TextView start_text, finish_text,start_attend_text,finish_attend_text;
    private CheckBox class_event_checkBox;
    private String begintime, finishtime,beginattendtime,finishattendtime;

    private String studentids;

    private static final int ADD_IMG_KEY1 = 101;
    private static final int ADD_IMG_KEY2 = 102;
    private static final int ADD_IMG_KEY3 = 103;
    private static final int ADD_IMG_KEY4 = 104;
    private static final int ADD_IMG_KEY5 = 105;
    private static final int ADD_IMG_KEY6 = 106;
    private static final int ADD_IMG_KEY7 = 107;
    private static final int ADD_IMG_KEY8 = 108;
    private static final int ADD_IMG_KEY9 = 109;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private ArrayList<PhotoInfo> mPhotoList;
    private String pushImgName;
    private KProgressHUD hud;
    private int imgFlag = 0;
    private FunctionConfig functionConfig;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case ADD_IMG_KEY1:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            imgFlag = 1;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY2);
                            } else {
                                send();
                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败1" + obj.optString("data"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY2:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                            imgFlag = 2;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY3);
                            } else {
                                send();
                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败2", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY3:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                            imgFlag = 3;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY4);
                            } else {
                                send();                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败3", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY4:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                            imgFlag = 4;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY5);
                            } else {
                                send();                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败4", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY5:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                            imgFlag = 5;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY6);
                            } else {
                                send();
                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败5", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY6:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                            imgFlag = 6;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY7);
                            } else {
                                send();                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败6", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY7:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                            imgFlag = 7;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY8);
                            } else {
                                send();                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败7", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY8:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            imgFlag = 8;
                            if (imgFlag < filepaths.size()) {
                                new UserRequest(mContext, handler).pushImg(filepaths.get(imgFlag), ADD_IMG_KEY9);
                            } else {
                                send();                            }
                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败8", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_IMG_KEY9:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                           send();

                        } else {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送失败9", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case ADD_KEY:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            hud.dismiss();
                            Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            hud.dismiss();

                            Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 5231:
                    mPhotoList.remove((int)msg.obj);
                    localImgGridAdapter = new LocalImgGridAdapter(mPhotoList, mContext,handler);
                    homework_pic_grid.setAdapter(localImgGridAdapter);
                    filepaths.remove((int) msg.obj);
                    picnames.remove((int)msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_class_event);
        ExitApplication.getInstance().addActivity(this);
        mContext = this;
        user = new UserInfo(mContext);
        initView();
    }

    private void initView() {
        tv_select_count = (TextView) findViewById(R.id.tv_select_count);
        type = "班级活动";
        back = (RelativeLayout) findViewById(R.id.up_jiantou);
        back.setOnClickListener(this);
        submit = (TextView) findViewById(R.id.class_event_send);
        submit.setOnClickListener(this);
        tv_chooseclass = (TextView) findViewById(R.id.choose_class);
        tv_chooseclass.setOnClickListener(this);
        et_event_title = (EditText) findViewById(R.id.class_event_title);
        et_event_content = (EditText) findViewById(R.id.class_event_content);
        homework_pic_grid = (GridView) findViewById(R.id.homework_pic_grid);
        String classid = getIntent().getStringExtra("classID");
        String classname = getIntent().getStringExtra("className");
        String type = getIntent().getStringExtra("type");
        tv_chooseclass.setText(classname);

        mPhotoList = new ArrayList<>();
        drawables = new ArrayList<>();
        filepaths = new ArrayList<>();
        picnames = new ArrayList<>();


        //添加图片按钮
        localImgGridAdapter = new LocalImgGridAdapter(mPhotoList, mContext,handler);
        homework_pic_grid.setAdapter(localImgGridAdapter);
        homework_pic_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("position", String.valueOf(position));
                if (position == mPhotoList.size()) {
                    showActionSheet();
                }

            }
        });

        contact_name = (EditText) findViewById(R.id.contact_name);
        contact_name.setText(user.getUserName());
        contact_phone = (EditText) findViewById(R.id.contact_phone);
        contact_phone.setText(user.getUserPhone());
        start_layout = (RelativeLayout) findViewById(R.id.start_layout);
        finish_layout = (RelativeLayout) findViewById(R.id.finish_layout);
        start_layout.setOnClickListener(this);
        finish_layout.setOnClickListener(this);

        attend_layout = (LinearLayout) findViewById(R.id.attend_layout);
        start_attend_layout = (RelativeLayout) findViewById(R.id.start_attend_layout);
        finish_attend_layout = (RelativeLayout) findViewById(R.id.finish_attend_layout);
        start_attend_layout.setOnClickListener(this);
        finish_attend_layout.setOnClickListener(this);


        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        start_text = (TextView) findViewById(R.id.start_text);
        finish_text = (TextView) findViewById(R.id.finish_text);
        start_attend_text = (TextView) findViewById(R.id.start_attend_text);
        finish_attend_text = (TextView) findViewById(R.id.finish_attend_text);

        setDefaultTime(start_text,"0");
        setDefaultTime(finish_text,"1");
        setDefaultTime(start_attend_text,"2");
        setDefaultTime(finish_attend_text,"3");

        class_event_checkBox = (CheckBox) findViewById(R.id.class_event_checkBox);
        class_event_checkBox.setOnClickListener(this);
    }

    private void setDefaultTime(final TextView v, final String type) {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = 0;
        if (type.equals("0")) {
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else if (type.equals("1")) {
            day = cal.get(Calendar.DAY_OF_MONTH)+1;
        }else if (type.equals("2")){
            day = cal.get(Calendar.DAY_OF_MONTH);
        }else if (type.equals("3")){
            day = cal.get(Calendar.DAY_OF_MONTH)+1;
        }



        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);

        String data = year + "-" + (month+1) + "-" + day;
        v.setText(data);
        //时分秒用0代替
        String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);
        Log.e("--333333---", data_new);

        if (type.equals("0")) {
            begintime = data_new;
        } else if (type.equals("1")) {
            finishtime = data_new;
        }else if (type.equals("2")){
            beginattendtime = data_new;
        }else if (type.equals("3")){
            finishattendtime = data_new;
        }
    }


    private void setDateText(final TextView v, final String type) {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);

        Log.e("MONTH", "year" + year);
        Log.e("MONTH", "month" + month);
        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);

        DatePickerDialog dlg = new DatePickerDialog(AddClassEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始

                String data = year + "-" + monthOfYear + "-" + dayOfMonth;
                v.setText(data);
//                        String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);

                //时分秒用0代替
                String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);
                Log.e("--444444---", data_new);

                if (type.equals("0")) {

                    begintime = data_new;
                } else if (type.equals("1")) {
                    finishtime = data_new;
                }else if (type.equals("2")){
                    beginattendtime = data_new;
                }else if (type.equals("3")){
                    finishattendtime = data_new;
                }
                Log.e("--555555---", data_new);

            }

        }, year, month, day);
        dlg.show();

    }


    /**
     * 将时间转换为时间戳
     */
    public String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
//            Log.d("--444444---", times);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
    /**
     * 展示dialog
     */

    private void showActionSheet() {
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;
        imageLoader = new PicassoImageLoader();
        pauseOnScrollListener = new PicassoPauseOnScrollListener(false, true);
        functionConfigBuilder.setMutiSelectMaxSize(9);
        functionConfigBuilder.setEnableEdit(false);
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


    /**
     * 选择图片后 返回的图片数据
     */

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {


                filepaths.clear();
                picnames.clear();
                mPhotoList.clear();

                mPhotoList.addAll(resultList);
//                localImgGridAdapter.notifyDataSetChanged();
                Bitmap bitmap;
                for (PhotoInfo photoInfo : resultList) {
                    bitmap = BitmapFactory.decodeFile(photoInfo.getPhotoPath(), getBitmapOption(2));
                    getImageToView(bitmap);

                }

                localImgGridAdapter = new LocalImgGridAdapter(mPhotoList, mContext,handler);
                homework_pic_grid.setAdapter(localImgGridAdapter);
                Log.e("mPhotoList", mPhotoList.toString());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.choose_class:
                Intent intent = new Intent(AddClassEventActivity.this, MyClassListActivity.class);
                intent.putExtra("type", "student");
                startActivityForResult(intent, 101);
                break;
            case R.id.class_event_send:

                sendClassevent();

                break;
            case R.id.start_layout:
                setDateText(start_text, "0");
                break;
            case R.id.finish_layout:
                setDateText(finish_text, "1");
                break;

            case R.id.start_attend_layout:
                setDateText(start_attend_text, "2");
                break;
            case R.id.finish_attend_layout:
                setDateText(finish_attend_text, "3");
                break;

            case R.id.class_event_checkBox:
                if (class_event_checkBox.isChecked()){
                    attend_layout.setVisibility(View.VISIBLE);
                }else {
                    attend_layout.setVisibility(View.GONE);
                }

                break;
        }
    }

    private void sendClassevent() {
        contents = et_event_content.getText().toString();
        title = et_event_title.getText().toString();
        if (title.length() > 0 && contents.length() > 0) {
            if (begintime != null && finishtime != null) {
                if (contact_name.getText().length() > 0) {
                    if (contact_phone.getText().length() == 11) {
                        if (studentids==null){

                            ToastUtils.ToastShort(mContext, "请选择接收人！");
                        }else {

                            if (filepaths.size()>0){
                                hud = KProgressHUD.create(this)
                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setCancellable(true);
                                hud.show();

                                for (int i = 0; i < picnames.size(); i++) {
                                    pushImgName = picnames.get(i) + "," + pushImgName;
                                }
                                pushImgName = pushImgName.substring(0, pushImgName.length() - 5);
                                new UserRequest(this, handler).pushImg(filepaths.get(0), ADD_IMG_KEY1);
                            }else {
                                send();
                            }
                        }
                    } else {
                        ToastUtils.ToastShort(mContext, "请输入正确的手机号！");
                    }
                } else {
                    ToastUtils.ToastShort(mContext, "请输入联系人姓名！");
                }
            } else {
                ToastUtils.ToastShort(mContext, "请输入活动开始或结束时间！");
            }

        } else {
            ToastUtils.ToastShort(mContext, "请输入标题或内容！");
        }
    }

    private void send() {
        if (class_event_checkBox.isChecked()){
            if (beginattendtime!=null&&finishattendtime!=null){
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true);
                hud.show();
                new NewsRequest(mContext, handler).send_event(studentids,title, contents, pushImgName, begintime, finishtime, beginattendtime,finishattendtime,contact_name.getText().toString(), contact_phone.getText().toString(), "1",ADD_KEY);

            }else {
                ToastUtils.ToastShort(mContext, "请输入活动报名开始或结束时间！");
            }
        }else {
            beginattendtime = "null";
            finishattendtime = "null";
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true);
            hud.show();
            new NewsRequest(mContext, handler).send_event(studentids,title, contents, pushImgName, begintime, finishtime, beginattendtime,finishattendtime, contact_name.getText().toString(), contact_phone.getText().toString(), "0",ADD_KEY);
        }
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


    @Override

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if(cameraAccepted){
                    //授权成功之后，调用系统相机进行拍照操作等


                }else{
                    //用户授权拒绝之后，友情提示一下就可以了
                    ToastUtils.ToastShort(this,"已拒绝进入相机，如想开启请到设置中开启！");
                }

                break;

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {

                case 101:
                    if (data != null) {
                        String sss = data.getStringExtra("sss");
                        Log.e("sssss", sss);
                        ArrayList<String> ids = data.getStringArrayListExtra("ids");
                        ArrayList<String> names = data.getStringArrayListExtra("names");
                        String haschoose = "";
                        for (int i = 0; i < names.size(); i++) {
                            if (i < 3) {
                                if (names.get(i) != null || names.get(i) != "null") {
                                    haschoose = haschoose + names.get(i) + "、";
                                }
                            } else if (i == 4) {
                                haschoose = haschoose.substring(0, haschoose.length() - 1);
                                haschoose = haschoose + "等...";
                            }

                        }

                        for (int i = 0; i < ids.size(); i++) {
                            studentids = studentids + "," + ids.get(i);
                        }
                        studentids = studentids.substring(5, studentids.length());
                        tv_chooseclass.setText(haschoose);
                        tv_select_count.setText("共选择" + ids.size() + "人");
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
            String picname = "newsgroup" + random.nextInt(1000) + String.valueOf(new Date().getTime()) + ".jpg";
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
            colorImage.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            filepaths.add(imagefile.getPath());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
