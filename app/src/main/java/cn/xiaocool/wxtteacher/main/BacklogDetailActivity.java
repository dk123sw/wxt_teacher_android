package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ImgGridAdapter;
import cn.xiaocool.wxtteacher.adapter.ReciverBaclogAdapter;
import cn.xiaocool.wxtteacher.bean.Backlog;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;

public class BacklogDetailActivity extends Activity implements View.OnClickListener{
    private Backlog.BacklogData backlogData;
    private Context context;
    private TextView tv_username,tv_usertype,tv_time,tv_title,tv_content,tv_reciver,tv_btn_next;
    private ImageView iv_head,homework_img;
    private RelativeLayout rl_back;
    private GridView parent_warn_img_gridview;
    private NoScrollListView reciver_list;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_backlog_detail);
        context = this;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        initview();
        initData();
    }

    private void initview() {
        homework_img = (ImageView) findViewById(R.id.homework_img);
        rl_back = (RelativeLayout) findViewById(R.id.up_jiantou);
        rl_back.setOnClickListener(this);
        tv_username = (TextView) findViewById(R.id.item_title);
        tv_usertype = (TextView) findViewById(R.id.item_content);
        tv_title = (TextView) findViewById(R.id.myhomework_title);
        tv_content = (TextView) findViewById(R.id.myhomework_content);
        tv_time = (TextView) findViewById(R.id.item_time);
        iv_head = (ImageView) findViewById(R.id.item_head);
        parent_warn_img_gridview = (GridView) findViewById(R.id.parent_warn_img_gridview);
        tv_btn_next = (TextView) findViewById(R.id.btn_next);
        tv_btn_next.setOnClickListener(this);
        reciver_list = (NoScrollListView) findViewById(R.id.reciver_list);
    }

    private void initData() {
        Intent intent = this.getIntent();
        backlogData =(Backlog.BacklogData)intent.getSerializableExtra("backlogdata");
        if (backlogData.getUsername()!=null){
            tv_username.setText(backlogData.getUsername());
        }


        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+backlogData.getAvatar(),iv_head,displayImage);
        Date date = new Date();
        date.setTime(Long.parseLong(backlogData.getCreate_time()) * 1000);
        tv_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
        tv_title.setText(backlogData.getTitle());
        tv_content.setText(backlogData.getContent());
        if (backlogData.getPic().size()>1){
            homework_img.setVisibility(View.GONE);
            parent_warn_img_gridview.setVisibility(View.VISIBLE);
            final ArrayList<String> imgs = new ArrayList<>();
            for (int i=0;i<backlogData.getPic().size();i++){
                imgs.add(backlogData.getPic().get(i).getPictureurl());
            }
            ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter(imgs,context);
            parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
            parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", imgs);
                    intent.putExtra("type","4");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });

        }else if (backlogData.getPic().size()==1&&!backlogData.getPic().get(0).getPictureurl().equals("")&&!backlogData.getPic().get(0).getPictureurl().equals("null")){
            homework_img.setVisibility(View.VISIBLE);
            parent_warn_img_gridview.setVisibility(View.GONE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + backlogData.getPic().get(0).getPictureurl(), homework_img, displayImage);
            homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(backlogData.getPic().get(0).getPictureurl());
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", list);
                    intent.putExtra("type", "4");
                    context.startActivity(intent);
                }
            });
        }else {
            parent_warn_img_gridview.setVisibility(View.GONE);
            homework_img.setVisibility(View.GONE);
        }

        reciver_list.setAdapter(new ReciverBaclogAdapter(backlogData.getReciverlist(),this));


        if (backlogData.getType().equals("1")||backlogData.getType().equals("send")||backlogData.getUserid().equals(new UserInfo(context).getUserId())){
            tv_btn_next.setVisibility(View.GONE);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.btn_next:
                Intent intent = new Intent();
                intent.setClass(BacklogDetailActivity.this, BacklogNextDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("backlog", backlogData);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
