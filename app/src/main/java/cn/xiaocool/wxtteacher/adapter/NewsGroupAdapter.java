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
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;

import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.NewsGroupDetailActivity;
import cn.xiaocool.wxtteacher.main.ReadAndNoreadActivity;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;

/**
 * Created by Administrator on 2016/4/22.
 */
public class NewsGroupAdapter extends BaseAdapter {
    private static final String TAG = "homework_praise";
    private List<Homework.HomeworkData> homeworkDataList;
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
    public NewsGroupAdapter(List<Homework.HomeworkData> homeworkDataList, Context mContext,Handler handler,LinearLayout linearLayout) {
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


        final ArrayList<LikeBean> notReads = new ArrayList<>();
        final ArrayList<LikeBean> alreadyReads = new ArrayList<>();
        if (homeworkDataList.get(position).getWorkPraise().size()>0){
            for (int i=0;i<homeworkDataList.get(position).getWorkPraise().size();i++){
                if (homeworkDataList.get(position).getWorkPraise().get(i).getTime().equals("null")){
                    notReads.add(homeworkDataList.get(position).getWorkPraise().get(i));
                }else {
                    alreadyReads.add(homeworkDataList.get(position).getWorkPraise().get(i));
                }
            }
        }


        holder.homework_content.setText(homeworkDataList.get(position).getContent());
        holder.teacher_name.setText(homeworkDataList.get(position).getUsername());

        //判断已读和未读
        holder.alread_text.setText("总发" + homeworkDataList.get(position).getAllreader()+" 已读"+alreadyReads.size()+" 未读"+notReads.size());
        holder.alread_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(context, ReadAndNoreadActivity.class);
                intent.putExtra("type","like");
                Bundle bundle=new Bundle();
                bundle.putSerializable("notReads",(Serializable)notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable)alreadyReads);
                intent.putExtras(bundle);//发送数据
//                intent.putExtras("notReads",(Serializable)notReads);
                context.startActivity(intent);//启动intent

            }
        });


        Date date = new Date();
        date.setTime(Long.parseLong(homeworkDataList.get(position).getCreate_time()) * 1000);
        holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        //图片显示
//        holder.homework_img.setVisibility(View.GONE);
//        if (homeworkDataList.get(position).getPics().size()>1){
//            holder.homework_img.setVisibility(View.VISIBLE);
//            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPhoto(), holder.homework_img, displayImage);
//            Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPhoto());
//            holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//        holder.homework_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,ShowImageActivity.class);
//                intent.putExtra("Image",homeworkDataList.get(position).getPhoto().toString());
//                context.startActivity(intent);
//            }
//        });

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
                    intent.putExtra("type","newsgroup");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (homeworkDataList.get(position).getPics().size()==1&&!homeworkDataList.get(position).getPics().get(0).equals("null")&&!homeworkDataList.get(position).getPics().get(0).equals("")){
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPics().get(0), holder.homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/" + homeworkDataList.get(position).getPics().get(0));
            holder.homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", homeworkDataList.get(position).getPics());
                    intent.putExtra("type","4");
                    context.startActivity(intent);
                }
            });
        }else {
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.GONE);

        }

        //判断点赞点赞与否
//        holder.linearLayout_homework_item_praise.setVisibility(View.GONE);
//        if (homeworkDataList.get(position).getWorkPraise().size()>0){
//            holder.linearLayout_homework_item_praise.setVisibility(View.VISIBLE);
//            String names = "";
//            for (int i=0;i<homeworkDataList.get(position).getWorkPraise().size();i++){
//                names = names+" "+homeworkDataList.get(position).getWorkPraise().get(i).getName();
//            }
//            holder.homework_item_praise_names.setText(names);
//        }
//
//        //判断本人是否已经点赞
//        if (isPraise(homeworkDataList.get(position).getWorkPraise())) {
//            //点赞成功后图片变红
//            holder.homework_praise.setSelected(true);
//        } else {
//            //取消点赞后
//            holder.homework_praise.setSelected(false);
//        }
//
//        //点赞事件
//        holder.homework_praise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPraise(homeworkDataList.get(position).getWorkPraise())) {
//                    LogUtils.d("FindFragment", "delPraise");
//                    delPraise(homeworkDataList.get(position).getId());
//                } else {
//                    LogUtils.d("FindFragment","workPraise");
//                    workPraise(homeworkDataList.get(position).getId());
//                }
//            }
//        });
//
//
//        //评论事件
//        final View finalConvertView = convertView;
//        holder.homework_discuss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                linearLayout.setVisibility(View.VISIBLE);
//                final EditText editText= (EditText) linearLayout.findViewById(R.id.parent_warn_comment_edit);
//                editText.setFocusable(true);
//                editText.setFocusableInTouchMode(true);
//                editText.requestFocus();
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//
//                                   public void run() {
//                                       InputMethodManager inputManager =
//                                               (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                                       inputManager.showSoftInput(editText, 0);
//                                   }
//
//                               },
//                        50);
//
//                //获取输入框的内容
//                final EditText edit = (EditText)linearLayout.findViewById(R.id.parent_warn_comment_edit);
//                Button send = (Button) linearLayout.findViewById(R.id.btn_parent_send);
//                send.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (edit.getText().length() > 0) {
//                            //获取到需要上传的参数
//
//                            new NewsRequest(context, handler).send_remark(homeworkDataList.get(position).getId(), String.valueOf(edit.getText()), "2");
//
//                        } else {
//
//                            Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });




                /*Intent intent = new Intent(context,ParentWarnCommentActivity.class);
                intent.putExtra("type","2");
                intent.putExtra("refid", homeworkDataList.get(position).getId());
                context.startActivity(intent);*/

//            }
//        });

//        //显示评论
//        if (homeworkDataList.get(position).getComment().size()>=1){
//            //显示评论布局
//            holder.news_group_comment_layout.setVisibility(View.VISIBLE);
//            holder.bg_divider.setVisibility(View.GONE);
//            //加载数据
//            holder.news_group_comment_list.setAdapter(new HomeworkRemarkAdapter(homeworkDataList.get(position).getComment(),context));
//            //长按删除评论功能
//            holder.news_group_comment_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("确定删除", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//
//                        }
//                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            dialog.dismiss();
//
//                        }
//                    }).show();
//                    return true;
//                }
//            });
//        }else {
//            holder.news_group_comment_layout.setVisibility(View.GONE);
//            holder.bg_divider.setVisibility(View.VISIBLE);
//        }



        //给内容添加进入详情事件（传递一个模型）
        holder.homework_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, NewsGroupDetailActivity.class);
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
                intent.setClass(context, NewsGroupDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("newsgroupdata", homeworkDataList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    // 点赞
    private void workPraise(String workBindId) {
        Log.i("begintopppp-=====","222222");
        new NewsRequest(context, handler).Praise(workBindId, HOMEWORK_PRAISE_KEY,"2");
    }

    // 取消点赞
    private void delPraise(String workBindId) {
        new NewsRequest(context, handler).DelPraise(workBindId, DEL_HOMEWORK_PRAISE_KEY,"2");
    }

    /**
     * 判断当前用户是否点赞
     * */
    private boolean isPraise(ArrayList<LikeBean> praises) {
        for (int i = 0; i < praises.size(); i++) {
            if (praises.get(i).getUserid().equals(user.getUserId())) {
                Log.d("praisesid", praises.get(i).getUserid());
                return true;
            }
        }
        return false;
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
