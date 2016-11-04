package cn.xiaocool.wxtteacher.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Classevents;
import cn.xiaocool.wxtteacher.fragment.readandnoread.ApplyListFragment;
import cn.xiaocool.wxtteacher.fragment.readandnoread.ReadOrNreadFragment;

public class ClassEventReadAndNreadActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentIndex;
    private ReadOrNreadFragment collectFinishedFragment;
    private ApplyListFragment collectPendingFragment;
    private FragmentManager fragmentManager;
    private List<Classevents.ClassEventData.IsApplyList> isapplylist;
    public ArrayList<Classevents.ClassEventData.ReciverlistInfo> notReads,alreadyReads;
    private RelativeLayout up_jiantou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_class_event_read_and_nread);

        initView();

        isapplylist = (List<Classevents.ClassEventData.IsApplyList>) getIntent().getSerializableExtra("applylist");
        alreadyReads =  (ArrayList<Classevents.ClassEventData.ReciverlistInfo>)getIntent().getSerializableExtra("alreadyReads");
        notReads = (ArrayList<Classevents.ClassEventData.ReciverlistInfo>)getIntent().getSerializableExtra("notReads");

        collectPendingFragment = new ApplyListFragment();
        collectFinishedFragment = new ReadOrNreadFragment();

        collectPendingFragment.isapplylist = isapplylist;
        collectFinishedFragment.alreadyReads = alreadyReads;
        collectFinishedFragment.notReads = notReads;
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
