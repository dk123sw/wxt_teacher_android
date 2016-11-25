package cn.xiaocool.wxtteacher.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.videogo.openapi.bean.EZCameraInfo;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.camera.lc_camera.ChannelInfo;


/**
 * Created by hzh on 16/11/21.
 */

public class MyCameraAdapter extends BaseAdapter{
    private Context context;
    private List<MyCameraInfo> myCameraInfos;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader;


    public MyCameraAdapter(Context context,List<MyCameraInfo> myCameraInfos){
        this.context = context;
        this.myCameraInfos = myCameraInfos;

        imageLoader = ImageLoader.getInstance();
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();


    }
    @Override
    public int getCount() {
        return myCameraInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return myCameraInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyCameraInfo myCameraInfo = myCameraInfos.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_camera_list,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cameraCoverIv = (ImageView) convertView.findViewById(R.id.iv_camera_img);
            viewHolder.cameraNameTv = (TextView) convertView.findViewById(R.id.tv_camera_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(myCameraInfo.getCameraType() == MyCameraInfo.EZ_CAMERA){
            EZCameraInfo ezCameraInfo = myCameraInfo.getEzCameraInfo();
            viewHolder.cameraNameTv.setText(ezCameraInfo.getCameraName());
            imageLoader.displayImage(myCameraInfo.getEzCameraInfo().getCameraCover(),viewHolder.cameraCoverIv,displayImage);
        }else{
            ChannelInfo channelInfo = myCameraInfo.getChannelInfo();
            viewHolder.cameraNameTv.setText(channelInfo.getName());
            imageLoader.displayImage(channelInfo.getBackgroudImgURL(),viewHolder.cameraCoverIv,displayImage);
        }

        return convertView;
    }


    class ViewHolder {
        private TextView cameraNameTv;
        private ImageView cameraCoverIv;//萤石视频封面无法从网络获取 建议退出观看时截图保存

    }

}
