package cn.xiaocool.wxtteacher.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import cn.xiaocool.wxtteacher.R;

public class ShareAppActivity extends AppCompatActivity {

    private ImageView erweima_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_share_app);
        erweima_img = (ImageView) findViewById(R.id.erweima_img);
        findViewById(R.id.up_jiantou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
