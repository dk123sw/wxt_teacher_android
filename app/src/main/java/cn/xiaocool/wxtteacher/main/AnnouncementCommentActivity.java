package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.AnnouncementCommentAdapter;
import cn.xiaocool.wxtteacher.bean.Announcement;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;

/**
 * Created by Administrator on 2016/5/18.
 */
public class AnnouncementCommentActivity extends Activity {
    private ImageView btn_exit;
    private ListView announcement_commentList;
    private List<Announcement.AnnouncementData> announcementDataList;
    private ArrayList<Comments> commentBeanList;
    private AnnouncementCommentAdapter announcementCommentAdapter;
    private int annInt;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ANNOUNCEMENT:
                    JSONObject obj = (JSONObject) msg.obj;

                    String status = obj.optString("status");
                    if (status.equals(CommunalInterfaces._STATE)) {
                        JSONArray annArray = obj.optJSONArray("data");
                        announcementDataList.clear();
                        JSONObject itemObject;
                            itemObject = annArray.optJSONObject(annInt);
                            Announcement.AnnouncementData announcementDate = new Announcement.AnnouncementData();
                            JSONArray commentArray = itemObject.optJSONArray("comment");
                            if (commentArray != null) {
                                for (int i = 0; i <commentArray.length(); i++) {
                                JSONObject commentObject = commentArray.optJSONObject(i);
                                 Comments commentBean = new Comments();
                                commentBean.setUserid(commentObject.optString("userid"));
                                commentBean.setAvatar(commentObject.optString("avatar"));
                                commentBean.setName(commentObject.optString("name"));
                                commentBean.setContent(commentObject.optString("content"));
                                commentBean.setComment_time(commentObject.optString("comment_time"));
                                commentBean.setPhoto(commentObject.optString("photo"));
                                commentBeanList.add(commentBean);
                                }
                                announcementDate.setComment(commentBeanList);
                            }
                        announcementCommentAdapter = new AnnouncementCommentAdapter(commentBeanList,AnnouncementCommentActivity.this);
                        announcement_commentList.setAdapter(announcementCommentAdapter);
                    }
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_click_announcement_comment);
        initView();
    }

    private void initView() {
        announcementDataList = new ArrayList<>();
        commentBeanList = new ArrayList<>();
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        announcement_commentList = (ListView) findViewById(R.id.announcement_commentList);
        annInt = getIntent().getIntExtra("announcementData", -1);
//        Log.i("announcementDatacount",getIntent().getIntExtra("announcementData",0)+"");
        new NewsRequest(AnnouncementCommentActivity.this, handler).announcement();

    }
}
