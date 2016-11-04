package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.HomeworkListAdapter;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class SpaceClickHomeworkActivity extends Activity implements View.OnClickListener{
    private PullToRefreshListView lv_homework;
    private String data = null;
    private LinearLayout commentView;
    private ListView lv;
    private List<Homework.HomeworkData> homeworkDataList;
    private HomeworkListAdapter homeworkListAdapter;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private String TAG="SpaceClickHomeworkActivity";
    private RelativeLayout btn_exit,btn_add;
    private ArrayList< ArrayList<LikeBean>> likeBeanList = new ArrayList<>();
//    private FragmentActivity mContext;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.SEND_PARENT_REMARK:
                    Log.d("是否成功", "======");

                    if (msg.obj !=null){
                        JSONObject obj2 = (JSONObject) msg.obj;
                        String state = obj2.optString("status");
                        LogUtils.e("HomeworkCommentActivity", obj2.optString("data"));
                        Log.d("是否成功", state);
                        if (state.equals(CommunalInterfaces._STATE)){
                            data = obj2.optString("data");
                            Toast.makeText(SpaceClickHomeworkActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            getAllInformation();
                        } else {
                            Toast.makeText(SpaceClickHomeworkActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                    break;

                case HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        LogUtils.i(TAG, "点赞" + msg.obj);
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            ToastUtils.ToastShort(SpaceClickHomeworkActivity.this, result);
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
                        LogUtils.i(TAG, "取消赞" + msg.obj);
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                ToastUtils.ToastShort(SpaceClickHomeworkActivity.this, "已取消");
                                getAllInformation();
                            }else
                            {
                                ToastUtils.ToastShort(SpaceClickHomeworkActivity.this, result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CommunalInterfaces.HOMEWORK:
                    JSONObject obj = (JSONObject) msg.obj;
                    ProgressViewUtil.dismiss();
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
                        JSONArray hwArray = obj.optJSONArray("data");
                        homeworkDataList.clear();
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);
                            Homework.HomeworkData homeworkData = new Homework.HomeworkData();
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
                            homeworkData.setSubject(itemObject.optString("subject"));
                            //点赞模型代替已读未读
                            JSONArray likeArray = itemObject.optJSONArray("receiverlist");
                            if (likeArray != null) {
                                ArrayList<LikeBean> likeBeanList = new ArrayList<>();
                                for (int j = 0; j < likeArray.length(); j++) {
                                    JSONObject reciveObject = likeArray.optJSONObject(j);
                                    LikeBean reciverlistInfo = new LikeBean();
                                    reciverlistInfo.setUserid(reciveObject.optString("receiverid"));
                                    reciverlistInfo.setTime(reciveObject.optString("read_time"));
                                    if (reciveObject.optJSONArray("receiver_info").length()>0){
                                        reciverlistInfo.setName(reciveObject.optJSONArray("receiver_info").optJSONObject(0).optString("name"));
                                        reciverlistInfo.setAvatar(reciveObject.optJSONArray("receiver_info").optJSONObject(0).optString("photo"));
                                        reciverlistInfo.setPhone(reciveObject.optJSONArray("receiver_info").optJSONObject(0).optString("phone"));

                                    }
                                    likeBeanList.add(reciverlistInfo);
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
                            }

                            JSONArray picsArray = itemObject.optJSONArray("pic");
                            if (picsArray.length()>0){
                                ArrayList<String> pics = new ArrayList<>();
                                for (int j =0;j<picsArray.length();j++){
                                    pics.add(picsArray.optJSONObject(j).optString("picture_url"));
                                }
                                homeworkData.setPics(pics);
                            }

                            homeworkData.setUsername(itemObject.optJSONObject("teacher_info").optString("name"));
                            homeworkData.setPhoto(itemObject.optJSONObject("teacher_info").optString("photo"));

                            homeworkDataList.add(homeworkData);

                        }

                        saveFirstMessageInSp();
                        if(homeworkListAdapter!=null){
                            homeworkListAdapter.notifyDataSetChanged();
                        }else {
                            homeworkListAdapter = new HomeworkListAdapter(homeworkDataList,SpaceClickHomeworkActivity.this,handler,commentView);
                            lv.setAdapter(homeworkListAdapter);
                        }

                        if (homeworkDataList.size()<1){
                            lv.setBackground(getResources().getDrawable(R.drawable.no_content));
                        }

                    }
                    break;

            }

        }
    };
    private void saveFirstMessageInSp() {
        if (homeworkDataList.size()>0){
            SPUtils.put(this, "homeWork", homeworkDataList.get(0).getTitle());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_click_homework);
        ProgressViewUtil.show(this);
        initView();
    }

    private void initView() {
        commentView= (LinearLayout) findViewById(R.id.edit_and_send);
        homeworkDataList = new ArrayList<>();
        lv_homework = (PullToRefreshListView) findViewById(R.id.lv_homework);
        lv = lv_homework.getRefreshableView();
        lv.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        btn_add = (RelativeLayout) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_exit = (RelativeLayout) findViewById(R.id.up_jiantou);
        btn_exit.setOnClickListener(this);
        //设置下拉上拉监听
        lv_homework.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(SpaceClickHomeworkActivity.this) == true) {
                    getAllInformation();

                } else {
                   ToastUtils.ToastShort(SpaceClickHomeworkActivity.this,"暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv_homework.onPullDownRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                /**
                 * 过1秒后 结束向上加载
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv_homework.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(SpaceClickHomeworkActivity.this,AddHomeworkActivity.class);
                startActivity(intent);
                break;
            case R.id.up_jiantou:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new NewsRequest(this,handler).homework();

    }

    /**
     * 获取家庭作业
     * */

    private void getAllInformation() {

        new NewsRequest(this,handler).homework();

    }
}

