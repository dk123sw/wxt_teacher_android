package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.xiaocool.wxtteacher.R;


/**
 * Created by wzh on 2016/3/28.
 */
public class SpaceClickRecipesItemActivity extends Activity {
    private ImageView img;
    private TextView tvTitle,tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_click_recipes_item);
        //初始化视图
        img = (ImageView) findViewById(R.id.recipes_content_img);
        tvTitle = (TextView) findViewById(R.id.recipes_content_title);
        tvInfo = (TextView) findViewById(R.id.recipes_content_info);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String info = intent.getStringExtra("info");
        String pic = intent.getStringExtra("pic");
        tvTitle.setText(title);
        tvInfo.setText(info);
        ImageLoader.getInstance().displayImage("http://www.xiaocool.cn:8016/uploads/recipe/" + pic, img, new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build());
    }
}
