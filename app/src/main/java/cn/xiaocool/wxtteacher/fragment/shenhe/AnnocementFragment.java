package cn.xiaocool.wxtteacher.fragment.shenhe;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ImgGridAdapter;
import cn.xiaocool.wxtteacher.bean.Announcement;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;

import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnocementFragment extends Fragment {

    private PullToRefreshListView listView;
    private ListView list;
    private ImageView btn_exit;
    private Button add_announcement;


    String annTitle = null;
    String annID = null;
    String annUserID = null;
    String annContent = null;
    String annType = null;
    String annCreate_time = null;
    String annPhoto = null;
    String annUsername = null;
    String annAvatar = null;
    String annReadcount = null;
    String annAllreader = null;
    String annReadtag = null;
    String picID = null;
    String picUrl = null;
    String picCreate_time = null;
    private String data;
    private int picNum;
    private static final int WORK_PRAISE_KEY = 4;
    private static final int DEL_WORK_PRAISE_KEY = 5;
    private Context mContext;
    private UserInfo user;
    private ArrayList<Announcement.AnnouncementData> announcementDateList;
    private ArrayList<Announcement.AnnouncementData.PicData> picDataList;
    private AnnouncementListaaaAdapter announcementListAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_PARENT_REMARK:
                    Log.d("是否成功", "======");

                    if (msg.obj !=null){
                        JSONObject obj2 = (JSONObject) msg.obj;
                        String state = obj2.optString("status");
                        LogUtils.e("HomeworkCommentActivity", obj2.optString("data"));
                        Log.d("是否成功", state);
                        if (state.equals(CommunalInterfaces._STATE)){
                            data = obj2.optString("data");
                            Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                            getAllInformation();
                        } else {
                            Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                    break;
                case CommunalInterfaces.ANNOUNCEMENT:
                    JSONObject obj = (JSONObject) msg.obj;
                    String status = obj.optString("status");

                    if (status.equals(CommunalInterfaces._STATE)) {
                        JSONArray annArray = obj.optJSONArray("data");
                        announcementDateList.clear();
                        JSONObject itemObject;
                        for (int i = 0; i < annArray.length(); i++) {
                            itemObject = annArray.optJSONObject(i);
                            Announcement.AnnouncementData announcementDate = new Announcement.AnnouncementData();
                            annTitle = itemObject.optString("title");
                            annID = itemObject.optString("id");
                            annUserID = itemObject.optString("userid");
                            annContent = itemObject.optString("content");
                            annType = itemObject.optString("type");
                            annCreate_time = itemObject.optString("create_time");
                            annUsername = itemObject.optString("username");
                            annAvatar = itemObject.optString("avatar");
                            annReadcount = itemObject.optString("readcount");
                            annAllreader = itemObject.optString("allreader");
                            annReadtag = itemObject.optString("readtag");
                            annPhoto = itemObject.optString("photo");

                            announcementDate.setTitle(itemObject.optString("title"));
                            announcementDate.setId(itemObject.optString("id"));
                            announcementDate.setUserid(itemObject.optString("userid"));
                            announcementDate.setContent(itemObject.optString("content"));
                            announcementDate.setType(itemObject.optString("type"));
                            announcementDate.setCreate_time(itemObject.optString("create_time"));
                            announcementDate.setUsername(itemObject.optString("username"));
                            announcementDate.setAvatar(itemObject.optString("avatar"));
                            announcementDate.setReadcount(itemObject.optInt("readcount"));
                            announcementDate.setAllreader(itemObject.optInt("allreader"));
                            announcementDate.setReadtag(itemObject.optInt("readtag"));
                            announcementDate.setPhoto(itemObject.optString("photo"));
                            JSONArray picArray = itemObject.optJSONArray("pic");
                            if (picArray != null) {
                                for (int j = 0; j < picArray.length(); j++) {
                                    JSONObject picObject = picArray.optJSONObject(j);
                                    Announcement.AnnouncementData.PicData picData = new Announcement.AnnouncementData.PicData();
                                    picNum = picArray.length();
                                    picID = itemObject.optString("id");
                                    picUrl = itemObject.optString("pictureurl");
                                    picCreate_time = itemObject.optString("create_time");
                                    picData.setId(picObject.optString("id"));
                                    picData.setPictureurl(picObject.optString("pictureurl"));
                                    picData.setCreate_time(picObject.optString("create_time"));
                                    picDataList.add(picData);
                                }
                                announcementDate.setPic(picDataList);
                            }
                            JSONArray commentArray = itemObject.optJSONArray("comment");
                            if (commentArray != null) {
                                ArrayList<Comments> commentses = new ArrayList<Comments>();
                                for (int j = 0; j < commentArray.length(); j++) {
                                    JSONObject commentObject = commentArray.optJSONObject(j);
                                    Comments commentBean = new Comments();
                                    commentBean.setUserid(commentObject.optString("userid"));
                                    commentBean.setAvatar(commentObject.optString("avatar"));
                                    commentBean.setName(commentObject.optString("name"));
                                    commentBean.setContent(commentObject.optString("content"));
                                    commentBean.setComment_time(commentObject.optString("comment_time"));
                                    commentBean.setPhoto(commentObject.optString("photo"));
                                    commentses.add(commentBean);
                                }
                                announcementDate.setComment(commentses);
                            }
                            JSONArray likeArray = itemObject.optJSONArray("like");
                            if (likeArray != null) {
                                List<LikeBean> likeBeanList = new ArrayList<>();
                                for (int j = 0; j < likeArray.length(); j++) {
                                    JSONObject likeObject = likeArray.optJSONObject(j);
                                    LikeBean likeBean = new LikeBean();
                                    likeBean.setUserid(likeObject.optString("userid"));
                                    likeBean.setName(likeObject.optString("name"));
                                    likeBean.setAvatar(likeObject.optString("avatar"));
                                    likeBeanList.add(likeBean);
                                }
                                announcementDate.setLike(likeBeanList);
                            }

                            JSONArray picsArray = itemObject.optJSONArray("pic");
                            ArrayList<Announcement.AnnouncementData.PicData> pics = new ArrayList<>();
                            if (picsArray!=null){
                                for (int j =0;j<picsArray.length();j++){
                                    Announcement.AnnouncementData.PicData picData = new Announcement.AnnouncementData.PicData();
                                    picData.setId(picsArray.optJSONObject(j).optString("id"));
                                    picData.setPictureurl(picsArray.optJSONObject(j).optString("pictureurl"));
                                    picData.setCreate_time(picsArray.optJSONObject(j).optString("create_time"));
                                    pics.add(picData);
                                }
                                announcementDate.setPic(pics);
                            }




                            announcementDateList.add(announcementDate);
                        }



                        if(announcementListAdapter!=null){

                            announcementListAdapter.notifyDataSetChanged();
                        }else {
                            announcementListAdapter = new AnnouncementListaaaAdapter( announcementDateList, getActivity(),null);
                            list.setAdapter(announcementListAdapter);
                        }

                    }
                    break;
                case WORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            ToastUtils.ToastShort(mContext, result);
                            if (state.equals(CommunalInterfaces._STATE)) {
                                getAllInformation();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DEL_WORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                ToastUtils.ToastShort(mContext, "已取消");
                                getAllInformation();
                            }else
                            {
                                ToastUtils.ToastShort(mContext, result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
    public AnnocementFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        new NewsRequest(mContext, handler).announcement();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_annocement, container, false);
        mContext = getActivity();

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        announcementDateList = new ArrayList<>();
        picDataList = new ArrayList<>();
        listView = (PullToRefreshListView) getView().findViewById(R.id.announcement_list_content);
        list  = listView.getRefreshableView();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(mContext) == true) {
                    getAllInformation();

                } else {
                    ToastUtils.ToastShort(mContext, "暂无网络");
                }
                /**
                 * 过1秒结束下拉刷新
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onPullDownRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                /**
                 * 过1秒后 结束向上加载
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
        list.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));


    }

    class AnnouncementListaaaAdapter extends BaseAdapter {

        /*private ArrayList<Announcement.AnnouncementData> announcementDate;*/
        private LayoutInflater inflater;
        private DisplayImageOptions displayImage;
        private Context mContext;
        private ImageLoader imageLoader = ImageLoader.getInstance();
        private LinearLayout linearLayout;
        public AnnouncementListaaaAdapter(ArrayList<Announcement.AnnouncementData> announcementDateList, Context mContext,LinearLayout linearLayout) {
            this.mContext = mContext;
            this.linearLayout = linearLayout;
           /* this.announcementDate = announcementDateList;*/
            this.inflater = LayoutInflater.from(mContext);
            user = new UserInfo();
            user.readData(mContext);
            displayImage = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }

        @Override
        public int getCount() {
            return announcementDateList.size();
        }

        @Override
        public Object getItem(int position) {
            return announcementDateList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Announcement.AnnouncementData announcementData = announcementDateList.get(position);
            final ViewHolder holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.message_myhomework,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.homework_title.setText(announcementDateList.get(position).getTitle());
            holder.homework_content.setText(announcementDateList.get(position).getContent());
            holder.teacher_name.setText(announcementDateList.get(position).getUsername());
            holder.alread_text.setText("已阅读" + announcementDateList.get(position).getReadcount());


            Date date = new Date();
            date.setTime(Long.parseLong(announcementDateList.get(position).getCreate_time()) * 1000);
            holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));



//        //获取ID回调刷新点赞数据
//
//        new NewsRequest(context,handler).get_like(homeworkDataList.get(position).getUserid(),homeworkDataList.get(position).getId(),"2");


            //判断是否有照片
            holder.homework_img.setVisibility(View.GONE);
            if (announcementDateList.get(position).getPic()!=null){
                if (announcementDateList.get(position).getPic().size()>1){
                    holder.homework_img.setVisibility(View.GONE);
                    holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
                    final ArrayList<String> picStringArray = new ArrayList<>();
                    for (int i = 0;i<announcementDateList.get(position).getPic().size();i++){
                        picStringArray.add(announcementDateList.get(position).getPic().get(i).getPictureurl());
                    }
                    ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter( picStringArray,mContext);
                    holder.parent_warn_img_gridview.setAdapter(parWarnImgGridAdapter);
                    holder.parent_warn_img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                            // 图片浏览
                            Intent intent = new Intent();
                            intent.setClass(mContext, CircleImagesActivity.class);
                            intent.putStringArrayListExtra("Imgs", picStringArray);
                            intent.putExtra("type", "newsgroup");
                            intent.putExtra("position", a);
                            mContext.startActivity(intent);
                        }
                    });

                }else if (announcementDateList.get(position).getPic().size()==1){
                    holder.parent_warn_img_gridview.setVisibility(View.GONE);
                    holder.homework_img.setVisibility(View.VISIBLE);
                    imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
                    imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + announcementDateList.get(position).getPhoto(), holder.homework_img, displayImage);
                    Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + announcementDateList.get(position).getPhoto());
                    holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.homework_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> imgs = new ArrayList<>();
                            imgs.add(announcementDateList.get(position).getPhoto().toString());
                            Intent intent = new Intent(mContext, CircleImagesActivity.class);
                            intent.putStringArrayListExtra("Imgs", imgs);
                            mContext.startActivity(intent);
                        }
                    });
                }else {
                    holder.homework_img.setVisibility(View.GONE);
                    holder.parent_warn_img_gridview.setVisibility(View.GONE);
                }
            }else {
                holder.homework_img.setVisibility(View.GONE);
                holder.parent_warn_img_gridview.setVisibility(View.GONE);
            }



            return convertView;
        }




        class ViewHolder{
            TextView homework_title,homework_content,teacher_name,homework_time,homework_item_praise_names,alread_text,not_read_text;
            ImageView homework_praise,homework_img,homework_discuss;
            LinearLayout linearLayout_homework_item_praise;
            ListView comment_list;
            NoScrollGridView parent_warn_img_gridview;
            public ViewHolder(View convertView) {
                parent_warn_img_gridview = (NoScrollGridView) convertView.findViewById(R.id.parent_warn_img_gridview);
                comment_list = (ListView) convertView.findViewById(R.id.comment_list);
                homework_title = (TextView) convertView.findViewById(R.id.myhomework_title);
                homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
                teacher_name = (TextView) convertView.findViewById(R.id.myhomework_teacher_name);
                homework_time = (TextView) convertView.findViewById(R.id.myhomework_time);
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
    private void getAllInformation() {

        new NewsRequest(mContext, handler).announcement();
    }
}
