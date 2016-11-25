package cn.xiaocool.wxtteacher.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.jauker.widget.BadgeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SelectClassAdapter;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.camera.DeviceInfoListActivity;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.main.FunctionWeekendPlanActivity;
import cn.xiaocool.wxtteacher.main.SpaceBabyAlbumActivity;
import cn.xiaocool.wxtteacher.main.SpaceClassAttendanceActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickAttendanceActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickClassActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickClassEventActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickConfimActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickCoursewareActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickLeaveActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickRecipesActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickTeacherReviewActivity;
import cn.xiaocool.wxtteacher.main.SpeaceSchoolWebActivity;
import cn.xiaocool.wxtteacher.main.WebClickEditActivity;
import cn.xiaocool.wxtteacher.main.WebClickIntroduceActivity;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by wzh on 2016/2/21.
 */
public class FunctionFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private RelativeLayout baby_courseware, baby_album, baby_confirm, baby_leave, baby_recipes, baby_activity, funciion_schoolweb, rlIntroduce, teacherReview, attendance, class_courseware, function_weekendplan, space_class_attrndance, space_info_shenhe, funciton_header;
    private ImageView space_grown;
    private RoundImageView iv_me_fragment_avatar;
    private TextView teacher_name,teacher_introduce,school_name,baby_video;
    private DisplayImageOptions options;
    private UserInfo user = new UserInfo();
    private LinearLayout class_change;
    private ArrayList<ClassList.ClassListData> arrayList;
    private RequestQueue mQueue;
    private int classIndex = 0;
    public BadgeView btn_daijie,btn_leave;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = (FragmentActivity) getActivity();
        user.readData(mContext);
        mQueue = Volley.newRequestQueue(mContext);
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square)
                .showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
        initView();
    }

    private void initView() {
        arrayList = new ArrayList<>();
        school_name = (TextView) getView().findViewById(R.id.school_name);
        school_name.setText(user.getUserCompany());
        teacher_introduce = (TextView) getView().findViewById(R.id.teacher_introduce);
        class_change = (LinearLayout) getView().findViewById(R.id.class_change);
        class_change.setOnClickListener(this);
        funciton_header = (RelativeLayout) getView().findViewById(R.id.funciton_header);
        space_info_shenhe = (RelativeLayout) getView().findViewById(R.id.space_info_shenhe);
        space_info_shenhe.setOnClickListener(this);
        function_weekendplan = (RelativeLayout) getView().findViewById(R.id.funciton_weekendplan);
        function_weekendplan.setOnClickListener(this);
        funciion_schoolweb = (RelativeLayout) getView().findViewById(R.id.function_schoolweb);
        funciion_schoolweb.setOnClickListener(this);
        baby_courseware = (RelativeLayout) getView().findViewById(R.id.space_courseware);
        baby_courseware.setOnClickListener(this);
        baby_album = (RelativeLayout) getView().findViewById(R.id.space_album);
        baby_album.setOnClickListener(this);
        baby_confirm = (RelativeLayout) getView().findViewById(R.id.space_confirm);
        baby_confirm.setOnClickListener(this);
        baby_leave = (RelativeLayout) getView().findViewById(R.id.space_leave);
        baby_leave.setOnClickListener(this);
        baby_recipes = (RelativeLayout) getView().findViewById(R.id.space_recipes);
        baby_recipes.setOnClickListener(this);
        baby_activity = (RelativeLayout) getView().findViewById(R.id.space_activity);
        baby_activity.setOnClickListener(this);
        rlIntroduce = (RelativeLayout) getView().findViewById(R.id.space_introduce);
        rlIntroduce.setOnClickListener(this);
        teacherReview = (RelativeLayout) getView().findViewById(R.id.space_review);
        teacherReview.setOnClickListener(this);
        attendance = (RelativeLayout) getView().findViewById(R.id.space_attendance);
        attendance.setOnClickListener(this);
        space_class_attrndance = (RelativeLayout) getView().findViewById(R.id.space_class_attrndance);
        space_class_attrndance.setOnClickListener(this);
        class_courseware = (RelativeLayout) getView().findViewById(R.id.space_course);
        class_courseware.setOnClickListener(this);
        space_grown = (ImageView) getView().findViewById(R.id.btn_files);
        space_grown.setOnClickListener(this);

        teacher_name = (TextView) getView().findViewById(R.id.teacher_name);
        iv_me_fragment_avatar = (RoundImageView) getView().findViewById(R.id.iv_me_fragment_avatar);
        iv_me_fragment_avatar.setOnClickListener(this);


        btn_daijie = new BadgeView(getActivity());
        btn_daijie.setTargetView(baby_confirm);
        btn_leave = new BadgeView(getActivity());
        btn_leave.setTargetView(baby_leave);
        baby_video = (TextView) getView().findViewById(R.id.main_baobaoshipin);
        baby_video.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        teacher_name.setText(user.getUserName());
        ImageLoader.getInstance().displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + user.getUserImg(), iv_me_fragment_avatar, options);
        volleyGetClassList();
        setRedPoint();

    }
    private static final String JPUSHDAIJIE = "JPUSHDAIJIE";
    private static final String JPUSHLEAVE = "JPUSHLEAVE";
    /**
     * 设置小红点
     */
    public  void setRedPoint(){
        //从本地取出各个小红点的个数

        int daijieNum = (int) SPUtils.get(getActivity(),JPUSHDAIJIE,0);
        int leaveNum = (int) SPUtils.get(getActivity(),JPUSHLEAVE,0);

        setBadgeView(daijieNum,this.btn_daijie);
        setBadgeView(leaveNum,this.btn_leave);

    }

    /**
     * 设置背景
     * @param message
     * @param message1
     */
    private void setBadgeView(int message, BadgeView message1) {
        if (message1==null)return;
        if (message==0){
            message1.setVisibility(View.GONE);
        }else {
            message1.setVisibility(View.VISIBLE);
            message1.setBadgeCount(message);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //班级课程
            case R.id.space_courseware:
                IntentUtils.getIntent(mContext, SpaceClickClassActivity.class);
                break;
            //班级相册
            case R.id.space_album:
                IntentUtils.getIntent(mContext, SpaceBabyAlbumActivity.class);
                break;
            //成长档案
            case R.id.btn_files:
//                IntentUtils.getIntent(mContext, SpaceClickGrowActivity.class);
                ToastUtils.ToastShort(mContext,"该功能暂未上线，敬请期待!");
                break;
            //待接确认
            case R.id.space_confirm:
                IntentUtils.getIntent(mContext, SpaceClickConfimActivity.class);
                break;
            //在线请假
            case R.id.space_leave:
                IntentUtils.getIntent(mContext, SpaceClickLeaveActivity.class);
                break;
            //本周食谱
            case R.id.space_recipes:
                IntentUtils.getIntent(mContext, SpaceClickRecipesActivity.class);
                break;
            //班级活动
            case R.id.space_activity:
                IntentUtils.getIntent(mContext, SpaceClickClassEventActivity.class);
                break;
            //学校官网
            case R.id.function_schoolweb:
                IntentUtils.getIntent(mContext, SpeaceSchoolWebActivity.class);
                break;
            //院所介绍
            case R.id.space_introduce:
                IntentUtils.getIntent(mContext, WebClickIntroduceActivity.class);
                break;
            //老师点评
            case R.id.space_review:
                IntentUtils.getIntent(mContext, SpaceClickTeacherReviewActivity.class);
                break;
            //老师考勤
            case R.id.space_attendance:
                IntentUtils.getIntent(mContext, SpaceClickAttendanceActivity.class);
                break;
            //班级考勤
            case R.id.space_class_attrndance:
                IntentUtils.getIntent(mContext, SpaceClassAttendanceActivity.class);
                break;
            //班级课件
            case R.id.space_course:
                IntentUtils.getIntent(mContext, SpaceClickCoursewareActivity.class);
                break;
            //周计划
            case R.id.funciton_weekendplan:
                IntentUtils.getIntent(mContext, FunctionWeekendPlanActivity.class);
                break;
            //信息审核
            case R.id.space_info_shenhe:
                ToastUtils.ToastShort(mContext,"该功能暂未上线，敬请期待!");
//                IntentUtils.getIntent(mContext, SpaceClikInfomationActivity.class);
                break;
            //头像
            case R.id.iv_me_fragment_avatar:
                IntentUtils.getIntent(mContext, WebClickEditActivity.class);
                break;

            //切换班级
            case R.id.class_change:
                showPopupWindow();
                break;
            //宝宝视频
            case R.id.main_baobaoshipin:
                Intent intent = new Intent(mContext, DeviceInfoListActivity.class);
                startActivity(intent);
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
        class_change.getLocationOnScreen(location);
        popupWindow.showAsDropDown(class_change);

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mContext.getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                mContext.getWindow().setAttributes(lp);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classIndex = position;
                teacher_introduce.setText(arrayList.get(position).getClassname());
                user.setClassId(arrayList.get(position).getClassid());
                user.writeData(mContext);
                ToastUtils.ToastShort(mContext, "切换成功！");
                popupWindow.dismiss();
            }
        });
    }

    private void volleyGetClassList() {
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
                        arrayList.clear();
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
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        mQueue.add(request);
    }

    private void fillData() {
        if (arrayList.size()>0){
            teacher_introduce.setText(arrayList.get(classIndex).getClassname());
        }else {
            ToastUtils.ToastShort(mContext,"您没有任教班级！");
        }

    }
}