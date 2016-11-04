package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ChatAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<Comments> commentsList;
    private DisplayImageOptions displayImage;
    private Context context;
    UserInfo user;
    public ChatAdapter(ArrayList<Comments> commentsArrayList, Context mContext) {
        context = mContext;
        user = new UserInfo(context);
        user.readData(context);
        Log.e("ChatAdapter=====",user.getUserId());
        this.commentsList = commentsArrayList;
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null){
            if (commentsList.get(position).getUserid().equals(user.getUserId())){
                convertView = inflater.inflate(R.layout.item_chat_right,null);
            }else {
                convertView = inflater.inflate(R.layout.item_chat_left,null);
            }

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        holder.item_content.setText(commentsList.get(position).getContent());

//        Date date = new Date();
//        date.setTime(Long.parseLong(commentsList.get(position).getComment_time()) * 1000);
//        holder.item_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
//        holder.item_time.setText(commentsList.get(position).getComment_time());
        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + commentsList.get(position).getAvatar(), holder.item_head, displayImage);
        //判断是否有照片
//        holder.item_information.setVisibility(View.GONE);
//        if (commentsList.get(position).getPhoto().length()>1){
//            holder.item_information.setVisibility(View.VISIBLE);
//            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + commentsList.get(position).getPhoto(), holder.item_information, displayImage);
//            Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + commentsList.get(position).getPhoto());
//        }

        return convertView;
    }

    class ViewHolder{

        TextView item_content;
        ImageView item_head;
        public ViewHolder(View convertView) {
            item_content = (TextView) convertView.findViewById(R.id.item_content);
            item_head = (ImageView) convertView.findViewById(R.id.item_head);
        }



    }
}
