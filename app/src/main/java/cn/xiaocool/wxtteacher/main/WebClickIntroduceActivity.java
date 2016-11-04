package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ListView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.SchoolIntrduceAdapter;

/**
 * Created by wzh on 2016/1/29.
 */
public class WebClickIntroduceActivity extends Activity implements View.OnClickListener {

    private RelativeLayout up_jiantou;
    private ListView wci_list;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.web_open_introduce);
        mContext = this;
        initView();

    }

    private void initView() {
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        wci_list = (ListView) findViewById(R.id.wci_list);

        String ss [] = new String[]{"班级相册","班级活动","老师点评"};
        ArrayList<String> s = new ArrayList<>();
        for (int i = 0;i<ss.length;i++){
            s.add(ss[i]);
        }
        wci_list.setAdapter(new SchoolIntrduceAdapter(s,this));

        wci_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Intent intent = new Intent(mContext,IntroduceClassActivity.class);
                    startActivity(intent);
                }
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
