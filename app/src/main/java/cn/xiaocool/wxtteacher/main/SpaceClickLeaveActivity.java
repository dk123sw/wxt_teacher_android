package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SpaceClickLeaveAdapter;
import cn.xiaocool.wxtteacher.bean.LeaveModel;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by wzh on 2016/1/29.
 */
public class SpaceClickLeaveActivity extends Activity{

    private RelativeLayout up_jiantou,leave_commit;
    private ListView parent_warnList;
    private List<LeaveModel> parentWarnDataList;
    private SpaceClickLeaveAdapter parentWarnListAdapter;
    private PullToRefreshListView pullList;
    private Context mContext;

    private static final String JPUSHLEAVE = "JPUSHLEAVE";
    private void clearJpushNum() {
        SPUtils.remove(this, JPUSHLEAVE);
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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.RECEIVED_MESSAGE:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        ProgressViewUtil.dismiss();
                        String state = obj.optString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            JSONArray dataArray = obj.optJSONArray("data");
                            parentWarnDataList.clear();
                            JSONObject itemObject;
                            for (int i = 0; i < dataArray.length(); i++) {
                                itemObject = dataArray.optJSONObject(i);

                               /* "id": "66",
                                        "studentid": "599",
                                        "parentid": "597",
                                        "teacherid": "599",
                                        "create_time": "0",
                                        "begintime": "1468339200",
                                        "endtime": "1468425600",
                                        "reason": "",
                                        "status": "1",
                                        "feedback": "",
                                        "deal_time": "0",
                                        "classname": null,
                                        "teachername": "",
                                        "teacheravatar": "default.png",
                                        "teacherphone": "",
                                        "parentname": "I'm so excited",
                                        "parentavatar": "weixiaotong.png",
                                        "parentphone": "18363866803",
                                        "studentname": "",
                                        "studentavatar": "default.png"*/
                                LeaveModel parentWarnData = new LeaveModel();
                                parentWarnData.setId(itemObject.optString("id"));
                                parentWarnData.setStudentid(itemObject.optString("studentid"));
                                parentWarnData.setParentid(itemObject.optString("parentid"));
                                parentWarnData.setTeacherid(itemObject.optString("teacherid"));
                                parentWarnData.setCreate_time(itemObject.optString("create_time"));
                                parentWarnData.setBegintime(itemObject.optString("begintime"));
                                parentWarnData.setEndtime(itemObject.optString("endtime"));
                                parentWarnData.setReason(itemObject.optString("reason"));
                                parentWarnData.setStatus(itemObject.optString("status"));
                                parentWarnData.setFeedback(itemObject.optString("feedback"));
                                parentWarnData.setDeal_time(itemObject.optString("deal_time"));
                                parentWarnData.setClassname(itemObject.optString("classname"));
                                parentWarnData.setTeachername(itemObject.optString("teachername"));
                                parentWarnData.setTeacheravatar(itemObject.optString("teacheravatar"));
                                parentWarnData.setLeavetype(itemObject.optString("leavetype"));
                                parentWarnData.setTeacherphone(itemObject.optString("teacherphone"));
                                parentWarnData.setParentname(itemObject.optString("parentname"));
                                parentWarnData.setParentavatar(itemObject.optString("parentavatar"));
                                parentWarnData.setParentphone(itemObject.optString("parentphone"));
                                parentWarnData.setStudentname(itemObject.optString("studentname"));
                                parentWarnData.setStudentavatar(itemObject.optString("studentavatar"));

                                JSONArray picsArray = itemObject.optJSONArray("pic");
                                if (picsArray.length()>0){
                                    ArrayList<String> pics = new ArrayList<>();
                                    for (int j =0;j<picsArray.length();j++){
                                        pics.add(picsArray.optJSONObject(j).optString("picture_url"));
                                    }
                                    parentWarnData.setPics(pics);
                                }

                                parentWarnDataList.add(parentWarnData);
                            }
                            Log.d("5555", String.valueOf(parentWarnDataList.size()));
                            if (parentWarnListAdapter!=null){
                                parentWarnListAdapter.notifyDataSetChanged();
                            }else {
                                parentWarnListAdapter  = new SpaceClickLeaveAdapter(parentWarnDataList, SpaceClickLeaveActivity.this, handler);
                                parent_warnList.setAdapter(parentWarnListAdapter);
                            }
                        }
                    }
                    break;

                case 1111:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        String state = obj.optString("status");
                        if (state.equals(CommunalInterfaces._STATE)) {
                            refresh();
                        }
                    }

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.web_open_leave);
        mContext = this;
        ProgressViewUtil.show(this);
        clearJpushNum();
        initView();
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

        pullList = (PullToRefreshListView) findViewById(R.id.leave_List);
        parent_warnList = pullList.getRefreshableView();
        parent_warnList.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        parent_warnList.setDividerHeight(50);
        pullList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(mContext) == true) {

                    refresh();

                } else {
                    ToastUtils.ToastShort(mContext, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullList.onPullDownRefreshComplete();
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
                        pullList.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
//        parent_warnList.setAdapter(new SpaceClickLeaveAdapter(parentWarnDataList, SpaceClickLeaveActivity.this));
//        parent_warnList.setItemsCanFocus(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        new NewsRequest(this,handler).getleavelist();
    }
}
