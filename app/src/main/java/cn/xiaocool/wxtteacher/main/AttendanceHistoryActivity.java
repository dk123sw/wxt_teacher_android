package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SelectClassAdapter;
import cn.xiaocool.wxtteacher.adapter.StuAttendanceInfoAdapter;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.bean.StudentData;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class AttendanceHistoryActivity extends Activity implements View.OnClickListener, NumberPicker.Formatter, NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener {
    private Context context;
    private RelativeLayout rl_back;
    private ListView lv_attendance;
    private LinearLayout class_select,month_select;
    private TextView class_text,year_month_text;
    private KProgressHUD hud;
    private ArrayList<ClassList.ClassListData> arrayList;
    private UserInfo user = new UserInfo();
    private RequestQueue mQueue;
    private int years, month;
    private int nowyear,nowmonth;
    private Calendar c;
    private NumberPicker numberPicker1,numberPicker2;
    private TextView button1,button2;
    private ArrayList<StudentData> studentDatas;
    private StuAttendanceInfoAdapter adapter;
    private String classid;
    private long begintime,endtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_attendance_history);
        context = this;
        user.readData(context);
        classid = user.getClassId();
        mQueue = Volley.newRequestQueue(context);
        arrayList = new ArrayList<>();
        studentDatas = new ArrayList<>();
        initView();
        volleyGetClassList();

    }

    private void initView() {
        lv_attendance = (ListView) findViewById(R.id.attendance_list);
        lv_attendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,SpaceClickAttendanceActivity.class);
                intent.putExtra("userid",studentDatas.get(position).getId());
                intent.putExtra("titlename",studentDatas.get(position).getName());
                intent.putExtra("begintime",String.valueOf(begintime));
                intent.putExtra("endtime",String.valueOf(endtime));
                intent.putExtra("year",years);
                intent.putExtra("month",month);
                startActivity(intent);
            }
        });
        rl_back = (RelativeLayout) findViewById(R.id.up_jiantou);
        rl_back.setOnClickListener(this);
        class_select = (LinearLayout) findViewById(R.id.class_select);
        class_select.setOnClickListener(this);
        month_select = (LinearLayout) findViewById(R.id.month_select);
        month_select.setOnClickListener(this);

        class_text = (TextView) findViewById(R.id.class_text);
        year_month_text = (TextView) findViewById(R.id.year_month_text);


        setYearAndMonthText();
    }

    private void setYearAndMonthText() {
        c = Calendar.getInstance();
        years = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        nowyear =years;
        nowmonth =month;
        year_month_text.setText(String.valueOf(years) + "年" + String.valueOf(month) + "月");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;

            case R.id.class_select:
                showClassListPopupWindow();
                break;

            case R.id.month_select:
//                setDateText(year_month_text);
                showPopupWindow();
                break;
        }
    }

    //显示下拉列表
    private void showClassListPopupWindow() {
        /**
         *显示选择菜单
         * */
        View layout = LayoutInflater.from(context).inflate(R.layout.select_class_menu, null);
        ListView list = (ListView) layout.findViewById(R.id.select_class_list);
        SelectClassAdapter adapter = new SelectClassAdapter(context,arrayList);
        list.setAdapter(adapter);
        //初始化popwindow
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = class_select.getWidth();
        final PopupWindow popupWindow = new PopupWindow(layout, width, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        int[] location = new int[2];
        class_select.getLocationOnScreen(location);
        popupWindow.showAsDropDown(class_select);

//        // 设置背景颜色变暗
//        final WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                lp.alpha = 1.0f;
//                getWindow().setAttributes(lp);
//            }
//        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                hud = KProgressHUD.create(context)
//                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                        .setDetailsLabel("切换班级中...")
//                        .setCancellable(true);
//                hud.show();
                class_text.setText(arrayList.get(position).getClassname());
                classid = arrayList.get(position).getClassid();

                VolleyGetData();
                popupWindow.dismiss();
            }
        });
    }

    private void volleyGetClassList() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        hud.show();
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=getteacherclasslist&teacherid="+user.getUserId();
        Log.e("volleyGetClassList", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                Log.d("onResponse", arg0);


                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {
                        JSONArray dataArray = jsonObject.optJSONArray("data");
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject dataObject = dataArray.optJSONObject(i);
                            ClassList.ClassListData classListData = new ClassList.ClassListData();
                            classListData.setClassid(dataObject.optString("classid"));
                            classListData.setClassname(dataObject.optString("classname"));
                            arrayList.add(classListData);
                        }


                        fillData();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ToastUtils.ToastShort(context, arg0.toString());
                hud.dismiss();
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        mQueue.add(request);
    }

    private void fillData() {
        if (arrayList.size()>0){
            for (int i=0;i<arrayList.size();i++){
                if (user.getClassId().equals(arrayList.get(i).getClassid())){
                    hud.dismiss();
                    class_text.setText(arrayList.get(i).getClassname());
                    VolleyGetData();
                    break;
                }
            }

            hud.dismiss();

        }else {
            hud.dismiss();
            ToastUtils.ToastShort(context,"您没有任教班级！");
        }

    }

    /**
     * volley 根据班级获取班级考勤情况数据
     * */
    private void VolleyGetData() {

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        hud.show();


        begintime = TimeToolUtils.getMonthBeginTimestamp(years, month)/1000;
        endtime = TimeToolUtils.getMonthEndTimestamp(years, month)/1000;

        String url = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetStudentAttendanceDays&userid="+user.getUserId()+"&begintime="+begintime+"&endtime="+endtime+"&classid="+classid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        if (response.optString("status").equals("success")){

                            studentDatas.clear();
                            Gson gson =new Gson();
                            ArrayList<StudentData> arrayList = gson.fromJson(response.optString("data"),new TypeToken<List<StudentData>>(){}.getType());
                            studentDatas.addAll(arrayList);

                            hud.dismiss();
                            if (adapter!=null){
                                adapter.notifyDataSetChanged();
                            }else {
                                adapter = new StuAttendanceInfoAdapter(studentDatas,context,getMonthDay());
                                lv_attendance.setAdapter(adapter);
                            }
                        }else {
                            hud.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                hud.dismiss();
            }
        });

        mQueue.add(jsonObjectRequest);

    }


    private void showPopupWindow() {
        View layout = LayoutInflater.from(this).inflate(R.layout.select_height_popview, null);
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(lv_attendance, Gravity.BOTTOM, 0, 0);

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
        numberPicker1 = (NumberPicker) layout.findViewById(R.id.picker1);
        numberPicker2 = (NumberPicker) layout.findViewById(R.id.picker2);
        numberPicker1.setFormatter(this);
        numberPicker1.setOnValueChangedListener(this);
        numberPicker1.setOnScrollListener(this);
        numberPicker1.setMaxValue(2100);
        numberPicker1.setMinValue(0);
        numberPicker1.setValue(years);
        numberPicker2.setOnValueChangedListener(this);
        numberPicker2.setMaxValue(12);
        numberPicker2.setMinValue(0);
        numberPicker2.setValue(month);
        button1 = (TextView) layout.findViewById(R.id.btn_confirm);
        button2 = (TextView) layout.findViewById(R.id.btn_cancel);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (numberPicker1.getValue()>=nowyear&&numberPicker2.getValue()>=nowmonth){
                    ToastUtils.ToastShort(AttendanceHistoryActivity.this,"您选择的年月大于当前年月！");

                }else {
                    year_month_text.setText(numberPicker1.getValue() + "年" + numberPicker2.getValue()+"月");
                    years = numberPicker1.getValue();
                    month = numberPicker2.getValue();
                    c.set(numberPicker1.getValue(),numberPicker2.getValue(),1);
                    VolleyGetData();

                }


                popupWindow.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 12) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        picker.setValue(newVal);
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {

    }

    private int getMonthDay() {
        //判断是否闰年
        if (isRun(years)){
            //判断月份有多少天
            return getMonthCountForRun(month);
        }else {
            return getMonthCountForNotRun(month);
        }

    }


    /**
     * 获取闰年该月多少天
     * */
    private int getMonthCountForNotRun(int month) {

        if (month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            return 31;
        }else if (month==2){
            return 28;
        }else {
            return 30;
        }
    }

    /**
     * 获取平年该月多少天
     * */
    private int getMonthCountForRun(int month) {
        if (month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            return 31;
        }else if (month==2){
            return 29;
        }else {
            return 30;
        }



    }

    /**
     * 判断是否闰年
     * */
    private Boolean isRun(int year) {
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
            return true;
        }else{
            return false;
        }
    }
}
