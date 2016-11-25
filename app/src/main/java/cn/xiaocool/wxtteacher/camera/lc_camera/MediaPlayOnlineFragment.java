/*  
 * 项目名: YYS 
 * 文件名: MediaPlayOnlineFragment.java  
 * 版权声明:
 *      本系统的所有内容，包括源码、页面设计，文字、图像以及其他任何信息，
 *      如未经特殊说明，其版权均属大华技术股份有限公司所有。
 *      Copyright (c) 2015 大华技术股份有限公司
 *      版权所有
 */
package cn.xiaocool.wxtteacher.camera.lc_camera;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.lechange.opensdk.listener.LCOpenSDK_EventListener;

import cn.xiaocool.wxtteacher.R;


/**
 * 描述：实时视频监控 作者： lc
 */
public class MediaPlayOnlineFragment extends MediaPlayFragment implements OnClickListener {
	
	private static final String TAG = "MediaPlayOnlineFragment";
	
	protected static final int MediaMain = 0;                        //主码流
	protected static final int MediaAssist = 1;                      //辅码流	
	protected static final int RECORDER_TYPE_DAV = 3;  			     //录制格式DAV
	protected static final int RECORDER_TYPE_MP4 = 4;				 //录制格式MP4
	
	public enum Cloud {
		up, down, left, right, leftUp, rightUp, leftDown, RightDown, zoomin, zoomout, stop
	}
	
	//状态值
	private int bateMode = MediaAssist;
	private enum AudioTalkStatus{talk_close, talk_opening, talk_open};
	private AudioTalkStatus mOpenTalk = AudioTalkStatus.talk_close; //语音对讲状态
	private boolean isRecord = false;	    //正在录制
	private boolean isOpenSound = false;    //声音打开
	private boolean isPlaying;				//正在播放
	private boolean IsPTZOpen = false;		//云台打开
	
	private ChannelInfo channelInfo;

	private LinearLayout mLiveMenu;
	private ImageView mLiveMode;
	private ImageView mLiveSound;
	private ImageView mLiveScale;
	
	private LinearLayout mLiveUseLayout;
	private ImageView mLiveScreenshot;

	/**
	 *      描述：
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			String channelId = b.getString("RESID");
			channelInfo = Business.getInstance().getChannel(channelId);
		}
		if (channelInfo == null){
			toast("设备不存在");
			getActivity().setResult(-1);
			getActivity().finish();
		}
	}

	/**
	 *      android.view.ViewGroup, android.os.Bundle) 描述：
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

			View mView = inflater.inflate(R.layout.fragment_media_live, container, false);
			//必须赋值，父类需要使用到
			mSurfaceParentView = (ViewGroup) mView.findViewById(R.id.live_window);
			//初始化窗口大小
			LayoutParams mLayoutParams = (LayoutParams) mSurfaceParentView.getLayoutParams();
			DisplayMetrics metric = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
			mLayoutParams.width = metric.widthPixels; // 屏幕宽度（像素）
			mLayoutParams.height = metric.widthPixels * 9 / 16;
			mLayoutParams.setMargins(0, 10, 0, 0);
			mSurfaceParentView.setLayoutParams(mLayoutParams);

			mPlayWin.initPlayWindow(this.getActivity(), (ViewGroup) mView.findViewById(R.id.live_window_content),0);

			mProgressDialog = (ProgressDialog) mView.findViewById(R.id.live_play_load);
			mReplayTip = (TextView) mView.findViewById(R.id.live_play_pressed);

			mLiveMenu = (LinearLayout) mView.findViewById(R.id.live_menu);
			mLiveMode = (ImageView) mView.findViewById(R.id.live_mode);
			mLiveSound = (ImageView) mView.findViewById(R.id.live_sound);
			mLiveUseLayout = (LinearLayout) mView.findViewById(R.id.live_use_layout);
			mLiveScale = (ImageView) mView.findViewById(R.id.live_scale);
			mLiveScreenshot = (ImageView) mView.findViewById(R.id.live_screenshot);


			mReplayTip.setOnClickListener(this);
			mLiveMode.setOnClickListener(this);
			mLiveSound.setOnClickListener(this);
			mLiveUseLayout.setOnClickListener(this);
			mLiveScale.setOnClickListener(this);
			mLiveScreenshot.setOnClickListener(this);


			return mView;

	}


	/**
	 *      描述：MediaPlayActivity创建完毕
	 * @param savedInstanceState
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listener = new MyBaseWindowListener();
		mPlayWin.setWindowListener(listener);
		mPlayWin.openTouchListener();

		//开启横竖屏切换
		startListener();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		play(0);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 停止实时视频,无效资源号
		stop(-1);

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mPlayWin.uninitPlayWindow();// 销毁底层资源
	}

	/**
	 *      描述：实现个性化界面
	 * @param mConfiguration
	 */
	@Override
	protected void resetViews(Configuration mConfiguration) {
		super.resetViews(mConfiguration);
		if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) { // 横屏
			mLiveUseLayout.setVisibility(View.GONE);
			mLiveScale.setTag("LANDSCAPE");
			mLiveScale.setImageResource(R.drawable.live_btn_smallscreen);
		} else if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mLiveUseLayout.setVisibility(View.VISIBLE);
			mLiveScale.setTag("PORTRAIT");
			mLiveScale.setImageResource(R.drawable.live_btn_fullscreen);
		}		
	}

	class MyBaseWindowListener extends LCOpenSDK_EventListener {
		@Override
		public void onPlayerResult(int index, String code, int type) {
			if (type == Business.RESULT_SOURCE_OPENAPI) {
				if (mHander != null) {
					mHander.post(new Runnable() {
						public void run() {
							if (isAdded()) {
								stop(R.string.video_monitor_play_error);
							}
						}
					});
				}
			} else {
				if (code.equals(Business.PlayerResultCode.STATE_PACKET_FRAME_ERROR) ||
				code.equals(Business.PlayerResultCode.STATE_RTSP_TEARDOWN_ERROR) ||
				code.equals(Business.PlayerResultCode.STATE_RTSP_AUTHORIZATION_FAIL)) {
					if (mHander != null) {
						mHander.post(new Runnable() {
							public void run() {
								if (isAdded()) {
									stop(R.string.video_monitor_play_error);
								}
							}
						});
					}
				}
			}
		}
		
		@Override
		public void onPlayBegan(int index) {
			// TODO Auto-generated method stub
			// 显示码率
			//if (mHander != null) {
			//	mHander.post(MediaPlayOnlineFragment.this);
			//}
			isPlaying = true;
			// 建立码流,自动开启音频
			if (mHander != null) {
				mHander.post(new Runnable() {
					@Override
					public void run() {
						if (isAdded()) {
							// showLoading(R.string.video_monitor_data_cache);		
							onClick(mLiveSound);									
						}
					}
				});
			}
			// 关闭播放加载控件
			mProgressDialog.setStop();
		}
	    
//		public void onReceiveData(int len) {
//			// 流量统计
//			mTotalFlow += len;
//		}
		@Override
		public void onWindowLongPressBegin(int index, Direction dir, float dx, float dy) {
		}

		@Override
		public void onWindowLongPressEnd(int index) {	
		}
		// 电子缩放
		@Override
		public void onZooming(int index,float dScale) {
			if (IsPTZOpen == false) {
				mPlayWin.doScale(dScale);
			}

		}

		// 云台缩放
		@Override
		public void onZoomEnd(int index,ZoomType zoom) {
			Log.d(TAG, "onZoomEnd" + zoom);
			if (IsPTZOpen == false ) {
				return;
			}

		}

		// 滑动开始
		@Override
		public boolean onSlipBegin(int index, Direction dir, float dx, float dy) {
			if (IsPTZOpen == false && mPlayWin.getScale() >1) {
				Log.d(TAG, "onflingBegin ");
			}
			return true;
		}

		

		//滑动中
		@Override
		public void onSlipping(int index, Direction dir, float prex, float prey, float dx, float dy) {
			if (IsPTZOpen == false && mPlayWin.getScale() > 1) {
				Log.d(TAG, "onflingBegin onFlinging" );
				mPlayWin.doTranslate(dx, dy);
			}
		}

		// 滑动结束
		@Override
		public void onSlipEnd(int index, Direction dir, float dx, float dy) {
			if(IsPTZOpen == false && mPlayWin.getScale() > 1 )
			{
				Log.d(TAG, "onflingBegin onFlingEnd");
				return;
			}

		//	sendCloudOrder(Cloud.stop, false);
		}

		private Cloud direction2Cloud(Direction dir) {
			Cloud cloud = null;
			switch (dir) {
			case Up:
				cloud = Cloud.up;
				break;
			case Down:
				cloud = Cloud.down;
				break;
			case Left:
				cloud = Cloud.left;
				break;
			case Right:
				cloud = Cloud.right;
				break;
			case Left_up:
				cloud = Cloud.leftUp;
				break;
			case Left_down:
				cloud = Cloud.leftDown;
				break;
			case Right_up:
				cloud = Cloud.rightUp;
				break;
			case Right_down:
				cloud = Cloud.RightDown;
				break;
			default:
				break;
			}
			return cloud;
		}
		
		public void onWindowDBClick(int index,float dx, float dy) {
			// TODO Auto-generated method stub
			switch(mLiveMenu.getVisibility()){
			case View.GONE:
				mLiveMenu.setVisibility(View.VISIBLE);
				break;
			case View.VISIBLE:
				mLiveMenu.setVisibility(View.GONE);
				break;
			default:
				break;
			}	
		}

	}


	/**
	 *  描述：开始播放
	 */
	public void play(int strRes) {
		if (isPlaying) {
			stop(-1);
		}
		if (strRes > 0) {
			showLoading(strRes);
		} else {
			showLoading(R.string.common_loading);
		}
		mPlayWin.playRtspReal(Business.getInstance().getToken(),
				channelInfo.getDeviceCode(), channelInfo.getIndex(), bateMode);
	}

	/**
	 *  描述：停止播放
	 */
	public void stop(final int res) {
		// 关闭播放加载控件
		mProgressDialog.setStop();
		isPlaying = false;

		 
		if (isOpenSound) {
			closeAudio();// 关闭音频
			isOpenSound = false;
			mLiveSound.setImageResource(R.drawable.live_btn_sound_off);
		}
		mPlayWin.stopRtspReal();// 关闭视频
		if (mHander != null) {
			mHander.post(new Runnable() {
				public void run() {
					if (isAdded()) {
						if (res > 0) showErrorTip(res);	
					}
				}
			});
		}
	}

	/**
	 *  描述：抓拍图像
	 */
	public String capture() {
		String captureFilePath = null;
		// 判断SD卡是否已存在
		// SD卡容量检查
		// FIXME 检查设备是否在线
		// 抓图
		String channelName = null;
		if (channelInfo != null) {
			channelName = channelInfo.getName();
		} else {
			channelName = "";
		}

		// 去除通道中在目录中的非法字符
		channelName = channelName.replace("-", "");

		captureFilePath = MediaPlayHelper.getCaptureAndVideoPath(MediaPlayHelper.DHFilesType.DHImage, channelName);
		int ret = mPlayWin.snapShot(captureFilePath);
		if (ret == retOK) {
			// 扫描到相册中
			MediaScannerConnection.scanFile(getActivity(),
					new String[] { captureFilePath }, null, null);
			Toast.makeText(getActivity(),
					R.string.video_monitor_image_capture_success,
					Toast.LENGTH_SHORT).show();
		} else {
			captureFilePath = null;
			Toast.makeText(getActivity(),
					R.string.video_monitor_image_capture_failed,
					Toast.LENGTH_SHORT).show();
		}
		return captureFilePath;
	}


	
	/**
	 * 打开声音
	 */
	public boolean openAudio() {
		return mPlayWin.playAudio() == retOK;
	}

	/**
	 * 关闭声音
	 */
	public boolean closeAudio() {
		return mPlayWin.stopAudio() == retOK;
	}




	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {

		case R.id.live_scale:
			if("LANDSCAPE".equals(mLiveScale.getTag())){
				mOrientation = ORIENTATION.isPortRait;
				getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}else{
				mOrientation = ORIENTATION.isLandScape;
				getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}		
			break;
		case R.id.live_mode:
			if(isPlaying) //播放是个异步的,多次点击会使停止播放顺序乱掉
				// 高清切换到流畅
				if (bateMode == MediaMain) {
					bateMode = MediaAssist;
					mLiveMode.setImageResource(R.drawable.live_btn_fluent);
					play(R.string.video_monitor_change_stream_normal);
				}// 流畅切换到高清
				else if (bateMode == MediaAssist) {
					bateMode = MediaMain;
					mLiveMode.setImageResource(R.drawable.live_btn_hd);
					play(R.string.video_monitor_change_stream_hd);
				}
			break;

		case R.id.live_sound:
			if (mOpenTalk != AudioTalkStatus.talk_close || !isPlaying) {
				toast(R.string.video_monitor_load_talk_sound_error);
			} else {
				if (isOpenSound) {
					boolean result = closeAudio();
					if (result) {
						mLiveSound.setImageResource(R.drawable.live_btn_sound_off);
						toast(R.string.video_monitor_sound_close);
						isOpenSound = false;
					}
				} else {
					boolean result = openAudio();
					if (result) {
						mLiveSound.setImageResource(R.drawable.live_btn_sound_on);
						toast(R.string.video_monitor_sound_open);
						isOpenSound = true;
					}
				}
			}
			break;
		case R.id.live_screenshot:
			mLiveScreenshot.setImageResource(R.drawable.live_btn_screenshot_click);
			capture();
			mLiveScreenshot.setImageResource(R.drawable.live_btn_screenshot_nor);
			break;

		case R.id.live_play_pressed:
			play(-1);
			break;
		default:
			break;
		}
	}
}
