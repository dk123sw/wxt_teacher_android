package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

/**
 * Created by Administrator on 2016/5/8.
 */
public class AnnouncementActivity extends Activity{
    private RoundImageView ann_avatar;
    private TextView ann_name;
    private TextView ann_time;
    private TextView ann_title;
    private TextView ann_content;
    private GridView ann_gv_pic;
    private ImageView ann_pic;
    private TextView announcement_readed;
    private TextView announcement_unread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_announcement);
        initView();
    }

    private void initView() {
        ann_avatar = (RoundImageView) findViewById(R.id.ann_avatar);
        ann_name = (TextView) findViewById(R.id.ann_name);
        ann_time = (TextView) findViewById(R.id.ann_time);
        ann_title = (TextView) findViewById(R.id.ann_title);
        ann_content = (TextView) findViewById(R.id.ann_content);
        ann_gv_pic = (GridView) findViewById(R.id.ann_gv_pic);
        ann_pic = (ImageView) findViewById(R.id.ann_pic);
        announcement_readed = (TextView) findViewById(R.id.announcement_readed);
        announcement_unread = (TextView) findViewById(R.id.announcement_unread);
//        intent.putExtra("ann_title",annTitle);
//        intent.putExtra("ann_id",annTitle);
//        intent.putExtra("ann_userId",annTitle);
//        intent.putExtra("ann_content",annTitle);
//        intent.putExtra("ann_type",annTitle);
//        intent.putExtra("ann_create_time",annTitle);
//        intent.putExtra("ann_username",annTitle);
//        intent.putExtra("ann_avatar",annTitle);
//        intent.putExtra("ann_read_count",annTitle);
//        intent.putExtra("ann_all_reader",annTitle);
//        intent.putExtra("ann_read_tag",annTitle);
//        intent.putExtra("pic_id",annTitle);
//        intent.putExtra("pic_url",annTitle);
//        intent.putExtra("pic_create_time",annTitle);
//        intent.putExtra("title",annTitle);
    }
}
