package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ImgGridAdapter extends BaseAdapter {

    private int i;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mWorkImgs;
    private Context mContext;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ImgGridAdapter(ArrayList<String> workImgs, Context context) {
        this.mContext = context;
        this.mWorkImgs = workImgs;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.i = mWorkImgs.size();
        options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square)
                .showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {

        return i > 9 ? 9 : i;
    }

    @Override
    public Object getItem(int position) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.user_img_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_user_img);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        LogUtils.d("weixiaotong-GridAdaper", mWorkImgs.get(position));
        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+ mWorkImgs.get(position), viewHolder.imageView,options);
        return convertView;
    }

    private static class MyGridViewHolder {
        ImageView imageView;
    }
}
