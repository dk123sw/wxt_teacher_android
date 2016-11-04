package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SystemNewsAdapter;
import cn.xiaocool.wxtteacher.bean.SystemNews;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;

public class SystemNewsActivity extends Activity implements View.OnClickListener{
    private PullToRefreshListView lv_homework;
    private ListView lv;
    private ArrayList<SystemNews.SystemData> SystemNewsDataList;
    private RelativeLayout btn_exit;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_system_news);
        context = this;
        initView();
        initData();
    }

    private void initData() {
        for(int i=0;i<5;i++){
            SystemNews.SystemData data=new SystemNews.SystemData();
            data.setId(String.valueOf(i));
            data.setCreate_time("5月23日");
            data.setContent("今天，我们在这里隆重集会，纪念全世界工人阶级和劳动群众的盛大节日——“五一”国际劳动节，表彰全国劳动模范和先进工作者，目的是弘扬劳模精神，弘扬劳动精神，弘扬我国工人阶级和广大劳动群众的伟大品格。\n" +
                    "\n" +
                    "首先，我代表党中央、国务院，向全国各族工人、农民、知识分子和其他各阶层劳动群众，向人民解放军指战员、武警部队官兵和公安民警，向香港同胞、澳门同胞、台湾同胞和海外侨胞，致以节日的祝贺！向为改革开放和社会主义现代化建设作出突出贡献的劳动模范和先进工作者，致以崇高的敬意！在这里，我代表中国工人阶级和广大劳动群众，向全世界工人阶级和广大劳动群众，致以诚挚的问候！");
            data.setPhoto("http://wxt.xiaocool.net/uploads/microblog/1.jpg");
            data.setTitle("习近平讲话");
            data.setUsername("园长");
            SystemNewsDataList.add(data);
        }
        SystemNewsAdapter adapter = new SystemNewsAdapter(SystemNewsActivity.this,SystemNewsDataList);
        lv.setAdapter(adapter);
    }

    private void initView() {
        SystemNewsDataList = new ArrayList<>();
        lv_homework = (PullToRefreshListView) findViewById(R.id.lv_homework);
        lv = lv_homework.getRefreshableView();
        lv.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        btn_exit = (RelativeLayout) findViewById(R.id.up_jiantou);
        btn_exit.setOnClickListener(this);
        lv_homework.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv_homework.onPullDownRefreshComplete();
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
                        lv_homework.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up_jiantou:
                finish();
                break;
        }
    }
}
