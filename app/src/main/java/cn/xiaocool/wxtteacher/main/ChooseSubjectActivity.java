package cn.xiaocool.wxtteacher.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.BaseActivity;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.MyBaseAdapter;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;

public class ChooseSubjectActivity extends BaseActivity {

    private RequestQueue mQueue;
    private ListView subject_list;
    private UserInfo user = new UserInfo();
    private ArrayList<String> arrayList;
    private ArrayList<String> idArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_subject);
        user.readData(this);
        arrayList = new ArrayList<>();
        idArray = new ArrayList<>();

        initView();
        requestData();
    }

    private void requestData() {
        mQueue = Volley.newRequestQueue(this);
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=getSubject&schoolid="+user.getSchoolId();
        Log.e("uuuurrrrll", URL);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String arg0) {
                Log.d("onResponse", arg0);

                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    if (state.equals(CommunalInterfaces._STATE)) {
                        arrayList.clear();
                        JSONArray dataArray = jsonObject.optJSONArray("data");
                        for (int i = 0;i<dataArray.length();i++){
                            arrayList.add(dataArray.optJSONObject(i).optString("subject"));
                            idArray.add(dataArray.optJSONObject(i).optString("id"));
                        }
                        setAdapter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.d("onErrorResponse", arg0.toString());
            }
        });
        mQueue.add(request);
    }

    private void setAdapter() {
        subject_list.setAdapter(new SubjectAdapter(arrayList,ChooseSubjectActivity.this));
    }


    private void initView() {
        subject_list = (ListView) findViewById(R.id.subject_list);
        findViewById(R.id.up_jiantou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("subject", arrayList.get(position));
                intent.putExtra("subjectid",idArray.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }



    private class SubjectAdapter extends MyBaseAdapter<String> {


        public SubjectAdapter(List t, Context context) {
            super(t, context);
        }

        @Override
        public View getHolderView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;



            if (convertView == null){
                convertView = inflater.inflate(R.layout.choose_subject, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.item_text.setText(t.get(position));
            return convertView;
        }
    }

    private class ViewHolder {
        TextView item_text;
        public ViewHolder(View convertView) {
            item_text = (TextView) convertView.findViewById(R.id.item_text);

        }
    }
}
