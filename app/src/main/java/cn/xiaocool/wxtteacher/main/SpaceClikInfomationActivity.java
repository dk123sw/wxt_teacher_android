package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.fragment.shenhe.AnnocementFragment;
import cn.xiaocool.wxtteacher.fragment.shenhe.NewsGroupFragment;
import cn.xiaocool.wxtteacher.fragment.shenhe.TrendFragment;
import cn.xiaocool.wxtteacher.utils.IntentUtils;

public class SpaceClikInfomationActivity extends Activity implements View.OnClickListener{



    private ImageView btn_exit;
    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentIndex;
    private TrendFragment collectFinishedFragment;
    private NewsGroupFragment collectOverdueFragment;
    private AnnocementFragment collectPendingFragment;
    private FragmentManager fragmentManager;
    private TextView add_collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_space_clik_infomation);
        initView();

        collectPendingFragment = new AnnocementFragment();
        collectFinishedFragment = new TrendFragment();
        collectOverdueFragment = new NewsGroupFragment();
        //装实例化好的fragment的数组
        fragments = new Fragment[]{collectPendingFragment,collectFinishedFragment,collectOverdueFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_content, collectPendingFragment);
        transaction.commit();
        fragmentManager = getFragmentManager();
    }

    private void initView() {
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
//        buttons = new Button[3];
//        buttons[0] = (Button) findViewById(R.id.btn_suspend);
//        buttons[1] = (Button) findViewById(R.id.btn_finished);
//        buttons[2] = (Button) findViewById(R.id.btn_expired);
//        buttons[0].setOnClickListener(this);
//        buttons[1].setOnClickListener(this);
//        buttons[2].setOnClickListener(this);
//        buttons[0].setSelected(true);
        add_collect = (TextView) findViewById(R.id.add_collect);
        add_collect.setOnClickListener(this);
        mTabs = new RelativeLayout[4];
        mTabs[0] = (RelativeLayout) findViewById(R.id.address_parent);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (RelativeLayout)findViewById(R.id.address_gardener);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (RelativeLayout)findViewById(R.id.address_group_chat);
        mTabs[2].setOnClickListener(this);
        //设置第一个按钮为选中状态
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                finish();
                break;
            case R.id.add_collect:
                IntentUtils.getIntent(SpaceClikInfomationActivity.this, ClikInfomationPrivilegeActivity.class);
                break;
            case R.id.address_parent:
                index = 0;
                break;
            case R.id.address_gardener:
                index = 1;
                break;
            case R.id.address_group_chat:
                index = 2;
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
}
