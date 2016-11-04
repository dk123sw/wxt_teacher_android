package cn.xiaocool.wxtteacher.adapter;

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

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassAlbumInfo;


/**
 * Created by Administrator on 2016/5/13.
 */
public class BabyAlbumAdapter extends BaseAdapter {
    private ImageLoader imageLoader= ImageLoader.getInstance();
    private Context mContext;
    private ArrayList<ClassAlbumInfo> list;
    private LayoutInflater inflater;
    private DisplayImageOptions displayImage;
    public BabyAlbumAdapter(ArrayList<ClassAlbumInfo> list, Context context) {
        this.mContext = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_class_album,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }

    public class ViewHolder{
        ImageView classevent_lv_imageview,classevent_lv_access;
        TextView classevent_lv_title,classevent_lv_content;
        public ViewHolder(View convertView){
//            classevent_lv_imageview = (ImageView) convertView.findViewById(R.id.classevent_lv_imageview);
//            classevent_lv_title = (TextView) convertView.findViewById(R.id.classevent_lv_title);
//            classevent_lv_content = (TextView) convertView.findViewById(R.id.classevent_lv_content);
//            classevent_lv_access = (ImageView) convertView.findViewById(R.id.classevent_lv_access);
        }
    }
}
