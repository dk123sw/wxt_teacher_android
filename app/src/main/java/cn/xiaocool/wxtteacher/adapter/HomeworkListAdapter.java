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
import android.widget.ListView;
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
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;

import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.ReadAndNoreadActivity;

import cn.xiaocool.wxtteacher.ui.NoScrollGridView;

/**
 * Created by Administrator on 2016/5/9.
 */
public class HomeworkListAdapter extends BaseAdapter {
    private static final String TAG = "homework_praise";
    private List<Homework.HomeworkData> homeworkDataList;
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

    private UserInfo user;
//    private int a;
    public HomeworkListAdapter(List<Homework.HomeworkData> homeworkDataList, Context mContext,Handler handler,LinearLayout commentView) {
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
        holder.teacher_name.setText(homeworkDataList.get(position).getUsername());
        holder.alread_text.setText("已阅读" + homeworkDataList.get(position).getReadcount());


        Date date = new Date();
        date.setTime(Long.parseLong(homeworkDataList.get(position).getCreate_time()) * 1000);
        holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));




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



        //判断已读和未读
        holder.alread_text.setText("总发" + homeworkDataList.get(position).getWorkPraise().size()+" 已读"+alreadyReads.size()+" 未读"+notReads.size());
        holder.alread_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, ReadAndNoreadActivity.class);
                intent.putExtra("type", "like");
                Bundle bundle = new Bundle();
                bundle.putSerializable("notReads", (Serializable) notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable) alreadyReads);
                intent.putExtras(bundle);//发送数据
//                intent.putExtras("notReads",(Serializable)notReads);
                context.startActivity(intent);//启动intent

            }
        });



        holder.iv_subject_text.setVisibility(View.VISIBLE);
        holder.iv_subject_text.setText(homeworkDataList.get(position).getSubject());











        holder.homework_img.setVisibility(View.GONE);

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


        }else if (homeworkDataList.get(position).getPics().size()==1&&!homeworkDataList.get(position).getPics().get(0).equals("null")&&!homeworkDataList.get(position).getPics().get(0).equals("")){

            holder.homework_img.setVisibility(View.VISIBLE);
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPics().get(0), holder.homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPics().get(0));
            holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> imgs = new ArrayList<>();
                    imgs.add(homeworkDataList.get(position).getPics().get(0).toString());
                    Intent intent = new Intent(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", imgs);
                    context.startActivity(intent);
                }
            });
        }else {
            holder.homework_img.setVisibility(View.GONE);
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
        }

//        //判断点赞点赞与否
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
//        //获取评论
//        comment=homeworkDataList.get(position).getComment();
//        CommentAdapter adapter=new CommentAdapter(context,comment);
//        holder.comment_list.setAdapter(adapter);
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
//        //详情页面
//        holder.homework_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(context, HomeworkDetailActivity.class);
//                Homework.HomeworkData homeworkData = homeworkDataList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("homework",homeworkData);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });
//        holder.homework_content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(context, HomeworkDetailActivity.class);
//                Homework.HomeworkData homeworkData = homeworkDataList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("homework",homeworkData);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });
//        final int a = position;
//        //评论事件
//        holder.homework_discuss.setOnClickListener(new View.OnClickListener() {
//            @Override
//              public void onClick(View v) {
//                commentView.setVisibility(View.VISIBLE);
//                final EditText editText = (EditText) commentView.findViewById(R.id.parent_warn_comment_edit);
//                final Button button = (Button) commentView.findViewById(R.id.btn_parent_send);
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
//                        500);
//
//                //holder.comment_view.setVisibility(View.VISIBLE);
//                /*Intent intent = new Intent(context,ParentWarnCommentActivity.class);
//                intent.putExtra("type","2");
//                intent.putExtra("refid", homeworkDataList.get(position).getId());
//                context.startActivity(intent);*/
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (editText.getText().length() > 0) {
//                            //获取到需要上传的参数
//                            Log.i("===============homwork", "22222--" + 2);
//                            Log.i("===============homwork", "11111--" + homeworkDataList.get(position).getId());
//                            new NewsRequest(context, handler).send_remark(homeworkDataList.get(position).getId(), String.valueOf(editText.getText()), "2");
//                            commentView.setVisibility(View.GONE);
//                            editText.setText("");
//                        } else {
//
//                            Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//        });
        return convertView;
    }

    class ViewHolder{
        TextView homework_title,homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text,not_read_text,iv_subject_text;
        ImageView homework_praise,homework_img,homework_discuss;
        LinearLayout linearLayout_homework_item_praise,comment_view;
        NoScrollGridView parent_warn_img_gridview;
        private ListView comment_list;
        public ViewHolder(View convertView) {
            iv_subject_text = (TextView) convertView.findViewById(R.id.iv_subject_text);
            parent_warn_img_gridview = (NoScrollGridView) convertView.findViewById(R.id.parent_warn_img_gridview);
            comment_list = (ListView) convertView.findViewById(R.id.comment_list);
            comment_view = (LinearLayout) convertView.findViewById(R.id.edit_and_send);
            homework_title = (TextView) convertView.findViewById(R.id.myhomework_title);
            homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
            teacher_name = (TextView) convertView.findViewById(R.id.myhomework_teacher_name);
            homework_time = (TextView) convertView.findViewById(R.id.myhomework_time);
            not_read_text = (TextView) convertView.findViewById(R.id.not_read_text);
            alread_text = (TextView) convertView.findViewById(R.id.alread_text);
            homework_praise = (ImageView) convertView.findViewById(R.id.homework_praise);
            homework_praise.setVisibility(View.GONE);
            homework_discuss = (ImageView) convertView.findViewById(R.id.homework_discuss);
            homework_discuss.setVisibility(View.GONE);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
            homework_item_praise_names = (TextView) convertView.findViewById(R.id.homework_item_praise_names);
            homework_item_praise_names.setVisibility(View.GONE);
            linearLayout_homework_item_praise = (LinearLayout) convertView.findViewById(R.id.linearLayout_homework_item_praise);
            linearLayout_homework_item_praise.setVisibility(View.GONE);
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
