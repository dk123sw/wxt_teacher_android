package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.fragment.announce.NoticeReciveFragment;
import cn.xiaocool.wxtteacher.fragment.announce.NoticeSendFragment;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;


/**
 * Created by wzh on 2016/3/17.
 */
public class SpaceClickAnnouActivity extends Activity implements View.OnClickListener {

    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentIndex;
    private NoticeSendFragment collectFinishedFragment;
    private NoticeReciveFragment collectPendingFragment;
    private FragmentManager fragmentManager;
    private RelativeLayout up_jiantou,btn_add;
    private static final String JPUSHNOTICE = "JPUSHNOTICE";
    private void clearJpushNum() {
        SPUtils.remove(this, JPUSHNOTICE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearJpushNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearJpushNum();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearJpushNum();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_click_announcement);
        clearJpushNum();
        initView();

        collectPendingFragment = new NoticeReciveFragment();
        collectFinishedFragment = new NoticeSendFragment();

        //装实例化好的fragment的数组
        fragments = new Fragment[]{collectPendingFragment,collectFinishedFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_content, collectPendingFragment);
        transaction.commit();
        fragmentManager = getFragmentManager();

        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        btn_add = (RelativeLayout) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        up_jiantou.setOnClickListener(this);



    }

    private void initView() {
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        btn_add = (RelativeLayout) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);



        mTabs = new RelativeLayout[2];
        mTabs[0] = (RelativeLayout) findViewById(R.id.address_parent);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (RelativeLayout)findViewById(R.id.address_gardener);
        mTabs[1].setOnClickListener(this);

        //设置第一个按钮为选中状态
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_jiantou:
                finish();
                break;
            case R.id.btn_add:
                showPopupMenu();
                break;
            case R.id.address_parent:
                index = 0;
                break;
            case R.id.address_gardener:
                index = 1;
                break;
        }

        if (currentIndex != index){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()){
                transaction.add(R.id.fragment_content,fragments[index]);

            }
            transaction.show(fragments[index]);
            transaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

    /**
     *显示选择菜单
     * */
    private void showPopupMenu() {

        //自定义布局
        View layout = LayoutInflater.from(this).inflate(R.layout.address_add_menu, null);
        //初始化popwindow
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        int[] location = new int[2];
        btn_add.getLocationOnScreen(location);

        popupWindow.showAsDropDown(btn_add);

        final TextView add_qun = (TextView)layout.findViewById(R.id.add_qun);
        TextView tong_bu = (TextView)layout.findViewById(R.id.tong_bu);

        add_qun.setText("教师公告");
        tong_bu.setText("学生公告");

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

        add_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history();
                popupWindow.dismiss();
            }
        });
        tong_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tongbu();
                popupWindow.dismiss();
            }
        });



    }

    /**
     * 同步通讯录
     * */
    private void tongbu() {
        ToastUtils.ToastShort(this, "学生公告");
        Intent intent = new Intent(this,WriteAnnouncementActivity.class);
        intent.putExtra("type", "student");
        startActivity(intent);

    }

    /**
     * 添加群
     * */
    private void history() {
        ToastUtils.ToastShort(this, "教师公告");

        Intent intent = new Intent(this,WriteAnnouncementActivity.class);
        intent.putExtra("type", "teacher");
        startActivity(intent);
    }



}
