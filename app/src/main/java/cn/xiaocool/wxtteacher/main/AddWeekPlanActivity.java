package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.bean.WeekendPlan;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class AddWeekPlanActivity extends Activity {

    private UserInfo user = new UserInfo();
    private EditText et_title, et_mon, et_tues, et_wed, et_thur, et_fri, et_sta, et_sun, et_workpoint;
    private TextView tv_begintime, tv_endtime;
    private RelativeLayout up_jiantou, add_jiantou;
    private String type, classid, split, send, begintime, finishtime;
    private KProgressHUD hud;
    private WeekendPlan weekendPlan;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    if (msg.obj != null) {
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String state = obj.getString("status");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                ToastUtils.ToastShort(AddWeekPlanActivity.this, "发送成功");
                                hud.dismiss();
                                finish();
                            } else {
                                hud.dismiss();
                                ToastUtils.ToastShort(AddWeekPlanActivity.this, "发送失败"+obj.optString("data"));
                            }


                        } catch (JSONException e) {
                            hud.dismiss();
                            ToastUtils.ToastShort(AddWeekPlanActivity.this, "发送失败");
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
        setContentView(R.layout.activity_add_week_plan);
        begintime="0";
        finishtime="0";
        user.readData(this);
        classid = user.getClassId();
        type = getIntent().getStringExtra("type");
        weekendPlan = (WeekendPlan) getIntent().getSerializableExtra("weekplan");


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
        et_title = (EditText) findViewById(R.id.et_title);

        et_mon = (EditText) findViewById(R.id.et_mon);
        et_tues = (EditText) findViewById(R.id.et_tues);
        et_wed = (EditText) findViewById(R.id.et_wed);
        et_thur = (EditText) findViewById(R.id.et_thur);
        et_fri = (EditText) findViewById(R.id.et_fri);
        et_sta = (EditText) findViewById(R.id.et_sta);
        et_sun = (EditText) findViewById(R.id.et_sun);
        et_workpoint = (EditText) findViewById(R.id.et_workpoint);

        tv_begintime = (TextView) findViewById(R.id.tv_begintime);
        tv_begintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateText(tv_begintime, "0");
            }
        });
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        tv_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateText(tv_endtime, "1");
            }
        });
        add_jiantou = (RelativeLayout) findViewById(R.id.add_jiantou);
        add_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                volleySend();

            }
        });


        if (type.equals("0")) {

        } else {
            et_mon.setText(weekendPlan.getMonday());
            et_tues.setText(weekendPlan.getTuesday());
            et_wed.setText(weekendPlan.getWednesday());
            et_thur.setText(weekendPlan.getThursday());
            et_fri.setText(weekendPlan.getFriday());
            et_sta.setText(weekendPlan.getSaturday());
            et_sun.setText(weekendPlan.getSunday());
            et_workpoint.setText(weekendPlan.getWorkpoint());
            Date date = new Date();
            date.setTime(Long.parseLong(weekendPlan.getBegintime()) * 1000);
            begintime = weekendPlan.getBegintime();
            SimpleDateFormat formatd = new SimpleDateFormat("yyyy-MM-dd");
            tv_begintime.setText(formatd.format(date));
            date.setTime(Long.parseLong(weekendPlan.getEndtime()) * 1000);
            tv_endtime.setText(formatd.format(date));
            finishtime = weekendPlan.getEndtime();
            et_title.setText(weekendPlan.getTitle());
        }

    }

    private void volleySend() {
        if (et_title.getText().length() > 0) {


                //            RequestQueue mQueue = Volley.newRequestQueue(this);
                String URL = "";
                String data = "";
                if (type.equals("0")) {
                    URL = "http://wxt.xiaocool.net/index.php?g=apps&m=school&a=publishschoolplan";
                    data = "&classid=" + classid + "&schoolid=" + user.getSchoolId() + "&userid=" + user.getUserId() + "&title=" + et_title.getText().toString() + "&monday=" + et_mon.getText().toString()
                            + "&tuesday=" + et_tues.getText().toString() + "&wednesday=" + et_wed.getText().toString() +
                            "&thursday=" + et_thur.getText().toString() + "&friday=" + et_fri.getText().toString() + "&saturday=" + et_sta.getText().toString()
                            + "&sunday=" + et_sun.getText().toString() + "&workpoint=" + et_workpoint.getText().toString() + "&begintime=" + begintime +
                            "&endtime=" + finishtime;
                } else {
                    URL = "http://wxt.xiaocool.net/index.php?g=apps&m=school&a=updataschoolplan";
                    data = "&id=" + weekendPlan.getId() + "&userid=" + user.getUserId() + "&title=" + et_title.getText().toString() + "&monday=" + et_mon.getText().toString()
                            + "&tuesday=" + et_tues.getText().toString() + "&wednesday=" + et_wed.getText().toString() +
                            "&thursday=" + et_thur.getText().toString() + "&friday=" + et_fri.getText().toString() + "&saturday=" + et_sta.getText().toString()
                            + "&sunday=" + et_sun.getText().toString() + "&workpoint=" + et_workpoint.getText().toString() + "&begintime=" + begintime +
                            "&endtime=" + finishtime;
                }
                Log.e("volleySend", data);
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDetailsLabel("发送中...")
                        .setCancellable(true);
                hud.show();

                new NewsRequest(this, handler).send_weekendplan(URL, data, 123);
//            CharsetJsonRequest request = new CharsetJsonRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject arg0) {
//                    Log.d("onResponse", arg0.toString());
//                    try {
//                        String state = arg0.optString("status");
//                        if (state.equals(CommunalInterfaces._STATE)) {
//                            ToastUtils.ToastShort(AddWeekPlanActivity.this, "发送成功");
//                            hud.dismiss();
//                            finish();
//                        } else {
//                            hud.dismiss();
//                            ToastUtils.ToastShort(AddWeekPlanActivity.this, "发送失败" + arg0.optString("data"));
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        ToastUtils.ToastShort(AddWeekPlanActivity.this, "发送失败");
//                        hud.dismiss();
//                    }
//
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError arg0) {
//                    Log.d("onErrorResponse", arg0.toString());
//                    hud.dismiss();
//                }
//            });
//            mQueue.add(request);



        } else {
            ToastUtils.ToastShort(this, "请输入标题!");
        }

    }

    private void setDateText(final TextView v, final String type) {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);

        Log.e("MONTH", "year" + year);
        Log.e("MONTH", "month" + month);
        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);

        DatePickerDialog dlg = new DatePickerDialog(AddWeekPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始

                String data = year + "-" + monthOfYear + "-" + dayOfMonth;

//                        String data_new = dataOne(data + "-" + hour + "-" + minute + "-" + second);

                //时分秒用0代替
                String data_new = dataOne(data + "-" + 0 + "-" + 0 + "-" + 0);
                Log.e("--444444---", data_new);

                if (type.equals("0")) {
                    v.setText(data);
                    begintime = data_new;
                } else if (type.equals("1")) {

                    if (Integer.valueOf(data_new)<Integer.valueOf(begintime)){
                        ToastUtils.ToastShort(AddWeekPlanActivity.this,"选择的结束日期要大于开始日期");
                    }else {
                        finishtime = data_new;
                        v.setText(data);

                    }
                }
                Log.e("--555555---", data_new);

            }

        }, year, month, day);
        dlg.show();

    }

    /**
     * 将时间转换为时间戳
     */
    public String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
//            Log.d("--444444---", times);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
