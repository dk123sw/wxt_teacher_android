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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.RecipesAdapter;
import cn.xiaocool.wxtteacher.bean.RecipeInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.utils.DateUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by wzh on 2016/3/27.
 */
public class SpaceClickRecipesActivity extends Activity implements View.OnClickListener {
    private RelativeLayout up_jiantou, btn_add;
    private ListView recipesView;
    private RecipesAdapter recipesAdapter;
    private ArrayList<RecipeInfo> recipeDatas,recipeInfoArrayList;
    private Context context;
    private View location_pop;
    private RelativeLayout iv_left, iv_right;
    private String begindate, enddate;
    private TextView tv_date;
    private Date date;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.RECIPESNEW:
                    if (((JSONObject) msg.obj).optJSONArray("data")!=null){
                        ProgressViewUtil.dismiss();
                        if (((JSONObject) msg.obj).optJSONArray("data").length()>0) {
                            JSONObject obj = (JSONObject) msg.obj;
                            JSONArray hwArray = obj.optJSONArray("data");
                            recipeDatas.clear();
                            recipeDatas = new ArrayList<>();
                            String str = hwArray.optJSONObject(0).optString("date") + "000";
                            Long t1 = Long.parseLong(str);
                            Long t2 = DateUtils.currentWeekMonday(new Date(t1)).getTime() / 1000;
                            Log.e("str----", t2.toString());
                            RecipeInfo recipeInfo1 = new RecipeInfo();
                            RecipeInfo recipeInfo2 = new RecipeInfo();
                            RecipeInfo recipeInfo3 = new RecipeInfo();
                            RecipeInfo recipeInfo4 = new RecipeInfo();
                            RecipeInfo recipeInfo5 = new RecipeInfo();
                            RecipeInfo recipeInfo6 = new RecipeInfo();
                            RecipeInfo recipeInfo7 = new RecipeInfo();
                            for (int i = 0; i < hwArray.length(); i++) {
                                JSONObject object = hwArray.optJSONObject(i);
                                if (object.optString("date").equals(t2.toString())) {
                                    recipeInfo1.setWeek("星期一");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo1.getData().add(data);
                                }
                                if (object.optString("date").equals(String.valueOf(t2 + 86400))) {
                                    recipeInfo2.setWeek("星期二");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo2.getData().add(data);
                                }
                                if (object.optString("date").equals(String.valueOf(t2 + 86400 * 2))) {
                                    recipeInfo3.setWeek("星期三");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo3.getData().add(data);
                                }
                                if (object.optString("date").equals(String.valueOf(t2 + 86400 * 3))) {
                                    recipeInfo4.setWeek("星期四");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo4.getData().add(data);
                                }
                                if (object.optString("date").equals(String.valueOf(t2 + 86400 * 4))) {
                                    recipeInfo5.setWeek("星期五");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo5.getData().add(data);
                                }
                                if (object.optString("date").equals(String.valueOf(t2 + 86400 * 5))) {
                                    recipeInfo6.setWeek("星期六");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo6.getData().add(data);
                                }
                                if (object.optString("date").equals(String.valueOf(t2 + 86400 * 6))) {
                                    recipeInfo7.setWeek("星期日");
                                    RecipeInfo.RecipeData data = new RecipeInfo.RecipeData();
                                    data.setContent(object.optString("content"));
                                    data.setTitle(object.optString("title"));
                                    data.setPhoto(object.optString("photo"));
                                    recipeInfo7.getData().add(data);
                                }
                            }
                            recipeDatas.add(recipeInfo1);
                            recipeDatas.add(recipeInfo2);
                            recipeDatas.add(recipeInfo3);
                            recipeDatas.add(recipeInfo4);
                            recipeDatas.add(recipeInfo5);
                            recipeDatas.add(recipeInfo6);
                            recipeDatas.add(recipeInfo7);
                            recipeInfoArrayList = new ArrayList<>();
                            for (int i = 0; i < recipeDatas.size(); i++) {
                                if (recipeDatas.get(i).getData().size() > 0) {
                                    recipeInfoArrayList.add(recipeDatas.get(i));
                                }
                            }
                            recipesAdapter = new RecipesAdapter(recipeInfoArrayList, SpaceClickRecipesActivity.this);
                            recipesView.setAdapter(recipesAdapter);
                        }else{
                            recipeDatas.clear();
                            recipesAdapter = new RecipesAdapter(recipeDatas, SpaceClickRecipesActivity.this);
                            recipesView.setAdapter(recipesAdapter);
                        }
                    }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_recipes_click);
        context = this;
        ProgressViewUtil.show(this);
        initView();
        showData();
        //发起网络请求
        //new NewsRequest(this, handler).recipes();
        getRecipes();
        //设置适配器
//        mondayAdapter = new SpaceRecipesAdapter(this,mondayList);
//        mondayView.setAdapter(mondayAdapter);
//        tuesdayAdapter = new SpaceRecipesAdapter(this,tuesdayList);
//        tuesdayView.setAdapter(tuesdayAdapter);
//        wednesdayAdapter = new SpaceRecipesAdapter(this,wednesdayList);
//        wednesdayView.setAdapter(wednesdayAdapter);
//        thursdayAdapter = new SpaceRecipesAdapter(this,thursdayList);
//        thursdayView.setAdapter(thursdayAdapter);
//        fridayAdapter = new SpaceRecipesAdapter(this,fridayList);
//        fridayView.setAdapter(fridayAdapter);
        //设置ListView点击事件
        //setOnClick();
    }

    private void showData() {

    }

    private void initView() {
        //初始化ListView组件
        tv_date = (TextView) findViewById(R.id.recipes_tv_date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        begindate = String.valueOf(DateUtils.lastDayWholePointDate(DateUtils.currentWeekMonday(date)).getTime() / 1000);
        Log.e("hello---------", begindate);
        enddate = String.valueOf(DateUtils.nextDayWholePointDate(DateUtils.currentWeekSunday(date)).getTime() / 1000);
        tv_date.setText(df.format(date));
        iv_left = (RelativeLayout) findViewById(R.id.recipes_iv_left);
        iv_left.setOnClickListener(this);
        iv_right = (RelativeLayout) findViewById(R.id.recipes_iv_right);
        iv_right.setOnClickListener(this);
        recipeDatas = new ArrayList<>();
        location_pop = findViewById(R.id.location_pop);
        recipesView = (ListView) findViewById(R.id.space_recipes_click_list);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        switch (v.getId()) {
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.btn_add:
                showPopupMenu();
                break;
            case R.id.recipes_iv_left:
                //上一周
                date = DateUtils.lastWeekMonday(date);
                begindate = String.valueOf(DateUtils.lastDayWholePointDate(DateUtils.currentWeekMonday(date)).getTime() / 1000);
                enddate = String.valueOf(DateUtils.nextDayWholePointDate(DateUtils.currentWeekSunday(date)).getTime() / 1000);
                getRecipes();
                tv_date.setText(df.format(date));
                break;
            case R.id.recipes_iv_right:
                //下一周
                date = DateUtils.nextWeekMonday(date);
                begindate = String.valueOf(DateUtils.lastDayWholePointDate(DateUtils.currentWeekMonday(date)).getTime() / 1000);
                enddate = String.valueOf(DateUtils.nextDayWholePointDate(DateUtils.currentWeekSunday(date)).getTime() / 1000);
                getRecipes();
                tv_date.setText(df.format(date));
                break;
        }
    }

    /**
     * 显示选择菜单
     */
    private void showPopupMenu() {
        View layout = LayoutInflater.from(this).inflate(R.layout.classattend_menu, null);
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

        final TextView qiandao_histroy = (TextView) layout.findViewById(R.id.qiandao_histroy);
        TextView quanxian_setting = (TextView) layout.findViewById(R.id.quanxian_setting);

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

    private void getRecipes() {
        new NewsRequest(this, handler).getRecipes(begindate, enddate);
    }

    /**
     * 权限设置
     */
    private void setting() {
        ToastUtils.ToastShort(this, "权限设置");
    }

    /**
     * 签到历史
     */
    private void history() {
        ToastUtils.ToastShort(this, "签到历史");
    }

}
