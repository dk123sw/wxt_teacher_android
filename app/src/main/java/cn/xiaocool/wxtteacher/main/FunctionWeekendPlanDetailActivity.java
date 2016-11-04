package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.WeekendPlan;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class FunctionWeekendPlanDetailActivity extends Activity {


    private TextView week_plan_detail_text,title_bar_name;
    private RelativeLayout add_jiantou,up_jiantou;
    private WeekendPlan weekendPlanData;
    private String id;
    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case CommunalInterfaces.GETSCHOOLPLAN:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)){
                                JSONArray dataArray = obj.getJSONArray("data");
                                JSONObject itemObject;
                                for (int i = 0; i < dataArray.length(); i++) {
                                    itemObject = dataArray.getJSONObject(i);
                                    if (itemObject.optString("id").equals(id)){
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
                                        ShowText();
                                        break;
                                    }
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
        setContentView(R.layout.activity_function_weekend_plan_detail);
        mContext =this;
        weekendPlanData = new WeekendPlan();
        id = getIntent().getStringExtra("planid");

        initView();
    }

    private void initView() {
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_jiantou = (RelativeLayout) findViewById(R.id.add_jiantou);

        week_plan_detail_text = (TextView) findViewById(R.id.week_plan_detail_text);
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);

        add_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.ToastShort(FunctionWeekendPlanDetailActivity.this, "编辑周计划");
                Intent intent = new Intent(FunctionWeekendPlanDetailActivity.this, AddWeekPlanActivity.class);
                intent.putExtra("type", "1");
                Bundle bundle = new Bundle();
                bundle.putSerializable("weekplan",weekendPlanData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    /**
     * 分割并展示
     * */
    private void ShowText() {


        String newntext ="要点："+ weekendPlanData.getWorkpoint()+"\n"+"星期一："+weekendPlanData.getMonday()+"\n"+"星期二："+weekendPlanData.getTuesday()+"\n"+
                "星期三："+weekendPlanData.getWednesday()+"\n"+"星期四："+weekendPlanData.getThursday()+"\n"+"星期五："+weekendPlanData.getFriday()+"\n"+"星期六："+weekendPlanData.getSaturday()+"\n"
                +"星期日："+weekendPlanData.getSunday();
        week_plan_detail_text.setText(newntext);
        title_bar_name.setText(weekendPlanData.getTitle());
    }


    @Override
    protected void onResume() {
        super.onResume();
        new NewsRequest(mContext,handler).weekendplan();
    }
}
