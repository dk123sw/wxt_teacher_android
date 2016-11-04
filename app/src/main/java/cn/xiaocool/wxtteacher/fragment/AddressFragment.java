package cn.xiaocool.wxtteacher.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.utils.ToastUtils;


/**
 * Created by wzh on 2016/2/21.
 */
public class AddressFragment extends Fragment implements View.OnClickListener {
    private AddressAddFragment addressAddFragment;
    private AddressTeacherFragment addressGardenerFragment;
    private AddressParentFragment addressParentFragment;
    private AddressParentFragment addressGroupchatFragment;
    private FragmentManager fragmentManager;
    private RelativeLayout[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private RelativeLayout up_jiantou,up_tianjia;



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInsatanceState){
        View view = inflater.inflate(R.layout.fragment_address2,container,false);
        return  view;

    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        init();
    }

    private void init() {
        addressAddFragment = new AddressAddFragment();
        addressGardenerFragment = new AddressTeacherFragment();
        addressGroupchatFragment = new AddressParentFragment();
        addressParentFragment = new AddressParentFragment();
        fragments = new Fragment[]{addressParentFragment,addressGardenerFragment,addressGroupchatFragment};
        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container1,addressParentFragment);
        transaction.commit();
        initview();
    }

    private void initview() {
        //初始化四个按钮
        mTabs = new RelativeLayout[4];
        mTabs[0] = (RelativeLayout)getView().findViewById(R.id.address_parent);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (RelativeLayout)getView().findViewById(R.id.address_gardener);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (RelativeLayout)getView().findViewById(R.id.address_group_chat);
        mTabs[2].setOnClickListener(this);
//        mTabs[3] = (RelativeLayout)getView().findViewById(R.id.address_add);
//        mTabs[3].setOnClickListener(this);
        up_jiantou = (RelativeLayout) getView().findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        up_tianjia = (RelativeLayout) getView().findViewById(R.id.up_tianjia);
        up_tianjia.setOnClickListener(this);

        //设置第一个按钮为选中状态
        mTabs[0].setSelected(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_parent:
                index = 0;
                break;
            case R.id.address_gardener:
                index = 1;
                break;
            case R.id.address_group_chat:
                index = 2;
                break;
//            case R.id.address_add:
//                index = 3;
//                break;
            case R.id.up_jiantou:
                getActivity().finish();
                break;
            case R.id.up_tianjia:
                showPopupMenu();
                break;

        }

        if (currentTabIndex != index){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()){
                transaction.add(R.id.fragment_container1, fragments[index]);

            }
            transaction.show(fragments[index]);
            transaction.commit();

        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }


    /**
     *显示选择菜单
     * */
    private void showPopupMenu() {

        //自定义布局
        View layout = LayoutInflater.from(this.getActivity()).inflate(R.layout.address_add_menu, null);
        //初始化popwindow
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        int[] location = new int[2];
        up_tianjia.getLocationOnScreen(location);
        popupWindow.showAsDropDown(up_tianjia);

        final TextView add_qun = (TextView)layout.findViewById(R.id.add_qun);
        TextView tong_bu = (TextView)layout.findViewById(R.id.tong_bu);

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        //监听popwindow消失事件，取消遮盖层
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
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
        ToastUtils.ToastShort(this.getActivity(), "同步通讯录");
    }

    /**
     * 添加群
     * */
    private void history() {

    }
}