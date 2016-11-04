package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.fragment.backlog.BaclogReciveFragment;
import cn.xiaocool.wxtteacher.fragment.backlog.BaclogSendFragment;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.SPUtils;

public class BacklogActivity extends Activity implements View.OnClickListener{



    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentIndex;
    private BaclogSendFragment collectFinishedFragment;
    private BaclogReciveFragment collectPendingFragment;
    private FragmentManager fragmentManager;


    private RelativeLayout up_jiantou,btn_add;
    private static final String JPUSHBACKLOG = "JPUSHBACKLOG";
    private void clearJpushNum() {
        SPUtils.remove(this, JPUSHBACKLOG);
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
        setContentView(R.layout.activity_backlog);
        clearJpushNum();
        initView();

        collectPendingFragment = new BaclogReciveFragment();
        collectFinishedFragment = new BaclogSendFragment();

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
                IntentUtils.getIntent(BacklogActivity.this, AddBacklogActivity.class);
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
