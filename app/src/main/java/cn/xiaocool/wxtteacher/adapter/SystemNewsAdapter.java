package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import java.util.zip.Inflater;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.SystemNews;
import cn.xiaocool.wxtteacher.main.HomeworkDetailActivity;
import cn.xiaocool.wxtteacher.main.SystemNewsDetailActivity;

/**
 * Created by THB on 2016/6/8.
 */
public class SystemNewsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SystemNews.SystemData> systemDataArrayList;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions displayImage;
    public SystemNewsAdapter(Context context,ArrayList<SystemNews.SystemData> systemDataArrayList){
        this.context=context;
        this.systemDataArrayList=systemDataArrayList;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return systemDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return systemDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_news_system,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(systemDataArrayList.get(position).getTitle());
        holder.tv_content.setText(systemDataArrayList.get(position).getContent());
        holder.tv_time.setText(systemDataArrayList.get(position).getCreate_time());
        imageLoader.displayImage(systemDataArrayList.get(position).getPhoto().toString(), holder.iv_photo, displayImage);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, SystemNewsDetailActivity.class);
                SystemNews.SystemData systemData = systemDataArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("systemData",systemData);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView tv_title,tv_content,tv_time;
        ImageView iv_photo;
        public ViewHolder(View convertView){
            tv_title = (TextView) convertView.findViewById(R.id.item_title);
            tv_content = (TextView) convertView.findViewById(R.id.item_content);
            tv_time = (TextView) convertView.findViewById(R.id.item_time);
            iv_photo = (ImageView) convertView.findViewById(R.id.item_head);
        }
    }
}
