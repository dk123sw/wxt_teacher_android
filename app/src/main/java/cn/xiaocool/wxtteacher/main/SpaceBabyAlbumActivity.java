package cn.xiaocool.wxtteacher.main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.AlbumListAdapter;
import cn.xiaocool.wxtteacher.bean.ClassCricleInfo;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.ClassCircleRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class SpaceBabyAlbumActivity extends FragmentActivity implements View.OnClickListener {
    private PullToRefreshListView lv_homework;
    private String data = null;
    private LinearLayout commentView;
    private ListView lv;
    private ArrayList<ClassCricleInfo> CricleList;
    private AlbumListAdapter mAdapter;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private static final int GET_CIRCLE_LIST_KEY = 2;
    private String TAG = "SpaceBabyAlbumActivity";
    private RelativeLayout btn_exit;
    private int beginid=0;
    private ArrayList<ArrayList<LikeBean>> likeBeanList = new ArrayList<>();
    //    private FragmentActivity mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_PARENT_REMARK:
                    Log.d("是否成功", "======");
                    ProgressViewUtil.dismiss();
                    if (msg.obj != null) {
                        JSONObject obj2 = (JSONObject) msg.obj;
                        String state = obj2.optString("status");
                        LogUtils.e("HomeworkCommentActivity", obj2.optString("data"));
                        Log.d("是否成功", state);
                        if (state.equals(CommunalInterfaces._STATE)) {
                            data = obj2.optString("data");
                            Toast.makeText(SpaceBabyAlbumActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            getAllInformation(""+beginid);
                        } else {
                            Toast.makeText(SpaceBabyAlbumActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                    break;

                case HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        LogUtils.i(TAG, "点赞" + msg.obj);
                        ProgressViewUtil.dismiss();
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                getAllInformation(""+beginid);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DEL_HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        LogUtils.i(TAG, "取消赞" + msg.obj);
                        ProgressViewUtil.dismiss();
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                getAllInformation(""+beginid);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case GET_CIRCLE_LIST_KEY:
                    JSONObject obj = (JSONObject) msg.obj;
                    try {
                        ProgressViewUtil.dismiss();
                        String state = obj.getString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            CricleList.clear();
                            JSONArray items = obj.getJSONArray("data");

                            JSONObject itemObject;
                            for (int i = 0; i < items.length(); i++) {
                                itemObject = (JSONObject) items.get(i);
                                ClassCricleInfo cricle = new ClassCricleInfo();
                                cricle.setId(itemObject.getString("mid"));
                                cricle.setMatter(itemObject.getString("content"));
                                String workPraise = itemObject.getString("like");

                                cricle.setMemberName(itemObject.getString("name"));
                                cricle.setMemberImg(itemObject.getString("photo"));
                                cricle.setAddtime(itemObject.getString("write_time"));

                                String jsonImg = itemObject.getString("pic");
                                JSONArray imgList = new JSONArray(jsonImg);
                                ArrayList<String> imgs = new ArrayList<String>();
                                for (int k = 0; k < imgList.length(); k++) {
                                    JSONObject imgobject = (JSONObject) imgList.get(k);
                                    imgs.add(imgobject.getString("pictureurl"));
                                }
                                cricle.setWorkImgs(imgs);
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
                                    cricle.setWorkPraise(workPraises);
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
                                    cricle.setComment(commentList);
                                }
                                CricleList.add(cricle);
                            }

                            beginid = CricleList.size();
                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }else{
                                mAdapter = new AlbumListAdapter(CricleList,SpaceBabyAlbumActivity.this,handler,commentView);
                                lv.setAdapter(mAdapter);
                            }

                            if (CricleList.size()<1){
                                lv.setBackground(getResources().getDrawable(R.drawable.no_content));
                            }

                        }
                    } catch (JSONException e) {
                        LogUtils.d("weixiaotong", "JSONException" + e.getMessage());
                        e.printStackTrace();
                    }

                    break;

            }

        }
    };
    private Button add_announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_baby_album);
        ProgressViewUtil.show(this);
        initView();
    }

    private void initView() {
        CricleList = new ArrayList<>();
        commentView = (LinearLayout) findViewById(R.id.edit_and_send);
        lv_homework = (PullToRefreshListView) findViewById(R.id.lv_homework);
        lv_homework.setPullLoadEnabled(true);
//        lv_homework.setScrollLoadEnabled(false);
        lv = lv_homework.getRefreshableView();
        lv.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        add_announcement = (Button) findViewById(R.id.add_announcement);
        add_announcement.setOnClickListener(this);
        btn_exit = (RelativeLayout) findViewById(R.id.up_jiantou);
        btn_exit.setOnClickListener(this);
        //设置下拉上拉监听
        lv_homework.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(SpaceBabyAlbumActivity.this) == true) {
                    beginid = 0;
                    getAllInformation(""+beginid);

                } else {
                    ToastUtils.ToastShort(SpaceBabyAlbumActivity.this, "暂无网络");
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
                if (NetUtil.isConnnected(SpaceBabyAlbumActivity.this) == true) {
                    getAllInformation(""+beginid);

                } else {
                    ToastUtils.ToastShort(SpaceBabyAlbumActivity.this, "暂无网络");
                }
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
        switch (v.getId()) {
            case R.id.add_announcement:
                IntentUtils.getIntent(SpaceBabyAlbumActivity.this, AddAlbumActivity.class);
                break;
            case R.id.up_jiantou:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAllInformation(""+beginid);

    }

    /**
     * 获取家庭作业
     */

    private void getAllInformation(String beginid) {

        new ClassCircleRequest(this, handler).getCircleList(beginid, "1", "1", "2", GET_CIRCLE_LIST_KEY);

    }
}

