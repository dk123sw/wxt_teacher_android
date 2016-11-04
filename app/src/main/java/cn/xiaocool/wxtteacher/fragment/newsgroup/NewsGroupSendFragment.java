package cn.xiaocool.wxtteacher.fragment.newsgroup;

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
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class NewsGroupSendFragment extends Fragment {

    private List<Homework.HomeworkData> homeworkDataList = new ArrayList<>();
    private NewsGroupAdapter homeworkListAdapter;
    private PullToRefreshListView backlog_list;
    private ListView listView;
    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.NEWSGROUP:
                    JSONObject obj = (JSONObject) msg.obj;
                    ProgressViewUtil.dismiss();
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
                            Log.e("picarray", itemObject.optJSONArray("picture").toString());
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
                                    likeBean.setAvatar(likeObject.optString("photo"));
                                    likeBean.setPhone(likeObject.optString("phone"));
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
                            homeworkListAdapter = new NewsGroupAdapter(homeworkDataList, mContext, handler, null);
                            listView.setAdapter(homeworkListAdapter);
                        }

                    }
                    break;


            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog_send, container, false);
        mContext = getActivity();
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProgressViewUtil.show(getActivity());
        initView();

    }

    private void initView() {

        backlog_list = (PullToRefreshListView) getView().findViewById(R.id.backlog_list);
        listView = backlog_list.getRefreshableView();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, BacklogDetailActivity.class);
//                Backlog.BacklogData backlogData = backlogDataList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("backlog", backlogData);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        listView.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        homeworkDataList = new ArrayList<>();
        backlog_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(mContext) == true) {
                    getAllInformation();

                } else {
                    ToastUtils.ToastShort(mContext, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backlog_list.onPullDownRefreshComplete();
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
                        backlog_list.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getAllInformation();
    }

    /**
     * 获取信息
     */

    private void getAllInformation() {

        new NewsRequest(mContext, handler).getNewsGoupInfos();

    }
}
