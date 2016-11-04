package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.HomeworkRemarkAdapter;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.LogUtils;

public class HomeworkCommentActivity extends Activity {

    private ImageView quit;
    ListView homework_remarkList;
    ArrayList<Comments> commentsArrayList;
    int position;
    HomeworkRemarkAdapter adapter;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.HOMEWORK:
                    JSONObject obj = (JSONObject) msg.obj;
                    LogUtils.e("HomeworkCommentActivity", obj.optString("data"));
                    String status = obj.optString("status");
                    if (status.equals(CommunalInterfaces._STATE)) {
                        JSONArray annArray = obj.optJSONArray("data");
                        commentsArrayList.clear();
                        JSONObject itemObject;
                        itemObject = annArray.optJSONObject(position);
                        Homework.HomeworkData announcementDate = new Homework.HomeworkData();
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
                                commentsArrayList.add(commentBean);
                            }
                            announcementDate.setComment(commentsArrayList);
                        }
                        adapter = new HomeworkRemarkAdapter(commentsArrayList,HomeworkCommentActivity.this);
                        homework_remarkList.setAdapter(adapter);
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
        commentsArrayList = new ArrayList<>();
        quit = (ImageView) findViewById(R.id.btn_exit);
        homework_remarkList = (ListView) findViewById(R.id.announcement_commentList);
        position = getIntent().getIntExtra("position", -1);
        Log.i("===============homwork","11111--"+position);
        new NewsRequest(HomeworkCommentActivity.this, handler).homework();

    }



}
