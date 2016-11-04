package cn.xiaocool.wxtteacher.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ChatListAdapter;
import cn.xiaocool.wxtteacher.bean.CommunicateListModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.main.AddressActivity;
import cn.xiaocool.wxtteacher.main.BacklogActivity;
import cn.xiaocool.wxtteacher.main.NewsGroupActivity;
import cn.xiaocool.wxtteacher.main.ReceiveParentWarnActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickAnnouActivity;
import cn.xiaocool.wxtteacher.main.SpaceClickHomeworkActivity;
import cn.xiaocool.wxtteacher.main.TeacherCommunicationActivity;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.utils.JsonParser;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;
import cn.xiaocool.wxtteacher.utils.VolleyUtil;

/**
 * Created by wzh on 2016/2/21.
 */
public class NewsFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout tian_jia;
    private NoScrollListView news_list;
    public BadgeView message,trust,notice,backlog;
    private LinearLayout news_group_send,news_parents_told,news_announcement,news_todo;
    private RelativeLayout news_gardener_communication,news_class_homework,news_address;
    private TextView newsMySent,parentWarnContent,announceContent,backlogContent,teacherCommunication,homeworkContent;
    private List<CommunicateListModel> communicateListModelList;
    private ChatListAdapter chatListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicateListModelList = new ArrayList<>();
        initView();
    }

    private void initView() {
        tian_jia = (RelativeLayout) getView().findViewById(R.id.tian_jia);
        tian_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        news_list = (NoScrollListView)getView().findViewById(R.id.news_list);

        //消息群发
        news_group_send = (LinearLayout) getView().findViewById(R.id.news_group_send);
        news_group_send.setOnClickListener(this);
        message = new BadgeView(getActivity());
        message.setTargetView(news_group_send.findViewById(R.id.news_hot_news));
        //家长叮嘱
        news_parents_told = (LinearLayout) getView().findViewById(R.id.news_parents_told);
        news_parents_told.setOnClickListener(this);
        trust = new BadgeView(getActivity());
        trust.setTargetView(news_parents_told.findViewById(R.id.news_hot_news));
        //通知公告
        news_announcement = (LinearLayout) getView().findViewById(R.id.news_announcement);
        news_announcement.setOnClickListener(this);
        notice = new BadgeView(getActivity());
        notice.setTargetView(news_announcement.findViewById(R.id.news_hot_news));
        //待办事项
        news_todo = (LinearLayout) getView().findViewById(R.id.news_todo);
        news_todo.setOnClickListener(this);
        backlog = new BadgeView(getActivity());
        backlog.setTargetView(news_todo.findViewById(R.id.news_hot_news));
        //园丁沟通
        news_gardener_communication = (RelativeLayout) getView().findViewById(R.id.news_gardener_communication);
        news_gardener_communication.setOnClickListener(this);
        //班级作业
        news_class_homework = (RelativeLayout) getView().findViewById(R.id.news_class_homework);
        news_class_homework.setOnClickListener(this);
        //班级活动
        news_address = (RelativeLayout) getView().findViewById(R.id.news_address);
        news_address.setOnClickListener(this);

        newsMySent = (TextView) news_group_send.findViewById(R.id.news_mySent);
        parentWarnContent = (TextView) news_parents_told.findViewById(R.id.parent_warn_content);
        announceContent = (TextView) news_announcement.findViewById(R.id.announce_content);
        backlogContent = (TextView) news_todo.findViewById(R.id.backlog_content);
        teacherCommunication = (TextView) news_gardener_communication.findViewById(R.id.teacher_communication);
        homeworkContent = (TextView) news_class_homework.findViewById(R.id.homework_content);
        news_list = (NoScrollListView) getView().findViewById(R.id.news_list);

    }

    /**
     * 显示选择菜单
     */
    private void showPopupMenu() {

        //自定义布局
        View layout = LayoutInflater.from(this.getActivity()).inflate(R.layout.address_add_menu, null);
        //初始化popwindow
        final PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置弹出位置
        int[] location = new int[2];
        tian_jia.getLocationOnScreen(location);

        popupWindow.showAsDropDown(tian_jia);

        final TextView add_qun = (TextView) layout.findViewById(R.id.add_qun);
        TextView tong_bu = (TextView) layout.findViewById(R.id.tong_bu);

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
     */
    private void tongbu() {
        ToastUtils.ToastShort(this.getActivity(), "同步通讯录成功");
    }

    /**
     * 添加群
     */
    private void history() {
        ToastUtils.ToastShort(this.getActivity(), "添加群");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //群发
            case R.id.news_group_send:
                startActivity(new Intent(getActivity(), NewsGroupActivity.class));
                break;
            //家长叮嘱
            case R.id.news_parents_told:
                startActivity(new Intent(getActivity(), ReceiveParentWarnActivity.class));
                break;
            //通知公告
            case R.id.news_announcement:
                startActivity(new Intent(getActivity(), SpaceClickAnnouActivity.class));
                break;
            //待办事宜
            case R.id.news_todo:
                startActivity(new Intent(getActivity(), BacklogActivity.class));
                break;
            //园丁沟通
            case R.id.news_gardener_communication:
                startActivity(new Intent(getActivity(), TeacherCommunicationActivity.class));
                break;
            //家庭作业
            case R.id.news_class_homework:
                startActivity(new Intent(getActivity(), SpaceClickHomeworkActivity.class));
                break;
            //通讯录
            case R.id.news_address:
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
            default:

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
            newsMySent.setText((String) SPUtils.get(getActivity(), "newsGroupRecive", "暂无最新群发消息"));
            parentWarnContent.setText((String) SPUtils.get(getActivity(), "receiveParentWarn", "暂无最新叮嘱"));
            announceContent.setText((String) SPUtils.get(getActivity(), "noticeRecive", "暂无最新公告"));
            backlogContent.setText((String) SPUtils.get(getActivity(), "backlogData", "暂无最新待办事项"));
            teacherCommunication.setText((String) SPUtils.get(getActivity(), "teacherCommunication", "暂无最新消息"));
            homeworkContent.setText((String) SPUtils.get(getActivity(), "homeWork", "暂无最新作业"));

        setRedPoint();
        getChatList();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getChatList();
    }

    private void getChatList() {
        String url = "http://wxt.xiaocool.net/index.php?g=apps&m=message&a=xcGetChatListData&uid="+new UserInfo(getActivity()).getUserId();
        VolleyUtil.VolleyGetRequest(getActivity(), url, new VolleyUtil.VolleyJsonCallback() {
            @Override
            public void onSuccess(String result) {
                if (JsonParser.JSONparser(getActivity(), result)) {
                    communicateListModelList.clear();
                    communicateListModelList.addAll(JsonParser.getBeanFromJsonCommunicateListModel(result));
                    setAdapter();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void setAdapter() {
        if (chatListAdapter==null){
            chatListAdapter = new ChatListAdapter(getActivity(),communicateListModelList);
            news_list.setAdapter(chatListAdapter);
        }else {
            chatListAdapter.notifyDataSetChanged();
        }

    }

    private static final String JPUSHMESSAGE = "JPUSHMESSAGE";
    private static final String JPUSHTRUST = "JPUSHTRUST";
    private static final String JPUSHNOTICE = "JPUSHNOTICE";
    private static final String JPUSHBACKLOG = "JPUSHBACKLOG";
    /**
     * 设置小红点
     */
    public  void setRedPoint(){
        //从本地取出各个小红点的个数
        int message = (int) SPUtils.get(getActivity(),JPUSHMESSAGE,0);
        int trust = (int) SPUtils.get(getActivity(),JPUSHTRUST,0);
        int notice = (int) SPUtils.get(getActivity(),JPUSHNOTICE,0);
        int backlogNum = (int) SPUtils.get(getActivity(),JPUSHBACKLOG,0);

        setBadgeView(message,this.message);
        setBadgeView(trust,this.trust);
        setBadgeView(notice,this.notice);
        setBadgeView(backlogNum,this.backlog);


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
}
