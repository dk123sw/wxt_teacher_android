package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.request.constant.WebHome;

/**
 * Created by Administrator on 2016/3/21.
 */
public class SpaceClickClassSpecificEventActivity extends Activity {
    private TextView title;
    private ImageView classactivityImageView;
    private TextView content;
    private TextView releasename;
    private ImageView btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_class_specificevent);
        initView();
        Intent intent = getIntent();
        final String activity_pic = intent.getStringExtra("activity_pic");
        ImageLoader.getInstance().displayImage(WebHome.NET_CLASSEVENT_HOST + activity_pic,classactivityImageView,new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build());
        title.setText(intent.getStringExtra("activity_title"));
        content.setText(intent.getStringExtra("activity_content"));
        releasename.setText("\t\t"+"--"+intent.getStringExtra("releasename"));
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        title = (TextView) findViewById(R.id.space_class_title);
        classactivityImageView = (ImageView) findViewById(R.id.space_class_imageView);
        content = (TextView) findViewById(R.id.space_class_content);
        releasename = (TextView) findViewById(R.id.space_class_releasename);
    }
}
