package cn.xiaocool.wxtteacher.fragment.shenhe;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.MyGridAdapter;
import cn.xiaocool.wxtteacher.bean.ClassCricleInfo;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.TrendsDetailActivity;
import cn.xiaocool.wxtteacher.net.request.constant.ClassCircleRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.DateUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendFragment extends Fragment {


    private Context context;
    private int start_id = 0;
    private ArrayList<ClassCricleInfo> CricleList = new ArrayList<>();
    private static ArrayList<ClassCricleInfo> indexNewsList_cricle;
    private ProfessionCircleAdapter mAdapter;
    private static final int GET_CIRCLE_LIST_KEY = 2;
    private Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case GET_CIRCLE_LIST_KEY:
                    getCircleListJson(msg);
                    break;

                default:
                    break;
            }
        }
    };
    private PullToRefreshListView list;

    public TrendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        new ClassCircleRequest(context, handler).getCircleList("1", "1", "1", "1",GET_CIRCLE_LIST_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trend, container, false);
        context = getActivity();

        return view;
       
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        list = (PullToRefreshListView) getView().findViewById(R.id.lv_comprehensive);
        if (mAdapter==null){
            mAdapter = new ProfessionCircleAdapter(context, CricleList);
        }
        list.getRefreshableView().setAdapter(mAdapter);

          /* 设置刷新的监听器 */
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {

                if (NetUtil.isConnnected(context) == true) {
                    new ClassCircleRequest(context, handler).getCircleList("1","1","1","1",GET_CIRCLE_LIST_KEY);

                } else {

                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.onPullDownRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
//                // TODO 上拉加载 添加新的页
//                String indexNewsId = "";
//                dots.clear();
//                layout_view_pager.removeAllViews();
//                if(indexNewsList_cricle!=null && indexNewsList_cricle.size()!=0){
//                    for (int i = 0; i < indexNewsList_cricle.size(); i++) {
//                        indexNewsId += indexNewsList_cricle.get(i).getId()
//                                + ",";
//                    }
//                    start_id = Integer.parseInt(indexNewsList_cricle.get(indexNewsList_cricle.size()-1).getId());
//                }
//                CricleList.clear();
//                if (NetUtil.isConnnected(mContext) == true) {
//                    getAllInformation(String.valueOf(start_id));
//                } else {
//                    CricleList.clear();
//                }

                /**
                 * 过1秒后 结束向上加载
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
    }

    private void getCircleListJson(Message msg) {
        if (msg.obj != null) {
            JSONObject obj = (JSONObject) msg.obj;
            try {
                String state = obj.getString("status");
                if (state.equals(CommunalInterfaces._STATE)) {
                    CricleList.clear();
                    JSONArray items = obj.getJSONArray("data");

                    JSONObject itemObject;
                    for (int i = 0; i < items.length(); i++) {
                        itemObject = (JSONObject) items.get(i);
                        ClassCricleInfo cricle = new ClassCricleInfo();
                        cricle.setId(itemObject.getString("mid"));
                        cricle.setMatter(itemObject.getString("content"));
                        String workPraise = itemObject.getString("like");

                        cricle.setMemberName(itemObject.getString("name"));
                        cricle.setMemberImg(itemObject.getString("photo"));
                        cricle.setAddtime(itemObject.getString("write_time"));

                        String jsonImg = itemObject.getString("pic");
                        JSONArray imgList = new JSONArray(jsonImg);
                        ArrayList<String> imgs = new ArrayList<String>();
                        for (int k = 0; k < imgList.length(); k++) {
                            JSONObject imgobject = (JSONObject) imgList.get(k);
                            imgs.add(imgobject.getString("pictureurl"));
                        }
                        cricle.setWorkImgs(imgs);
                        if (workPraise != null && !workPraise.equals("null")) {
                            JSONArray jsonWorkPraiseArray = new JSONArray(workPraise);
                            ArrayList<LikeBean> workPraises = new ArrayList<>();
                            for (int k = 0; k < jsonWorkPraiseArray.length(); k++) {
                                JSONObject jsonPraise = jsonWorkPraiseArray.getJSONObject(k);
                                LikeBean praise = new LikeBean();
                                praise.setUserid(jsonPraise.getString("userid"));
                                praise.setName(jsonPraise.getString("name"));
                                workPraises.add(praise);
                            }
                            cricle.setWorkPraise(workPraises);
                        }
                        JSONArray commentArray = itemObject.optJSONArray("comment");
                        if (commentArray.length()>0) {
                            ArrayList<Comments> commentList = new ArrayList<>();
                            for (int j = 0; j < commentArray.length(); j++) {
                                JSONObject commentObject = commentArray.optJSONObject(j);
                                Comments comments = new Comments();
                                comments.setUserid(commentObject.optString("userid"));
                                comments.setName(commentObject.optString("name"));
                                comments.setAvatar(commentObject.optString("avatar"));
                                comments.setComment_time(commentObject.optString("comment_time"));
                                comments.setContent(commentObject.optString("content"));
                                commentList.add(comments);
                            }
                            cricle.setComment(commentList);
                        }
                        CricleList.add(cricle);
                    }


                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                LogUtils.d("weixiaotong", "JSONException" + e.getMessage());
                e.printStackTrace();
            }
        } else {

            LogUtils.d("weixiaotong", "listerror");
            LogUtils.d("weixiaotong", String.valueOf(msg.obj));
        }
    }

    class ProfessionCircleAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<ClassCricleInfo> workRingList;
        private ImageLoader imageLoader = ImageLoader.getInstance();

        public ProfessionCircleAdapter(Context mContext, ArrayList<ClassCricleInfo> workRings) {
            this.context = mContext;
            if (workRings == null) {
                workRings = new ArrayList<ClassCricleInfo>();
            }
            this.workRingList = workRings;
        }

        @Override
        public int getCount() {
            return workRingList.size();
        }

        @Override
        public ClassCricleInfo getItem(int position) {
            return workRingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            final ClassCricleInfo workRing = workRingList.get(position);
            final ArrayList<LikeBean> praises = workRing.getWorkPraise();
            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.profession_circle_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            date.setTime(Long.parseLong(workRing.getAddtime()) * 1000);
            long todayZero =  DateUtils.lastDayWholePointDate(calendar.getTime()).getTime()/1000;
            if (todayZero>Long.parseLong(workRing.getAddtime())){
                holder.item_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }else {
                holder.item_time.setText("今天 " + new SimpleDateFormat("HH:mm:ss").format(date));
            }

            holder.homework_content.setText(workRing.getMatter());
            holder.homework_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, TrendsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("newsgroupdata", workRing);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            holder.item_title.setText(workRing.getMemberName());

            //头像
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            DisplayImageOptions displayImage = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square).cacheInMemory(true).cacheOnDisc(true).build();
            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + workRing.getMemberImg(), holder.item_head, displayImage);


            //判断图片并显示（一张图片显示imageview，多于一张显示gridview）
            Log.d("img_gridview.count", String.valueOf(workRing.getWorkImgs().size()));
            if (workRing.getWorkImgs().size() > 1) {
                holder.homework_img.setVisibility(View.GONE);
                holder.img_gridview.setVisibility(View.VISIBLE);
                MyGridAdapter parWarnImgGridAdapter = new MyGridAdapter(workRing.getWorkImgs(), context);
                holder.img_gridview.setAdapter(parWarnImgGridAdapter);
                holder.img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                        // 图片浏览
                        Intent intent = new Intent();
                        intent.setClass(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", workRing.getWorkImgs());
                        intent.putExtra("type", "4");
                        intent.putExtra("position", a);
                        context.startActivity(intent);
                    }
                });

            } else if (workRing.getWorkImgs().size() == 1) {
                holder.img_gridview.setVisibility(View.GONE);
                holder.homework_img.setVisibility(View.VISIBLE);
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + workRing.getWorkImgs().get(0), holder.homework_img, displayImage);
                Log.d("img", "http://wxt.xiaocool.net/" + workRing.getWorkImgs().get(0));
                holder.homework_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 图片浏览
                        Intent intent = new Intent();
                        intent.setClass(context, CircleImagesActivity.class);
                        intent.putStringArrayListExtra("Imgs", workRing.getWorkImgs());
                        intent.putExtra("type", "4");
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.homework_img.setVisibility(View.GONE);
                holder.img_gridview.setVisibility(View.GONE);

            }

            //判断点赞点赞与否
            holder.linearLayout_homework_item_praise.setVisibility(View.GONE);
            if (workRing.getWorkPraise().size() > 0) {
                holder.linearLayout_homework_item_praise.setVisibility(View.VISIBLE);
                String names = "";
                for (int i = 0; i < workRing.getWorkPraise().size(); i++) {
                    names = names + " " + workRing.getWorkPraise().get(i).getName();
                }
                holder.homework_item_praise_names.setText(names);
            }


            return convertView;
        }



        public long getTodayZero() {
            Date date = new Date();
            long l = 24 * 60 * 60 * 1000; //每天的毫秒数
            //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
            //减8个小时的毫秒值是为了解决时区的问题。
            return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
        }

        public String getTime(String time) {

            long todayZero = getTodayZero();
            String ret_time;
            if (todayZero > Long.parseLong(time)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                date.setTime(Long.parseLong(time) * 1000);
                ret_time = (dateFormat.format(date));

            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                date.setTime(Long.parseLong(time) * 1000);
                ret_time = dateFormat.format(date);

            }

            return ret_time;

        }

        public class ViewHolder {
            TextView item_title,item_time ,homework_content, homework_item_praise_names, alread_text, not_read_text;
            ImageView homework_praise, homework_img, homework_discuss;
            LinearLayout linearLayout_homework_item_praise, comment_view;
            private NoScrollGridView img_gridview;
            private ListView comment_list;
            RoundImageView item_head;


            public ViewHolder(View convertView) {
                item_title = (TextView) convertView.findViewById(R.id.item_title);
                item_time = (TextView) convertView.findViewById(R.id.item_time);
                item_head = (RoundImageView) convertView.findViewById(R.id.item_head);
                img_gridview = (NoScrollGridView) convertView.findViewById(R.id.img_gridview);
                comment_list = (ListView) convertView.findViewById(R.id.comment_list);
                comment_view = (LinearLayout) convertView.findViewById(R.id.edit_and_send);
                homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
                not_read_text = (TextView) convertView.findViewById(R.id.not_read_text);
                alread_text = (TextView) convertView.findViewById(R.id.alread_text);
                homework_praise = (ImageView) convertView.findViewById(R.id.homework_praise);
                homework_discuss = (ImageView) convertView.findViewById(R.id.homework_discuss);
                homework_img = (ImageView) convertView.findViewById(R.id.homework_img);
                homework_item_praise_names = (TextView) convertView.findViewById(R.id.homework_item_praise_names);
                linearLayout_homework_item_praise = (LinearLayout) convertView.findViewById(R.id.linearLayout_homework_item_praise);

            }
        }
    }


}
