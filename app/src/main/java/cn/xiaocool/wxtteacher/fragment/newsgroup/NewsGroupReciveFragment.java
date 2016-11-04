package cn.xiaocool.wxtteacher.fragment.newsgroup;

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
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.NewsGroupReciveAdapter;
import cn.xiaocool.wxtteacher.bean.NewsGroupRecive;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class NewsGroupReciveFragment extends Fragment {
    private UserInfo user = new UserInfo();
    private List<NewsGroupRecive.DataBean> homeworkDataList;
    private NewsGroupReciveAdapter homeworkListAdapter;
    private PullToRefreshListView backlog_list;
    private ListView listView;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog_send, container, false);
        mContext = getActivity();
        user.readData(mContext);
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

        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        String URL = "http://wxt.xiaocool.net/index.php?g=Apps&m=Message&a=user_reception_message&receiver_user_id="+user.getUserId();
        Log.e("uuuurrrrll", URL);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {



            @Override
            public void onResponse(String arg0) {
                Log.d("onResponse", arg0);
                ProgressViewUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {
                        JSONArray dataArray = jsonObject.optJSONArray("data");
                        homeworkDataList.clear();
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject dataObject = dataArray.optJSONObject(i);
                            NewsGroupRecive.DataBean dataBean =new NewsGroupRecive.DataBean();
                            dataBean.setId(dataObject.optString("id"));
                            dataBean.setMessage_id(dataObject.optString("message_id"));
                            dataBean.setReceiver_user_id(dataObject.optString("receiver_user_id"));
                            dataBean.setReceiver_user_name(dataObject.optString("receiver_user_name"));
                            dataBean.setMessage_type(dataObject.optString("message_type"));
                            dataBean.setRead_time(dataObject.optString("read_time"));


                            JSONObject send_message = dataObject.optJSONArray("send_message").optJSONObject(0);

                            NewsGroupRecive.DataBean.SendMessageBean sendMessageBean = new NewsGroupRecive.DataBean.SendMessageBean();
                             sendMessageBean.setId(send_message.optString("id"));
                            sendMessageBean.setSchoolid(send_message.optString("schoolid"));
                            sendMessageBean.setSend_user_id(send_message.optString("send_user_id"));
                            sendMessageBean.setSend_user_name(send_message.optString("send_user_name"));
                            sendMessageBean.setMessage_content(send_message.optString("message_content"));
                            sendMessageBean.setMessage_time(send_message.optString("message_time"));
                            sendMessageBean.setPhoto(send_message.optString("photo"));


                            dataBean.setSend_message(sendMessageBean);
                            JSONArray picArray = dataObject.optJSONArray("pic");
                            List<NewsGroupRecive.DataBean.PictureBean> pictureBeanList = new ArrayList<>();
                            for (int j=0;j<picArray.length();j++){
                                JSONObject picObject = picArray.optJSONObject(j);
                                NewsGroupRecive.DataBean.PictureBean pictureBean = new NewsGroupRecive.DataBean.PictureBean();
                                pictureBean.setPicture_url(picObject.optString("picture_url"));
                                pictureBeanList.add(pictureBean);
                            }
                            dataBean.setPicture(pictureBeanList);

                            JSONArray receiverArray = dataObject.optJSONArray("receiver");
                            List<NewsGroupRecive.DataBean.ReceiverBean> receiverBeanList = new ArrayList<>();
                            for (int j=0;j<receiverArray.length();j++){
                                JSONObject reciverObject = receiverArray.optJSONObject(j);
                                NewsGroupRecive.DataBean.ReceiverBean receiverBean = new NewsGroupRecive.DataBean.ReceiverBean();
                                receiverBean.setMessage_id(reciverObject.optString("message_id"));
                                receiverBean.setReceiver_user_id(reciverObject.optString("receiver_user_id"));
                                receiverBean.setReceiver_user_name(reciverObject.optString("receiver_user_name"));
                                receiverBean.setRead_time(reciverObject.optString("read_time"));
                                receiverBean.setPhone(reciverObject.optString("phone"));
                                receiverBean.setPhoto(reciverObject.optString("photo"));
                                receiverBeanList.add(receiverBean);

                            }
                            dataBean.setReceiver(receiverBeanList);

                            homeworkDataList.add(dataBean);


                        }

                        saveFirstMessageInSp();

                        if (homeworkListAdapter != null) {
                            homeworkListAdapter.notifyDataSetChanged();
                        } else {
                            homeworkListAdapter = new NewsGroupReciveAdapter(homeworkDataList, mContext);
                            listView.setAdapter(homeworkListAdapter);
                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ProgressViewUtil.dismiss();
            }
        });


        mQueue.add(request);

    }

    private void saveFirstMessageInSp() {
        if (homeworkDataList.size()>0){
            SPUtils.put(mContext,"newsGroupRecive",homeworkDataList.get(0).getSend_message().getMessage_content());
        }
    }
}
