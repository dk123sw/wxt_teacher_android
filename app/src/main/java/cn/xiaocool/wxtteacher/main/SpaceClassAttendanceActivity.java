package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sevenheaven.segmentcontrol.SegmentControl;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.fragment.daiban.ClassAllFragment;
import cn.xiaocool.wxtteacher.fragment.daiban.ClassLeaveFragment;
import cn.xiaocool.wxtteacher.utils.IntentUtils;


public class SpaceClassAttendanceActivity extends Activity implements View.OnClickListener{
    private RelativeLayout up_jiantou;
    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentIndex;
    private ClassLeaveFragment collectFinishedFragment;
    private ClassAllFragment collectPendingFragment;
    private FragmentManager fragmentManager;
    private ImageView add_collect;
    private View location_pop;
    private SegmentControl mSegmentHorzontal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_space_class_attendance);
        initView();

        collectPendingFragment = new ClassAllFragment();
        collectFinishedFragment = new ClassLeaveFragment();

        //装实例化好的fragment的数组
        fragments = new Fragment[]{collectPendingFragment,collectFinishedFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_content, collectPendingFragment);
        transaction.commit();
        fragmentManager = getFragmentManager();
    }

    private void initView() {
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
//        buttons = new Button[3];
//        buttons[0] = (Button) findViewById(R.id.btn_suspend);
//        buttons[1] = (Button) findViewById(R.id.btn_finished);
//        buttons[2] = (Button) findViewById(R.id.btn_expired);
//        buttons[0].setOnClickListener(this);
//        buttons[1].setOnClickListener(this);
//        buttons[2].setOnClickListener(this);
//        buttons[0].setSelected(true);
        location_pop = findViewById(R.id.location_pop);
        add_collect = (ImageView) findViewById(R.id.add_collect);
        add_collect.setOnClickListener(this);
        mTabs = new RelativeLayout[4];
//        mTabs[0] = (RelativeLayout) findViewById(R.id.address_parent);
//        mTabs[0].setOnClickListener(this);
//        mTabs[1] = (RelativeLayout)findViewById(R.id.address_gardener);
//        mTabs[1].setOnClickListener(this);

        //设置第一个按钮为选中状态
//        mTabs[0].setSelected(true);

        mSegmentHorzontal = (SegmentControl) findViewById(R.id.segment_control);
        mSegmentHorzontal.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                Log.i("Tag", "onSegmentControlClick: index = " + index);
                if (currentIndex != index){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.hide(fragments[currentIndex]);
                    if (!fragments[index].isAdded()){
                        transaction.add(R.id.fragment_content,fragments[index]);

                    }
                    transaction.show(fragments[index]);
                    transaction.commit();
                }
//                mTabs[currentIndex].setSelected(false);
//                mTabs[index].setSelected(true);
                currentIndex = index;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.add_collect:
                showPopupMenu();
                break;

        }

    }


    /**
     *显示选择菜单
     * */
    private void showPopupMenu() {
        View layout = LayoutInflater.from(this).inflate(R.layout.classattend_menu,null);
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
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

        qiandao_histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                history();
            }
        });
        quanxian_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setting();
            }
        });

    }

    /**
     * 权限设置
     * */
    private void setting() {
        IntentUtils.getIntent(SpaceClassAttendanceActivity.this,AttendancePrivilegeActivity.class);
    }

    /**
     * 签到历史
     * */
    private void history() {
        IntentUtils.getIntent(SpaceClassAttendanceActivity.this,AttendanceHistoryActivity.class);
    }
}
