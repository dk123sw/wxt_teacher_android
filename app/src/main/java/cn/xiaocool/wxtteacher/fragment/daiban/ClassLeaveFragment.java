package cn.xiaocool.wxtteacher.fragment.daiban;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassAttendanceAdapter;
import cn.xiaocool.wxtteacher.adapter.StudentGridListAdapter;
import cn.xiaocool.wxtteacher.bean.ClassAttendance;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassLeaveFragment extends Fragment {

    private RelativeLayout last_month, next_month,send_btn;
    private TextView year_month,tv_class_attendance,send_text;
    private Calendar c;
    private NoScrollGridView gv_childrenList;
    private NoScrollListView buqian_list;
    private StudentGridListAdapter studentGridListAdapter;
    private ClassAttendanceAdapter classAttendanceAdapter;
    private View anchor;
    private CheckBox checkbox_all;
    private Context mContext;
    private RequestQueue mQueue;
    private int years, month, day;
    private int nowyear,nowmonth,nowday;
    private ArrayList<ClassAttendance> studentDataArrayList;
    private UserInfo user = new UserInfo();
    private long lastClickTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_class_all, container, false);

        return view;

    }
    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取班级考勤数据
     */
    private void getAttendList() {
//        new NewsRequest(getActivity(),handler).getStudentAttendance("1", 111);

        c.set(years, month-1, day, 0, 0, 0);
        String sign_date = String.valueOf(c.getTimeInMillis()/1000);
        mQueue = Volley.newRequestQueue(mContext);
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetStudentAttendanceList&classid=" + user.getClassId() + "&sign_date=" + sign_date;
        Log.e("getAttendList", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {

                Log.d("onResponse", arg0);
                ProgressViewUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {

                        studentDataArrayList.clear();
                        Gson gson = new Gson();
                        ArrayList<ClassAttendance> arrayList = gson.fromJson(jsonObject.optString("data"), new TypeToken<List<ClassAttendance>>() {
                        }.getType());
                        studentDataArrayList.addAll(arrayList);
                        Log.e("studentDataArrayList", studentDataArrayList.toString());

                        setText();

                        setAdapter();
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

    /**
     * 显示已到，未到，请假人数
     * */
    private void setText() {

        int arrive = 0;
        int leave = 0;
        for (int i=0;i<studentDataArrayList.size();i++){
            if (studentDataArrayList.get(i).getLeavetime()==null||studentDataArrayList.get(i).getLeavetime().equals("0")||studentDataArrayList.get(i).getLeavetime().equals("")){

            }else {
                arrive=arrive+1;
            }
            if (studentDataArrayList.get(i).getStatus().equals("1")){
                leave=leave+1;
            }

        }
        tv_class_attendance.setText("已退:" +arrive  + " 未退:" + (studentDataArrayList.size() - arrive - leave) + " 请假:" + leave);

    }


    /**
     * 用户签退
     *
     * */
    private void sendBuQian() {

        String id = getIds();
        if (id.length()>1){
            c.set(years, month-1, day, 0, 0, 0);
            String sign_date = String.valueOf(c.getTimeInMillis()/1000);
            String URL ="http://wxt.xiaocool.net/index.php?g=apps&m=index&a=LeaveTime&userid="+ id +"&schoolid="+user.getSchoolId()+"&sign_date="+sign_date;
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

                            ToastUtils.ToastShort(mContext,"签退成功!");
                            getAttendList();

                        }else {
                            ToastUtils.ToastShort(mContext, "签退失败" );
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
        }else {
            ToastUtils.ToastShort(mContext,"暂无需要签退的人！");
        }



    }

    /**
     * 获取补签的id
     * */
    private String getIds() {
        String ids = "";
        for (int i=0;i<studentDataArrayList.size();i++){
            if (studentDataArrayList.get(i).getCheckedTypeByGo().equals("2")){
                ids = ids+","+studentDataArrayList.get(i).getUserid();
            }
        }
        if (ids.length()>1){
            ids= ids.substring(1,ids.length());
        }

        Log.e("ids",ids);
        return ids;
    }

    /**
     * 设置适配器
     * */
    private void setAdapter() {
        if (studentGridListAdapter!=null){
            studentGridListAdapter.notifyDataSetChanged();
        }else {
            studentGridListAdapter = new StudentGridListAdapter(studentDataArrayList,mContext,"2");
            gv_childrenList.setAdapter(studentGridListAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAttendList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentDataArrayList = new ArrayList<>();
        mContext = getActivity();
        ProgressViewUtil.show(mContext);
        mQueue = Volley.newRequestQueue(mContext);
        user.readData(mContext);
        initView();
    }

    /**
     * 初始化控件
     * */
    private void initView() {

        send_text = (TextView) getView().findViewById(R.id.send_text);
        send_text.setText("签退");

        tv_class_attendance = (TextView) getView().findViewById(R.id.tv_class_attendance);
        checkbox_all = (CheckBox) getView().findViewById(R.id.checkbox_all);
        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkall();
            }
        });
        send_btn = (RelativeLayout) getView().findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick())return;
                sendBuQian();
            }
        });
        anchor = getView().findViewById(R.id.anchor);
        gv_childrenList = (NoScrollGridView) getView().findViewById(R.id.gv_childrenList);
        buqian_list = (NoScrollListView) getView().findViewById(R.id.buqian_list);


        gv_childrenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (studentDataArrayList.get(position).getCheckedTypeByGo().equals("1")) {
                    studentDataArrayList.get(position).setCheckedType("2");
                    setcheckedall();
                } else if (studentDataArrayList.get(position).getCheckedTypeByGo().equals("2")) {
                    studentDataArrayList.get(position).setCheckedType("1");
                    setcheckedall();
                }


                Log.e("dsdsd", "sadsad");
            }
        });


        //获取今天的年月
        c = Calendar.getInstance();
        years = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        nowyear = years;
        nowmonth = month;
        nowday = day;

        year_month = (TextView) getView().findViewById(R.id.year_month);
        year_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateText(year_month);
            }
        });
        year_month.setText(String.valueOf(years) + "-" + String.valueOf(month) + "-"+ String.valueOf(day));
        Log.d("年-月", String.valueOf(years) + String.valueOf(month));
        last_month = (RelativeLayout) getView().findViewById(R.id.rl_last);
        last_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = day - 1;
                if (day == 0) {
                    c.set(years, month - 1, day);
                    day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    month = month - 1;
                    if (month == 0) {
                        month = 12;
                        years = years - 1;
                    }
                }
                c.set(years, month, day);
                getAttendList();


                year_month.setText(String.valueOf(years) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
            }
        });
        next_month = (RelativeLayout) getView().findViewById(R.id.rl_next);
        next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (years>=nowyear&&month>=nowmonth&&day>=nowday){
                    ToastUtils.ToastShort(getActivity(),"您切换的日期大于当前日期！");

                }else {

                    day = day + 1;
                    Log.e("getMonthDay",getMonthDay()+"");
                    if (day == getMonthDay()+1) {

                        day = 1;
                        month = month + 1;
                        if (month == 13) {
                            month = 1;
                            years = years + 1;
                        }
                    }
                    c.set(years, month, day);
                    getAttendList();
                    year_month.setText(String.valueOf(years) + "-" + String.valueOf(month) + "-" + String.valueOf(day));

                }

            }
        });
    }

    private void setcheckedall() {
        int flag =0;
        for (int i=0;i<studentDataArrayList.size();i++){
            if (studentDataArrayList.get(i).getCheckedTypeByGo().equals("1")) {
                flag =1;
            }
        }

        if (flag==0){

            checkbox_all.setChecked(true);
        }else {
            checkbox_all.setChecked(false);
        }

        studentGridListAdapter.notifyDataSetChanged();
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
    /**
     * 将可以补签的人全选
     */
    private void checkall() {

        if (checkbox_all.isChecked()){

            for (int i = 0; i < studentDataArrayList.size(); i++) {
                if (studentDataArrayList.get(i).getCheckedTypeByGo().equals("1")) {
                    studentDataArrayList.get(i).setCheckedType("2");
                }

            }

        }else {

            for (int i = 0; i < studentDataArrayList.size(); i++) {
                if (studentDataArrayList.get(i).getCheckedTypeByGo().equals("2")) {
                    studentDataArrayList.get(i).setCheckedType("1");
                }

            }

        }


        studentGridListAdapter.notifyDataSetChanged();

    }




    private void setDateText(final TextView v) {
        Calendar cal = Calendar.getInstance();
        Date myData = new Date();
        cal.setTime(myData);

        //获取系统的时间
        years = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);

        Log.e("MONTH", "year" + years);
        Log.e("MONTH", "month" + month);
        Log.e("MONTH", "day" + day);
        Log.e("MONTH", "hour" + hour);
        Log.e("MONTH", "minute" + minute);
        Log.e("MONTH", "second" + second);

        DatePickerDialog dlg = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("MONTH", "monthOfYear" + monthOfYear);
                monthOfYear += 1;//monthOfYear 从0开始

                if (year>=nowyear&&monthOfYear>=nowmonth&&dayOfMonth>=nowday){
                    ToastUtils.ToastShort(getActivity(),"您选择的日期大于今天日期！");
                }else {

                    years = year;
                    month = monthOfYear;
                    day = dayOfMonth;

                    String data = year + "-" + monthOfYear + "-" + dayOfMonth;
                    v.setText(data);

                    getAttendList();
                }


            }

        }, years, month, day);
        dlg.show();

    }



}
