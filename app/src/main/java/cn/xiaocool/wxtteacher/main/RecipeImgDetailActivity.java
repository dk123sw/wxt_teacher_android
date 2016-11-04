package cn.xiaocool.wxtteacher.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.app.ExitApplication;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class RecipeImgDetailActivity extends Activity implements View.OnClickListener {

    private RelativeLayout back;
    private ViewPager viewPager;
    private ArrayList<String> imgsList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private int selectedPosition;
    private Button btn_save;
    private Context mContext;
    private Bitmap mBitmap;
    private String path;
    private String type;
    private ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profession_circle_image);
        mContext = this;
        ExitApplication.getInstance().addActivity(this);
        initView();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        // 显示图片的配置
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square).cacheInMemory(true).cacheOnDisc(true).build();
        back = (RelativeLayout) findViewById(R.id.relativeLayout_profession_circle_image_back);
        back.setOnClickListener(this);
        btn_save = (Button) findViewById(R.id.btn_profession_cicle_imgeview_save);
        proDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
        imgsList = getIntent().getStringArrayListExtra("Imgs");
        type = getIntent().getStringExtra("type");
        int ID = getIntent().getIntExtra("position", 0);
        viewPager = (ViewPager) findViewById(R.id.viewPager_profession_cicle_image);
        PagerAdapter adapter = getViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(ID);
        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // 上传图片和数据资料
//                proDialog.setMessage("图片正在保存中，请稍等...");
//                proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                proDialog.setCanceledOnTouchOutside(false);
//                proDialog.show();
//                path= NetBaseConstant.NET_CIRCLEPIC_HOST + imgsList.get(selectedPosition);
//                new Thread(connectNet).start();
            }
        });
        // 默认显示数组第0位置的内容
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 滑动完成以后的事件方法，arg0为滑动以后的position
             */
            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
            }

            /**
             * 页面滑动起来的时候调用。arg0为当前显示的pager的position，arg1是滑动百分比，
             * arg2是滑动以后的position
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int position) {

            }

            /**
             * 页面滑动状态改变的时候被调用，arg0就是当前显示pager的position
             */
            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    private Runnable connectNet = new Runnable() {
        @Override
        public void run() {
            try {
                //以下是取得图片的两种方法
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap
                byte[] data = getImage(path);
                if (data != null) {
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                    saveImageToGallery(mContext, mBitmap);
                } else {
                    ToastUtils.ToastShort(mContext, "Image error!");
                }
                ////////////////////////////////////////////////////////

                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
//                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
                //********************************************************************/

                // 发送消息，通知handler在主线程中更新UI
                connectHanlder.sendEmptyMessage(0);
            } catch (Exception e) {
                ToastUtils.ToastShort(mContext, "无法链接网络！");
                e.printStackTrace();
            }
        }

    };
    @SuppressLint("HandlerLeak")
    private Handler connectHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            proDialog.setMessage("保存成功！");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    proDialog.dismiss();
                }
            }).start();
        }
    };

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Caterin");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }

    /**
     * 点击事件的处理
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout_profession_circle_image_back:
                finish();
                break;
        }
    }

    /**
     * 取得Viewpager的adapter填充
     *
     * @return
     */
    private PagerAdapter getViewPagerAdapter() {
        PagerAdapter adapter = new PagerAdapter() {
            /**
             * 获取要滑动的控件的数量，在这里以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
             */
            @Override
            public int getCount() {
                return imgsList.size();
            }

            /**
             * 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
             */
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;

            }

            /**
             * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
             */
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            /**
             * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，
             * 然后作为返回值返回即可
             */
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView ims = new ImageView(RecipeImgDetailActivity.this);
                ims.setScaleType(ImageView.ScaleType.FIT_CENTER);
                final String imagesUrl;
                imagesUrl = "http://wxt.xiaocool.net/uploads/recipe/" + imgsList.get(position);
                if (imagesUrl != null && !imagesUrl.equals("")) {
                    imageLoader.displayImage(imagesUrl, ims, options);
                }
                container.addView(ims);
                return ims;
            }

        };
        return adapter;
    }
}
