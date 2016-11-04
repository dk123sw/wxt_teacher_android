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
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;

/**
 * Created by THB on 2016/6/6.
 */
public class CommentAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<Comments> commentsList;
    private DisplayImageOptions displayImage;
    private Context context;

    public CommentAdapter(Context mContext,ArrayList<Comments> commentsList){
        context = mContext;
        this.commentsList = commentsList;
        inflater = LayoutInflater.from(context);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }
    @Override
    public int getCount() {
        return commentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_homework_comment,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        holder.item_title.setText(commentsList.get(position).getName());
        holder.item_content.setText(commentsList.get(position).getContent());

        Date date = new Date();
        date.setTime(Long.parseLong(commentsList.get(position).getComment_time()) * 1000);
        holder.item_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
//        holder.item_time.setText(commentsList.get(position).getComment_time());

        //头像
        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + commentsList.get(position).getAvatar(), holder.item_head, displayImage);
        return convertView;
    }

    class ViewHolder{

        TextView item_title,item_content,item_time;
        ImageView item_head;
        public ViewHolder(View convertView) {
            item_title = (TextView) convertView.findViewById(R.id.item_title);
            item_content = (TextView) convertView.findViewById(R.id.item_content);
            item_time = (TextView) convertView.findViewById(R.id.item_time);
            item_head = (ImageView) convertView.findViewById(R.id.item_head);
        }
    }
}
