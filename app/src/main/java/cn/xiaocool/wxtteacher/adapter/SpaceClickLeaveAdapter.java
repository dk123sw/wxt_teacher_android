package cn.xiaocool.wxtteacher.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.LeaveModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;

import cn.xiaocool.wxtteacher.main.CircleImagesActivity;

import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;

/**
 * Created by Administrator on 2016/6/9.
 */
public class SpaceClickLeaveAdapter extends BaseAdapter{

    private UserInfo user = new UserInfo();
    private List<LeaveModel> backlogDataList;
    private LayoutInflater inflater;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions displayImage;
    private Handler handler;
    private int index;
    private String et_text;
    public SpaceClickLeaveAdapter( List<LeaveModel> backlogDataList,Context mContext,Handler handler) {
        this.handler = handler;
        this.context = mContext;
        user.readData(context);
        this.backlogDataList = backlogDataList;
        this.inflater = LayoutInflater.from(mContext);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.leave_pic).showImageOnFail(R.drawable.leave_pic)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    private String satus = "0";
    @Override
    public int getCount() {
        return backlogDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return backlogDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_online_leave,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.item_title.setText(backlogDataList.get(position).getStudentname());
        holder.item_content.setText(backlogDataList.get(position).getClassname());
        holder.myhomework_content.setText(backlogDataList.get(position).getReason());
        holder.parent_warn_from.setText("受理人: " + backlogDataList.get(position).getTeachername());
        holder.item_time.setText(TimeToolUtils.fromateTimeShowByRule(Long.parseLong(backlogDataList.get(position).getCreate_time())*1000));

        //显示请假时间
        final String begin = TimeToolUtils.fromateTimeShowByRule(Long.parseLong(backlogDataList.get(position).getBegintime())*1000);
        String end = TimeToolUtils.fromateTimeShowByRule(Long.parseLong(backlogDataList.get(position).getEndtime())*1000);
        holder.parent_warn_time.setText(begin + "到" + end);


        //显示头像--图片
        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + backlogDataList.get(position).getStudentavatar(), holder.item_head, displayImage);


        if (backlogDataList.get(position).getFeedback().length()>0){
            //判断显示哪一个布局
//            holder.edit_and_send.setVisibility(View.GONE);
            holder.feedback_layout.setVisibility(View.VISIBLE);

            //显示评论内容布局
            holder.feedback_name.setText(backlogDataList.get(position).getTeachername());
            holder.reciver_feedback_text.setText(backlogDataList.get(position).getFeedback());
            holder.deal_time.setText(TimeToolUtils.fromateTimeShowByRule(Long.parseLong(backlogDataList.get(position).getDeal_time())*1000));

            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + backlogDataList.get(position).getTeacheravatar(), holder.item_feed_head, displayImage);

        }else {
//            holder.edit_and_send.setVisibility(View.VISIBLE);
            holder.feedback_layout.setVisibility(View.GONE);
        }



//        holder.btn_discuss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(holder.btn_discuss,position);
//            }
//        });

        holder.btn_parent_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
        //批准与否

        holder.say_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //不同意
                satus = "2";

                new NewsRequest(context,handler).send_replayleave(backlogDataList.get(position).getId(),satus,holder.btn_discuss.getText().toString(),1111);
//                RequestQueue mQueue = Volley.newRequestQueue(context);
//                String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=replyleave&leaveid="+backlogDataList.get(position).getId()
//                        +"&teacherid="+ user.getUserId()+"&feedback="+holder.btn_discuss.getText().toString()+"&status="+satus;
//
//
//                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        ToastUtils.ToastShort(context, arg0);
//                        Log.d("onResponse", arg0);
//                        handler.sendEmptyMessage(1111);
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError arg0) {
//                        ToastUtils.ToastShort(context, arg0.toString());
//                        Log.d("onErrorResponse", arg0.toString());
//                    }
//                });
//                mQueue.add(request);
            }
        });

        holder.say_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同意
                satus = "1";
                new NewsRequest(context,handler).send_replayleave(backlogDataList.get(position).getId(),satus,holder.btn_discuss.getText().toString(),1111);

//                RequestQueue mQueue = Volley.newRequestQueue(context);
//                String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=replyleave&leaveid="+backlogDataList.get(position).getId()
//                        +"&teacherid="+ user.getUserId()+"&feedback="+holder.btn_discuss.getText().toString()+"&status="+satus;
//                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        ToastUtils.ToastShort(context, arg0);
//                        Log.d("onResponse", arg0);
//                        handler.sendEmptyMessage(1111);
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError arg0) {
//                        ToastUtils.ToastShort(context, arg0.toString());
//                        Log.d("onErrorResponse", arg0.toString());
//                    }
//                });
//                mQueue.add(request);

            }
        });

//        //发送回复
//        holder.btn_parent_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RequestQueue mQueue = Volley.newRequestQueue(context);
//                String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=replyleave&leaveid="+backlogDataList.get(position).getId()
//                        +"&teacherid="+ user.getUserId()+"&feedback="+holder.btn_discuss.getText().toString()+"&status="+satus;
//                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        ToastUtils.ToastShort(context, arg0);
//                        Log.d("onResponse", arg0);
//                        handler.sendEmptyMessage(1111);
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError arg0) {
//                        ToastUtils.ToastShort(context, arg0.toString());
//                        Log.d("onErrorResponse", arg0.toString());
//                    }
//                });
//                mQueue.add(request);
//            }
//        });


        if (backlogDataList.get(position).getPics()!=null){

            if (backlogDataList.get(position).getPics().size()>1){
                holder.homework_img.setVisibility(View.GONE);
                holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
               ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter( backlogDataList.get(position).getPics(),context);
                holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
                holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                        // 图片浏览
                        Intent intent = new Intent();
                        intent.setClass(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", backlogDataList.get(position).getPics());
                        intent.putExtra("type", "newsgroup");
                        intent.putExtra("position", a);
                        context.startActivity(intent);
                    }
                });


            }else if (backlogDataList.get(position).getPics().size()==1&&!backlogDataList.get(position).getPics().get(0).equals("null")&&!backlogDataList.get(position).getPics().get(0).equals("")){

                holder.homework_img.setVisibility(View.VISIBLE);
                holder.parent_warn_img_gridview.setVisibility(View.GONE);
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + backlogDataList.get(position).getPics().get(0), holder.homework_img, displayImage);
                Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + backlogDataList.get(position).getPics().get(0));
                holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.homework_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> imgs = new ArrayList<>();
                        imgs.add(backlogDataList.get(position).getPics().get(0).toString());
                        Intent intent = new Intent(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", imgs);
                        context.startActivity(intent);
                    }
                });
            }else {
                holder.homework_img.setVisibility(View.GONE);
                holder.parent_warn_img_gridview.setVisibility(View.GONE);
            }

        }



        if (backlogDataList.get(position).getStatus().equals("1")){
            holder.discuss_layout.setVisibility(View.GONE);
            holder.say_no.setVisibility(View.GONE);
            holder.say_yes.setVisibility(View.GONE);
            holder.say_yes_show.setVisibility(View.VISIBLE);
            holder.say_no_show.setVisibility(View.GONE);


        }else if (backlogDataList.get(position).getStatus().equals("2")){
            holder.discuss_layout.setVisibility(View.GONE);
            holder.say_yes_show.setVisibility(View.GONE);
            holder.say_no.setVisibility(View.GONE);
            holder.say_yes.setVisibility(View.GONE);
            holder.say_no_show.setVisibility(View.VISIBLE);

        }else {
            holder.discuss_layout.setVisibility(View.VISIBLE);
            holder.say_no.setVisibility(View.VISIBLE);
            holder.say_yes.setVisibility(View.VISIBLE);
            holder.say_yes_show.setVisibility(View.GONE);
            holder.say_no_show.setVisibility(View.GONE);
        }

        if (index==position){
            holder.btn_discuss.setText(et_text);
        }else {
            holder.btn_discuss.setText(null);
        }

        return convertView;
    }


    /**
     * 评论点击事件
     * */
    private void showDialog(final int position) {
        index =position;

        // 1.创建弹出式对话框
        final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);	// 系统默认Dialog没有输入框

        // 获取自定义的布局
        View alertDialogView = View.inflate(context, R.layout.leave_success_no, null);
        final AlertDialog tempDialog = alertDialog.create();
        tempDialog.setView(alertDialogView, 0, 0, 0, 0);

        // 2.密码框-EditText。alertDialogView.findViewById(R.id.自定义布局中的文本框)
        final EditText et_dialog_confirmphoneguardpswd = (EditText) alertDialogView.findViewById(R.id.btn_discuss);

        // 确认按钮，确认验证密码
        TextView btn_dialog_resolve_confirmphoneguardpswd = (TextView) alertDialogView.findViewById(R.id.btn_ok);
        btn_dialog_resolve_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                // 提取文本框中输入的文本密码
                et_text = et_dialog_confirmphoneguardpswd.getText().toString();
                satus = "2";
                new NewsRequest(context,handler).send_replayleave(backlogDataList.get(position).getId(),satus,et_text,1111);
                tempDialog.dismiss();
            }
        });
        // 取消按钮，不验证密码
        TextView btn_dialog_cancel_confirmphoneguardpswd = (TextView) alertDialogView.findViewById(R.id.btn_cancel);
        btn_dialog_cancel_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                et_text = et_dialog_confirmphoneguardpswd.getText().toString();
                satus = "1";
                new NewsRequest(context,handler).send_replayleave(backlogDataList.get(position).getId(),satus,et_text,1111);
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
        TextView leave_type_tv,item_title,item_content,myhomework_content,item_time,parent_warn_from,parent_warn_time,feedback_name,reciver_feedback_text,deal_time;
        ImageView homework_img;
        RoundImageView item_head,item_feed_head;
        EditText btn_discuss;
        LinearLayout edit_and_send,feedback_layout;
        RelativeLayout say_no_show,say_yes_show,discuss_layout;
        TextView say_no,say_yes;
        NoScrollGridView parent_warn_img_gridview;
        Button btn_parent_send;
        public ViewHolder(View convertView) {

            leave_type_tv = (TextView)convertView.findViewById(R.id.leave_type_tv);
            btn_parent_send = (Button) convertView.findViewById(R.id.btn_parent_send);
            parent_warn_img_gridview = (NoScrollGridView) convertView.findViewById(R.id.parent_warn_img_gridview);
            item_title = (TextView) convertView.findViewById(R.id.item_title);
            item_content = (TextView) convertView.findViewById(R.id.item_content);
            myhomework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
            item_time = (TextView) convertView.findViewById(R.id.item_time);
            parent_warn_from = (TextView) convertView.findViewById(R.id.parent_warn_from);
            parent_warn_time = (TextView) convertView.findViewById(R.id.parent_warn_time);
            item_time = (TextView) convertView.findViewById(R.id.item_time);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
            item_head = (RoundImageView) convertView.findViewById(R.id.item_head);
            btn_discuss = (EditText) convertView.findViewById(R.id.btn_discuss);

            edit_and_send = (LinearLayout) convertView.findViewById(R.id.edit_and_send);
            say_no_show = (RelativeLayout) convertView.findViewById(R.id.say_no_show);
            say_yes_show = (RelativeLayout) convertView.findViewById(R.id.say_yes_show);
            discuss_layout = (RelativeLayout) convertView.findViewById(R.id.discuss_layout);
            feedback_layout = (LinearLayout) convertView.findViewById(R.id.feedback_layout);
            reciver_feedback_text = (TextView) convertView.findViewById(R.id.reciver_feedback_text);
            feedback_name = (TextView) convertView.findViewById(R.id.feedback_name);
            deal_time = (TextView) convertView.findViewById(R.id.deal_time);
            item_feed_head = (RoundImageView) convertView.findViewById(R.id.item_feed_head);

            say_no = (TextView) convertView.findViewById(R.id.say_no);
            say_yes = (TextView) convertView.findViewById(R.id.say_yes);

        }
    }

}
