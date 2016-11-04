package cn.xiaocool.wxtteacher.fragment.backlog;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.BacklogListAdapter;
import cn.xiaocool.wxtteacher.bean.Backlog;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class BaclogReciveFragment extends Fragment {
    private ImageView quit;
    private ImageView add_backlog;
    private PullToRefreshListView backlog_list;
    private ListView listView;
    private Context mContext;
    private RelativeLayout up_jiantou, btn_add;
    private ArrayList<Backlog.BacklogData> backlogDataList;
    private BacklogListAdapter backlogListAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.BACKLOG:
                    JSONObject obj = (JSONObject) msg.obj;
                    String status = obj.optString("status");
                    ProgressViewUtil.dismiss();
                    if (status.equals(CommunalInterfaces._STATE)) {
                        JSONArray annArray = obj.optJSONArray("data");
                        Log.e("BacklogActivity", obj.optString("data"));
                        backlogDataList.clear();
                        JSONObject itemObject;
                        for (int i = 0; i < annArray.length(); i++) {
                            itemObject = annArray.optJSONObject(i);

                            Backlog.BacklogData announcementDate = new Backlog.BacklogData();
                            announcementDate.setType("recive");
                            announcementDate.setTitle(itemObject.optString("title"));
                            announcementDate.setId(itemObject.optString("id"));
                            announcementDate.setUserid(itemObject.optString("userid"));
                            announcementDate.setContent(itemObject.optString("content"));
                            announcementDate.setType(itemObject.optString("status"));
                            announcementDate.setCreate_time(itemObject.optString("create_time"));
                            announcementDate.setUsername(itemObject.optString("name"));
                            announcementDate.setAvatar(itemObject.optString("photo"));
                            announcementDate.setIscando(itemObject.optString("iscando"));
                            JSONArray picArray = itemObject.optJSONArray("piclist");
                            if (picArray != null) {
                                ArrayList<Backlog.BacklogData.PicData> picDataList = new ArrayList<>();
                                for (int j = 0; j < picArray.length(); j++) {
                                    JSONObject picObject = picArray.optJSONObject(j);
                                    Backlog.BacklogData.PicData picData = new Backlog.BacklogData.PicData();
                                    picData.setId(picObject.optString("id"));
                                    picData.setPictureurl(picObject.optString("picture_url"));

                                    picDataList.add(picData);
                                }
                                announcementDate.setPic(picDataList);

                            }

                            JSONArray reciverArray = itemObject.optJSONArray("reciverlist");
                            if (reciverArray != null) {
                                ArrayList<Backlog.BacklogData.Reciverlist> picDataList = new ArrayList<>();
                                for (int j = 0; j < reciverArray.length(); j++) {
                                    JSONObject picObject = reciverArray.optJSONObject(j);
                                    Backlog.BacklogData.Reciverlist picData = new Backlog.BacklogData.Reciverlist();
                                    picData.setName(picObject.optString("name"));
                                    picData.setFeedback(picObject.optString("feedback"));
                                    picData.setFinish(picObject.optString("finish"));
                                    picData.setCreate_time(picObject.optString("create_time"));
                                    picData.setRead_time(picObject.optString("read_time"));
                                    picData.setPictureurl(picObject.optString("photo"));
                                    picDataList.add(picData);
                                }
                                announcementDate.setReciverlist(picDataList);
                            }
                            backlogDataList.add(announcementDate);
                        }

                        saveFirstMessageInSp();
                        if (backlogListAdapter==null){
                            backlogListAdapter = new BacklogListAdapter(backlogDataList, mContext);
                            listView.setAdapter(backlogListAdapter);
                        }else {
                            backlogListAdapter.notifyDataSetChanged();
                        }

                        break;
                    }
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

    private void saveFirstMessageInSp() {
        if (backlogDataList.size()>0){
            SPUtils.put(mContext, "backlogData", backlogDataList.get(0).getTitle());
        }
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
        backlogDataList = new ArrayList<>();
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



    public void onResume() {
        super.onResume();
        getAllInformation();
    }

    private void getAllInformation() {
        new NewsRequest(mContext, handler).backlogRecive();
    }
}
