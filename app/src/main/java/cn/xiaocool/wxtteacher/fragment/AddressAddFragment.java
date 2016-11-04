package cn.xiaocool.wxtteacher.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by 潘 on 2016/4/2.
 */
public class AddressAddFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private ExpandableListView expandableListView;
    /**
     * 创建一级条目容器
     */
    List<Map<String, String>> gruops = new ArrayList<>();
    /**
     * 存放内容, 以便显示在列表中
     */
    List<List<Map<String, String>>> childs = new ArrayList<>();
    List<List<Map<String, String>>> childs2 = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MESSAGEADDRESS:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String status = obj.getString("status");
                            if (status.equals("success")) {
                                expandableListView = (ExpandableListView) getView().findViewById(R.id.address_class);
                                JSONArray dataArray = obj.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    Map<String, String> title = new HashMap<String, String>();
                                    title.put("group", dataArray.getJSONObject(i).getString("classid"));
                                    gruops.add(title);
                                    JSONArray dayaArray2 = dataArray.getJSONObject(i).getJSONArray("teacherinfo");
                                    List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
                                    for (int j = 0; j < dayaArray2.length(); j++) {
                                        //   childs.get(i).get(j).put("child",dayaArray2.getJSONObject(j).getString("name"));
                                        Map<String, String> title_1_content_1 = new HashMap<String, String>();
                                        title_1_content_1.put("child", dayaArray2.getJSONObject(j).getString("name"));
                                        LogUtils.e("111", title_1_content_1.toString());
                                        childs_1.add(title_1_content_1);
                                    }
                                    childs.add(childs_1);
                                }
                                SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
                                        mContext, gruops, R.layout.groups, new String[]{"group"},
                                        new int[]{R.id.textGroup}, childs, R.layout.childs,
                                        new String[]{"child"}, new int[]{R.id.textChild});
                                // 加入列表
                                expandableListView.setAdapter(sela);
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
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_address_parent, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        expandableListView = (ExpandableListView)getView().findViewById(R.id.android_list);
        mContext = getActivity();
        news();
//        setListData();
    }

    private void news() {
        new NewsRequest(mContext, handler).address();
    }
}
