package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassEventCountAdapter;
import cn.xiaocool.wxtteacher.bean.ClassEventCount;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class IntroduceClassActivity extends Activity implements View.OnClickListener{


    private RelativeLayout up_jiantou;
    private RelativeLayout last_month_layout, next_month_layout;
    private TextView year_month;
    private NoScrollListView attend_list;
    private int year, month;
    private int nowyear, nowmonth;
    private Context mContext;
    private RequestQueue mQueue;
    private ArrayList<ClassEventCount> classEventCounts;
    private UserInfo user;
    private ClassEventCountAdapter classEventCountAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce_class);
        mContext = this;
        mQueue = Volley.newRequestQueue(mContext);
        classEventCounts = new ArrayList<>();
        user = new UserInfo();
        user.readData(mContext);
        initview();


    }

    @Override
    protected void onResume() {
        super.onResume();
        requsetData();
    }

    private void requsetData() {
        long begintime = TimeToolUtils.getMonthBeginTimestamp(year, month) / 1000;
        long endtime = TimeToolUtils.getMonthEndTimestamp(year, month) / 1000;
        mQueue = Volley.newRequestQueue(mContext);
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=school&a=ClassPicInfo&schoolid=" + user.getSchoolId();
        Log.e("getAttendList", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {

                Log.d("onResponse", arg0);

                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {

                        classEventCounts.clear();
                        Gson gson = new Gson();
                        ArrayList<ClassEventCount> arrayList = gson.fromJson(jsonObject.optString("data"), new TypeToken<List<ClassEventCount>>() {
                        }.getType());
                        classEventCounts.addAll(arrayList);
                        Log.e("studentDataArrayList", classEventCounts.toString());


                        setAdapter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ToastUtils.ToastShort(mContext, arg0.toString());
                Log.d("onErrorResponse", arg0.toString());
            }
        });

        mQueue.add(request);
    }

    private void setAdapter() {
        if (classEventCountAdapter!=null){
            classEventCountAdapter.notifyDataSetChanged();
        }else {
            classEventCountAdapter = new ClassEventCountAdapter(classEventCounts,null,mContext);
            attend_list.setAdapter(classEventCountAdapter);
        }
    }

    private void initview() {

        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        attend_list = (NoScrollListView) findViewById(R.id.attend_list);
        attend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,PeopleCountActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher_info", (Serializable) classEventCounts.get(position).getTeacher_info());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getdate();
    }

    private void getdate() {


        //获取当前年月日
        Calendar c = Calendar.getInstance();
        year = getIntent().getIntExtra("year", c.get(Calendar.YEAR));
        month = getIntent().getIntExtra("month", c.get(Calendar.MONTH) + 1);

        nowyear = year;
        nowmonth = month;
        year_month = (TextView) findViewById(R.id.year_month);
        year_month.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月");
        Log.d("年-月", String.valueOf(year) + String.valueOf(month));
        last_month_layout = (RelativeLayout) findViewById(R.id.last_month_layout);
        last_month_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month = month - 1;
                if (month == 0) {
                    month = 12;
                    year = year - 1;
                }
                year_month.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月");


            }
        });
        next_month_layout = (RelativeLayout) findViewById(R.id.next_month_layout);
        next_month_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year >= nowyear && month >= nowmonth) {
                    ToastUtils.ToastShort(IntroduceClassActivity.this, "您切换的月份大于当前月份！");
                } else {
                    month = month + 1;
                    if (month == 13) {
                        month = 1;
                        year = year + 1;
                    }
                    year_month.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月");
                    
                }

            }
        });
        year_month = (TextView) findViewById(R.id.year_month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_jiantou:
                finish();
                break;
        }
    }
}
