package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.presenter.ImageBrowsePresenter;
import cn.xiaocool.wxtteacher.ui.ImageBrowseView;



public class CircleImagesActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener,ImageBrowseView {

    private ViewPager vp;
    private TextView hint;
    private TextView save;
    private cn.xiaocool.wxtteacher.adapter.ViewPageAdapter adapter;
    private ImageBrowsePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image_browse);
//        steepStatusBar();
        vp = (ViewPager) this.findViewById(R.id.viewPager);
        hint = (TextView) this.findViewById(R.id.hint);
        save = (TextView) this.findViewById(R.id.save);
        save.setOnClickListener(this);
        initPresenter();
        presenter.loadImage();
    }

    public void initPresenter(){
        presenter = new cn.xiaocool.wxtteacher.presenter.ImageBrowsePresenter(this);
    }

    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void setImageBrowse(List<String> images,int position) {
        if(adapter == null && images != null && images.size() != 0){
            adapter = new cn.xiaocool.wxtteacher.adapter.ViewPageAdapter(this,images);
            vp.setAdapter(adapter);
            vp.setCurrentItem(position);
            vp.addOnPageChangeListener(this);
            hint.setText(position + 1 + "/" + images.size());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.setPosition(position);
        hint.setText(position + 1 + "/" + presenter.getImages().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        presenter.saveImage();
    }


    public static void startActivity(Context context, ArrayList<String> images, int position){
        Intent intent = new Intent(context, CircleImagesActivity.class);
        intent.putStringArrayListExtra("Imgs",images);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            setStatusHeight();
        }
    }

    /**
     * 设置沉浸式状态栏高度
     */
    private void setStatusHeight() {
        ViewGroup.LayoutParams layoutParams =  findViewById(R.id.status_view).getLayoutParams();
        layoutParams.width = cn.xiaocool.wxtteacher.utils.ScreenUtils.getScreenWidth(this);
        layoutParams.height = cn.xiaocool.wxtteacher.utils.ScreenUtils.getStatusHeight(this);
        findViewById(R.id.status_view).setLayoutParams(layoutParams);
    }
}
