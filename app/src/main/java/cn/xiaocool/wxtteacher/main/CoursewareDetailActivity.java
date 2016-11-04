package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.CoursewareAdapter;
import cn.xiaocool.wxtteacher.bean.ClassCourseWare;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

public class CoursewareDetailActivity extends Activity {


    private PullToRefreshListView class_events_listcontent;
    private ListView listView;
    private RelativeLayout up_jiantou;
    private List<ClassCourseWare.CoursewareInfoBean> coursewareInfoBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_courseware_detail);

        coursewareInfoBeanList = (List<ClassCourseWare.CoursewareInfoBean>) getIntent().getSerializableExtra("coursewareinfo");
        initview();
    }

    /**
     * 初始化组件
     * */
    private void initview() {

        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        class_events_listcontent = (PullToRefreshListView) findViewById(R.id.class_events_listcontent);
        listView = class_events_listcontent.getRefreshableView();
        listView.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));

        List<ClassCourseWare.CoursewareInfoBean> coursewareInfoBeans = new ArrayList<>();
        for (int i =0 ;i<coursewareInfoBeanList.size();i++){
            coursewareInfoBeans.add(coursewareInfoBeanList.get(coursewareInfoBeanList.size()-1-i));
        }
        listView.setAdapter(new CoursewareAdapter(CoursewareDetailActivity.this,coursewareInfoBeans));
        class_events_listcontent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(CoursewareDetailActivity.this) == true) {


                } else {
                    ToastUtils.ToastShort(CoursewareDetailActivity.this, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        class_events_listcontent.onPullDownRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                /**
                 * 过1秒后 结束向上加载
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        class_events_listcontent.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
    }
}
