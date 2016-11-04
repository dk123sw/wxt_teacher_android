package cn.xiaocool.wxtteacher.fragment.announce;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import cn.xiaocool.wxtteacher.bean.NoticeRecive;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.NoticeReciveDetailActivity;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.ProgressViewUtil;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.SPUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by Administrator on 2016/5/9.
 */
public class NoticeReciveFragment extends Fragment {
    private UserInfo user = new UserInfo();
    private List<NoticeRecive.DataBean> homeworkDataList;
    private AnnouncementListaaaAdapter homeworkListAdapter;
    private PullToRefreshListView backlog_list;
    private ListView listView;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog_send, container, false);
        mContext = getActivity();
        user.readData(mContext);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProgressViewUtil.show(getActivity());
        initView();
    }

    private void initView() {

        backlog_list = (PullToRefreshListView) getView().findViewById(R.id.backlog_list);
        listView = backlog_list.getRefreshableView();

        listView.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        homeworkDataList = new ArrayList<>();
        backlog_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
                        backlog_list.onPullDownRefreshComplete();
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
                        backlog_list.onPullUpRefreshComplete();
                    }
                }, 1000);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getAllInformation();
    }

    /**
     * 获取信息
     */

    private void getAllInformation() {

        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        String URL = "http://wxt.xiaocool.net/index.php?g=apps&m=school&a=get_receive_notice&receiverid=" + user.getUserId();
        Log.e("uuuurrrrll", URL);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String arg0) {
                Log.d("onResponse", arg0);

                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    String state = jsonObject.optString("status");
                    ProgressViewUtil.dismiss();
                    if (state.equals(CommunalInterfaces._STATE)) {
                        JSONArray dataArray = jsonObject.optJSONArray("data");
                        homeworkDataList.clear();
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.optJSONObject(i);
                            NoticeRecive.DataBean dataBean = new NoticeRecive.DataBean();
                            dataBean.setId(dataObject.optString("id"));
                            dataBean.setNoticeid(dataObject.optString("noticeid"));
                            dataBean.setReceiverid(dataObject.optString("receiverid"));
                            dataBean.setReceivertype(dataObject.optString("receivertype"));
                            dataBean.setCreate_time(dataObject.optString("create_time"));


                            JSONArray send_message = dataObject.optJSONArray("notice_info");
                            ArrayList<NoticeRecive.DataBean.NoticeInfoBean> noticeInfoBeanArrayList = new ArrayList<>();

                            for (int j = 0; j < send_message.length(); j++) {
                                JSONObject noticeinfoobject = send_message.optJSONObject(j);
                                NoticeRecive.DataBean.NoticeInfoBean sendMessageBean = new NoticeRecive.DataBean.NoticeInfoBean();

                                sendMessageBean.setName(noticeinfoobject.optString("name"));
                                sendMessageBean.setPhoto(noticeinfoobject.optString("photo"));
                                sendMessageBean.setId(noticeinfoobject.optString("id"));
                                sendMessageBean.setUserid(noticeinfoobject.optString("userid"));
                                sendMessageBean.setTitle(noticeinfoobject.optString("title"));
                                sendMessageBean.setType(noticeinfoobject.optString("type"));
                                sendMessageBean.setContent(noticeinfoobject.optString("content"));
                                sendMessageBean.setCreate_time(noticeinfoobject.optString("create_time"));
                                noticeInfoBeanArrayList.add(sendMessageBean);
                            }


                            dataBean.setNotice_info(noticeInfoBeanArrayList);


                            JSONArray picArray = dataObject.optJSONArray("pic");
                            List<NoticeRecive.DataBean.PicBean> pictureBeanList = new ArrayList<>();
                            for (int j = 0; j < picArray.length(); j++) {
                                JSONObject picObject = picArray.optJSONObject(j);
                                NoticeRecive.DataBean.PicBean pictureBean = new NoticeRecive.DataBean.PicBean();
                                pictureBean.setPhoto(picObject.optString("photo"));
                                pictureBeanList.add(pictureBean);
                            }
                            dataBean.setPic(pictureBeanList);

                            JSONArray receiverArray = dataObject.optJSONArray("receiv_list");
                            List<NoticeRecive.DataBean.ReceivListBean> receiverBeanList = new ArrayList<>();
                            for (int j = 0; j < receiverArray.length(); j++) {
                                JSONObject reciverObject = receiverArray.optJSONObject(j);
                                NoticeRecive.DataBean.ReceivListBean receiverBean = new NoticeRecive.DataBean.ReceivListBean();
                                receiverBean.setId(reciverObject.optString("id"));
                                receiverBean.setNoticeid(reciverObject.optString("noticeid"));
                                receiverBean.setReceiverid(reciverObject.optString("receiverid"));
                                receiverBean.setReceivertype(reciverObject.optString("receivertype"));
                                receiverBean.setPhone(reciverObject.optString("phone"));
                                receiverBean.setPhoto(reciverObject.optString("photo"));
                                receiverBean.setName(reciverObject.optString("name"));
                                receiverBean.setCreate_time(reciverObject.optString("create_time"));
                                receiverBeanList.add(receiverBean);

                            }
                            dataBean.setReceiv_list(receiverBeanList);

                            homeworkDataList.add(dataBean);


                        }

                        saveFirstMessageInSp();
                        if (homeworkListAdapter != null) {
                            homeworkListAdapter.notifyDataSetChanged();
                        } else {
                            homeworkListAdapter = new AnnouncementListaaaAdapter();
                            listView.setAdapter(homeworkListAdapter);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ToastUtils.ToastShort(mContext, arg0.toString());
                Log.d("onErrorResponse", arg0.toString());
            }
        });


        mQueue.add(request);

    }

    private void saveFirstMessageInSp() {
        if (homeworkDataList.size() > 0) {
            if (homeworkDataList.get(0).getNotice_info().size() > 0) {
                SPUtils.put(mContext, "noticeRecive", homeworkDataList.get(0).getNotice_info().get(0).getTitle());
            }
        }
    }

    class AnnouncementListaaaAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private DisplayImageOptions displayImage;

        private ImageLoader imageLoader = ImageLoader.getInstance();

        public AnnouncementListaaaAdapter() {

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
            return homeworkDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return homeworkDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.message_myhomework, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //详情
            convertView.findViewById(R.id.tecxt_homwork).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, NoticeReciveDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("noticedata", homeworkDataList.get(position));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            convertView.findViewById(R.id.from_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, NoticeReciveDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("noticedata", homeworkDataList.get(position));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });


            holder.homework_title.setText(homeworkDataList.get(position).getNotice_info().get(0).getTitle());
            holder.homework_content.setText(homeworkDataList.get(position).getNotice_info().get(0).getContent());
            holder.teacher_name.setText(homeworkDataList.get(position).getNotice_info().get(0).getName());

            //判断已读和未读
            final ArrayList<NoticeRecive.DataBean.ReceivListBean> notReads = new ArrayList<>();
            final ArrayList<NoticeRecive.DataBean.ReceivListBean> alreadyReads = new ArrayList<>();
            if (homeworkDataList.get(position).getReceiv_list().size() > 0) {
                for (int i = 0; i < homeworkDataList.get(position).getReceiv_list().size(); i++) {
                    if (homeworkDataList.get(position).getReceiv_list().get(i).getCreate_time() == null ||homeworkDataList.get(position).getReceiv_list().get(i).getCreate_time().equals("null")) {
                        notReads.add(homeworkDataList.get(position).getReceiv_list().get(i));
                    } else {
                        alreadyReads.add(homeworkDataList.get(position).getReceiv_list().get(i));
                    }
                }
            }
            holder.alread_text.setText("总发" + homeworkDataList.get(position).getReceiv_list().size() + " 已读" + alreadyReads.size() + " 未读" + notReads.size());

            Date date = new Date();
            date.setTime(Long.parseLong(homeworkDataList.get(position).getNotice_info().get(0).getCreate_time()) * 1000);
            holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
            Log.e(" holder",homeworkDataList.get(position).getCreate_time()+"ddddddddddd"+ holder.homework_time.getText().toString());

            //判断是否有照片
            holder.homework_img.setVisibility(View.GONE);
            if (homeworkDataList.get(position).getPic() != null) {
                if (homeworkDataList.get(position).getPic().size() > 1) {
                    holder.homework_img.setVisibility(View.GONE);
                    holder.parent_warn_img_gridview.setVisibility(View.VISIBLE);
                    final ArrayList<String> picStringArray = new ArrayList<>();
                    for (int i = 0; i < homeworkDataList.get(position).getPic().size(); i++) {
                        picStringArray.add(homeworkDataList.get(position).getPic().get(i).getPhoto());
                    }
                    ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter(picStringArray, mContext);
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

                } else if (homeworkDataList.get(position).getPic().size() == 1 && !homeworkDataList.get(position).getPic().get(0).getPhoto().equals("null") && !homeworkDataList.get(position).getPic().get(0).getPhoto().equals("")) {
                    holder.parent_warn_img_gridview.setVisibility(View.GONE);
                    holder.homework_img.setVisibility(View.VISIBLE);
                    imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
                    imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPic().get(0).getPhoto(), holder.homework_img, displayImage);
                    Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + homeworkDataList.get(position).getPic().get(0).getPhoto());
                    holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.homework_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> imgs = new ArrayList<>();
                            imgs.add(homeworkDataList.get(position).getPic().get(0).getPhoto().toString());
                            Intent intent = new Intent(mContext, CircleImagesActivity.class);
                            intent.putStringArrayListExtra("Imgs", imgs);
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    holder.homework_img.setVisibility(View.GONE);
                    holder.parent_warn_img_gridview.setVisibility(View.GONE);
                }
            } else {
                holder.homework_img.setVisibility(View.GONE);
                holder.parent_warn_img_gridview.setVisibility(View.GONE);
            }


            return convertView;
        }


        class ViewHolder {
            TextView homework_title, homework_content, teacher_name, homework_time, homework_item_praise_names, alread_text, not_read_text;
            ImageView homework_praise, homework_img, homework_discuss;
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

}
