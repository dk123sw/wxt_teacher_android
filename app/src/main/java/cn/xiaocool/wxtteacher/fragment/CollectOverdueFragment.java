package cn.xiaocool.wxtteacher.fragment;

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
import cn.xiaocool.wxtteacher.adapter.ChildrenCollectAdapter;
import cn.xiaocool.wxtteacher.bean.ChildrenCollect;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CollectOverdueFragment extends Fragment {
    private PullToRefreshListView children_list;
    private ListView lv;
    private List<ChildrenCollect.ChildrenCollectData> childrenCollectDataList;
    private ChildrenCollectAdapter childrenCollectAdapter;
    private Context context;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.COLLECTION:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
                        JSONArray hwArray = obj.optJSONArray("data");
                        Log.d("CollectPendingFragment", obj.optString("data"));
                        childrenCollectDataList.clear();
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);
//                            Homework.HomeworkData homeworkData = new Homework.HomeworkData();
                            ChildrenCollect.ChildrenCollectData childrenCollectData = new ChildrenCollect.ChildrenCollectData();
                            childrenCollectData.setId(itemObject.optString("id"));
                            childrenCollectData.setTeacherid(itemObject.optString("teacherid"));
                            childrenCollectData.setDelivery_time(itemObject.optString("delivery_time"));
                            childrenCollectData.setDelivery_status(itemObject.optString("delivery_status"));
                            childrenCollectData.setParentid(itemObject.optString("parentid"));
                            childrenCollectData.setParenttime(itemObject.optString("parenttime"));
                            childrenCollectData.setClassname(itemObject.optString("classname"));

                            childrenCollectData.setParentname(itemObject.optString("parentname"));
                            childrenCollectData.setParentavatar(itemObject.optString("parentavatar"));
                            childrenCollectData.setParentphone(itemObject.optString("parentphone"));

                            childrenCollectData.setTeacheravatar(itemObject.optString("teacheravatar"));
                            childrenCollectData.setTeacherphone(itemObject.optString("teacherphone"));
                            childrenCollectData.setStudentname(itemObject.optString("studentname"));
                            childrenCollectData.setStudentavatar(itemObject.optString("studentavatar"));
                            childrenCollectData.setStudentphone(itemObject.optString("studentphone"));
                            childrenCollectData.setContent(itemObject.optString("content"));
                            childrenCollectData.setPhoto(itemObject.optString("photo"));
                            if (itemObject.optString("delivery_status").equals("2")){
                                childrenCollectDataList.add(childrenCollectData);
                                childrenCollectAdapter = new ChildrenCollectAdapter(childrenCollectDataList,context);
                                lv.setAdapter(childrenCollectAdapter);
                            }
                        }
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.collect_children, container, false);
        context = getActivity();
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        childrenCollectDataList = new ArrayList<>();
        children_list = (PullToRefreshListView) getView().findViewById(R.id.children_list);
        lv = children_list.getRefreshableView();
        lv.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        lv.setDividerHeight(10);
        //设置下拉上拉监听
        children_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(context) == true) {

                } else {
                    ToastUtils.ToastShort(context, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        children_list.onPullDownRefreshComplete();
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
                        children_list.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
        childrenCollectAdapter = new ChildrenCollectAdapter(childrenCollectDataList,context);
        lv.setAdapter(childrenCollectAdapter);
        new NewsRequest(context,handler).collection();
    }
}
