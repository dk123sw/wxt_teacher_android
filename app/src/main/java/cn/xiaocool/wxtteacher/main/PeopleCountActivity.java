package cn.xiaocool.wxtteacher.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.ListView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassEventCountAdapter;
import cn.xiaocool.wxtteacher.bean.ClassEventCount;

public class PeopleCountActivity extends AppCompatActivity {

    private TextView title_bar_name;
    private RelativeLayout up_jiantou;
    private ListView people_list;
    private ArrayList<ClassEventCount.TeacherInfoBean> teacherInfoBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_people_count);
        getDataByIntent();
        initview();
    }

    private void getDataByIntent() {
        teacherInfoBeans = (ArrayList<ClassEventCount.TeacherInfoBean>) getIntent().getSerializableExtra("teacher_info");
    }


    private void initview() {
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        people_list = (ListView) findViewById(R.id.people_list);
        people_list.setAdapter(new ClassEventCountAdapter(null,teacherInfoBeans,this));
    }


}
