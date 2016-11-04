package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.RearingChildAdapter;
import cn.xiaocool.wxtteacher.bean.RearingChild;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.SpaceRequest;
import cn.xiaocool.wxtteacher.ui.RearingChildListView;

/**
 * Created by 潘 on 2016/4/5.
 */
public class WebClickRearingChildActivity extends Activity implements View.OnClickListener {
    private ImageView btn_exit;
    private RearingChildListView rearingChildLv;
    private List<RearingChild.RearingChildData> rearingChildDataList;
    private RearingChildAdapter rearingChildAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.REARING_CHILD:
                    if (msg.obj!=null){
                        JSONObject obj = (JSONObject) msg.obj;
                        try {
                            String status = obj.getString("status");
                            if (status.equals(CommunalInterfaces._STATE)){
                                JSONArray dataArray = obj.getJSONArray("data");
                                JSONObject itemObject;
                                for (int i = 0; i < dataArray.length(); i++) {
                                    itemObject = dataArray.getJSONObject(i);
                                    RearingChild.RearingChildData rearingChildData = new RearingChild.RearingChildData();
                                    rearingChildData.setReleasename(itemObject.getString("releasename"));
                                    rearingChildData.setHappy_content(itemObject.getString("happy_content"));
                                    rearingChildData.setHappy_pic(itemObject.getString("happy_pic"));
                                    rearingChildData.setHappy_title(itemObject.getString("happy_title"));
                                    rearingChildData.setHappy_time(itemObject.getString("happy_time"));
                                    rearingChildDataList.add(rearingChildData);
                                }
                                rearingChildAdapter = new RearingChildAdapter(WebClickRearingChildActivity.this,rearingChildDataList);
                                rearingChildLv.setAdapter(rearingChildAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.web_click_child_rearing_list);
        initView();

    }

    private void initView() {
        rearingChildDataList = new ArrayList<>();
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        rearingChildLv = (RearingChildListView) findViewById(R.id.web_rearingChildLv);
        new SpaceRequest(WebClickRearingChildActivity.this,handler).rearingChild();
        rearingChildLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WebClickRearingChildActivity.this, WebClickRearingChildSpecificActivity.class);
                intent.putExtra("happy_title", rearingChildDataList.get(position).getHappy_title());
                intent.putExtra("happy_pic", rearingChildDataList.get(position).getHappy_pic());
                intent.putExtra("happy_content", rearingChildDataList.get(position).getHappy_content());
                intent.putExtra("releasename", rearingChildDataList.get(position).getReleasename());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                finish();
        }

    }

}
