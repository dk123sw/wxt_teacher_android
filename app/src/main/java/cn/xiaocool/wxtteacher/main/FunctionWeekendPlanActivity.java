package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.WeekendPlanAdapter;
import cn.xiaocool.wxtteacher.bean.WeekendPlan;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by 潘 on 2016/4/10.
 */
public class FunctionWeekendPlanActivity extends Activity implements View.OnClickListener {

    private RelativeLayout up_jiantou,add_jiantou;
    private Context mContext;
    private ListView function_weekendplan;
    private WeekendPlanAdapter weekendPlanAdapter;
    private View location_pop;
    private List<WeekendPlan> weekendPlanDatasList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case CommunalInterfaces.GETSCHOOLPLAN:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        ProgressViewUtil.dismiss();
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)){
                                JSONArray dataArray = obj.getJSONArray("data");
                                weekendPlanDatasList.clear();
                                JSONObject itemObject;
                                for (int i = 0; i < dataArray.length(); i++) {
                                    itemObject = dataArray.getJSONObject(i);
                                    /**
                                     * id : 1
                                     * schoolid : 1
                                     * classid : 1
                                     * userid : 0
                                     * type : 1
                                     * title : 北京市东城区汪芝麻胡同19号
                                     * monday : 张明
                                     * tuesday :
                                     * wednesday :
                                     * thursday :
                                     * friday :
                                     * saturday :
                                     * sunday :
                                     * workpoint :
                                     * begintime : 0
                                     * endtime : 0
                                     * school_phone : 12345681901
                                     * create_time : 1458376723
                                     * school_status : 2
                                     * classname : 小一班
                                     */

                                    WeekendPlan weekendPlanData = new WeekendPlan();
                                    weekendPlanData.setId(itemObject.optString("id"));
                                    weekendPlanData.setTitle(itemObject.optString("title"));
                                    weekendPlanData.setSchoolid(itemObject.optString("schoolid"));
                                    weekendPlanData.setCreate_time(itemObject.optString("create_time"));

                                    weekendPlanData.setClassid(itemObject.optString("classid"));
                                    weekendPlanData.setUserid(itemObject.optString("userid"));
                                    weekendPlanData.setType(itemObject.optString("type"));
                                    weekendPlanData.setMonday(itemObject.optString("monday"));

                                    weekendPlanData.setTuesday(itemObject.optString("tuesday"));
                                    weekendPlanData.setWednesday(itemObject.optString("wednesday"));
                                    weekendPlanData.setThursday(itemObject.optString("thursday"));
                                    weekendPlanData.setFriday(itemObject.optString("friday"));

                                    weekendPlanData.setSaturday(itemObject.optString("saturday"));
                                    weekendPlanData.setSunday(itemObject.optString("sunday"));
                                    weekendPlanData.setWorkpoint(itemObject.optString("workpoint"));
                                    weekendPlanData.setBegintime(itemObject.optString("begintime"));

                                    weekendPlanData.setEndtime(itemObject.optString("endtime"));
                                    weekendPlanData.setSchool_phone(itemObject.optString("school_phone"));
                                    weekendPlanData.setSchool_status(itemObject.optString("school_status"));
                                    weekendPlanData.setClassname(itemObject.optString("classname"));


                                    LogUtils.e("info", weekendPlanData.toString());
                                    weekendPlanDatasList.add(weekendPlanData);
                                    LogUtils.e("info",weekendPlanDatasList.toString());
                                }
                                if (weekendPlanAdapter!=null){
                                    weekendPlanAdapter.notifyDataSetChanged();
                                }else {
                                    weekendPlanAdapter = new WeekendPlanAdapter(weekendPlanDatasList,mContext);
                                    function_weekendplan.setAdapter(weekendPlanAdapter);
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.function_weekendplan);
        mContext=this;
        ProgressViewUtil.show(mContext);
        init();
    }

    private void init() {
        weekendPlanDatasList = new ArrayList<>();
        location_pop = findViewById(R.id.location_pop);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        add_jiantou = (RelativeLayout) findViewById(R.id.add_jiantou);
        add_jiantou.setOnClickListener(this);
        function_weekendplan=(ListView)findViewById(R.id.function_weekendplanlist);
        function_weekendplan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FunctionWeekendPlanActivity.this, FunctionWeekendPlanDetailActivity.class);
                intent.putExtra("planid", weekendPlanDatasList.get(position).getId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("plan", (Serializable) weekendPlanDatasList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.add_jiantou:

                Intent intent  = new Intent(FunctionWeekendPlanActivity.this,AddWeekPlanActivity.class);
                intent.putExtra("type","0");
                this.startActivity(intent);
                break;
        }

    }
    /**
     *显示选择菜单
     * */
    private void showPopupMenu() {
        View layout = LayoutInflater.from(this).inflate(R.layout.classattend_menu,null);
        PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        location_pop.getLocationOnScreen(location);
        popupWindow.showAsDropDown(location_pop);

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

        final TextView qiandao_histroy = (TextView)layout.findViewById(R.id.qiandao_histroy);
        TextView quanxian_setting = (TextView)layout.findViewById(R.id.quanxian_setting);
        qiandao_histroy.setText("编辑周计划");
        quanxian_setting.setText("新增周计划");
        qiandao_histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history();
            }
        });
        quanxian_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });

    }

    /**
     * 新增周计划
     * */
    private void setting() {
        ToastUtils.ToastShort(this, "新增周计划");
        Intent intent  = new Intent(FunctionWeekendPlanActivity.this,AddWeekPlanActivity.class);

        this.startActivity(intent);
    }

    /**
     * 编辑周计划
     * */
    private void history() {
        ToastUtils.ToastShort(this,"编辑周计划");
        Intent intent  = new Intent(FunctionWeekendPlanActivity.this,AddWeekPlanActivity.class);

        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new NewsRequest(mContext,handler).weekendplan();
    }
}
