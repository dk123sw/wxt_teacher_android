package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.NewsGroupRecive;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.NewsGroupReciveDetailActivity;
import cn.xiaocool.wxtteacher.main.ReadAndNoreadActivity;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;

/**
 * Created by Administrator on 2016/4/22.
 */
public class NewsGroupReciveAdapter extends BaseAdapter {
    private static final String TAG = "homework_praise";
    private List<NewsGroupRecive.DataBean> homeworkDataList;
    private LayoutInflater inflater;
    private Context context;
    private Handler handler;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private static final int GET_VIEWPAPER_LIST_KEY = 1;
    private static final int GET_LIST_KEY = 102;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private DisplayImageOptions displayImage;
    private LinearLayout linearLayout;
    private UserInfo user;
    //    private int a;
    public NewsGroupReciveAdapter(List<NewsGroupRecive.DataBean> homeworkDataList, Context mContext) {
        this.linearLayout = linearLayout;
        this.context = mContext;
        this.handler= handler;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.homeworkDataList = homeworkDataList;
        this.inflater = LayoutInflater.from(mContext);
        user = new UserInfo(context);
        user.readData(context);
    }
    @Override
    public int getCount() {
        return homeworkDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeworkDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_news_group,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        final ArrayList<NewsGroupRecive.DataBean.ReceiverBean> notReads = new ArrayList<>();
        final ArrayList<NewsGroupRecive.DataBean.ReceiverBean> alreadyReads = new ArrayList<>();
        if (homeworkDataList.get(position).getReceiver().size()>0){
            for (int i=0;i<homeworkDataList.get(position).getReceiver().size();i++){
                if (homeworkDataList.get(position).getReceiver().get(i).getRead_time()==null||homeworkDataList.get(position).getReceiver().get(i).getRead_time().equals("null")){
                    notReads.add(homeworkDataList.get(position).getReceiver().get(i));
                }else {
                    alreadyReads.add(homeworkDataList.get(position).getReceiver().get(i));
                }
            }
        }


        holder.homework_content.setText(homeworkDataList.get(position).getSend_message().getMessage_content());
        holder.teacher_name.setText(homeworkDataList.get(position).getSend_message().getSend_user_name());

        //判断已读和未读
        holder.alread_text.setText("总发" + homeworkDataList.get(position).getReceiver().size()+" 已读"+alreadyReads.size()+" 未读"+notReads.size());
        holder.alread_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(context, ReadAndNoreadActivity.class);
                intent.putExtra("type","recive");
                Bundle bundle=new Bundle();
                bundle.putSerializable("notReads",(Serializable)notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable)alreadyReads);
                intent.putExtras(bundle);//发送数据
                context.startActivity(intent);//启动intent

            }
        });


        Date date = new Date();
        date.setTime(Long.parseLong(homeworkDataList.get(position).getSend_message().getMessage_time()) * 1000);
        holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));


        if (homeworkDataList.get(position).getPicture().size()>1){
            holder.homework_img.setVisibility(View.GONE);
            holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
            final ArrayList<String> pics = new ArrayList<>();
            for (int k=0;k<homeworkDataList.get(position).getPicture().size();k++){
                pics.add(homeworkDataList.get(position).getPicture().get(k).getPicture_url());
            }
            ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter( pics,context);
            holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
            holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", pics);
                    intent.putExtra("type", "newsgroup");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (homeworkDataList.get(position).getPicture().size()==1&&!homeworkDataList.get(position).getPicture().get(0).getPicture_url().equals("null")&&!homeworkDataList.get(position).getPicture().get(0).getPicture_url().equals("")){
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPicture().get(0).getPicture_url(), holder.homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/" + homeworkDataList.get(position).getPicture().get(0));
            final ArrayList<String> pics = new ArrayList<>();
            for (int k=0;k<homeworkDataList.get(position).getPicture().size();k++){
                pics.add(homeworkDataList.get(position).getPicture().get(k).getPicture_url());
            }
            holder.homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", pics);
                    intent.putExtra("type","4");
                    context.startActivity(intent);
                }
            });
        }else {
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.GONE);

        }



        //给内容添加进入详情事件（传递一个模型）
        holder.homework_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, NewsGroupReciveDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("newsgroupdata", homeworkDataList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.touch_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, NewsGroupReciveDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("newsgroupdata", homeworkDataList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text;
        ImageView homework_praise,homework_img,homework_discuss;
        LinearLayout linearLayout_homework_item_praise;
        RelativeLayout news_group_comment_layout;
        NoScrollListView news_group_comment_list;
        NoScrollGridView parent_warn_img_gridview;
        View bg_divider;
        RelativeLayout touch_outside;
        public ViewHolder(View convertView) {
            touch_outside = (RelativeLayout) convertView.findViewById(R.id.touch_outside);
            bg_divider =  convertView.findViewById(R.id.bg_divider);
            news_group_comment_layout = (RelativeLayout) convertView.findViewById(R.id.news_group_comment_layout);
            news_group_comment_list = (NoScrollListView) convertView.findViewById(R.id.news_group_comment_list);
            homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
            teacher_name = (TextView) convertView.findViewById(R.id.myhomework_teacher_name);
            homework_time = (TextView) convertView.findViewById(R.id.myhomework_time);
            alread_text = (TextView) convertView.findViewById(R.id.alread_text);
            homework_praise = (ImageView) convertView.findViewById(R.id.homework_praise);
            homework_discuss = (ImageView) convertView.findViewById(R.id.homework_discuss);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
            homework_item_praise_names = (TextView) convertView.findViewById(R.id.homework_item_praise_names);
            linearLayout_homework_item_praise = (LinearLayout) convertView.findViewById(R.id.linearLayout_homework_item_praise);
            parent_warn_img_gridview = (NoScrollGridView) convertView.findViewById(R.id.parent_warn_img_gridview);
        }
    }



}
