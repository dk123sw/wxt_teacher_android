package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.DateGridAdapter;
import cn.xiaocool.wxtteacher.adapter.TeacherAttendanceAdapter;
import cn.xiaocool.wxtteacher.bean.Attendance;
import cn.xiaocool.wxtteacher.bean.DayModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

import static java.lang.Thread.sleep;

/**
 * Created by wzh on 2016/2/19.
 */
public class SpaceClickAttendanceActivity extends Activity implements View.OnClickListener {
    private RequestQueue mQueue;
    private RelativeLayout up_jiantou;
    private ImageView btn_exit, last_month, next_month;
    private RelativeLayout last_month_layout, next_month_layout;
    private TextView time, mom, title_bar_name, leave_commit;
    private String mYear, mMonth, mDay, mWay;
    private NoScrollGridView grid_date;
    private NoScrollListView teacher_attend_list;
    private TextView year_month;
    private int year, month;
    private int nowyear, nowmonth;
    private TeacherAttendanceAdapter studentGridListAdapter;
    ArrayList<Attendance> attendances;
    private UserInfo user = new UserInfo();
    private String userid;
    private DateGridAdapter dateGridAdapter;
    private ArrayList<DayModel> dateArray;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    if (msg.obj != null) {
                        ProgressViewUtil.dismiss();
                        JSONObject obj = (JSONObject) msg.obj;
                        Log.d("ChooseCollectActivity", obj.optString("status"));
                        if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                            JSONArray dataArray = obj.optJSONArray("data");
                            Log.d("ChooseCollectActivity", obj.optString("data"));
                            JSONObject itemObject;

                            attendances.clear();
                            for (int i = 0; i < dataArray.length(); i++) {
                                itemObject = dataArray.optJSONObject(i);
                                Attendance attendance = new Attendance();

                                attendance.setId(itemObject.optString("id"));
                                attendance.setName(itemObject.optString("name"));
                                attendance.setUserid(itemObject.optString("userid"));
                                attendance.setPhoto(itemObject.optString("photo"));
                                attendance.setSchoolid(itemObject.optString("schoolid"));
                                attendance.setArrivetime(itemObject.optString("arrivetime"));
                                attendance.setLeavetime(itemObject.optString("leavetime"));
                                attendance.setArrivepicture(itemObject.optString("arrivepicture"));
                                attendance.setLeavepicture(itemObject.optString("leavepicture"));
                                attendance.setArrivevideo(itemObject.optString("arrivevideo"));
                                attendance.setLeavevideo(itemObject.optString("leavevideo"));
                                attendance.setCreate_time(itemObject.optString("create_time"));
                                attendance.setType(itemObject.optString("type"));

                                Date date = new Date();
                                date.setTime(Long.parseLong(itemObject.optString("create_time")) * 1000);
                                SimpleDateFormat formatd = new SimpleDateFormat("yyyy-MM-dd");
//                                int day = Integer.parseInt(formatd.format(date));
                                String fomate = formatd.format(date);
                                String a[] = fomate.split("-");
                                int day = 0;
                                if (a.length >= 3) {
                                    day = Integer.parseInt(a[2]);
                                }
                                Log.e("formatd.format(date", day + "");
                                for (int j = 0; j < dateArray.size(); j++) {
                                    if (dateArray.get(j).getDate().equals(String.valueOf(day))) {
                                        dateArray.get(j).setType("3");
                                        break;
                                    }


                                }

                                attendances.add(attendance);
                            }
                            Log.e("ChooseCollectActivity", attendances.size() + "");


                            // 如果老师今天未补签，提示补签，学生不要
                            isShowBuqianWindow();


                            dateGridAdapter.notifyDataSetChanged();
                            studentGridListAdapter.notifyDataSetChanged();

                        }else {
                            if (obj.optString("data").equals("no data")){

                                SimpleDateFormat todayFomate = new SimpleDateFormat("yyyy-MM-dd");
                                String today = todayFomate.format(new Date());
                                String t[] = today.split("-");
                                int tday = 0;
                                int tMonth =0;
                                int tYear =0;
                                if (t.length >= 3) {
                                    tYear = Integer.parseInt(t[0]);
                                    tday = Integer.parseInt(t[2]);
                                    tMonth = Integer.parseInt(t[1]);
                                }
                                if (tYear==year&&tMonth==month){
                                    // 如果老师今天未补签，提示补签，学生不要
                                    isShowBuqianWindow();

                                }


                            }
                        }
                        break;

                    }

                case 123:
                    showDialog();

                    break;
            }
        }
    };

    /**
     * 如果老师今天未补签，提示补签，学生不要
     */
    private void isShowBuqianWindow() {
        if (getIntent().getStringExtra("userid") == null) {
            SimpleDateFormat todayFomate = new SimpleDateFormat("yyyy-MM-dd");
            String today = todayFomate.format(new Date());
            String t[] = today.split("-");
            int tday = 0;
            int tMonth =0;
            int tYear =0;
            if (t.length >= 3) {
                tYear = Integer.parseInt(t[0]);
                tday = Integer.parseInt(t[2]);
                tMonth = Integer.parseInt(t[1]);
            }

            int flag = 0;
            for (int j = 0; j < dateArray.size(); j++) {
                if (tYear==year&&tMonth==month&&dateArray.get(j).getDate().equals(String.valueOf(tday)) && dateArray.get(j).getType().equals("3")) {
                    flag = 1;
                    break;
                }
            }
            if (tYear!=year||tMonth!=month){
                flag = 1;
            }
            if (flag == 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(500); //睡眠500毫秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendEmptyMessage(123);
                        }
                    }
                }).start();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_attendance);
        ProgressViewUtil.show(this);
        user.readData(this);
        userid = user.getUserId();
        dateArray = new ArrayList<>();
        attendances = new ArrayList<>();
        mQueue = Volley.newRequestQueue(this);
        if (getIntent().getStringExtra("userid") != null) {
            userid = getIntent().getStringExtra("userid");
        }
        initView();
        getdate();
    }

    private void initView() {
        attendances = new ArrayList<>();
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        grid_date = (NoScrollGridView) findViewById(R.id.grid_date);
        teacher_attend_list = (NoScrollListView) findViewById(R.id.teacher_attend_list);
        leave_commit = (TextView) findViewById(R.id.leave_commit);
        leave_commit.setOnClickListener(this);
//        studentGridListAdapter = new TeacherAttendanceAdapter(studentDataArrayList,SpaceClickAttendanceActivity.this);
//        teacher_attend_list.setAdapter(studentGridListAdapter);
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        studentGridListAdapter = new TeacherAttendanceAdapter(attendances, SpaceClickAttendanceActivity.this);
        teacher_attend_list.setAdapter(studentGridListAdapter);

        if (getIntent().getStringExtra("titlename") != null) {
            title_bar_name.setText(getIntent().getStringExtra("titlename") + "签到信息");
            leave_commit.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getAttendList();
    }

    /**
     * 获取数据
     */
    private void getAttendList() {
        attendances.clear();
        studentGridListAdapter.notifyDataSetChanged();
        long begintime = TimeToolUtils.getMonthBeginTimestamp(year, month) / 1000;
        long endtime = TimeToolUtils.getMonthEndTimestamp(year, month) / 1000;
        new NewsRequest(this, handler).getTeacherAttendance(String.valueOf(begintime), String.valueOf(endtime), userid, 111);
    }

    private void getdate() {


        //获取当前年月日
        Calendar c = Calendar.getInstance();
        year = getIntent().getIntExtra("year", c.get(Calendar.YEAR));
        month = getIntent().getIntExtra("month", c.get(Calendar.MONTH) + 1);

        nowyear = year;
        nowmonth = month;
        getDateTextArray(year, month);
        dateGridAdapter = new DateGridAdapter(dateArray, this);
        grid_date.setAdapter(dateGridAdapter);
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
                getDateTextArray(year, month);
                dateGridAdapter.notifyDataSetChanged();
//                grid_date.setAdapter(new DateGridAdapter(, getApplicationContext()));

                getAttendList();

            }
        });
        next_month_layout = (RelativeLayout) findViewById(R.id.next_month_layout);
        next_month_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year >= nowyear && month >= nowmonth) {
                    ToastUtils.ToastShort(SpaceClickAttendanceActivity.this, "您切换的月份大于当前月份！");
                } else {
                    month = month + 1;
                    if (month == 13) {
                        month = 1;
                        year = year + 1;
                    }
                    year_month.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月");
                    getDateTextArray(year, month);
                    dateGridAdapter.notifyDataSetChanged();
//                    grid_date.setAdapter(new DateGridAdapter(, getApplicationContext()));

                    getAttendList();
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
            case R.id.leave_commit:
                showPopupMenu();
                break;
        }

    }

    /**
     * 获取该月日历所填充的数据
     */
    private void getDateTextArray(int year, int month) {

        dateArray.clear();
        int datenum;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int a = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("本月第一天是：" + a);

        //判断是否闰年
        if (isRun(year)) {
            //判断月份有多少天
            datenum = getMonthCountForRun(month);
        } else {
            datenum = getMonthCountForNotRun(month);
        }


        for (int i = 0; i < a - 1; i++) {
            DayModel model = new DayModel();
            model.setDate(" ");
            model.setType("0");
            dateArray.add(model);
        }


        for (int i = 0; i < datenum; i++) {

            DayModel dayModel = new DayModel();
            dayModel.setDate(String.valueOf(i + 1));
            if ((a + i) % 7 == 0 || (a + i) % 7 == 1) {
                dayModel.setType("2");
            } else {
                dayModel.setType("1");
            }
            dateArray.add(dayModel);
        }

        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(new Date());

        for (int i = 0; i < dateArray.size(); i++) {
            if (Integer.valueOf(dateArray.get(i).getDate().equals(" ")?"0":dateArray.get(i).getDate())> nowCal.get(Calendar.DAY_OF_MONTH)&&month==nowCal.get(Calendar.MONTH)+1){
                if (dateArray.get(i).getType().equals("1")){

                    dateArray.get(i).setType("4");

                }
            }

        }
    }

    /**
     * 获取闰年该月多少天
     */
    private int getMonthCountForNotRun(int month) {

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            return 28;
        } else {
            return 30;
        }
    }

    /**
     * 获取平年该月多少天
     */
    private int getMonthCountForRun(int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            return 29;
        } else {
            return 30;
        }


    }

    /**
     * 判断是否闰年
     */
    private Boolean isRun(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 显示选择菜单
     */
    private void showPopupMenu() {
        View layout = LayoutInflater.from(this).inflate(R.layout.classattend_menu, null);
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        leave_commit.getLocationOnScreen(location);
        popupWindow.showAsDropDown(leave_commit);

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

        final TextView qiandao_histroy = (TextView) layout.findViewById(R.id.qiandao_histroy);
        TextView quanxian_setting = (TextView) layout.findViewById(R.id.quanxian_setting);
        quanxian_setting.setVisibility(View.GONE);
        qiandao_histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                history();
            }
        });


    }


    /**
     * 签到历史
     */
    private void history() {
        IntentUtils.getIntent(SpaceClickAttendanceActivity.this, AttendanceHistoryActivity.class);
    }


    private void showDialog() {

        // 1.创建弹出式对话框
        final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);    // 系统默认Dialog没有输入框

        // 获取自定义的布局
        View alertDialogView = View.inflate(this, R.layout.buqian_layout, null);
        final AlertDialog tempDialog = alertDialog.create();
        tempDialog.setView(alertDialogView, 0, 0, 0, 0);

        // 确认按钮
        Button btn_dialog_resolve_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_ok);
        btn_dialog_resolve_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {

                sendBuQian();

                tempDialog.dismiss();
            }
        });
        // 取消按钮
        Button btn_dialog_cancel_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_cancel);
        btn_dialog_cancel_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
            // 点击按钮处理
            public void onClick(View v) {
                //
                tempDialog.dismiss();
            }
        });


        tempDialog.show();
    }


    /**
     * 用户补签
     */
    private void sendBuQian() {

        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=resign&userid=" + userid + "&schoolid=" + user.getSchoolId();
        Log.e("sendBuQian", URL);
        URL = URL.trim();
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {

                Log.d("onResponse", arg0);

                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {

                        ToastUtils.ToastShort(SpaceClickAttendanceActivity.this, "补签成功!");
                        getAttendList();

                    } else {
                        ToastUtils.ToastShort(SpaceClickAttendanceActivity.this, "补签失败!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        mQueue.add(request);


    }
}
