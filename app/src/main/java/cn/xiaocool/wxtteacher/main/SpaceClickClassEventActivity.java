package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassEventsAdapter;
import cn.xiaocool.wxtteacher.bean.Classevents;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/3/20.
 */
public class SpaceClickClassEventActivity extends Activity implements View.OnClickListener{

    private RelativeLayout btn_add,up_jiantou;
    private PullToRefreshListView class_events_listcontent;
    private ListView listView;
    private LinearLayout commentView;
    private ClassEventsAdapter classEventsAdapter;
    private List<Classevents.ClassEventData> classEventDataList;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private String TAG="SpaceClickClassEventActivity";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case HOMEWORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        LogUtils.i(TAG, "点赞" + msg.obj);
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            ToastUtils.ToastShort(SpaceClickClassEventActivity.this, result);
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
                                ToastUtils.ToastShort(SpaceClickClassEventActivity.this, "已取消");
                                getAllInformation();
                            }else
                            {
                                ToastUtils.ToastShort(SpaceClickClassEventActivity.this, result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CommunalInterfaces.CLASS_EVENTS:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        ProgressViewUtil.dismiss();
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)){
                                JSONArray dataArray = obj.getJSONArray("data");
                                classEventDataList.clear();
                                JSONObject itemObject;
                                for (int i = 0; i < dataArray.length(); i++) {
                                    itemObject = dataArray.getJSONObject(i);
                                    Classevents.ClassEventData classEventData = new Classevents.ClassEventData();
                                    classEventData.setId(itemObject.optString("id"));
                                    classEventData.setUserid(itemObject.optString("userid"));
                                    classEventData.setTitle(itemObject.optString("title"));
                                    classEventData.setContent(itemObject.optString("content"));
                                    classEventData.setCreate_time(itemObject.optString("create_time"));
                                    classEventData.setUsername(itemObject.optString("username"));
                                    classEventData.setReadcount(itemObject.optInt("readcount"));
                                    classEventData.setAllreader(itemObject.optInt("allreader"));
                                    classEventData.setReadtag(itemObject.optInt("readtag"));
                                    classEventData.setPhoto(itemObject.optString("photo"));
                                    classEventData.setStarttime(itemObject.optString("starttime"));
                                    classEventData.setFinishtime(itemObject.optString("finishtime"));
                                    classEventData.setContactman(itemObject.optString("contactman"));
                                    classEventData.setContactphone(itemObject.optString("contactphone"));
                                    classEventData.setBegintime(itemObject.optString("begintime"));
                                    classEventData.setEndtime(itemObject.optString("endtime"));
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
                                        classEventData.setWorkPraise(likeBeanList);
                                    }


                                    classEventData.setTeachername(itemObject.optJSONObject("teacher_info").optString("name"));
                                    classEventData.setTeacheravtar(itemObject.optJSONObject("teacher_info").optString("photo"));


                                    JSONArray commentArray = itemObject.optJSONArray("comment");
                                    if (commentArray.length()>0) {
                                        ArrayList<Comments> commentList = new ArrayList<>();
                                        for (int j = 0; j < commentArray.length(); j++) {
                                            JSONObject commentObject = commentArray.optJSONObject(j);
                                            Comments comments = new Comments();
                                            comments.setUserid(commentObject.optString("userid"));
                                            comments.setName(commentObject.optString("name"));
                                            comments.setAvatar(commentObject.optString("avatar"));
                                            comments.setContent(commentObject.optString("content"));
                                            comments.setComment_time(commentObject.optString("comment_time"));
                                            commentList.add(comments);
                                        }
                                        classEventData.setComment(commentList);
                                    }

                                    JSONArray picsArray = itemObject.optJSONArray("pic");
                                    if (picsArray.length()>0){
                                        ArrayList<String> pics = new ArrayList<>();
                                        for (int j =0;j<picsArray.length();j++){
                                            pics.add(picsArray.optJSONObject(j).optString("picture_url"));
                                        }
                                        classEventData.setPics(pics);
                                    }

                                    JSONArray reciveObjectArray = itemObject.optJSONArray("receiverlist");
                                    ArrayList<Classevents.ClassEventData.ReciverlistInfo> reciverlistInfos = new ArrayList<>();

                                    for (int j=0;j<reciveObjectArray.length();j++){
                                        JSONObject reciveObject = reciveObjectArray.optJSONObject(j);
                                        Classevents.ClassEventData.ReciverlistInfo reciverlistInfo = new Classevents.ClassEventData.ReciverlistInfo();
                                        reciverlistInfo.setId(reciveObject.optString("id"));
                                        reciverlistInfo.setActivity_id(reciveObject.optString("activity_id"));
                                        reciverlistInfo.setReceiverid(reciveObject.optString("receiverid"));
                                        reciverlistInfo.setRead_time(reciveObject.optString("read_time"));
                                        if (reciveObject.optJSONArray("receiver_info").length()>0){
                                            reciverlistInfo.setName(reciveObject.optJSONArray("receiver_info").optJSONObject(0).optString("name"));
                                            reciverlistInfo.setPhoto(reciveObject.optJSONArray("receiver_info").optJSONObject(0).optString("photo"));
                                            reciverlistInfo.setPhone(reciveObject.optJSONArray("receiver_info").optJSONObject(0).optString("phone"));

                                        }



                                        reciverlistInfos.add(reciverlistInfo);
                                    }

                                    classEventData.setReciverlist(reciverlistInfos);

                                    JSONArray isapplyObjectArray = itemObject.optJSONArray("applylist");
                                    ArrayList<Classevents.ClassEventData.IsApplyList> isapplylistInfos = new ArrayList<>();
                                    for (int j=0;j<isapplyObjectArray.length();j++){
                                        JSONObject isapplyObject = isapplyObjectArray.optJSONObject(j);
                                        Classevents.ClassEventData.IsApplyList isApply = new Classevents.ClassEventData.IsApplyList();
                                        isApply.setUserid(isapplyObject.optString("userid"));
                                        isApply.setAvatar(isapplyObject.optString("avatar"));
                                        isApply.setName(isapplyObject.optString("name"));
                                        isApply.setApplyid(isapplyObject.optString("applyid"));
                                        isApply.setFathername(isapplyObject.optString("fathername"));
                                        isApply.setContactphone(isapplyObject.optString("contactphone"));
                                        isApply.setAge(isapplyObject.optString("age"));
                                        isApply.setSex(isapplyObject.optString("sex"));
                                        isApply.setCreate_time(isapplyObject.optString("create_time"));

                                        isapplylistInfos.add(isApply);

                                    }
                                    classEventData.setIsApplyLists(isapplylistInfos);


                                    classEventDataList.add(classEventData);
                                    Log.i("Info1", classEventDataList.toString());
                                }

                                if(classEventsAdapter!=null){
                                    classEventsAdapter.notifyDataSetChanged();
                                }else {
                                    classEventsAdapter  = new ClassEventsAdapter(classEventDataList,SpaceClickClassEventActivity.this,handler,commentView);
                                    listView.setAdapter(classEventsAdapter);
                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
        setContentView(R.layout.space_class_event);
        ProgressViewUtil.show(this);
        initView();
    }
    private void initView() {
        classEventDataList = new ArrayList<>();
        commentView= (LinearLayout) findViewById(R.id.edit_and_send);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        btn_add = (RelativeLayout) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpaceClickClassEventActivity.this,AddClassEventActivity.class);
                intent.putExtra("type","5");
                SpaceClickClassEventActivity.this.startActivity(intent);
            }
        });
        class_events_listcontent = (PullToRefreshListView) findViewById(R.id.class_events_listcontent);
        listView = class_events_listcontent.getRefreshableView();
        listView.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        class_events_listcontent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(SpaceClickClassEventActivity.this) == true) {
                    getAllInformation();

                } else {
                    ToastUtils.ToastShort(SpaceClickClassEventActivity.this, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        class_events_listcontent.onPullDownRefreshComplete();
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
                        class_events_listcontent.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });

        /**
         * 监听listview滑动事件，隐藏评论输入框和软键盘
         * */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动状态
                        closeEdit();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
                        closeEdit();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.setClass(SpaceClickClassEventActivity.this, ClassEventsDetailActivity.class);
//                Log.e("position",position+"");
//                Classevents.ClassEventData homeworkData = classEventDataList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("homework", homeworkData);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllInformation();
    }

    /**
     * 获取班级活动
     * */
    private void getAllInformation() {
        new NewsRequest(this,handler).classEvents();

    }

    /**
     * 关闭软键盘，并隐藏输入框
     * */

    private void closeEdit() {
        //关闭键盘
        View v = getWindow().peekDecorView();
        if (v != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        //隐藏输入框
        commentView.setVisibility(View.GONE);
    }

    /**
     * 监听返回健
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //do something...
            closeEdit();
            return true;
        }

        return true;
    }
}
