package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;


/**
 * Created by wzh on 2016/3/16.
 */
public class AnnouContentActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle,tvContent;
    private ImageView ivExit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.space_annou_itemclick);
        tvTitle = (TextView) findViewById(R.id.text_title);
        tvContent = (TextView) findViewById(R.id.text_content);
        ivExit = (ImageView) findViewById(R.id.btn_exit);
        ivExit.setOnClickListener(this);
        Intent intent = getIntent();
        String intentTitle = intent.getStringExtra("intent_title");
        String intentContent = intent.getStringExtra("intent_content");
        String intentName = intent.getStringExtra("intent_name");
        String intentDate = intent.getStringExtra("intent_date");
        tvTitle.setText(intentTitle);
        tvContent.setText(intentContent + "\n\n\n" + intentName + "\n\n" + intentDate);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_exit:
                finish();
                break;
        }
    }
}
