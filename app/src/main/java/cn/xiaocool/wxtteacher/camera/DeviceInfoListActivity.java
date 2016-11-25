package cn.xiaocool.wxtteacher.camera;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.camera.ez_camera.EZRealPlayActivity;
import cn.xiaocool.wxtteacher.camera.lc_camera.Business;
import cn.xiaocool.wxtteacher.camera.lc_camera.ChannelInfo;
import cn.xiaocool.wxtteacher.camera.lc_camera.MediaPlayActivity;
import cn.xiaocool.wxtteacher.view.WxtApplication;

/**
 * Created by hzh on 16/11/15.
 */

public class DeviceInfoListActivity extends BaseActivity {
    private ListView deviceLv;
    private List<EZDeviceInfo> deviceInfos;//萤石设备列表
    private List<ChannelInfo> lcChannelInfos;//乐橙视频列表
    private MyCameraAdapter myCameraAdapter;
    private List<MyCameraInfo> myCameraInfos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info_list);
        initAccessToken();
        deviceInfos = new ArrayList<>();
        myCameraInfos = new ArrayList<>();
        myCameraAdapter = new MyCameraAdapter(this, myCameraInfos);

        deviceLv = (ListView) findViewById(R.id.lv_device);
        deviceLv.setAdapter(myCameraAdapter);

        deviceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyCameraInfo my = myCameraInfos.get(position);
                if (my.getCameraType() == MyCameraInfo.EZ_CAMERA) {
                    Intent intent = new Intent(DeviceInfoListActivity.this, EZRealPlayActivity.class);
                    intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, my.getEzCameraInfo());
                    intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, my.getMyEzDeviceInfo());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DeviceInfoListActivity.this, MediaPlayActivity.class);
                    intent.putExtra("UUID", my.getChannelInfo().getUuid());
                    intent.putExtra("TYPE", MediaPlayActivity.IS_VIDEO_ONLINE);
                    intent.putExtra("MEDIA_TITLE", R.string.live_play_name);
                    startActivityForResult(intent, 0);
                }

            }
        });

        new GetDeviceInfoTask().execute();
        getLeChangeList();
    }


    /**
     * 此处的两个token写死
     * 以后从服务器获取
     */
    private void initAccessToken() {
        WxtApplication.getInstance().EZ_ACCESS_TOKEN = "at.7p7cn8v9amk8de561qjhe3ppdjgcq1rl-4n4j52wjzz-07ozia7-qgzdvhndv";
        WxtApplication.getInstance().LECHANGE_ACCESS_TOKEN = "At_c8f4390050c84528b2b2df9ddefa6bea";

        EzvizAPI.getInstance().setAccessToken(WxtApplication.getInstance().EZ_ACCESS_TOKEN);
    }


    /**
     * 从萤石服务器获取萤石设备信息
     * 以后将从我们自己的服务器获取
     */
    public class GetDeviceInfoTask extends AsyncTask<Void, Void, List<EZDeviceInfo>> {

        @Override
        protected List<EZDeviceInfo> doInBackground(Void... params) {
            try {
                //可分页获取
                return EZOpenSDK.getInstance().getDeviceList(0, 20);
            } catch (BaseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<EZDeviceInfo> mDeviceInfos) {
            super.onPostExecute(mDeviceInfos);
            deviceInfos.clear();
            deviceInfos.addAll(mDeviceInfos);
            myCameraInfos.addAll(changeToMyCamerInfo(mDeviceInfos.get(0)));
            myCameraAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 从乐橙服务器获取乐橙视频设备信息
     * 以后将从我门的服务器获取
     * Business以后可删除
     */
    private void getLeChangeList() {
        // 初始化数据
        Business.getInstance().getChannelList(new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                Business.RetObject retObject = (Business.RetObject) msg.obj;
                if (msg.what == 0) {
                    lcChannelInfos = (List<ChannelInfo>) retObject.resp;
                    if (lcChannelInfos != null && lcChannelInfos.size() > 0) {
//                        mChnlAdapter.notifyDataSetChanged();
                        myCameraInfos.addAll(changeToMyCamerInfo(lcChannelInfos));
                        myCameraAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DeviceInfoListActivity.this, "没有设备", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeviceInfoListActivity.this, retObject.mMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 将从萤石服务器获取到的deviceinfo转换成自定义的MyCameraInfo
     */
    private List<MyCameraInfo> changeToMyCamerInfo(EZDeviceInfo deviceInfo) {
        List<MyCameraInfo> infos = new ArrayList<>();
        List<EZCameraInfo> ezCameraInfos = deviceInfo.getCameraInfoList();
        for (EZCameraInfo c : ezCameraInfos) {
            MyCameraInfo myCameraInfo = new MyCameraInfo();
            myCameraInfo.setCameraType(MyCameraInfo.EZ_CAMERA);
            myCameraInfo.setEzCameraInfo(c);

            MyCameraInfo.MyEzDeviceInfo myEzDeviceInfo = new MyCameraInfo.MyEzDeviceInfo();
            myEzDeviceInfo.setSupportPTZ(deviceInfo.isSupportPTZ());
            myEzDeviceInfo.setStatus(deviceInfo.getStatus());
            myEzDeviceInfo.setSupportZoom(deviceInfo.isSupportZoom());
            myCameraInfo.setMyEzDeviceInfo(myEzDeviceInfo);

            infos.add(myCameraInfo);

        }
        return infos;
    }


    private List<MyCameraInfo> changeToMyCamerInfo(List<ChannelInfo> channelInfos) {
        List<MyCameraInfo> infos = new ArrayList<>();
        for (ChannelInfo c : channelInfos) {
            MyCameraInfo my = new MyCameraInfo();
            my.setCameraType(MyCameraInfo.LC_CAMERA);
            my.setChannelInfo(c);
            infos.add(my);
        }
        return infos;
    }

}
