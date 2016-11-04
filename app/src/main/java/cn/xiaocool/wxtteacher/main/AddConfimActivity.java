package cn.xiaocool.wxtteacher.main;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.UserRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.view.PicassoImageLoader;
import cn.xiaocool.wxtteacher.view.PicassoPauseOnScrollListener;

public class AddConfimActivity extends BaseActivity implements View.OnClickListener {

    private TextView  tv_select_count;
    private ImageView btn_exit, confim_addpic,iv_user_img;
    private RelativeLayout choose_student, send_btn;
    private EditText confim_content;
    private TextView choose_class;
    private ArrayList<Drawable> drawables;
    private ArrayList<String> filepaths;
    private ArrayList<String> picnames;
    private String filepath = "/sdcard/confimimg";
    private String picname = "newpic.jpg";
    private String studentId;

    private Context mContext;
    private ArrayList<String> ids;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁

    private DisplayImageOptions options;
    private static final int ADD_KEY = 4;
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
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_confim);
        mContext =this;
        initview();
    }

    private void initview() {
        tv_select_count = (TextView) findViewById(R.id.tv_select_count);
        iv_user_img = (ImageView) findViewById(R.id.iv_user_img);
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        confim_addpic = (ImageView) findViewById(R.id.confim_addpic);
        confim_addpic.setOnClickListener(this);
        choose_student = (RelativeLayout) findViewById(R.id.choose_student);
        choose_student.setOnClickListener(this);
        choose_class = (TextView) findViewById(R.id.choose_class);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        confim_content = (EditText) findViewById(R.id.confim_content);
        drawables = new ArrayList<>();
        filepaths = new ArrayList<>();
        picnames = new ArrayList<>();
        mPhotoList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出
            case R.id.btn_exit:
                finish();
                break;
            //增加图片
            case R.id.confim_addpic:
                addPic();
                break;
            //选择学生
            case R.id.choose_student:
                Intent intent = new Intent(AddConfimActivity.this, MyClassListActivity.class);
                intent.putExtra("type", "student");
                startActivityForResult(intent, 101);
                break;
            //发送按钮
            case R.id.send_btn:
                sendConfim();
                break;

        }
    }

    /**
     * 增加图片
     */
    private void addPic() {
        showActionSheet();
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
        functionConfigBuilder.setMutiSelectMaxSize(1);
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



    //重写onActivityResult以获得你需要的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {

                case 101:
                    if (data != null) {
                        String sss = data.getStringExtra("sss");
                        Log.e("sssss", sss);
                        ids = data.getStringArrayListExtra("ids");
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

                        for (int i = 0; i < ids.size(); i++){
                            studentId = studentId+","+ids.get(i);
                        }
                        studentId = studentId.substring(5,studentId.length());
                        choose_class.setText(haschoose);
                        tv_select_count.setText("共选择" + ids.size() + "人");
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

                iv_user_img.setVisibility(View.VISIBLE);
                options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square).cacheInMemory(true).cacheOnDisc(true).build();

                ImageLoader.getInstance().displayImage("file:/" + mPhotoList.get(0).getPhotoPath(), confim_addpic, options);
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
            colorImage.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            filepaths.add(imagefile.getPath());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送代接信息
     */
    private void sendConfim() {
        if (confim_content.getText().length() > 0) {
            if (NetUtil.isConnnected(this)) {
                if (filepaths.size()>0){
                    hud = KProgressHUD.create(this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true);
                    hud.show();
                    new UserRequest(this,handler).pushImg(filepaths.get(0),ADD_IMG_KEY1);
                }else {
                    hud = KProgressHUD.create(this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true);
                    hud.show();
                    new NewsRequest(this,handler).send_confim(studentId,confim_content.getText().toString(),"null",ADD_KEY);
                }

            } else {
                Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            ToastUtils.ToastShort(AddConfimActivity.this, "发送内容不能为空");
        }
    }


    private void send() {
        new NewsRequest(this,handler).send_confim(studentId,confim_content.getText().toString(),picname,ADD_KEY);

    }
}
