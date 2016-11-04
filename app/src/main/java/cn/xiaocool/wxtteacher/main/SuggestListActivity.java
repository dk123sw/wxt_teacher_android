package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SuggestListAdapter;
import cn.xiaocool.wxtteacher.bean.SuggestModel;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.JsonParser;
import cn.xiaocool.wxtteacher.utils.VolleyUtil;

public class SuggestListActivity extends Activity {

    private ListView listView;
    private PullToRefreshListView pullToRefreshListView;
    private List<SuggestModel> suggestModels;
    private SuggestListAdapter suggestListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_suggest_list);
        suggestModels = new ArrayList<>();
        //back
        findViewById(R.id.up_jiantou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //add
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuggestListActivity.this,SuggestBackActivity.class);
                startActivity(intent);
            }
        });


        pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.sug_lv);
        listView = pullToRefreshListView.getRefreshableView();
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取数据
     */
    private void initData() {
        String url = "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=GetLeaveMessageBySelf&userid="+new UserInfo(this).getUserId();
        VolleyUtil.VolleyGetRequest(this, url, new VolleyUtil.VolleyJsonCallback() {
            @Override
            public void onSuccess(String result) {
                pullToRefreshListView.onPullDownRefreshComplete();
                if (JsonParser.JSONparser(SuggestListActivity.this, result)) {
                    suggestModels.clear();
                    suggestModels.addAll(JsonParser.getBeanFromJsonSuggestModel(result));
                    setAdapter();
                }
            }

            @Override
            public void onError() {
                pullToRefreshListView.onPullDownRefreshComplete();
            }
        });
    }

    private void setAdapter() {
        if (suggestListAdapter==null){
            suggestListAdapter = new SuggestListAdapter(suggestModels,this);
            listView.setAdapter(suggestListAdapter);
        }else {
            suggestListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
