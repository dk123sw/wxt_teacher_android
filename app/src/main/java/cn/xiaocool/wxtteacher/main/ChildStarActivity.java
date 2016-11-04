package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.xiaocool.wxtteacher.R;

public class ChildStarActivity extends Activity {

    private EditText apply_content;
    private RelativeLayout send_btn,up_jiantou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_child_star);
        initview();
    }

    private void initview() {
        apply_content = (EditText) findViewById(R.id.apply_content);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
