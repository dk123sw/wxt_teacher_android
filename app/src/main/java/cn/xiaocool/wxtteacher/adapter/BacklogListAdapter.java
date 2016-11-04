package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Backlog;
import cn.xiaocool.wxtteacher.main.BacklogDetailActivity;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;

import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;

/**
 * Created by Administrator on 2016/5/14.
 */
public class BacklogListAdapter extends BaseAdapter{

    private List<Backlog.BacklogData> backlogDataList;
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public BacklogListAdapter(List<Backlog.BacklogData> backlogDataList, Context mContext) {
        this.context = mContext;
        this.backlogDataList = backlogDataList;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return backlogDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return backlogDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_backlog,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.parent_warn_title.setText(backlogDataList.get(position).getTitle());
        holder.parent_warn_content.setText(backlogDataList.get(position).getContent());
        holder.parent_warn_from.setText("发起人："+backlogDataList.get(position).getUsername());
        Date date = new Date();
        date.setTime(Long.parseLong(backlogDataList.get(position).getCreate_time()) * 1000);
        holder.parent_warn_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        // 显示图片
        if (backlogDataList.get(position).getPic().size()>1){
            holder.homework_img.setVisibility(View.GONE);
            holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
            final ArrayList<String> imgs = new ArrayList<>();
            for (int i=0;i<backlogDataList.get(position).getPic().size();i++){
                imgs.add(backlogDataList.get(position).getPic().get(i).getPictureurl());
            }
            ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter(imgs,context);
            holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
            holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", imgs);
                    intent.putExtra("type","newsgroup");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (backlogDataList.get(position).getPic().size()==1&&!backlogDataList.get(position).getPic().get(0).getPictureurl().equals("")&&!backlogDataList.get(position).getPic().get(0).getPictureurl().equals("null")){
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + backlogDataList.get(position).getPic().get(0).getPictureurl(), holder.homework_img, displayImage);
            holder.homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(backlogDataList.get(position).getPic().get(0).getPictureurl());
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", list);
                    intent.putExtra("type", "4");
                    context.startActivity(intent);
                }
            });
        }else {
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.GONE);
        }



        holder.parent_warn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDetail(backlogDataList.get(position));
            }
        });
        holder.parent_warn_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDetail(backlogDataList.get(position));
            }
        });
        holder.parent_warn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDetail(backlogDataList.get(position));
            }
        });
        holder.parent_warn_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDetail(backlogDataList.get(position));
            }
        });

        return convertView;
    }

    private void intentToDetail(Backlog.BacklogData data) {
        Intent intent = new Intent();
        intent.setClass(context, BacklogDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("backlogdata", data);
        intent.putExtras(bundle);
        context.startActivity(intent);


    }





    class ViewHolder{
        TextView parent_warn_title,parent_warn_time,parent_warn_content,parent_warn_from;
        ImageView homework_img;
        GridView parent_warn_img_gridview;
        public ViewHolder(View convertView) {
            parent_warn_title = (TextView) convertView.findViewById(R.id.parent_warn_title);
            parent_warn_time = (TextView) convertView.findViewById(R.id.parent_warn_time);
            parent_warn_content = (TextView) convertView.findViewById(R.id.parent_warn_content);
            parent_warn_from = (TextView) convertView.findViewById(R.id.parent_warn_from);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
            parent_warn_img_gridview = (GridView) convertView.findViewById(R.id.parent_warn_img_gridview);


        }
    }
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24*60*60*1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime()%l) - 8* 60 * 60 *1000);
    }
}
