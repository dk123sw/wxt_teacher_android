/*  
 * 项目名: YYS 
 * 文件名: MediaPlayActivity.java  
 * 版权声明:
 *      本系统的所有内容，包括源码、页面设计，文字、图像以及其他任何信息，
 *      如未经特殊说明，其版权均属大华技术股份有限公司所有。
 *      Copyright (c) 2015 大华技术股份有限公司
 *      版权所有
 */
package cn.xiaocool.wxtteacher.camera.lc_camera;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import cn.xiaocool.wxtteacher.R;


/**
 * 描述：视频播放Activity 作者： lc
 */
public class MediaPlayActivity extends FragmentActivity implements MediaPlayFragment.BackHandlerInterface{
	
	private final static String tag = "MediaPlayActivity";
	private CommonTitle mCommonTitle;
	private MediaPlayFragment mMediaPlayFragment;
	
	public static final int IS_VIDEO_ONLINE = 1;
	public static final int IS_VIDEO_REMOTE_RECORD = 2;	
	public static final int IS_VIDEO_REMOTE_CLOUD_RECORD = 3;
	//public static final int IS_VIDEO_ALARM_RECORD = 4;
	//public static final int IS_VIDEO_LOCAL_FILE = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_video);
		//绘制标题
		mCommonTitle = (CommonTitle) findViewById(R.id.title);
		mCommonTitle.initView(R.drawable.title_btn_back, 0, getIntent().getIntExtra("MEDIA_TITLE", 0));
		
		mCommonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
			@Override
			public void onCommonTitleClick(int id) {
				// TODO Auto-generated method stub
				switch (id) {
				case CommonTitle.ID_LEFT:
					finish();
					break;
				default:
					break;
				}
			}
		});
		
		//嵌入使用的帧
		MediaPlayFragment mediaPlayFragment; //引用的布局帧
		Bundle b = new Bundle();
		String resId; //资源id号
		switch(getIntent().getIntExtra("TYPE", 0)){
		case IS_VIDEO_ONLINE:
			mediaPlayFragment = new MediaPlayOnlineFragment();
			resId = getIntent().getStringExtra("UUID");
			b.putString("RESID", resId);
			mediaPlayFragment.setArguments(b);
			changeFragment(mediaPlayFragment, false);
			break;
		default:
			break;
		}

	}


	/**
	 * @see com.mm.android.yys.common.MainControllerListener#changeFragment(com.mm.android.commonlib.base.BaseFragment,
	 *      boolean) 描述：切换fragment
	 */
	public void changeFragment(Fragment targetFragment, boolean isAddToStack) {
		if (isAddToStack) {
			getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, targetFragment).addToBackStack(null).commitAllowingStateLoss();
		} else {
			getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, targetFragment).commitAllowingStateLoss();
		}

	}
	
	@Override
	public void onBackPressed() {  	
        if(mMediaPlayFragment == null || !mMediaPlayFragment.onBackPressed()) {  
        	Log.d(tag, "onBackPressed");
            super.onBackPressed();  
        }  
    }

	@Override
	public void setSelectedFragment(MediaPlayFragment backHandledFragment) {
		// TODO Auto-generated method stub
		this.mMediaPlayFragment = backHandledFragment;
	}
	
	//横竖屏切换需要,隐藏标题栏
	public void toggleTitle(boolean isShow) {
		mCommonTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
	}


}