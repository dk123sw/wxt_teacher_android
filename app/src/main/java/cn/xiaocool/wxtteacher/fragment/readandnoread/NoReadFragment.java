package cn.xiaocool.wxtteacher.fragment.readandnoread;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ChildrenCollectAdapter;
import cn.xiaocool.wxtteacher.bean.ChildrenCollect;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoReadFragment extends Fragment {



    private ListView children_list;
    private List<ChildrenCollect.ChildrenCollectData> childrenCollectDataList;
    private ChildrenCollectAdapter childrenCollectAdapter;
    private Context context;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.COLLECTION:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)){
                        JSONArray hwArray = obj.optJSONArray("data");
                        childrenCollectDataList.clear();
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);
//                            Homework.HomeworkData homeworkData = new Homework.HomeworkData();
                            ChildrenCollect.ChildrenCollectData childrenCollectData = new ChildrenCollect.ChildrenCollectData();
                            childrenCollectData.setId(itemObject.optString("id"));
                            childrenCollectData.setTeacherid(itemObject.optString("teacherid"));
                            childrenCollectData.setDelivery_time(itemObject.optString("delivery_time"));
                            childrenCollectData.setDelivery_status(itemObject.optString("delivery_status"));
                            childrenCollectData.setParentid(itemObject.optString("parentid"));
                            childrenCollectData.setParenttime(itemObject.optString("parenttime"));
                            childrenCollectData.setTeacheravatar(itemObject.optString("teacheravatar"));
                            childrenCollectData.setTeacherphone(itemObject.optString("teacherphone"));
                            childrenCollectData.setStudentname(itemObject.optString("studentname"));
                            childrenCollectData.setStudentavatar(itemObject.optString("studentavatar"));
                            if (itemObject.optString("delivery_status").equals("1")){
                                childrenCollectDataList.add(childrenCollectData);
                                childrenCollectAdapter = new ChildrenCollectAdapter(childrenCollectDataList,context);
                                children_list.setAdapter(childrenCollectAdapter);
                            }

                        }
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_no_read, container, false);
        context = getActivity();
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        childrenCollectDataList = new ArrayList<>();
        children_list = (ListView) getView().findViewById(R.id.children_list);
        children_list.setAdapter(new ReadAdapter(new ArrayList<Comments>()));
    }

    public class ReadAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ImageLoader imageLoader = ImageLoader.getInstance();
        private ArrayList<Comments> commentsList;
        private DisplayImageOptions displayImage;

        public ReadAdapter(ArrayList<Comments> commentsArrayList) {
            this.commentsList = commentsArrayList;
            inflater = LayoutInflater.from(context);
            displayImage = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }




        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return commentsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_no_read_list,null);
                holder = new ViewHolder(convertView);
                convertView.setBackground(new ColorDrawable(Color.parseColor("#ffffff")));
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
//            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//            holder.item_title.setText(commentsList.get(position).getName());
//            holder.item_content.setText(commentsList.get(position).getContent());
//
//            Date date = new Date();
//            date.setTime(Long.parseLong(commentsList.get(position).getComment_time()) * 1000);
//            holder.item_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
//
//            //头像
//            imageLoader.displayImage(NetBaseConstant.NET_AVATAR_HOST +"/"+ commentsList.get(position).getAvatar(), holder.item_head, displayImage);
            return convertView;
        }

        class ViewHolder{

            TextView item_title,item_content,item_time;
            ImageView item_head,item_information;
            public ViewHolder(View convertView) {
                item_title = (TextView) convertView.findViewById(R.id.item_title);
                item_content = (TextView) convertView.findViewById(R.id.item_content);
                item_time = (TextView) convertView.findViewById(R.id.item_time);
                item_head = (ImageView) convertView.findViewById(R.id.item_head);
                item_information = (ImageView) convertView.findViewById(R.id.item_information);
            }



        }
    }

}
