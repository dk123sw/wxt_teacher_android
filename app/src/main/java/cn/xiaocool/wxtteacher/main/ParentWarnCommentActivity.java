package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.HomeworkRemarkAdapter;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.LogUtils;

public class ParentWarnCommentActivity extends Activity {


    private String data = null;
    private String id;
    private String type;
    private String refid;
    private ListView homework_remarkList;
    private TextView title_name;
    ArrayList<Comments> commentsArrayList;
    HomeworkRemarkAdapter adapter;
    private EditText parent_warn_comment_edit;
    private Button btn_send;
    ImageView btn_exit;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_COMMENTS:
                    JSONObject obj = (JSONObject) msg.obj;

                    String status = obj.optString("status");
                    if (status.equals(CommunalInterfaces._STATE)) {
                        JSONArray commentArray = obj.optJSONArray("data");
                        commentsArrayList.clear();
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
                        }
                        if (adapter!=null){
                            adapter.notifyDataSetChanged();
                        }else {
                            adapter = new HomeworkRemarkAdapter(commentsArrayList,ParentWarnCommentActivity.this);
                            homework_remarkList.setAdapter(adapter);
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
                            data = obj2.optString("data");
                            Toast.makeText(ParentWarnCommentActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            parent_warn_comment_edit.setText(null);
                            new NewsRequest(ParentWarnCommentActivity.this,handler).get_comments(id, refid, type);
                        } else {
                            Toast.makeText(ParentWarnCommentActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_parent_warn_comment);

        initView();
    }

    private void initView() {

        title_name = (TextView) findViewById(R.id.title_name);
        parent_warn_comment_edit = (EditText) findViewById(R.id.parent_warn_comment_edit);
        btn_send = (Button) findViewById(R.id.btn_parent_send);
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        homework_remarkList = (ListView) findViewById(R.id.parent_warn_commentlist);
        commentsArrayList = new ArrayList<Comments>();

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        refid = getIntent().getStringExtra("refid");
        new NewsRequest(ParentWarnCommentActivity.this,handler).get_comments(id, refid, type);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent_warn_comment_edit.getText().length() > 0) {
                    //获取到需要上传的参数
                    Log.i("===============homwork", "22222--" + id);
                    Log.i("===============homwork", "11111--" + type);
                    new NewsRequest(ParentWarnCommentActivity.this, handler).send_remark(refid, String.valueOf(parent_warn_comment_edit.getText()), type);

                } else {

                    Toast.makeText(ParentWarnCommentActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

//    private void requestFromType(String type) {
//        switch (type){
//            //动态
//            case "1":
//                break;
//            //作业
//            case "2":
//                break;
//            //公告
//            case "3":
//                new NewsRequest(ParentWarnCommentActivity.this, handler).announcement();
//                break;
//           // 家长叮嘱评论
//            case "4":
//                new NewsRequest(ParentWarnCommentActivity.this, handler).parentWarn();
//                break;
//            //班级活动评论
//            case "5":
//                new NewsRequest(ParentWarnCommentActivity.this, handler).classEvents();
//                break;
//        }
//
//    }
}
