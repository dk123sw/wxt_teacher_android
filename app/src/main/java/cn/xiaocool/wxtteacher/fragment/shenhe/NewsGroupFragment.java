package cn.xiaocool.wxtteacher.fragment.shenhe;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.NewsGroupAdapter;
import cn.xiaocool.wxtteacher.bean.Homework;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.net.request.constant.SpaceRequest;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsGroupFragment extends Fragment {


    private Context context;
    private PullToRefreshListView news_list_view;
    private List<Homework.HomeworkData> homeworkDataList = new ArrayList<>();
    private ListView lv;
    private NewsGroupAdapter homeworkListAdapter;
    private static final int HOMEWORK_PRAISE_KEY = 104;
    private static final int DEL_HOMEWORK_PRAISE_KEY = 105;
    private String TAG="SpaceClickHomeworkActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.NEWSGROUP:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                        JSONArray hwArray = obj.optJSONArray("data");
                        Log.e("hwArray",obj.optJSONArray("data").toString());
                        homeworkDataList.clear();
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);
                            Homework.HomeworkData homeworkData = new Homework.HomeworkData();
                            homeworkData.setId(itemObject.optString("id"));
                            homeworkData.setUserid(itemObject.optString("userid"));
                            homeworkData.setTitle(itemObject.optString("title"));
                            homeworkData.setContent(itemObject.optString("message_content"));
                            homeworkData.setCreate_time(itemObject.optString("message_time"));
                            homeworkData.setUsername(itemObject.optString("send_user_name"));
                            homeworkData.setAllreader(itemObject.optInt("receiver_num"));


                            JSONArray picArray = itemObject.optJSONArray("picture");
                            Log.e("picarray",itemObject.optJSONArray("picture").toString());
                            Log.e("picarray", String.valueOf(itemObject.optJSONArray("picture").length()));
                            if (picArray.length()>0) {
                                ArrayList<String> pics = new ArrayList<>();
                                for (int k=0;k<picArray.length();k++){
                                    JSONObject pic = picArray.optJSONObject(k);
                                    pics.add(pic.optString("picture_url"));
                                }

                                homeworkData.setPics(pics);
                            }

                            //点赞模型代替已读人的模型
                            JSONArray likeArray = itemObject.optJSONArray("receiver");
                            if (likeArray != null) {
                                ArrayList<LikeBean> likeBeanList = new ArrayList<>();
                                for (int j = 0; j < likeArray.length(); j++) {
                                    JSONObject likeObject = likeArray.optJSONObject(j);
                                    LikeBean likeBean = new LikeBean();
                                    likeBean.setUserid(likeObject.optString("receiver_user_id"));
                                    likeBean.setName(likeObject.optString("receiver_user_name"));
//                                    likeBean.setAvatar(likeObject.optString("avatar"));
                                    likeBean.setTime(likeObject.optString("read_time"));
                                    likeBeanList.add(likeBean);
                                }
                                homeworkData.setWorkPraise(likeBeanList);
                            }

                            homeworkDataList.add(homeworkData);

                        }
                        if (homeworkListAdapter != null) {
                            homeworkListAdapter.notifyDataSetChanged();
                        } else {
                            homeworkListAdapter = new NewsGroupAdapter(homeworkDataList, getActivity(), handler, null);
                            lv.setAdapter(homeworkListAdapter);
                        }

                    }
                    break;


            }

        }
    };
    public NewsGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllInformation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_group, container, false);
        context = getActivity();

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        news_list_view = (PullToRefreshListView) getView().findViewById(R.id.news_list_view);
        lv = news_list_view.getRefreshableView();
        lv.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        new SpaceRequest(context, handler).sendMessage();



        //设置下拉上拉监听
        news_list_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(context) == true) {
                    getAllInformation();

                } else {
                    ToastUtils.ToastShort(context, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        news_list_view.onPullDownRefreshComplete();
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
                        news_list_view.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });

        /**
         * 监听listview滑动事件，隐藏评论输入框和软键盘
         * */
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                HashMap<String, Object> map = (HashMap<String, Object>) listItemAdapter.getItem(position);
//                String intentNewsName = (String) map.get("news_name");
//                String intentNewsContent = (String) map.get("news_content");
//                String intentNewsTime = (String) map.get("news_time");
//                String intentReceivreID = (String) map.get("news_receive");
//                Intent intent = new Intent();
//                intent.putExtra("name", intentNewsName);
//                intent.putExtra("content", intentNewsContent);
//                intent.putExtra("time", intentNewsTime);
//                intent.putExtra("receiveID", intentReceivreID);
//                intent.setClass(NewsGroupActivity.this, HotNewsSendActivity.class);
//                startActivity(intent);

            }
        });

    }

    /**
     * 获取家庭作业
     * */

    private void getAllInformation() {

        new NewsRequest(getActivity(), handler).getNewsGoupInfos();

    }

    /**
     * 关闭软键盘，并隐藏输入框
     * */

    private void closeEdit() {
        //关闭键盘
        View v = getActivity().getWindow().peekDecorView();
        if (v != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


}
