package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassScheduleExAdapter;
import cn.xiaocool.wxtteacher.adapter.SelectClassAdapter;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by wzh on 2016/1/29.
 */
public class SpaceClickClassActivity extends Activity implements View.OnClickListener {

    private RelativeLayout up_jiantou;
    private ExpandableListView class_schedule_list;
    private ArrayList<ArrayList<String>> classlists;
    private ClassScheduleExAdapter classScheduleExAdapter;
    private RequestQueue mQueue;
    private Context mContext;
    private UserInfo user = new UserInfo();
    private ArrayList<ClassList.ClassListData> arrayList;
    private KProgressHUD hud;
    private TextView scca_top_title;
    private LinearLayout change_class;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.CLASS_SCHEDULE:
                    if (msg.obj !=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String state = obj.optString("status");
                            if (state.equals(CommunalInterfaces._STATE)){
                                JSONObject jsonObject = obj.optJSONObject("data");
                                classlists.clear();

                                JSONArray mon = jsonObject.optJSONArray("mon");
                                ArrayList<String> monArray = new ArrayList<>();
                                for (int i=0;i<mon.length();i++){
                                    JSONObject monObject = mon.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    monArray.add(monObject.optString(pos));
                                }
                                classlists.add(monArray);

                                JSONArray tu = jsonObject.optJSONArray("tu");
                                ArrayList<String> tuArray = new ArrayList<>();
                                for (int i=0;i<tu.length();i++){
                                    JSONObject tuObject = tu.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    tuArray.add(tuObject.optString(pos));
                                }
                                classlists.add(tuArray);

                                JSONArray we = jsonObject.optJSONArray("we");
                                ArrayList<String> weArray = new ArrayList<>();
                                for (int i=0;i<we.length();i++){
                                    JSONObject weObject = we.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    weArray.add(weObject.optString(pos));
                                }
                                classlists.add(weArray);

                                JSONArray th = jsonObject.optJSONArray("th");
                                ArrayList<String> thArray = new ArrayList<>();
                                for (int i=0;i<th.length();i++){
                                    JSONObject thObject = th.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    thArray.add(thObject.optString(pos));
                                }
                                classlists.add(thArray);

                                JSONArray fri = jsonObject.optJSONArray("fri");
                                ArrayList<String> friArray = new ArrayList<>();
                                for (int i=0;i<fri.length();i++){
                                    JSONObject friObject = fri.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    friArray.add(friObject.optString(pos));
                                }
                                classlists.add(friArray);

                                JSONArray sat = jsonObject.optJSONArray("sat");
                                ArrayList<String> satArray = new ArrayList<>();
                                for (int i=0;i<sat.length();i++){
                                    JSONObject satObject = sat.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    satArray.add(satObject.optString(pos));
                                }
                                classlists.add(satArray);

                                JSONArray sun = jsonObject.optJSONArray("sun");
                                ArrayList<String> sunArray = new ArrayList<>();
                                for (int i=0;i<sun.length();i++){
                                    JSONObject sunObject = sun.optJSONObject(i);
                                    String pos = ""+(i+1);
                                    sunArray.add(sunObject.optString(pos));
                                }
                                classlists.add(sunArray);


                                if (classScheduleExAdapter!=null){
                                    classScheduleExAdapter.notifyDataSetChanged();
                                }else {
                                    classScheduleExAdapter = new ClassScheduleExAdapter(classlists,SpaceClickClassActivity.this);
                                    class_schedule_list.setAdapter(classScheduleExAdapter);
                                }

                                int groupCount = class_schedule_list.getCount();
                                for (int i=0; i<groupCount; i++) {
                                    class_schedule_list.expandGroup(i);
                                }

                                hud.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("课表数据异常","dsadsad");
                            hud.dismiss();
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
        setContentView(R.layout.space_class);
        mContext = this;
        mQueue = Volley.newRequestQueue(mContext);
        user.readData(mContext);
        arrayList = new ArrayList<>();
        initView();

    }
    private void initView() {
        classlists = new ArrayList<>();
        class_schedule_list = (ExpandableListView) findViewById(R.id.class_schedule_list);
        class_schedule_list.setGroupIndicator(null);

        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);


        change_class = (LinearLayout) findViewById(R.id.change_class);
        scca_top_title = (TextView) findViewById(R.id.scca_top_title);
        change_class.setOnClickListener(this);

        //获取班级
        volleyGetClassList();

    }

    private void volleyGetClassList() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        hud.show();
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=getteacherclasslist&teacherid="+user.getUserId();
        Log.e("uuuurrrrll", URL);
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
                ToastUtils.ToastShort(mContext, arg0.toString());
                hud.dismiss();
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        mQueue.add(request);
    }

    private void fillData() {
        if (arrayList.size()>0){

            new NewsRequest(SpaceClickClassActivity.this,handler).classSchedule(arrayList.get(0).getClassid());
            scca_top_title.setText(arrayList.get(0).getClassname());
        }else {
            hud.dismiss();
            ToastUtils.ToastShort(mContext,"您没有任教班级！");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;

            case R.id.change_class:
                showPopupWindow();
                break;
        }

    }

    //显示下拉列表
    private void showPopupWindow() {
        /**
         *显示选择菜单
         * */
        View layout = LayoutInflater.from(mContext).inflate(R.layout.select_class_menu, null);
        ListView list = (ListView) layout.findViewById(R.id.select_class_list);
        SelectClassAdapter adapter = new SelectClassAdapter(mContext,arrayList);
        list.setAdapter(adapter);
        //初始化popwindow
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        final PopupWindow popupWindow = new PopupWindow(layout, width, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        int[] location = new int[2];
        up_jiantou.getLocationOnScreen(location);
        popupWindow.showAsDropDown(up_jiantou);

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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hud = KProgressHUD.create(mContext)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setDetailsLabel("切换班级中...")
                        .setCancellable(true);
                hud.show();
                scca_top_title.setText(arrayList.get(position).getClassname());
                new NewsRequest(SpaceClickClassActivity.this,handler).classSchedule(arrayList.get(position).getClassid());
                popupWindow.dismiss();
            }
        });
    }

}
