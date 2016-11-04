package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ClassChildrenListAdapter;
import cn.xiaocool.wxtteacher.bean.ClassChildren;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.SpaceRequest;

/**
 * Created by Administrator on 2016/4/26.
 */
public class AddChildrenActivity extends Activity{
    private ImageView quit;
    private ListView children_list;
    private EditText et_message;
    private Button btn_sendMessage;
    private ClassChildrenListAdapter classChildrenListAdapter;
    private List<ClassChildren.ClassChildrenData> childrenDataList;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.STUDENT_LIST:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
                        JSONArray items = obj.optJSONArray("data");
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject itemObject = items.optJSONObject(i);
                            ClassChildren.ClassChildrenData childrenData = new ClassChildren.ClassChildrenData();
                            childrenData.setId(itemObject.optString("id"));
                            childrenData.setName(itemObject.optString("name"));
                            childrenData.setPhone(itemObject.optString("phone"));
                            childrenDataList.add(childrenData);
                        }
                        classChildrenListAdapter = new ClassChildrenListAdapter(childrenDataList,AddChildrenActivity.this);
                        children_list.setAdapter(classChildrenListAdapter);
                        children_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        });
                        children_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_children);
        initView();

    }

    private void initView() {
        childrenDataList = new ArrayList<>();
        quit = (ImageView) findViewById(R.id.quit);
        children_list = (ListView) findViewById(R.id.children_list);
        et_message = (EditText) findViewById(R.id.et_message);
        btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);
        new SpaceRequest(this,handler).newsGroupSend();
    }

}
