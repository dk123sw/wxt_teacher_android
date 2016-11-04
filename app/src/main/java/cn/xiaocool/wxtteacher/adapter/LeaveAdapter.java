package cn.xiaocool.wxtteacher.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LeaveModel;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;

/**
 * Created by Administrator on 2016/7/16.
 */
public class LeaveAdapter extends BaseAdapter {

    private List<LeaveModel> parentWarnDataList;
    private LayoutInflater inflater;
    private Context context;
    private String data = null;
    private String remark_content;
    private DisplayImageOptions displayImage;
    private LinearLayout linearLayout;
    private Handler handler;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public LeaveAdapter(List<LeaveModel> parentWarnDataList, Context mContext,Handler handler,LinearLayout linearLayout) {
        this.handler = handler;
        this.linearLayout = linearLayout;
        this.context = mContext;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.parentWarnDataList = parentWarnDataList;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return parentWarnDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return parentWarnDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_parent_warn,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.parent_warn_title.setText(parentWarnDataList.get(position).getStudentname());
        holder.parent_warn_time.setText(getTime(parentWarnDataList.get(position).getCreate_time()));
        holder.parent_warn_content.setText(parentWarnDataList.get(position).getReason());
        holder.parent_warn_from.setText("受理人" + parentWarnDataList.get(position).getTeachername());
//        holder.btn_discuss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                IntentUtils.getIntents(context, ParentWarnCommentActivity.class).putExtra("position",a).putExtra("id",parentWarnDataList.get(position).getId());
//                Intent intent = new Intent(context,ParentWarnCommentActivity.class);
//                intent.putExtra("type","4");
//                intent.putExtra("refid",parentWarnDataList.get(position).getId());
//                context.startActivity(intent);
//            }
//        });


        //判断图片并显示（一张图片显示imageview，多于一张显示gridview）
//        if (parentWarnDataList.get(position).getPic().size()>1){
//            holder.parent_warn_img.setVisibility(View.GONE);
//            holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
//            ParWarnImgGridAdapter parWarnImgGridAdapter = new ParWarnImgGridAdapter( parentWarnDataList.get(position).getPic(),context);
//            holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
//            holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
//                    // 图片浏览
//                    Intent intent = new Intent();
//                    intent.setClass(context, ImgDetailActivity.class);
//                    intent.putStringArrayListExtra("Imgs", parentWarnDataList.get(position).getPic());
//                    intent.putExtra("type","4");
//                    intent.putExtra("position", a);
//                    context.startActivity(intent);
//                }
//            });
//
//        }else if (parentWarnDataList.get(position).getPic().size()==1){
//            holder.parent_warn_img_gridview.setVisibility(View.GONE);
//            holder.parent_warn_img.setVisibility(View.VISIBLE);
//            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//            imageLoader.displayImage("http://wxt.xiaocool.net/" + parentWarnDataList.get(position).getPic().get(0), holder.parent_warn_img, displayImage);
//            Log.d("img", "http://wxt.xiaocool.net/" + parentWarnDataList.get(position).getPic().get(0));
//        }else {
//            holder.parent_warn_img_gridview.setVisibility(View.GONE);
//            holder.parent_warn_img.setVisibility(View.GONE);
//
//        }

        //评论事件
        final View finalConvertView = convertView;
        holder.btn_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(holder.btn_discuss);
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
//
//                    }
//                });




                /*Intent intent = new Intent(context,ParentWarnCommentActivity.class);
                intent.putExtra("type","2");
                intent.putExtra("refid", homeworkDataList.get(position).getId());
                context.startActivity(intent);*/

            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btn_discuss.getText().length() > 0) {
                    //获取到需要上传的参数

                    new NewsRequest(context, handler).send_remark(parentWarnDataList.get(position).getId(), String.valueOf(holder.btn_discuss.getText()), "4");

                } else {

                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //显示评论
        if (parentWarnDataList.get(position).getFeedback().length()>0){
            //显示评论布局
            holder.discuss_layout.setVisibility(View.GONE);
            holder.news_group_comment_layout.setVisibility(View.VISIBLE);
            //holder.bg_divider.setVisibility(View.GONE);

            Comments comments = new Comments();
            comments.setContent(parentWarnDataList.get(position).getFeedback());

            ArrayList<Comments> commentses = new ArrayList<>();
            commentses.add(comments);

            //加载数据
            holder.news_group_comment_list.setAdapter(new HomeworkRemarkAdapter(commentses, context));

            //发送评论功能



            //长按删除评论功能
            holder.news_group_comment_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("确定删除", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                        }
                    }).show();
                    return true;
                }
            });
        }else {
            holder.discuss_layout.setVisibility(View.VISIBLE);
            holder.news_group_comment_layout.setVisibility(View.GONE);
            holder.bg_divider.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private void showDialog(final EditText editText) {

        // 1.创建弹出式对话框
        final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);	// 系统默认Dialog没有输入框

        // 获取自定义的布局
        View alertDialogView = View.inflate(context, R.layout.edit_and_send, null);
        final AlertDialog tempDialog = alertDialog.create();
        tempDialog.setView(alertDialogView, 0, 0, 0, 0);

        // 2.密码框-EditText。alertDialogView.findViewById(R.id.自定义布局中的文本框)
        final EditText et_dialog_confirmphoneguardpswd = (EditText) alertDialogView.findViewById(R.id.btn_discuss);

        // 确认按钮，确认验证密码
        Button btn_dialog_resolve_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_ok);
        btn_dialog_resolve_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                // 提取文本框中输入的文本密码
                editText.setText(et_dialog_confirmphoneguardpswd.getText());
                tempDialog.dismiss();
            }
        });
        // 取消按钮，不验证密码
        Button btn_dialog_cancel_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_cancel);
        btn_dialog_cancel_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                //
                tempDialog.dismiss();
            }
        });

        tempDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_dialog_confirmphoneguardpswd, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        /** 3.自动弹出软键盘 **/
        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_dialog_confirmphoneguardpswd, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
    }

    class ViewHolder{
        TextView parent_warn_title,parent_warn_time,parent_warn_content,parent_warn_from,btn_send,remark,remark_time,remark_username;
        EditText remark_edit,btn_discuss;
        ImageView parent_warn_img;
        RelativeLayout news_group_comment_layout;
        GridView parent_warn_img_gridview;
        NoScrollListView news_group_comment_list;
        View bg_divider;
        LinearLayout discuss_layout;
        Button send;
        public ViewHolder(View convertView) {
            bg_divider =  convertView.findViewById(R.id.bg_divider);
            news_group_comment_list = (NoScrollListView) convertView.findViewById(R.id.news_group_comment_list);
            news_group_comment_layout = (RelativeLayout) convertView.findViewById(R.id.news_group_comment_layout);
            parent_warn_title = (TextView) convertView.findViewById(R.id.parent_warn_title);
            parent_warn_time = (TextView) convertView.findViewById(R.id.parent_warn_time);
            parent_warn_content = (TextView) convertView.findViewById(R.id.parent_warn_content);
            parent_warn_from = (TextView) convertView.findViewById(R.id.parent_warn_from);
            btn_discuss = (EditText) convertView.findViewById(R.id.btn_discuss);
            parent_warn_img = (ImageView) convertView.findViewById(R.id.parent_warn_img);
            parent_warn_img_gridview = (GridView) convertView.findViewById(R.id.parent_warn_img_gridview);
            discuss_layout = (LinearLayout) convertView.findViewById(R.id.edit_and_send);
            send = (Button) convertView.findViewById(R.id.btn_parent_send);

        }
    }
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24*60*60*1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime()%l) - 8* 60 * 60 *1000);
    }


    public void showInfo(final ViewHolder holder,final int position){

        final EditText editText = new EditText(context);
        new AlertDialog.Builder(context).setTitle("请输入").setView(editText).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub


                        remark_content = String.valueOf(editText.getText());
                        holder.remark_edit.setText(remark_content);



                    }
                }).setNegativeButton("取消", null).show();
    }

    public String getTime(String time){

        long todayZero = getTodayZero();
        String ret_time;
        if (todayZero>Long.parseLong(time)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date.setTime(Long.parseLong(time)*1000);
            ret_time = (dateFormat.format(date));

        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            date.setTime(Long.parseLong(time)*1000);
            ret_time = dateFormat.format(date);

        }

        return ret_time;

    }
}
