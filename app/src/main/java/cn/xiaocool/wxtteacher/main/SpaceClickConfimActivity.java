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

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.fragment.CollectFinishedFragment;
import cn.xiaocool.wxtteacher.fragment.CollectPendingFragment;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;

/**
 * Created by wzh on 2016/1/29.
 */
public class SpaceClickConfimActivity extends Activity implements View.OnClickListener {

    private RelativeLayout up_jiantou;
    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentIndex;
    private CollectFinishedFragment collectFinishedFragment;
    private CollectPendingFragment collectPendingFragment;
    private FragmentManager fragmentManager;
    private ImageView add_collect;
    private static final String JPUSHDAIJIE = "JPUSHDAIJIE";
    private void clearJpushNum() {
        SPUtils.remove(this, JPUSHDAIJIE);
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
        setContentView(R.layout.space_confirm_click);
        initView();

        clearJpushNum();
        collectPendingFragment = new CollectPendingFragment();
        collectFinishedFragment = new CollectFinishedFragment();

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
        add_collect = (ImageView) findViewById(R.id.add_collect);
        add_collect.setOnClickListener(this);
        mTabs = new RelativeLayout[4];
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
            case R.id.add_collect:
                IntentUtils.getIntent(SpaceClickConfimActivity.this,AddConfimActivity.class);
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
}
