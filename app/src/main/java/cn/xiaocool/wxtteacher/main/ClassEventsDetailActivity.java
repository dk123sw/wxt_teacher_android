package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ImgGridAdapter;
import cn.xiaocool.wxtteacher.bean.Classevents;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class ClassEventsDetailActivity extends Activity {
    private TextView homework_title, homework_content, teacher_name, homework_time, homework_item_praise_names, alread_text,
            start_text, finish_text, contact_people, contact_phone, start_attend_text, finish_attend_text,item_title;
    private ImageView homework_praise, homework_img, homework_discuss;
    private LinearLayout linearLayout_homework_item_praise;
    private RelativeLayout news_group_comment_layout, back, img_layout;
    private NoScrollListView news_group_comment_list;
    private NoScrollGridView parent_warn_img_gridview;
    private RoundImageView item_head;

    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private UserInfo user;
    private String type = "5";
    private Classevents.ClassEventData homeworkData;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {

                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            ToastUtils.ToastShort(ClassEventsDetailActivity.this, result);
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
                                ToastUtils.ToastShort(ClassEventsDetailActivity.this, "已取消");
                                getAllInformation();
                            } else {
                                ToastUtils.ToastShort(ClassEventsDetailActivity.this, result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case CommunalInterfaces.CLASS_EVENTS:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                        JSONArray hwArray = obj.optJSONArray("data");
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            if (homeworkData.getId().equals(itemObject.optString("id"))) {
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
                                homeworkData.setStarttime(itemObject.optString("starttime"));
                                homeworkData.setFinishtime(itemObject.optString("finishtime"));
                                homeworkData.setContactman(itemObject.optString("contactman"));
                                homeworkData.setContactphone(itemObject.optString("contactphone"));
                                homeworkData.setBegintime(itemObject.optString("begintime"));
                                homeworkData.setEndtime(itemObject.optString("endtime"));


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
                                if (commentArray.length() > 0) {
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

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homework_detail);
        context = this;
        init();
        initview();

    }

    private void init() {
        Intent intent = this.getIntent();
        homeworkData = (Classevents.ClassEventData) intent.getSerializableExtra("homework");
        user = new UserInfo(context);
        user.readData(context);

        //初始化组件
        start_text = (TextView) findViewById(R.id.start_text);
        finish_text = (TextView) findViewById(R.id.finish_text);
        contact_people = (TextView) findViewById(R.id.contact_people);
        contact_phone = (TextView) findViewById(R.id.contact_phone);
        start_attend_text = (TextView) findViewById(R.id.start_attend_text);
        finish_attend_text = (TextView) findViewById(R.id.finish_attend_text);
        item_title = (TextView) findViewById(R.id.item_title);
        item_head = (RoundImageView) findViewById(R.id.item_head);
        back = (RelativeLayout) findViewById(R.id.up_jiantou);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parent_warn_img_gridview = (NoScrollGridView) findViewById(R.id.parent_warn_img_gridview);
        news_group_comment_layout = (RelativeLayout) findViewById(R.id.news_group_comment_layout);
        news_group_comment_list = (NoScrollListView) findViewById(R.id.news_group_comment_list);
        homework_title = (TextView) findViewById(R.id.myhomework_title);
        homework_content = (TextView) findViewById(R.id.myhomework_content);
        teacher_name = (TextView) findViewById(R.id.item_title);
        homework_time = (TextView) findViewById(R.id.item_time);
        alread_text = (TextView) findViewById(R.id.alread_text);
        homework_praise = (ImageView) findViewById(R.id.homework_praise);
        homework_discuss = (ImageView) findViewById(R.id.homework_discuss);
        homework_img = (ImageView) findViewById(R.id.homework_img);
        homework_item_praise_names = (TextView) findViewById(R.id.homework_item_praise_names);
        linearLayout_homework_item_praise = (LinearLayout) findViewById(R.id.linearLayout_homework_item_praise);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    private void initview() {


        //填充数据
        homework_title.setText(homeworkData.getTitle());
        homework_content.setText(homeworkData.getContent());
        teacher_name.setText(homeworkData.getUsername());

        contact_people.setText(homeworkData.getContactman());
        contact_phone.setText(homeworkData.getContactphone());


        item_title.setText(homeworkData.getTeachername());
        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+homeworkData.getTeacheravtar(),item_head,displayImage);

        //计算已读未读和已报名
        final ArrayList<Classevents.ClassEventData.ReciverlistInfo> notReads = new ArrayList<>();
        final ArrayList<Classevents.ClassEventData.ReciverlistInfo> alreadyReads = new ArrayList<>();
        if (homeworkData.getReciverlist().size() > 0) {
            for (int i = 0; i < homeworkData.getReciverlist().size(); i++) {
                if (homeworkData.getReciverlist().get(i).getRead_time().equals("null")) {
                    notReads.add(homeworkData.getReciverlist().get(i));
                } else {
                    alreadyReads.add(homeworkData.getReciverlist().get(i));
                }
            }
        }

        alread_text.setText("已报名" + homeworkData.getIsApplyLists().size() + " 已读" + alreadyReads.size() + " 未读" + notReads.size());
        alread_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ClassEventReadAndNreadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("notReads", (Serializable) notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable) alreadyReads);
                bundle.putSerializable("applylist", homeworkData.getIsApplyLists());
                intent.putExtras(bundle);//发送数据
//                intent.putExtras("notReads",(Serializable)notReads);
                context.startActivity(intent);//启动intent
            }
        });


        Date date = new Date();


        date.setTime(Long.parseLong(homeworkData.getCreate_time()) * 1000);
        homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        date.setTime(Long.parseLong(homeworkData.getBegintime()) * 1000);
        start_text.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

        date.setTime(Long.parseLong(homeworkData.getEndtime()) * 1000);
        finish_text.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

        date.setTime(Long.parseLong(homeworkData.getStarttime()) * 1000);
        if (Long.parseLong(homeworkData.getStarttime()) * 1000==0){
            start_attend_text.setText("无");
        }else {
            start_attend_text.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }

        date.setTime(Long.parseLong(homeworkData.getFinishtime()) * 1000);
        if (Long.parseLong(homeworkData.getFinishtime()) * 1000==0){
            finish_attend_text.setText("无");
        }else {
            finish_attend_text.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }





        homework_img.setVisibility(View.GONE);
        if (homeworkData.getPics() != null) {
            if (homeworkData.getPics().size() > 1) {
                homework_img.setVisibility(View.GONE);
                parent_warn_img_gridview.setVisibility(View.VISIBLE);
                ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter(homeworkData.getPics(), context);
                parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
                parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                        // 图片浏览
                        Intent intent = new Intent();
                        intent.setClass(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", homeworkData.getPics());
                        intent.putExtra("type", "newsgroup");
                        intent.putExtra("position", a);
                        context.startActivity(intent);
                    }
                });


            } else if (homeworkData.getPics().size() == 1&&!homeworkData.getPics().get(0).equals("null")&&!homeworkData.getPics().get(0).equals("")) {

                homework_img.setVisibility(View.VISIBLE);
                parent_warn_img_gridview.setVisibility(View.GONE);
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkData.getPhoto(), homework_img, displayImage);
                Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + homeworkData.getPhoto());
                homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
            } else {
                homework_img.setVisibility(View.GONE);
                parent_warn_img_gridview.setVisibility(View.GONE);
            }

        } else {
            homework_img.setVisibility(View.GONE);
            parent_warn_img_gridview.setVisibility(View.GONE);
        }

    }

    /**
     * 获取家庭作业
     */

    private void getAllInformation() {

        new NewsRequest(this, handler).classEvents();

    }
}
