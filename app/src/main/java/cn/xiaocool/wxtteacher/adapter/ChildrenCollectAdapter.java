package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ChildrenCollect;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.DateUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ChildrenCollectAdapter extends BaseAdapter {
    private List<ChildrenCollect.ChildrenCollectData> childrenCollectDataList;
    private LayoutInflater inflater;
    private DisplayImageOptions imageOptions;
    private Context context;
    public ChildrenCollectAdapter(List<ChildrenCollect.ChildrenCollectData> childrenCollectDataList, Context context) {
        this.context = context;
        this.childrenCollectDataList = childrenCollectDataList;
        this.inflater = LayoutInflater.from(context);
        imageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return childrenCollectDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return childrenCollectDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_children_collect,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (childrenCollectDataList.get(position).getStudentname()==""||childrenCollectDataList.get(position).getStudentname()==null){
            holder.collect_baby_name.setText("宝宝名称");
        }else {
            holder.collect_baby_name.setText(childrenCollectDataList.get(position).getStudentname());
        }

        holder.item_content.setText(childrenCollectDataList.get(position).getClassname());

        String studentavatar = childrenCollectDataList.get(position).getStudentavatar();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        //显示头像
        ImageLoader.getInstance().displayImage("http://wxt.xiaocool.net/uploads/microblog/" + childrenCollectDataList.get(position).getStudentavatar(), holder.collect_avatar, imageOptions);
        ImageLoader.getInstance().displayImage("http://wxt.xiaocool.net/uploads/microblog/" + childrenCollectDataList.get(position).getPhoto(), holder.collect_photo, imageOptions);


        holder.collect_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgs = new ArrayList<>();
                imgs.add( childrenCollectDataList.get(position).getPhoto().toString());
                Intent intent = new Intent(context, CircleImagesActivity.class);
                intent.putStringArrayListExtra("Imgs", imgs);
                context.startActivity(intent);
            }
        });

        if (childrenCollectDataList.get(position).getPhoto().equals("")){
            holder.collect_photo.setVisibility(View.GONE);
        }else {
            holder.collect_photo.setVisibility(View.VISIBLE);
        }

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(Long.parseLong(childrenCollectDataList.get(position).getDelivery_time()) * 1000);
        long todayZero =  DateUtils.lastDayWholePointDate(calendar.getTime()).getTime()/1000;
        if (todayZero>Long.parseLong(childrenCollectDataList.get(position).getDelivery_time())){
            holder.collect_time.setText( new SimpleDateFormat("yyyy-MM-dd").format(date));
        }else {
            holder.collect_time.setText("今天 " + new SimpleDateFormat("HH:mm:ss").format(date));
        }
        if (childrenCollectDataList.get(position).getDelivery_status().equals("0")){
            holder.call_and_alert_layout.setVisibility(View.VISIBLE);
            holder.type_layout.setVisibility(View.GONE);
            holder.rl_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + childrenCollectDataList.get(position).getParentphone()));
                    context.startActivity(intent);
                }
            });
            holder.rl_alert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "提醒" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            holder.type_layout.setVisibility(View.VISIBLE);
            holder.call_and_alert_layout.setVisibility(View.GONE);
            if (childrenCollectDataList.get(position).getDelivery_status().equals("2")){
                holder.daijie_type.setText("不同意");
            }else {
                holder.daijie_type.setText("已同意");
            }
        }
        holder.daijie_content.setText(childrenCollectDataList.get(position).getContent());
        return convertView;
    }
    class ViewHolder{
        RoundImageView collect_avatar;
        TextView collect_baby_name, daijie_type,collect_time,item_content,daijie_content;
        ImageView collect_delete,collect_photo;
        RelativeLayout rl_call,rl_alert,type_layout,call_and_alert_layout;
        public ViewHolder(View convertView) {
            daijie_content = (TextView) convertView.findViewById(R.id.daijie_content);
            item_content = (TextView) convertView.findViewById(R.id.item_content);
            collect_avatar = (RoundImageView) convertView.findViewById(R.id.item_head);
            collect_baby_name = (TextView) convertView.findViewById(R.id.item_title);
//            collect_baby_class = (TextView) convertView.findViewById(R.id.collect_baby_class);
            collect_time = (TextView) convertView.findViewById(R.id.item_time);
            collect_photo = (ImageView) convertView.findViewById(R.id.daijie_img);
            rl_call = (RelativeLayout) convertView.findViewById(R.id.rl_call);
            rl_alert = (RelativeLayout) convertView.findViewById(R.id.rl_alert);
            type_layout = (RelativeLayout) convertView.findViewById(R.id.type_layout);
            call_and_alert_layout = (RelativeLayout) convertView.findViewById(R.id.call_and_alert_layout);
            daijie_type = (TextView) convertView.findViewById(R.id.daijie_type);
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
