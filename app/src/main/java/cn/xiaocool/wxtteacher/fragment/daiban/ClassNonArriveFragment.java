package cn.xiaocool.wxtteacher.fragment.daiban;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ChildrenCollectAdapter;
import cn.xiaocool.wxtteacher.bean.ChildrenCollect;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassNonArriveFragment extends Fragment {


    private ListView children_list;
    private List<ChildrenCollect.ChildrenCollectData> childrenCollectDataList;
    private ChildrenCollectAdapter childrenCollectAdapter;
    private Context context;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case CommunalInterfaces.COLLECTION:
//                    JSONObject obj = (JSONObject) msg.obj;
//                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
//                        JSONArray hwArray = obj.optJSONArray("data");
//                        childrenCollectDataList.clear();
//                        JSONObject itemObject;
//                        for (int i = 0; i < hwArray.length(); i++) {
//                            itemObject = hwArray.optJSONObject(i);
////                            Homework.HomeworkData homeworkData = new Homework.HomeworkData();
//                            ChildrenCollect.ChildrenCollectData childrenCollectData = new ChildrenCollect.ChildrenCollectData();
//                            childrenCollectData.setId(itemObject.optString("id"));
//                            childrenCollectData.setTeacherid(itemObject.optString("teacherid"));
//                            childrenCollectData.setDelivery_time(itemObject.optString("delivery_time"));
//                            childrenCollectData.setDelivery_status(itemObject.optString("delivery_status"));
//                            childrenCollectData.setParentid(itemObject.optString("parentid"));
//                            childrenCollectData.setParenttime(itemObject.optString("parenttime"));
//                            childrenCollectData.setTeacheravatar(itemObject.optString("teacheravatar"));
//                            childrenCollectData.setTeacherphone(itemObject.optString("teacherphone"));
//                            childrenCollectData.setStudentname(itemObject.optString("studentname"));
//                            childrenCollectData.setStudentavatar(itemObject.optString("studentavatar"));
//                            if (itemObject.optString("delivery_status").equals("2")){
//                                childrenCollectDataList.add(childrenCollectData);
//                                childrenCollectAdapter = new ChildrenCollectAdapter(childrenCollectDataList,context);
//                                children_list.setAdapter(childrenCollectAdapter);
//                            }
//                        }
//                    }
//                    break;
//            }
//        }
//    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.collect_children, container, false);
        context = getActivity();
        return view;

    }

//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        initView();
//    }
//
//    private void initView() {
//        childrenCollectDataList = new ArrayList<>();
//        children_list = (ListView) getView().findViewById(R.id.children_list);
//        new NewsRequest(context,handler).collection();
//    }

}
