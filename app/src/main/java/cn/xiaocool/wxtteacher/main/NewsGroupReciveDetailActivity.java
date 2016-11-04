package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ImgGridAdapter;
import cn.xiaocool.wxtteacher.bean.NewsGroupRecive;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.JsonParser;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.utils.VolleyUtil;

public class NewsGroupReciveDetailActivity extends Activity {

    private TextView homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text;
    private ImageView homework_praise,homework_img,homework_discuss,quit;
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
    private NewsGroupRecive.DataBean homeworkData;
    private RoundImageView item_head;
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
        homeworkData =(NewsGroupRecive.DataBean)intent.getSerializableExtra("newsgroupdata");
        user = new UserInfo(context);
        user.readData(context);
        //读取信息
        readMessage();
    }

    private void readMessage() {
        String url = "http://wxt.xiaocool.net/index.php?g=Apps&m=Message&a=read_message";
        url = url +"&message_id="+homeworkData.getMessage_id()+"&receiver_user_id="+user.getUserId();
        VolleyUtil.VolleyGetRequest(context, url, new VolleyUtil.VolleyJsonCallback() {
            @Override
            public void onSuccess(String result) {
                if (JsonParser.JSONparser(context,result)){
                    ToastUtils.ToastShort(context,"读取信息成功");
                }else {
                    Log.e("readMessage",result);
                }
            }

            @Override
            public void onError() {
                Log.e("readMessage","读取信息error");
            }
        });
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

        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+homeworkData.getSend_message().getPhoto(),item_head,displayImage);
       homework_content.setText(homeworkData.getSend_message().getMessage_content());
        teacher_name.setText(homeworkData.getSend_message().getSend_user_name());


        final ArrayList<NewsGroupRecive.DataBean.ReceiverBean> notReads = new ArrayList<>();
        final ArrayList<NewsGroupRecive.DataBean.ReceiverBean> alreadyReads = new ArrayList<>();
        for (int i=0;i<homeworkData.getReceiver().size();i++){
            if (homeworkData.getReceiver().get(i).getRead_time()==null||homeworkData.getReceiver().get(i).getRead_time().equals("null")){
                notReads.add(homeworkData.getReceiver().get(i));
            }else {
                alreadyReads.add(homeworkData.getReceiver().get(i));
            }
        }
        alread_text.setText("总发" + homeworkData.getReceiver().size()+" 已读"+alreadyReads.size()+" 未读"+notReads.size());
        alread_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(context, ReadAndNoreadActivity.class);
                intent.putExtra("type", "recive");
                Bundle bundle=new Bundle();
                bundle.putSerializable("notReads",(Serializable)notReads);//序列化
                bundle.putSerializable("alreadyReads", (Serializable)alreadyReads);
                intent.putExtras(bundle);//发送数据
//                intent.putExtras("notReads",(Serializable)notReads);
                context.startActivity(intent);//启动intent
            }
        });


        Date date = new Date();
        date.setTime(Long.parseLong(homeworkData.getSend_message().getMessage_time()) * 1000);
        homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));



        if (homeworkData.getPicture().size()>1){
            homework_img.setVisibility(View.GONE);
           parent_warn_img_gridview.setVisibility(View.VISIBLE);

            final ArrayList<String> pics = new ArrayList<>();
            for (int k=0;k<homeworkData.getPicture().size();k++){
                pics.add(homeworkData.getPicture().get(k).getPicture_url());
            }
            ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter(pics,context);
            parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
            parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs",pics);
                    intent.putExtra("type","4");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (homeworkData.getPicture().size()==1){
          parent_warn_img_gridview.setVisibility(View.GONE);
            homework_img.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            final ArrayList<String> pics = new ArrayList<>();
            for (int k=0;k<homeworkData.getPicture().size();k++){
                pics.add(homeworkData.getPicture().get(k).getPicture_url());
            }
            imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkData.getPicture().get(0).getPicture_url(), homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/" +homeworkData.getPicture().get(0));
            homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", pics);
                    intent.putExtra("type","4");
                    context.startActivity(intent);
                }
            });
        }else {
            parent_warn_img_gridview.setVisibility(View.GONE);
            homework_img.setVisibility(View.GONE);

        }
    }

}
