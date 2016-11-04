package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

/**
 * Created by Administrator on 2016/5/18.
 */
public class AnnouncementCommentAdapter extends BaseAdapter{
    private List<Comments> commentBeanList;
    private LayoutInflater inflater;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public AnnouncementCommentAdapter(List<Comments> commentBeanList, Context context) {
        this.commentBeanList = commentBeanList;
        this.inflater = LayoutInflater.from(context);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return commentBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_announcement_comment,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + commentBeanList.get(position).getAvatar(), holder.iv_item_head, displayImage);
        holder.tv_item_name.setText(commentBeanList.get(position).getName());
        holder.tv_item_content.setText(commentBeanList.get(position).getContent());
        long todayZero = getTodayZero();
        Date date = new Date();
        date.setTime(Long.parseLong(commentBeanList.get(position).getComment_time()) * 1000);
        Log.i("---------time----------",todayZero+"------"+Long.parseLong(commentBeanList.get(position).getComment_time()) * 1000);
        if (todayZero > Long.parseLong(commentBeanList.get(position).getComment_time())* 1000) {
            holder.tv_item_time.setText( new SimpleDateFormat("yyyy-MM-dd").format(date));
        }else {
            holder.tv_item_time.setText(new SimpleDateFormat("HH:mm:ss").format(date));
        }


//        holder.tv_item_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
        return convertView;
    }
    class ViewHolder{
        private RoundImageView iv_item_head;
        private TextView tv_item_name;
        private TextView tv_item_time;
        private TextView tv_item_content;
        public ViewHolder(View convertView) {
        iv_item_head = (RoundImageView) convertView.findViewById(R.id.iv_item_head);
        tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
        tv_item_time = (TextView) convertView.findViewById(R.id.tv_item_time);
        tv_item_content = (TextView) convertView.findViewById(R.id.tv_item_content);
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
