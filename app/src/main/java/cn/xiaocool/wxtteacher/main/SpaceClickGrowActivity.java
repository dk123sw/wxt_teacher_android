package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.GrownListAdapter;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;

public class SpaceClickGrowActivity extends Activity {

    private NoScrollListView class_left_list;
    private ListView class_right_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_space_click_grow);

        initView();
    }

    private void initView() {
        class_left_list = (NoScrollListView) findViewById(R.id.class_left_list);
        class_left_list.setAdapter(new GrownListAdapter(this, new ArrayList<String>()));
        class_right_list = (ListView) findViewById(R.id.class_right_list);
        class_left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                refreshList();
            }
        });
    }

    private void refreshList() {

        class_right_list.setAdapter(new GrownListAdapter(this, new ArrayList<String>()));
        class_right_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SpaceClickGrowActivity.this, ChooseCollectActivity.class);
                intent.putExtra("classID", "1");
                startActivity(intent);
            }
        });
    }
}
