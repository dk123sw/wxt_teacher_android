package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ParentWarnListAdapter;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.ParentWarn;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ReceiveParentWarnActivity extends Activity {
    private ImageView quit;
    private PullToRefreshListView parent_warnList;
    private ListView warnList;
    private List<ParentWarn.ParentWarnData> parentWarnDataList;
    private ParentWarnListAdapter parentWarnListAdapter;
    private LinearLayout edit_and_send;
    private RelativeLayout up_jiantou;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.PARENT_WARN:
                    if (msg.obj != null) {
                        ProgressViewUtil.dismiss();
                        JSONObject obj = (JSONObject) msg.obj;
                        String state = obj.optString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            JSONArray dataArray = obj.optJSONArray("data");
                            parentWarnDataList.clear();
                            JSONObject itemObject;
                            for (int i = 0; i < dataArray.length(); i++) {
                                itemObject = dataArray.optJSONObject(i);
                                ParentWarn.ParentWarnData parentWarnData = new ParentWarn.ParentWarnData();
                                parentWarnData.setId(itemObject.optString("id"));
                                parentWarnData.setUserid(itemObject.optString("userid"));
                                parentWarnData.setTitle(itemObject.optString("title"));
                                parentWarnData.setContent(itemObject.optString("content"));
                                parentWarnData.setCreate_time(itemObject.optString("create_time"));
                                parentWarnData.setUsername(itemObject.optString("username"));
                                parentWarnData.setReadcount(itemObject.optInt("readcount"));
                                parentWarnData.setAllreader(itemObject.optInt("allreader"));
                                parentWarnData.setReadtag(itemObject.optInt("readtag"));
                                parentWarnData.setPhoto(itemObject.optString("photo"));
                                parentWarnData.setStudentname(itemObject.optString("studentname"));
                                parentWarnData.setStudentavatar(itemObject.optString("studentavatar"));


                                JSONArray picArray = itemObject.optJSONArray("pic");
                                if (picArray != null) {
                                    ArrayList<String> pics = new ArrayList<>();
                                    for (int j = 0; j < picArray.length(); j++) {
                                        JSONObject likeObject = picArray.optJSONObject(j);
                                        pics.add(likeObject.optString("picture_url"));
                                    }
                                    parentWarnData.setPic(pics);
                                }
                                JSONArray likeArray = itemObject.optJSONArray("like");
                                if (likeArray != null) {
                                    ArrayList<ParentWarn.ParentWarnData.LikeBean> likeBeanList = new ArrayList<>();
                                    for (int j = 0; j < likeArray.length(); j++) {
                                        JSONObject likeObject = likeArray.optJSONObject(j);
                                        ParentWarn.ParentWarnData.LikeBean likeBean = new ParentWarn.ParentWarnData.LikeBean();
                                        likeBean.setUserid(likeObject.optString("userid"));
                                        likeBean.setName(likeObject.optString("name"));
                                        likeBean.setAvatar(likeObject.optString("avatar"));
                                        likeBeanList.add(likeBean);
                                    }
                                    parentWarnData.setWorkPraise(likeBeanList);
                                }
                                JSONArray commentArray = itemObject.optJSONArray("comment");
                                if (commentArray.length() > 0) {
                                    ArrayList<Comments> commentList = new ArrayList<>();
                                    for (int j = 0; j < 1; j++) {
                                        JSONObject commentObject = commentArray.optJSONObject(j);
                                        Comments comments = new Comments();
                                        comments.setUserid(commentObject.optString("userid"));
                                        comments.setName(commentObject.optString("name"));
                                        comments.setAvatar(commentObject.optString("avatar"));
                                        comments.setComment_time(commentObject.optString("comment_time"));
                                        comments.setContent(commentObject.optString("content"));
                                        commentList.add(comments);
                                    }
                                    parentWarnData.setComment(commentList);
                                }


                                parentWarnDataList.add(parentWarnData);
                            }
//                            Log.d("5555", String.valueOf(parentWarnDataList.size()));
//                            ArrayList<ParentWarn.ParentWarnData> shang = new ArrayList<>();
//                            ArrayList<ParentWarn.ParentWarnData> xia = new ArrayList<>();
//                            for (int i = 0; i < parentWarnDataList.size(); i++) {
//                                if (parentWarnDataList.get(i).getComment().size() > 0) {
//                                    xia.add(parentWarnDataList.get(i));
//                                } else {
//                                    shang.add(parentWarnDataList.get(i));
//                                }
//                            }
//
//                            parentWarnDataList.clear();
//                            for (int i = 0; i < shang.size(); i++) {
//                                parentWarnDataList.add(shang.get(i));
//                            }
//                            for (int i = 0; i < xia.size(); i++) {
//                                parentWarnDataList.add(xia.get(i));
//                            }
                            saveFirstMessageInSp();
                            if (parentWarnListAdapter == null) {
                                parentWarnListAdapter = new ParentWarnListAdapter(parentWarnDataList, ReceiveParentWarnActivity.this, handler, edit_and_send);
                                warnList.setAdapter(parentWarnListAdapter);
                            } else {
                                parentWarnListAdapter.notifyDataSetChanged();
                            }


                        }
                    }
                    break;
                case CommunalInterfaces.SEND_PARENT_REMARK:

                    if (msg.obj != null) {
                        ProgressViewUtil.dismiss();
                        JSONObject obj = (JSONObject) msg.obj;
                        String state = obj.optString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            ToastUtils.ToastShort(ReceiveParentWarnActivity.this, "发送成功");
                            edit_and_send.setVisibility(View.GONE);
                            EditText editText = (EditText) edit_and_send.findViewById(R.id.parent_warn_comment_edit);
                            editText.setText(null);
                            getAllInformation();
                        }
                    }
                    break;

            }
        }
    };

    private void saveFirstMessageInSp() {
        if (parentWarnDataList.size() > 0) {

            SPUtils.put(this, "receiveParentWarn", parentWarnDataList.get(0).getContent());

        }
    }
    private static final String JPUSHTRUST = "JPUSHTRUST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.warn_receive);
        ProgressViewUtil.show(this);
        clearJpushNum();
        initView();

    }
    private void clearJpushNum() {
        SPUtils.remove(this, JPUSHTRUST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearJpushNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearJpushNum();
    }
    @Override
    protected void onPause() {
        super.onPause();
        clearJpushNum();
    }

    private void initView() {
        parentWarnDataList = new ArrayList<>();
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parent_warnList = (PullToRefreshListView) findViewById(R.id.parent_warnList);
        warnList = parent_warnList.getRefreshableView();
        warnList.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        warnList.setItemsCanFocus(false);
        edit_and_send = (LinearLayout) findViewById(R.id.edit_and_send);
        //设置下拉上拉监听
        parent_warnList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(ReceiveParentWarnActivity.this) == true) {
                    getAllInformation();

                } else {
                    ToastUtils.ToastShort(ReceiveParentWarnActivity.this, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        parent_warnList.onPullDownRefreshComplete();
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
                        parent_warnList.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });

        /**
         * 监听listview滑动事件，隐藏评论输入框和软键盘
         * */
        warnList.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    }

    /**
     * 关闭软键盘，并隐藏输入框
     */

    private void closeEdit() {
        //关闭键盘
        View v = getWindow().peekDecorView();
        if (v != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        //隐藏输入框
        edit_and_send.setVisibility(View.GONE);
    }

    private void getAllInformation() {
        new NewsRequest(this, handler).parentWarn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllInformation();
    }
}
