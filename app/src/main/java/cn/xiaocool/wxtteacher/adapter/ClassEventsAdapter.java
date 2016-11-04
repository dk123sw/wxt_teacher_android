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
import cn.xiaocool.wxtteacher.bean.Classevents;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.ClassEventReadAndNreadActivity;
import cn.xiaocool.wxtteacher.main.ClassEventsDetailActivity;

import cn.xiaocool.wxtteacher.ui.NoScrollGridView;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ClassEventsAdapter extends BaseAdapter {

    private static final String TAG = "homework_praise";
    private List<Classevents.ClassEventData> homeworkDataList;
    private LinearLayout commentView;
    private LayoutInflater inflater;
    private Context context;
    private Handler handler;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private static final int GET_VIEWPAPER_LIST_KEY = 1;
    private static final int GET_LIST_KEY = 102;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private DisplayImageOptions displayImage;
    private ArrayList<Comments> comment;
    private String type = "5";
    private UserInfo user;
    //    private int a;
    public ClassEventsAdapter(List<Classevents.ClassEventData> homeworkDataList, Context mContext,Handler handler,LinearLayout commentView) {
        this.context = mContext;
        this.handler= handler;
        this.commentView=commentView;
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
            convertView = inflater.inflate(R.layout.message_myhomework,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.homework_title.setText(homeworkDataList.get(position).getTitle());
        holder.homework_content.setText(homeworkDataList.get(position).getContent());
        holder.teacher_name.setText(homeworkDataList.get(position).getTeachername());



        Date date = new Date();
        date.setTime(Long.parseLong(homeworkDataList.get(position).getCreate_time()) * 1000);
        holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        holder.homework_img.setVisibility(View.GONE);
        if (homeworkDataList.get(position).getPics()!=null){
            if (homeworkDataList.get(position).getPics().size()>1){
                holder.homework_img.setVisibility(View.GONE);
                holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
                ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter( homeworkDataList.get(position).getPics(),context);
                holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
                holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                        // 图片浏览
                        Intent intent = new Intent();
                        intent.setClass(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", homeworkDataList.get(position).getPics());
                        intent.putExtra("type", "newsgroup");
                        intent.putExtra("position", a);
                        context.startActivity(intent);
                    }
                });


            }else if (homeworkDataList.get(position).getPics().size()==1&&!homeworkDataList.get(position).getPics().get(0).equals("null")){

                holder.homework_img.setVisibility(View.VISIBLE);
                holder.parent_warn_img_gridview.setVisibility(View.GONE);
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPics().get(0), holder.homework_img, displayImage);
                Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPhoto());
                holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.homework_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", homeworkDataList.get(position).getPics());
                        intent.putExtra("type", "newsgroup");
                        intent.putExtra("position", 0);
                        context.startActivity(intent);
                    }
                });
            }else {
                holder.homework_img.setVisibility(View.GONE);
                holder.parent_warn_img_gridview.setVisibility(View.GONE);
            }

        }else {
            holder.homework_img.setVisibility(View.GONE);
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
        }


        //详情页面
        holder.tecxt_homwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ClassEventsDetailActivity.class);
                Classevents.ClassEventData homeworkData = homeworkDataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("homework",homeworkData);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ClassEventsDetailActivity.class);
                Classevents.ClassEventData homeworkData = homeworkDataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("homework",homeworkData);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //计算已读未读和已报名
        final ArrayList<Classevents.ClassEventData.ReciverlistInfo> notReads = new ArrayList<>();
        final ArrayList<Classevents.ClassEventData.ReciverlistInfo> alreadyReads = new ArrayList<>();
        if (homeworkDataList.get(position).getReciverlist().size()>0){
            for (int i=0;i<homeworkDataList.get(position).getReciverlist().size();i++){
                if (homeworkDataList.get(position).getReciverlist().get(i).getRead_time().equals("null")){
                    notReads.add(homeworkDataList.get(position).getReciverlist().get(i));
                }else {
                    alreadyReads.add(homeworkDataList.get(position).getReciverlist().get(i));
                }
            }
        }

        holder.alread_text.setText("已报名" + homeworkDataList.get(position).getIsApplyLists().size() +" 已读"+alreadyReads.size() +" 未读"+ notReads.size());
        holder.read_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, ClassEventReadAndNreadActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("notReads",(Serializable)notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable)alreadyReads);
                bundle.putSerializable("applylist",homeworkDataList.get(position).getIsApplyLists());
                intent.putExtras(bundle);//发送数据
//                intent.putExtras("notReads",(Serializable)notReads);
                context.startActivity(intent);//启动intent
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView homework_title,homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text;
        ImageView homework_praise,homework_img,homework_discuss;
        LinearLayout linearLayout_homework_item_praise,comment_view;
        NoScrollGridView parent_warn_img_gridview;
        RelativeLayout tecxt_homwork, from_layout,read_layout;
        public ViewHolder(View convertView) {

            tecxt_homwork = (RelativeLayout) convertView.findViewById(R.id.tecxt_homwork);
            from_layout = (RelativeLayout) convertView.findViewById(R.id.from_layout);
            read_layout = (RelativeLayout) convertView.findViewById(R.id.read_layout);


            parent_warn_img_gridview = (NoScrollGridView) convertView.findViewById(R.id.parent_warn_img_gridview);
            comment_view = (LinearLayout) convertView.findViewById(R.id.edit_and_send);
            homework_title = (TextView) convertView.findViewById(R.id.myhomework_title);
            homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
            teacher_name = (TextView) convertView.findViewById(R.id.myhomework_teacher_name);
            homework_time = (TextView) convertView.findViewById(R.id.myhomework_time);
            alread_text = (TextView) convertView.findViewById(R.id.alread_text);
            homework_praise = (ImageView) convertView.findViewById(R.id.homework_praise);
            homework_discuss = (ImageView) convertView.findViewById(R.id.homework_discuss);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
            homework_item_praise_names = (TextView) convertView.findViewById(R.id.homework_item_praise_names);
            linearLayout_homework_item_praise = (LinearLayout) convertView.findViewById(R.id.linearLayout_homework_item_praise);

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
