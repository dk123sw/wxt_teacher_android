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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ImgGridAdapter;
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class NewsGroupDetailActivity extends Activity {

    private TextView homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text;
    private ImageView homework_praise,homework_img,homework_discuss,quit;

    private RoundImageView item_head;

    private LinearLayout linearLayout_homework_item_praise,edit_and_send;
    private RelativeLayout news_group_comment_layout;
    private NoScrollListView news_group_comment_list;
    private NoScrollGridView parent_warn_img_gridview;
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
                            ToastUtils.ToastShort(NewsGroupDetailActivity.this, result);
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
                                ToastUtils.ToastShort(NewsGroupDetailActivity.this, "已取消");
                                getAllInformation();
                            }else
                            {
                                ToastUtils.ToastShort(NewsGroupDetailActivity.this, result);
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
                                homeworkData.setContent(itemObject.optString("message_content"));
                                homeworkData.setCreate_time(itemObject.optString("message_time"));
                                homeworkData.setUsername(itemObject.optString("send_user_name"));
//                            homeworkData.setReadcount(itemObject.optInt("receiver_num"));
                                homeworkData.setAllreader(itemObject.optInt("receiver_num"));
//                            homeworkData.setReadtag(itemObject.optInt("readtag"));
//                            homeworkData.setPhoto(itemObject.optString("photo"));

                                JSONArray picArray = itemObject.optJSONArray("picture");
                                Log.e("picarray", itemObject.optJSONArray("picture").toString());
                                Log.e("picarray", String.valueOf(itemObject.optJSONArray("picture").length()));
                                if (picArray.length()>0) {
                                    ArrayList<String> pics = new ArrayList<>();
                                    for (int k=0;k<picArray.length();k++){
                                        JSONObject pic = picArray.optJSONObject(k);
                                        pics.add(pic.optString("picture_url"));
                                    }

                                    homeworkData.setPics(pics);
                                }

                                //点赞模型代替已读人的模型
                                JSONArray likeArray = itemObject.optJSONArray("receiver");
                                if (likeArray != null) {
                                    ArrayList<LikeBean> likeBeanList = new ArrayList<>();
                                    for (int j = 0; j < likeArray.length(); j++) {
                                        JSONObject likeObject = likeArray.optJSONObject(j);
                                        LikeBean likeBean = new LikeBean();
                                        likeBean.setUserid(likeObject.optString("receiver_user_id"));
                                        likeBean.setName(likeObject.optString("receiver_user_name"));
//                                    likeBean.setAvatar(likeObject.optString("avatar"));
                                        likeBean.setTime(likeObject.optString("read_time"));
                                        likeBeanList.add(likeBean);
                                    }
                                    homeworkData.setWorkPraise(likeBeanList);
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
                            Toast.makeText(NewsGroupDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            edit_and_send.setVisibility(View.GONE);
                            EditText editText =(EditText)edit_and_send.findViewById(R.id.parent_warn_comment_edit);
                            editText.setText(null);
                            getAllInformation();
                        } else {
                            Toast.makeText(NewsGroupDetailActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_news_group_detail);
        context = this;
        initData();
        initview();

    }


    private void initData() {
        Intent intent = this.getIntent();
        homeworkData =(Homework.HomeworkData)intent.getSerializableExtra("newsgroupdata");
        user = new UserInfo(context);
        user.readData(context);
    }

    private void initview() {

        //初始化组件
        item_head = (RoundImageView) findViewById(R.id.item_head);
        parent_warn_img_gridview = (NoScrollGridView) findViewById(R.id.parent_warn_img_gridview);
        quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit_and_send = (LinearLayout) findViewById(R.id.edit_and_send);
        bg_divider =  findViewById(R.id.bg_divider);
        news_group_comment_layout = (RelativeLayout)findViewById(R.id.news_group_comment_layout);
        news_group_comment_list = (NoScrollListView) findViewById(R.id.news_group_comment_list);
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

       homework_content.setText(homeworkData.getContent());
        teacher_name.setText(homeworkData.getUsername());

        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+homeworkData.getPhoto(),item_head,displayImage);

        final ArrayList<LikeBean> notReads = new ArrayList<>();
        final ArrayList<LikeBean> alreadyReads = new ArrayList<>();
        for (int i=0;i<homeworkData.getWorkPraise().size();i++){
            if (homeworkData.getWorkPraise().get(i).getTime().equals("null")){
                notReads.add(homeworkData.getWorkPraise().get(i));
            }else {
                alreadyReads.add(homeworkData.getWorkPraise().get(i));
            }
        }
        alread_text.setText("总发" + homeworkData.getAllreader()+" 已读"+alreadyReads.size()+" 未读"+notReads.size());
        alread_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(context, ReadAndNoreadActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("notReads",(Serializable)notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable)alreadyReads);
                intent.putExtras(bundle);//发送数据
//                intent.putExtras("notReads",(Serializable)notReads);
                context.startActivity(intent);//启动intent
            }
        });


        Date date = new Date();
        date.setTime(Long.parseLong(homeworkData.getCreate_time()) * 1000);
        homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));



        if (homeworkData.getPics().size()>1){
            homework_img.setVisibility(View.GONE);
           parent_warn_img_gridview.setVisibility(View.VISIBLE);
           ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter( homeworkData.getPics(),context);
            parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
            parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs",homeworkData.getPics());
                    intent.putExtra("type","4");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (homeworkData.getPics().size()==1&&!homeworkData.getPics().get(0).equals("null")&&!homeworkData.getPics().get(0).equals("")){
          parent_warn_img_gridview.setVisibility(View.GONE);
            homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkData.getPics().get(0), homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/" +homeworkData.getPics().get(0));
            homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", homeworkData.getPics());
                    intent.putExtra("type","4");
                    context.startActivity(intent);
                }
            });
        }else {
            parent_warn_img_gridview.setVisibility(View.GONE);
            homework_img.setVisibility(View.GONE);

        }




//        //判断点赞点赞与否
//        linearLayout_homework_item_praise.setVisibility(View.GONE);
//        if (homeworkData.getWorkPraise().size()>0){
//            linearLayout_homework_item_praise.setVisibility(View.VISIBLE);
//            String names = "";
//            for (int i=0;i<homeworkData.getWorkPraise().size();i++){
//                names = names+" "+homeworkData.getWorkPraise().get(i).getName();
//            }
//           homework_item_praise_names.setText(names);
//        }
//
//        //判断本人是否已经点赞
//        if (isPraise(homeworkData.getWorkPraise())) {
//            //点赞成功后图片变红
//            homework_praise.setSelected(true);
//        } else {
//            //取消点赞后
//            homework_praise.setSelected(false);
//        }
//
//        //点赞事件
//        homework_praise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPraise(homeworkData.getWorkPraise())) {
//                    LogUtils.d("FindFragment", "delPraise");
//                    delPraise(homeworkData.getId());
//                } else {
//                    LogUtils.d("FindFragment","workPraise");
//                    workPraise(homeworkData.getId());
//                }
//            }
//        });
//
//
//        //评论事件
//
//        homework_discuss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                edit_and_send.setVisibility(View.VISIBLE);
//                final EditText editText= (EditText) edit_and_send.findViewById(R.id.parent_warn_comment_edit);
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
//                final EditText edit = (EditText)edit_and_send.findViewById(R.id.parent_warn_comment_edit);
//                Button send = (Button) edit_and_send.findViewById(R.id.btn_parent_send);
//                send.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (edit.getText().length() > 0) {
//                            //获取到需要上传的参数
//
//                            new NewsRequest(context, handler).send_remark(homeworkData.getId(), String.valueOf(edit.getText()), "2");
//
//                        } else {
//
//                            Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//
//
//
//                /*Intent intent = new Intent(context,ParentWarnCommentActivity.class);
//                intent.putExtra("type","2");
//                intent.putExtra("refid", homeworkDataList.get(position).getId());
//                context.startActivity(intent);*/
//
//            }
//        });
//
//        //显示评论
//        if (homeworkData.getComment().size()>=1){
//            //显示评论布局
//            news_group_comment_layout.setVisibility(View.VISIBLE);
//            bg_divider.setVisibility(View.GONE);
//            //加载数据
//            news_group_comment_list.setAdapter(new HomeworkRemarkAdapter(homeworkData.getComment(),context));
//            //长按删除评论功能
//            news_group_comment_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
//            news_group_comment_layout.setVisibility(View.GONE);
//            bg_divider.setVisibility(View.VISIBLE);
//        }
//






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
     * 获取信息
     * */

    private void getAllInformation() {

        new NewsRequest(this, handler).getNewsGoupInfos();

    }
}
