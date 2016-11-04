package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.UserRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.MyDatePickerDialog;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class ApplySchoolActivity extends Activity implements View.OnClickListener {

    private EditText apply_name, aply_birthy, apply_address, apply_class, apply_phone_num, apply_qq, apply_wechat, apply_edu,
            apply_school, apply_remark;
    private RadioGroup apply_sex;
    private RadioButton sex_rdb_1, sex_rdb_2;
    private ImageView apply_addpic;
    private GridView apply_pic_grid;
    private RelativeLayout send_btn,up_jiantou;
    private int sex = 0;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private String data1 = "";
    private String data2 = "";
    private static final int ADD_IMG_KEY = 5;
    private static final int ADD_KEY = 4;
    private ArrayList<Drawable> drawables;
    private ArrayList<String> filepaths;
    private ArrayList<String> picnames;
    private String picname;
    private String pushImgName;
    private String filepath = "/sdcard/sendSchool";
    private String imagePath;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_APPLY:
                    JSONObject obj = (JSONObject) msg.obj;
                    Log.e("sdsd",obj.optString("status"));
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)) {

                        ToastUtils.ToastShort(getApplicationContext(),"发送成功");
                        finish();
                    }
                    break;
                case ADD_KEY:
                    if (msg.obj != null) {
                        JSONObject obj1 = (JSONObject) msg.obj;
                        if (obj1.optString("status").equals(CommunalInterfaces._STATE)) {
                            data2 = obj1.optString("data");
                            finish();
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
        setContentView(R.layout.activity_apply_school);
        initview();
    }

    private void initview() {
        drawables = new ArrayList<>();
        filepaths = new ArrayList<>();
        picnames = new ArrayList<>();
        apply_name = (EditText) findViewById(R.id.apply_name);
        aply_birthy = (EditText) findViewById(R.id.aply_birthy);
        apply_address = (EditText) findViewById(R.id.apply_address);
        apply_class = (EditText) findViewById(R.id.apply_class);
        apply_phone_num = (EditText) findViewById(R.id.apply_phone_num);
        apply_qq = (EditText) findViewById(R.id.apply_qq);
        apply_wechat = (EditText) findViewById(R.id.apply_wechat);
        apply_edu = (EditText) findViewById(R.id.apply_edu);
        apply_school = (EditText) findViewById(R.id.apply_school);
        apply_remark = (EditText) findViewById(R.id.apply_remark);
        apply_sex = (RadioGroup) findViewById(R.id.apply_sex);
        sex_rdb_1 = (RadioButton) findViewById(R.id.sex_rdb_1);
        sex_rdb_2 = (RadioButton) findViewById(R.id.sex_rdb_2);
        apply_addpic = (ImageView) findViewById(R.id.apply_addpic);
        apply_addpic.setOnClickListener(this);
        apply_pic_grid = (GridView) findViewById(R.id.apply_pic_grid);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        //日期选择
        aply_birthy.setOnClickListener(this);


        //性别选择
        apply_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sex_rdb_1) {//男
                    sex = 0;
                } else if (checkedId == R.id.sex_rdb_2) {//女
                    sex = 1;
                }
            }
        });


        //报名上传
        send_btn.setOnClickListener(this);

    }

    /**
     * 上传报名
     */
    private void submit() {

        //判断各个字段不能为空
        if (apply_name.length()<1||aply_birthy.length()<1||apply_address.length()<1||apply_class.length()<1||apply_phone_num.length()<1||
                apply_qq.length()<1||apply_wechat.length()<1||apply_edu.length()<1||apply_school.length()<0||apply_remark.length()<1){
            ToastUtils.ToastShort(getApplicationContext(), "无法登录，请输入所有信息");
        }else {
            if (NetUtil.isConnnected(this)) {

                for (int i = 0; i < filepaths.size(); i++) {
                    new UserRequest(this, handler).pushImg(filepaths.get(i), ADD_IMG_KEY);
                }
                for (int i = 0; i < picnames.size(); i++) {
                    if (i==0){

                    }
                    pushImgName = picnames.get(i) + "," + pushImgName;
                }
                Log.d("pushimg",pushImgName);
                pushImgName = pushImgName.substring(0,pushImgName.length()-5);
                Log.d("pushimg",pushImgName);
                new NewsRequest(this,handler).send_apply(apply_name.getText().toString(), String.valueOf(sex), aply_birthy.getText().toString(),
                        apply_address.getText().toString(), apply_class.getText().toString(), apply_phone_num.getText().toString(), apply_qq.getText().toString(),
                        apply_wechat.getText().toString(), apply_edu.getText().toString(), apply_school.getText().toString(), apply_remark.getText().toString(), null);

            } else {
                ToastUtils.ToastShort(ApplySchoolActivity.this, "网络请求失败");
            }

        }
    }

    /**
     * 日期选择器
     */
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        MyDatePickerDialog dlg = new MyDatePickerDialog(ApplySchoolActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始

                String data = year + "-" + monthOfYear + "-" + dayOfMonth;
                aply_birthy.setText(data);

            }
        }, year, month, day);
        dlg.show();

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //日期选择
            case R.id.aply_birthy:
                showDatePicker();
                break;
            //报名上传
            case R.id.send_btn:
                submit();
                break;
            //返回
            case R.id.up_jiantou:
                finish();
                break;
            //添加图片
            case R.id.apply_addpic:
                showMyDialog();
                break;
            default:
                break;
        }
    }

    /**
     * dialog展示
     * */
    private void showMyDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("相册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, PHOTO_REQUEST_ALBUM);

            }
        }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, "newpic.png");
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAMERA);
            }
        }).show();
    }

    //重写onActivityResult以获得你需要的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAMERA:// 相册
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, "newpic.png");
                        startPhotoZoom(Uri.fromFile(tempFile));
                        getImageToView(data);
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
                    startPhotoZoom(data.getData());
                    getImageToView(data);
                    break;

                case PHOTO_REQUEST_CUT: // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            drawables.add(drawable);
//            LocalImgGridAdapter localImgGridAdapter = new LocalImgGridAdapter(drawables, ApplySchoolActivity.this);
//            apply_pic_grid.setAdapter(localImgGridAdapter);
            picname = "album" + String.valueOf(new Date().getTime()) + ".png";
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
            colorImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            imagePath = imagefile.getPath();
            filepaths.add(imagefile.getPath());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
