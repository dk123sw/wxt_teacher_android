package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.SystemNews;

public class SystemNewsDetailActivity extends Activity {
    private Context context;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    private DisplayImageOptions displayImage;
    private SystemNews.SystemData data;
    private TextView tv_title,tv_time,tv_username,tv_content;
    private ImageView iv_photo;
    private RelativeLayout rl_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_system_news_detail);
        context = this;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        initView();
        initData();
    }

    private void initData() {
        data = (SystemNews.SystemData) getIntent().getSerializableExtra("systemData");
        tv_title.setText(data.getTitle());
        tv_content.setText(data.getContent());
        tv_time.setText(data.getCreate_time());
        tv_username.setText(data.getUsername());
        imageLoader.displayImage(data.getPhoto(),iv_photo,displayImage);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.news_title);
        tv_content = (TextView) findViewById(R.id.news_content);
        tv_time = (TextView) findViewById(R.id.news_time);
        tv_username = (TextView) findViewById(R.id.news_username);
        iv_photo = (ImageView) findViewById(R.id.news_image);
        rl_back = (RelativeLayout) findViewById(R.id.up_jiantou);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }
}
