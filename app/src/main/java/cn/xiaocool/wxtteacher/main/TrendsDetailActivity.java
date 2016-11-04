package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.HomeworkRemarkAdapter;
import cn.xiaocool.wxtteacher.adapter.MyGridAdapter;
import cn.xiaocool.wxtteacher.bean.ClassCricleInfo;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.ClassCircleRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.LogUtils;

public class TrendsDetailActivity extends Activity {

    private static final int GET_VIEWPAPER_LIST_KEY = 1;
    private static final int GET_CIRCLE_LIST_KEY = 2;
    private static final int WORK_PRAISE_KEY = 4;
    private static final int DEL_WORK_PRAISE_KEY = 5;
    private TextView homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text,not_read_text;
    private ImageView homework_praise,homework_img,quit;
    private LinearLayout linearLayout_homework_item_praise,comment_view;
    private RelativeLayout news_group_comment_layout;
    private Button btn_parent_send;
    private EditText parent_warn_comment_edit;
    private NoScrollGridView img_gridview;
    private NoScrollListView news_group_comment_list;
    private RoundImageView item_head;
    private Context context;
    private UserInfo user;
    private ClassCricleInfo homeworkData;
    private static long lastClickTime;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case GET_CIRCLE_LIST_KEY:
                    getCircleListJson(msg);
                    break;
                case WORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                new ClassCircleRequest(context, handler).getCircleList("", "1", "1", "1",GET_CIRCLE_LIST_KEY);
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

                                new ClassCircleRequest(context, handler).getCircleList("", "1", "1", "1",GET_CIRCLE_LIST_KEY);
                            }else
                            {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                            Toast.makeText(TrendsDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            parent_warn_comment_edit.setText(null);
                            new ClassCircleRequest(context, handler).getCircleList("", "1", "1", "1",GET_CIRCLE_LIST_KEY);
                        } else {
                            Toast.makeText(TrendsDetailActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;

                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_trends_detail);
        context = this;
        initData();
        initview();
    }

    private void initData() {
        Intent intent = this.getIntent();
        homeworkData =(ClassCricleInfo)intent.getSerializableExtra("newsgroupdata");
        user = new UserInfo(context);
        user.readData(context);
    }
    private void initview() {
        item_head = (RoundImageView) findViewById(R.id.item_head);
        quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_parent_send = (Button) findViewById(R.id.btn_parent_send);
        parent_warn_comment_edit = (EditText) findViewById(R.id.parent_warn_comment_edit);
        img_gridview = (NoScrollGridView) findViewById(R.id.parent_warn_img_gridview);
        news_group_comment_layout = (RelativeLayout)findViewById(R.id.news_group_comment_layout);
        news_group_comment_list = (NoScrollListView) findViewById(R.id.news_group_comment_list);
        comment_view = (LinearLayout) findViewById(R.id.edit_and_send);
        homework_content = (TextView) findViewById(R.id.myhomework_content);
        teacher_name = (TextView) findViewById(R.id.item_title);
        homework_time = (TextView) findViewById(R.id.item_time);
        homework_praise = (ImageView) findViewById(R.id.homework_praise);
        homework_img = (ImageView) findViewById(R.id.homework_img);
        homework_item_praise_names = (TextView) findViewById(R.id.homework_item_praise_names);
        linearLayout_homework_item_praise = (LinearLayout) findViewById(R.id.linearLayout_homework_item_praise);

        
        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+homeworkData.getMemberImg(),item_head);
        homework_time.setText(getTime(homeworkData.getAddtime()));
        homework_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        homework_content.setText(homeworkData.getMatter());
        homework_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        teacher_name.setText("来自" + homeworkData.getMemberName());
        teacher_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_parent_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent_warn_comment_edit.getText().length() > 0) {
                    //获取到需要上传的参数

                    new NewsRequest(context, handler).send_remark(homeworkData.getId(), String.valueOf(parent_warn_comment_edit.getText()), "1");

                } else {

                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //判断图片并显示（一张图片显示imageview，多于一张显示gridview）
        Log.d("img_gridview.count", String.valueOf(homeworkData.getWorkImgs().size()));
        if (homeworkData.getWorkImgs().size()>1){
            homework_img.setVisibility(View.GONE);
            img_gridview.setVisibility(View.VISIBLE);
            MyGridAdapter parWarnImgGridAdapter = new MyGridAdapter( homeworkData.getWorkImgs(),context);
            img_gridview.setAdapter(parWarnImgGridAdapter);
            img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", homeworkData.getWorkImgs());
                    intent.putExtra("type","4");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (homeworkData.getWorkImgs().size()==1){
            img_gridview.setVisibility(View.GONE);
            homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            DisplayImageOptions displayImage = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square).cacheInMemory(true).cacheOnDisc(true).build();
            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + homeworkData.getWorkImgs().get(0), homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/" + homeworkData.getWorkImgs().get(0));
            homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", homeworkData.getWorkImgs());
                    intent.putExtra("type","4");
                    context.startActivity(intent);
                }
            });
        }else {
            homework_img.setVisibility(View.GONE);
            img_gridview.setVisibility(View.GONE);

        }

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
                if (fastClick())return;
                if (isPraise(homeworkData.getWorkPraise())) {
                    LogUtils.d("FindFragment", "delPraise");
                    delPraise(homeworkData.getId());
                } else {
                    LogUtils.d("FindFragment","workPraise");
                    workPraise(homeworkData.getId());
                }
            }
        });


        //显示评论
        if (homeworkData.getComment().size()>=1){
            //显示评论布局
            news_group_comment_layout.setVisibility(View.VISIBLE);

            //加载数据
            news_group_comment_list.setAdapter(new HomeworkRemarkAdapter(homeworkData.getComment(), context));

            //发送评论功能



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
//                holder.img_gridview.setVisibility(View.GONE);

        }
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
    private boolean isPraise(ArrayList<LikeBean> praises) {
        UserInfo user = new UserInfo();
        user.readData(context);
        for (int i = 0; i < praises.size(); i++) {
            if (praises.get(i).getUserid().equals(user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    public long getTodayZero() {
        Date date = new Date();
        long l = 24*60*60*1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime()%l) - 8* 60 * 60 *1000);
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
    // 点赞
    private void workPraise(String workBindId) {
        new ClassCircleRequest(context, handler).Praise(workBindId, WORK_PRAISE_KEY);
    }

    // 取消点赞
    private void delPraise(String workBindId) {
        new ClassCircleRequest(context, handler).DelPraise(workBindId, DEL_WORK_PRAISE_KEY);
    }
    /**
     * 返回 普通资讯 JSON
     *
     * @param msg
     */
    @SuppressWarnings({ "static-access" })
    private void getCircleListJson(Message msg) {
        if (msg.obj != null) {
            JSONObject obj = (JSONObject) msg.obj;
            try {
                String state = obj.getString("status");
                if (state.equals(CommunalInterfaces._STATE)) {

                    JSONArray items = obj.getJSONArray("data");

                    JSONObject itemObject;
                    for (int i = 0; i < items.length(); i++) {
                        itemObject = (JSONObject) items.get(i);
                        if (homeworkData.getId().equals(itemObject.getString("mid"))){

                            homeworkData.setId(itemObject.getString("mid"));
                            homeworkData.setMatter(itemObject.getString("content"));
                            String workPraise = itemObject.getString("like");

                            homeworkData.setMemberName(itemObject.getString("name"));
                            homeworkData.setMemberImg(itemObject.getString("photo"));
                            homeworkData.setAddtime(itemObject.getString("write_time"));

                            String jsonImg = itemObject.getString("pic");
                            JSONArray imgList = new JSONArray(jsonImg);
                            ArrayList<String> imgs = new ArrayList<String>();
                            for (int k = 0; k < imgList.length(); k++) {
                                JSONObject imgobject = (JSONObject) imgList.get(k);
                                imgs.add(imgobject.getString("pictureurl"));
                            }
                            homeworkData.setWorkImgs(imgs);
                            if (workPraise != null && !workPraise.equals("null")) {
                                JSONArray jsonWorkPraiseArray = new JSONArray(workPraise);
                                ArrayList<LikeBean> workPraises = new ArrayList<>();
                                for (int k = 0; k < jsonWorkPraiseArray.length(); k++) {
                                    JSONObject jsonPraise = jsonWorkPraiseArray.getJSONObject(k);
                                    LikeBean praise = new LikeBean();
                                    praise.setUserid(jsonPraise.getString("userid"));
                                    praise.setName(jsonPraise.getString("name"));
                                    workPraises.add(praise);
                                }
                                homeworkData.setWorkPraise(workPraises);
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
                            }
                            initview();
                        }
                    }

                }
            } catch (JSONException e) {
                LogUtils.d("weixiaotong", "JSONException" + e.getMessage());
                e.printStackTrace();
            }
        } else {

            LogUtils.d("weixiaotong", "listerror");
            LogUtils.d("weixiaotong", String.valueOf(msg.obj));
        }
    }
}
