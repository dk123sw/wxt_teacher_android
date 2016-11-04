package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassCricleInfo;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.main.AlbumDetailActivity;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.CommentPopupWindow;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class AlbumListAdapter extends BaseAdapter {
    private static final String TAG = "homework_praise";
    private ArrayList<String> pics;
    private List<ClassCricleInfo> homeworkDataList;
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
    private static long lastClickTime;
    private UserInfo user;
    private CommentPopupWindow commentPopupWindow;
    //    private int a;
    public AlbumListAdapter(List<ClassCricleInfo> homeworkDataList, Context mContext,Handler handler,LinearLayout commentView) {
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
        Log.e("sdsd",homeworkDataList.toString());
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
            convertView = inflater.inflate(R.layout.message_myalbum,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.homework_content.setText(homeworkDataList.get(position).getMatter());
        holder.teacher_name.setText(homeworkDataList.get(position).getMemberName());
        Log.e("555555555555555555555",homeworkDataList.get(position).getMemberName());
//        holder.alread_text.setText("已阅读" + homeworkDataList.get(position).getReadcount());
//        holder.not_read_text.setText("未读" + homeworkDataList.get(position).getAllreader());

        Date date = new Date();
        date.setTime(Long.parseLong(homeworkDataList.get(position).getAddtime()) * 1000);
        holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));



//        //获取ID回调刷新点赞数据
//
//        new NewsRequest(context,handler).get_like(homeworkDataList.get(position).getUserid(),homeworkDataList.get(position).getId(),"2");

        //判断图片并显示（一张图片显示imageview，多于一张显示gridview）
        if (homeworkDataList.get(position).getWorkImgs().size()>1){
//            pics =new ArrayList<String>();
//            pics.add("uploads/microblog/notice5971307016.png");
//            pics.add("uploads/microblog/notice5971417777.png");
//            pics.add("uploads/microblog/notice5971480427.png");
//            pics.add("uploads/microblog/notice5971055923.png");
            holder.homework_img.setVisibility(View.GONE);
            holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
            MyGridAdapter parWarnImgGridAdapter = new MyGridAdapter( homeworkDataList.get(position).getWorkImgs(),context);
            holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
            holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    /*ArrayList<String> pics = new ArrayList<String>();
                    pics.add(homeworkDataList.get(position).getPhoto());*/
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", homeworkDataList.get(position).getWorkImgs());
//                    intent.putExtra("type", "4");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });
        }else if (homeworkDataList.get(position).getWorkImgs().size()==1){
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + homeworkDataList.get(position).getWorkImgs().get(0), holder.homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/" + homeworkDataList.get(position).getWorkImgs().get(0));
            holder.homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", homeworkDataList.get(position).getWorkImgs());
                    intent.putExtra("type", "4");
                    context.startActivity(intent);
                }
            });
        }else {
            holder.parent_warn_img_gridview.setVisibility(View.GONE);
            holder.homework_img.setVisibility(View.GONE);
        }
        //判断点赞点赞与否
        holder.linearLayout_homework_item_praise.setVisibility(View.GONE);
        if (homeworkDataList.get(position).getWorkPraise().size()>0){
            holder.linearLayout_homework_item_praise.setVisibility(View.VISIBLE);
            String names = "";
            for (int i=0;i<homeworkDataList.get(position).getWorkPraise().size();i++){
                names = names+" "+homeworkDataList.get(position).getWorkPraise().get(i).getName();
            }
            holder.homework_item_praise_names.setText(names);
        }

        //判断本人是否已经点赞
        if (isPraise(homeworkDataList.get(position).getWorkPraise())) {
            //点赞成功后图片变红
            holder.homework_praise.setSelected(true);
        } else {
            //取消点赞后
            holder.homework_praise.setSelected(false);
        }

        //获取评论
        comment=homeworkDataList.get(position).getComment();
        HomeworkRemarkAdapter adapter=new HomeworkRemarkAdapter(comment,context);
        holder.comment_list.setAdapter(adapter);

        //点赞事件
        holder.homework_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick())return;
                if (isPraise(homeworkDataList.get(position).getWorkPraise())) {
                    LogUtils.d("FindFragment", "delPraise");
                    delPraise(homeworkDataList.get(position).getId());
                } else {
                    LogUtils.d("FindFragment","workPraise");
                    workPraise(homeworkDataList.get(position).getId());
                }
            }
        });

        //详情页面
        holder.homework_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, AlbumDetailActivity.class);
                ClassCricleInfo  homeworkData= homeworkDataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("album",homeworkData);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        final int a = position;
        //评论事件
        holder.homework_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                showDialog(homeworkDataList.get(position).getId());
            }
        });
        return convertView;
    }
    private void showDialog(final String id) {


        commentPopupWindow = new CommentPopupWindow(context, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_comment:
                        if (commentPopupWindow.ed_comment.getText().length() > 0) {
                            new NewsRequest(context, handler).send_remark(id, String.valueOf(commentPopupWindow.ed_comment.getText()), "2");
                            commentPopupWindow.dismiss();
                            commentPopupWindow.ed_comment.setText("");
                        } else {

                            Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        final EditText editText = commentPopupWindow.ed_comment;
        commentPopupWindow.showAtLocation(commentView, Gravity.BOTTOM, 0, 0);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        },300);

    }



    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
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
        TextView homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text,not_read_text;
        ImageView homework_praise,homework_img,homework_discuss;
        LinearLayout linearLayout_homework_item_praise,comment_view;
        GridView parent_warn_img_gridview;
        private ListView comment_list;
        public ViewHolder(View convertView) {
            comment_list = (ListView) convertView.findViewById(R.id.comment_list);
            comment_view = (LinearLayout) convertView.findViewById(R.id.edit_and_send);
            homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
            teacher_name = (TextView) convertView.findViewById(R.id.myhomework_teacher_name);
            homework_time = (TextView) convertView.findViewById(R.id.myhomework_time);
            not_read_text = (TextView) convertView.findViewById(R.id.not_read_text);
            alread_text = (TextView) convertView.findViewById(R.id.alread_text);
            homework_praise = (ImageView) convertView.findViewById(R.id.homework_praise);
            homework_discuss = (ImageView) convertView.findViewById(R.id.homework_discuss);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
            homework_item_praise_names = (TextView) convertView.findViewById(R.id.homework_item_praise_names);
            linearLayout_homework_item_praise = (LinearLayout) convertView.findViewById(R.id.linearLayout_homework_item_praise);
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
