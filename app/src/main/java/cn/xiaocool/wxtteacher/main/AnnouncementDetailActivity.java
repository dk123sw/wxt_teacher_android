package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.HomeworkRemarkAdapter;
import cn.xiaocool.wxtteacher.bean.Announcement;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class AnnouncementDetailActivity extends Activity {

    private TextView homework_title,homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text;
    private ImageView homework_praise,homework_img,homework_discuss;
    private LinearLayout linearLayout_homework_item_praise,edit_and_send;
    private RelativeLayout news_group_comment_layout,back;
    private NoScrollListView news_group_comment_list;
    private View bg_divider;
    private static final int WORK_PRAISE_KEY = 4;
    private static final int DEL_WORK_PRAISE_KEY = 5;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private UserInfo user;
    private Announcement.AnnouncementData announcementData;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WORK_PRAISE_KEY:
                    if (msg.obj != null) {

                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            ToastUtils.ToastShort(AnnouncementDetailActivity.this, result);
                            if (state.equals(CommunalInterfaces._STATE)) {
                                getAllInformation();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DEL_WORK_PRAISE_KEY:
                    if (msg.obj != null) {

                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                ToastUtils.ToastShort(AnnouncementDetailActivity.this, "已取消");
                                getAllInformation();
                            }else
                            {
                                ToastUtils.ToastShort(AnnouncementDetailActivity.this, result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case CommunalInterfaces.ANNOUNCEMENT:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
                        JSONArray hwArray = obj.optJSONArray("data");
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            if (announcementData.getId().equals(itemObject.optString("id"))){
                                announcementData.setId(itemObject.optString("id"));
                                announcementData.setUserid(itemObject.optString("userid"));
                                announcementData.setTitle(itemObject.optString("title"));
                                announcementData.setContent(itemObject.optString("content"));
                                announcementData.setCreate_time(itemObject.optString("create_time"));
                                announcementData.setUsername(itemObject.optString("username"));
                                announcementData.setReadcount(itemObject.optInt("readcount"));
                                announcementData.setAllreader(itemObject.optInt("allreader"));
                                announcementData.setReadtag(itemObject.optInt("readtag"));
                                announcementData.setPhoto(itemObject.optString("photo"));
                                JSONArray likeArray = itemObject.optJSONArray("like");
                                if (likeArray != null) {
                                    ArrayList<LikeBean> likeBeanList = new ArrayList<>();
                                    for (int j = 0; j < likeArray.length(); j++) {
                                        JSONObject likeObject = likeArray.optJSONObject(j);
                                        LikeBean likeBean = new LikeBean();
                                        likeBean.setUserid(likeObject.optString("userid"));
                                        likeBean.setName(likeObject.optString("name"));
                                        likeBean.setAvatar(likeObject.optString("avatar"));
                                        likeBeanList.add(likeBean);
                                    }
                                    announcementData.setLike(likeBeanList);
                                }
                                JSONArray commentArray = itemObject.optJSONArray("comment");
                                if (commentArray.length()>0) {
                                    ArrayList<Comments> commentList = new ArrayList<>();
                                    for (int j = 0; j < commentArray.length(); j++) {
                                        JSONObject commentObject = commentArray.optJSONObject(j);
                                        Comments comments = new Comments();
                                        comments.setUserid(commentObject.optString("userid"));
                                        comments.setName(commentObject.optString("name"));
                                        comments.setAvatar(commentObject.optString("avatar"));
                                        comments.setComment_time(commentObject.optString("comment_time"));
                                        comments.setContent(commentObject.optString("content"));
                                        commentList.add(comments);
                                    }
                                    announcementData.setComment(commentList);
                                    initview();
                                }
                            }
                        }
                    }
                    break;
                case CommunalInterfaces.SEND_PARENT_REMARK:
                    Log.d("是否成功", "======");

                    if (msg.obj !=null){
                        JSONObject obj2 = (JSONObject) msg.obj;
                        String state = obj2.optString("status");
                        LogUtils.e("HomeworkCommentActivity", obj2.optString("data"));
                        Log.d("是否成功", state);
                        if (state.equals(CommunalInterfaces._STATE)){
                            Toast.makeText(AnnouncementDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            edit_and_send.setVisibility(View.GONE);
                            EditText editText =(EditText)edit_and_send.findViewById(R.id.parent_warn_comment_edit);
                            editText.setText(null);
                            getAllInformation();
                        } else {
                            Toast.makeText(AnnouncementDetailActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_announcement_detail);
        context = this;
        initData();
        initview();

    }

    private void initData() {
        Intent intent = this.getIntent();
        announcementData = (Announcement.AnnouncementData) intent.getSerializableExtra("announcement");
        user = new UserInfo(context);
        user.readData(context);
    }

    private void initview() {

        //初始化组件
        back = (RelativeLayout) findViewById(R.id.up_jiantou);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit_and_send = (LinearLayout) findViewById(R.id.edit_and_send);
        bg_divider =  findViewById(R.id.bg_divider);
        news_group_comment_layout = (RelativeLayout)findViewById(R.id.news_group_comment_layout);
        news_group_comment_list = (NoScrollListView) findViewById(R.id.news_group_comment_list);
        homework_title = (TextView) findViewById(R.id.myhomework_title);
        homework_content = (TextView) findViewById(R.id.myhomework_content);
        teacher_name = (TextView) findViewById(R.id.item_title);
        homework_time = (TextView) findViewById(R.id.item_time);
        alread_text = (TextView) findViewById(R.id.alread_text);
        homework_praise = (ImageView) findViewById(R.id.homework_praise);
        homework_discuss = (ImageView)findViewById(R.id.homework_discuss);
        homework_img = (ImageView) findViewById(R.id.homework_img);
        homework_item_praise_names = (TextView) findViewById(R.id.homework_item_praise_names);
        linearLayout_homework_item_praise = (LinearLayout)findViewById(R.id.linearLayout_homework_item_praise);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        //填充数据
        homework_title.setText(announcementData.getTitle());
        homework_content.setText(announcementData.getContent());
        teacher_name.setText(announcementData.getUsername());
        alread_text.setText("已阅读" + announcementData.getReadcount() + " 未读" + announcementData.getAllreader());


        Date date = new Date();
        date.setTime(Long.parseLong(announcementData.getCreate_time()) * 1000);
        homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        homework_img.setVisibility(View.GONE);
        if (announcementData.getPhoto().length()>1){
            homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" +announcementData.getPhoto(), homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + announcementData.getPhoto());
            homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        homework_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgs = new ArrayList<>();
                imgs.add(announcementData.getPhoto().toString());
                Intent intent = new Intent(context, CircleImagesActivity.class);
                intent.putStringArrayListExtra("Imgs", imgs);
                context.startActivity(intent);
            }
        });
        //判断点赞点赞与否
        linearLayout_homework_item_praise.setVisibility(View.GONE);
        if (announcementData.getLike().size()>0){
            linearLayout_homework_item_praise.setVisibility(View.VISIBLE);
            String names = "";
            for (int i=0;i<announcementData.getLike().size();i++){
                names = names+" "+announcementData.getLike().get(i).getName();
            }
            homework_item_praise_names.setText(names);
        }

        //判断本人是否已经点赞
        if (isPraise(announcementData.getLike())) {
            //点赞成功后图片变红
            homework_praise.setSelected(true);
        } else {
            //取消点赞后
            homework_praise.setSelected(false);
        }

        //点赞事件
        homework_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPraise(announcementData.getLike())) {
                    LogUtils.d("FindFragment", "delPraise");
                    delPraise(announcementData.getId());
                } else {
                    LogUtils.d("FindFragment","workPraise");
                    workPraise(announcementData.getId());
                }
            }
        });


        //评论事件
        final EditText edit = (EditText)findViewById(R.id.parent_warn_comment_edit);
        Button send = (Button)findViewById(R.id.btn_parent_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().length() > 0) {
                    //获取到需要上传的参数

                    new NewsRequest(context, handler).send_remark(announcementData.getId(), String.valueOf(edit.getText()), "3");

                } else {

                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //显示评论
        if (announcementData.getComment().size()>=1){
            //显示评论布局
            news_group_comment_layout.setVisibility(View.VISIBLE);
            bg_divider.setVisibility(View.GONE);
            //加载数据
            news_group_comment_list.setAdapter(new HomeworkRemarkAdapter(announcementData.getComment(),context));
            //长按删除评论功能
            news_group_comment_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
            news_group_comment_layout.setVisibility(View.GONE);
            bg_divider.setVisibility(View.VISIBLE);
        }
    }

    // 点赞
    private void workPraise(String workBindId) {
        Log.i("begintopppp-=====", "222222");
        new NewsRequest(context, handler).Praise(workBindId, WORK_PRAISE_KEY, "3");
    }

    // 取消点赞
    private void delPraise(String workBindId) {
        new NewsRequest(context, handler).DelPraise(workBindId, DEL_WORK_PRAISE_KEY, "3");
    }

    /**
     * 判断当前用户是否点赞
     * */
    private boolean isPraise(List<LikeBean> praises) {
        for (int i = 0; i < praises.size(); i++) {
            if (praises.get(i).getUserid().equals(user.getUserId())) {
                Log.d("praisesid", praises.get(i).getUserid());
                return true;
            }
        }
        return false;
    }
    /**
     * 获取家庭作业
     * */

    private void getAllInformation() {

        new NewsRequest(this,handler).announcement();

    }
}
