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
import android.view.inputmethod.InputMethodManager;
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
import java.util.Timer;
import java.util.TimerTask;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.CommentAdapter;
import cn.xiaocool.wxtteacher.adapter.HomeworkRemarkAdapter;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class HomeworkDetailActivity extends Activity {

    private TextView homework_title,homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text;
    private ImageView homework_praise,homework_img,homework_discuss;
    private LinearLayout linearLayout_homework_item_praise,edit_and_send;
    private RelativeLayout news_group_comment_layout,back;
    private NoScrollListView news_group_comment_list;
    private View bg_divider;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private UserInfo user;
    private Homework.HomeworkData homeworkData;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {

                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            ToastUtils.ToastShort(HomeworkDetailActivity.this, result);
                            if (state.equals(CommunalInterfaces._STATE)) {
                                getAllInformation();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DEL_HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {

                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                ToastUtils.ToastShort(HomeworkDetailActivity.this, "已取消");
                                getAllInformation();
                            }else
                            {
                                ToastUtils.ToastShort(HomeworkDetailActivity.this, result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case CommunalInterfaces.HOMEWORK:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
                        JSONArray hwArray = obj.optJSONArray("data");
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            if (homeworkData.getId().equals(itemObject.optString("id"))){
                                homeworkData.setId(itemObject.optString("id"));
                                homeworkData.setUserid(itemObject.optString("userid"));
                                homeworkData.setTitle(itemObject.optString("title"));
                                homeworkData.setContent(itemObject.optString("content"));
                                homeworkData.setCreate_time(itemObject.optString("create_time"));
                                homeworkData.setUsername(itemObject.optString("username"));
                                homeworkData.setReadcount(itemObject.optInt("readcount"));
                                homeworkData.setAllreader(itemObject.optInt("allreader"));
                                homeworkData.setReadtag(itemObject.optInt("readtag"));
                                homeworkData.setPhoto(itemObject.optString("photo"));
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
                                    homeworkData.setWorkPraise(likeBeanList);
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
                                    homeworkData.setComment(commentList);
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
                            Toast.makeText(HomeworkDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            edit_and_send.setVisibility(View.GONE);
                            EditText editText =(EditText)edit_and_send.findViewById(R.id.parent_warn_comment_edit);
                            editText.setText(null);
                            getAllInformation();
                        } else {
                            Toast.makeText(HomeworkDetailActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homework_detail);
        context = this;
        initData();
        initview();

    }

    private void initData() {
        Intent intent = this.getIntent();
        homeworkData =(Homework.HomeworkData)intent.getSerializableExtra("homework");
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
        homework_title.setText(homeworkData.getTitle());
        homework_content.setText(homeworkData.getContent());
        teacher_name.setText(homeworkData.getUsername());
        alread_text.setText("已阅读" + homeworkData.getReadcount() + " 未读" + homeworkData.getAllreader());


        Date date = new Date();
        date.setTime(Long.parseLong(homeworkData.getCreate_time()) * 1000);
        homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        homework_img.setVisibility(View.GONE);
        if (homeworkData.getPhoto().length()>1){
            homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" +homeworkData.getPhoto(), homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + homeworkData.getPhoto());
            homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        homework_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgs = new ArrayList<>();
                imgs.add( homeworkData.getPhoto().toString());
                Intent intent = new Intent(context, CircleImagesActivity.class);
                intent.putStringArrayListExtra("Imgs", imgs);
                context.startActivity(intent);
            }
        });
        //判断点赞点赞与否
        linearLayout_homework_item_praise.setVisibility(View.GONE);
        if (homeworkData.getWorkPraise().size()>0){
            linearLayout_homework_item_praise.setVisibility(View.VISIBLE);
            String names = "";
            for (int i=0;i<homeworkData.getWorkPraise().size();i++){
                names = names+" "+homeworkData.getWorkPraise().get(i).getName();
            }
            homework_item_praise_names.setText(names);
        }

        //判断本人是否已经点赞
        if (isPraise(homeworkData.getWorkPraise())) {
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
                if (isPraise(homeworkData.getWorkPraise())) {
                    LogUtils.d("FindFragment", "delPraise");
                    delPraise(homeworkData.getId());
                } else {
                    LogUtils.d("FindFragment","workPraise");
                    workPraise(homeworkData.getId());
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

                    new NewsRequest(context, handler).send_remark(homeworkData.getId(), String.valueOf(edit.getText()), "2");

                } else {

                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //显示评论
        if (homeworkData.getComment().size()>=1){
            //显示评论布局
            news_group_comment_layout.setVisibility(View.VISIBLE);
            bg_divider.setVisibility(View.GONE);
            //加载数据
            news_group_comment_list.setAdapter(new HomeworkRemarkAdapter(homeworkData.getComment(),context));
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
        new NewsRequest(context, handler).Praise(workBindId, HOMEWORK_PRAISE_KEY, "2");
    }

    // 取消点赞
    private void delPraise(String workBindId) {
        new NewsRequest(context, handler).DelPraise(workBindId, DEL_HOMEWORK_PRAISE_KEY, "2");
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
    /**
     * 获取家庭作业
     * */

    private void getAllInformation() {

        new NewsRequest(this,handler).homework();

    }
}
