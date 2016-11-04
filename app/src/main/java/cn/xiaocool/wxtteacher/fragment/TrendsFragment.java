package cn.xiaocool.wxtteacher.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.kaopiz.kprogresshud.KProgressHUD;
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
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.HomeworkRemarkAdapter;
import cn.xiaocool.wxtteacher.adapter.MyGridAdapter;
import cn.xiaocool.wxtteacher.bean.ClassCricleInfo;
import cn.xiaocool.wxtteacher.bean.Comments;
import cn.xiaocool.wxtteacher.bean.LikeBean;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.bean.find.IndexNewsListInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.main.AddTrendActivity;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.main.TrendsDetailActivity;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.ClassCircleRequest;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.ui.CommentPopupWindow;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshBase;
import cn.xiaocool.wxtteacher.ui.list.PullToRefreshListView;
import cn.xiaocool.wxtteacher.utils.DateUtils;
import cn.xiaocool.wxtteacher.utils.IntentUtils;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * Created by wzh on 2016/2/23.
 */
public class TrendsFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private static final String TAG = "FindFragment";
    private PullToRefreshListView list;
    /* 整个资讯 */
    private ListView lv;
    private ProfessionCircleAdapter mAdapter;

    private static final int GET_VIEWPAPER_LIST_KEY = 1;
    private static final int GET_CIRCLE_LIST_KEY = 2;
    private static final int WORK_PRAISE_KEY = 4;
    private static final int DEL_WORK_PRAISE_KEY = 5;
    private static long lastClickTime;

    /**
     * 朋友圈信息
     */
    private static ArrayList<ClassCricleInfo> indexNewsList_cricle;
    /**
     * 幻灯片资讯
     */
    private static ArrayList<IndexNewsListInfo> viewPagerList;
    /**
     * 朋友圈一级信息列表
     */
    private ArrayList<ClassCricleInfo> CricleList;



    private ImageView add_trends;//发布动态


    private int start_id = 0;

    private FragmentActivity mContext;

    private View viewH = null;


    private SliderLayout sliderLayout;
    private KProgressHUD hud;
    //事件处理句柄
    private Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {


                case CommunalInterfaces.SEND_PARENT_REMARK:
                    Log.d("是否成功", "======");

                    if (msg.obj != null) {
                        JSONObject obj2 = (JSONObject) msg.obj;
                        String state = obj2.optString("status");
                        LogUtils.e("HomeworkCommentActivity", obj2.optString("data"));
                        Log.d("是否成功", state);
                        if (state.equals(CommunalInterfaces._STATE)) {
                            Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                            getAllInformation(""+start_id);
                        } else {
                            Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                    break;

                case GET_CIRCLE_LIST_KEY:
                    getCircleListJson(msg);
                    break;
                case WORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        LogUtils.i(TAG, "点赞" + msg.obj);
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                hud.dismiss();
                                getAllInformation(""+start_id);
                            }else {
                                hud.dismiss();
                            }
                        } catch (JSONException e) {
                            hud.dismiss();
                            e.printStackTrace();
                        }
                    }
                    break;
                case DEL_WORK_PRAISE_KEY:
                    if (msg.obj != null) {
                        LogUtils.i(TAG, "取消赞" + msg.obj);
                        try {
                            JSONObject json = (JSONObject) msg.obj;
                            String state = json.getString("status");
                            String result = json.getString("data");
                            if (state.equals(CommunalInterfaces._STATE)) {
                                hud.dismiss();
                                getAllInformation(""+start_id);
                            } else {
                                hud.dismiss();
                            }
                        } catch (JSONException e) {
                            hud.dismiss();
                            e.printStackTrace();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };

    //1:页面的创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = (FragmentActivity) getActivity();
        viewPagerList = new ArrayList<IndexNewsListInfo>();
        indexNewsList_cricle = new ArrayList<ClassCricleInfo>();
        CricleList = new ArrayList<>();
    }

    //2:加载布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.information_listview, null);
        list = (PullToRefreshListView) view.findViewById(R.id.lv_comprehensive);
        add_trends = (ImageView) view.findViewById(R.id.add_trends);
        add_trends.setOnClickListener(this);
        list.setPullLoadEnabled(true);
        list.setScrollLoadEnabled(false);
        lv = list.getRefreshableView();

        lv.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));

        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (NetUtil.isConnnected(mContext) == true) {
                    start_id = 0;
                    getAllInformation("" + start_id);

                } else {
                    ToastUtils.ToastShort(mContext, "暂无网络");
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
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


                getAllInformation("" + start_id);
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


//        // 幻灯片整体
        viewH = LayoutInflater.from(getActivity()).inflate(R.layout.web_slide_image, null);
        sliderLayout = (SliderLayout) viewH.findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.ll1);
        file_maps.put("Big Bang Theory",R.drawable.ll2);
        file_maps.put("House of Cards",R.drawable.ll3);
        file_maps.put("Game of Thrones", R.drawable.ll4);
        showViewPager(file_maps);
        lv.addHeaderView(viewH);
        return view;
    }

    //轮播图片
    private void showViewPager(HashMap<String, Integer> file_maps) {
        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(mContext);
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onResume() {
        LogUtils.d("weixiaotong--", "onResume");
        super.onResume();
        getAllInformation(""+start_id);

    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtils.d("weixiaotong--", "onStart");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_trends:
                IntentUtils.getIntents(getActivity(), AddTrendActivity.class);
                break;
            default:
                break;
        }
    }



    /**
     * 获取数据
     */
    //5.2
    public void getAllInformation(String indexNewsId) {
        /* 朋友圈动态*/
        new ClassCircleRequest(mContext, handler).getCircleList(indexNewsId, "1", "1", "1", GET_CIRCLE_LIST_KEY);
    }


    /**
     * 解析数据
     */
    @SuppressWarnings({"static-access"})
    private void getCircleListJson(Message msg) {
        if (msg.obj != null) {
            JSONObject obj = (JSONObject) msg.obj;
            try {
                String state = obj.getString("status");
                if (state.equals(CommunalInterfaces._STATE)) {

                    if (start_id == 0) {
                        CricleList.clear();

                    }
                    indexNewsList_cricle.clear();
                    JSONArray items = obj.getJSONArray("data");
                    if (items.length()<1){
                        LogUtils.d("setBackground", "getResources().getDrawable(R.drawable.no_content)");
                        lv.setBackground(getResources().getDrawable(R.drawable.no_content));
                    }
                    viewPagerList.clear();
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
                        if (commentArray.length() > 0) {
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

                        indexNewsList_cricle.add(cricle);
                    }

                    CricleList.addAll(indexNewsList_cricle);
                    if (mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }else {
                        mAdapter = new ProfessionCircleAdapter(mContext, indexNewsList_cricle);
                        lv.setAdapter(mAdapter);
                    }

                    start_id = CricleList.size();



                }else {
                    LogUtils.d("setBackground", "getResources().getDrawable(R.drawable.no_content)");
                    lv.setBackground(getResources().getDrawable(R.drawable.no_content));
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    class ProfessionCircleAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<ClassCricleInfo> workRingList;
        private ImageLoader imageLoader = ImageLoader.getInstance();

        private CommentPopupWindow commentPopupWindow;

        public ProfessionCircleAdapter(Context mContext, ArrayList<ClassCricleInfo> workRings) {
            this.context = mContext;
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

            holder.head_layout.setOnClickListener(new View.OnClickListener() {
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

            //判断本人是否已经点赞
            if (isPraise(workRing.getWorkPraise())) {
                //点赞成功后图片变红
                holder.homework_praise.setSelected(true);
            } else {
                //取消点赞后
                holder.homework_praise.setSelected(false);
            }

            //点赞事件
            holder.homework_praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fastClick())return;
                        if (isPraise(workRing.getWorkPraise())) {
                            LogUtils.d("FindFragment", "delPraise");
                            delPraise(workRing.getId());
                        } else {
                            LogUtils.d("FindFragment", "workPraise");
                            workPraise(workRing.getId());
                        }


                }
            });

            //评论事件

            holder.homework_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialog(workRing.getId());

                }
            });

            //显示评论
            if (workRing.getComment().size() >= 1) {
                //显示评论布局
                holder.comment_list.setVisibility(View.VISIBLE);

                //加载数据
                holder.comment_list.setAdapter(new HomeworkRemarkAdapter(workRing.getComment(), context));

                //发送评论功能


                //长按删除评论功能
                holder.comment_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("确定删除", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();

                            }
                        }).show();
                        return true;
                    }
                });
            } else {
                holder.comment_list.setVisibility(View.GONE);

            }
            return convertView;
        }

        /**
         * [防止快速点击]
         *
         * @return
         */
        private boolean fastClick() {
            long time = System.currentTimeMillis();
            if ( time - lastClickTime < 500) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
        private void showDialog(final String id) {

//            // 1.创建弹出式对话框
//            final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);    // 系统默认Dialog没有输入框
//
//            // 获取自定义的布局
//            View alertDialogView = View.inflate(context, R.layout.edit_and_send, null);
//            final AlertDialog tempDialog = alertDialog.create();
//            tempDialog.setView(alertDialogView, 0, 0, 0, 0);
//
//            // 2.密码框-EditText。alertDialogView.findViewById(R.id.自定义布局中的文本框)
//            final EditText et_dialog_confirmphoneguardpswd = (EditText) alertDialogView.findViewById(R.id.btn_discuss);
//
//            // 确认按钮，确认验证密码
//            Button btn_dialog_resolve_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_ok);
//            btn_dialog_resolve_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
//                // 点击按钮处理
//                public void onClick(View v) {
//                    // 提取文本框中输入的文本密码
//                    if (et_dialog_confirmphoneguardpswd.getText().length() > 0) {
//                        //获取到需要上传的参数
//
//                        new NewsRequest(context, handler).send_remark(id, String.valueOf(et_dialog_confirmphoneguardpswd.getText()), "1");
//
//                    } else {
//
//                        Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
//                    }
//                    tempDialog.dismiss();
//                }
//            });
//            // 取消按钮，不验证密码
//            Button btn_dialog_cancel_confirmphoneguardpswd = (Button) alertDialogView.findViewById(R.id.btn_cancel);
//            btn_dialog_cancel_confirmphoneguardpswd.setOnClickListener(new View.OnClickListener() {
//                // 点击按钮处理
//                public void onClick(View v) {
//                    //
//                    tempDialog.dismiss();
//                }
//            });
//
//            tempDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(et_dialog_confirmphoneguardpswd, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                }
//            });
//
//            /** 3.自动弹出软键盘 **/
//            tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                public void onShow(DialogInterface dialog) {
//                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(et_dialog_confirmphoneguardpswd, InputMethodManager.SHOW_IMPLICIT);
//                }
//            });
//            tempDialog.show();

            commentPopupWindow = new CommentPopupWindow(context, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tv_comment:
                            if (commentPopupWindow.ed_comment.getText().length() > 0) {
                        new NewsRequest(context, handler).send_remark(id, String.valueOf(commentPopupWindow.ed_comment.getText()), "1");
                                commentPopupWindow.dismiss();
                                commentPopupWindow.ed_comment.setText("");
                            } else {

                                Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            });
            final EditText editText = commentPopupWindow.ed_comment;
            commentPopupWindow.showAtLocation(list, Gravity.BOTTOM, 0, 0);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                public void run() {
                    InputMethodManager inputManager =
                            (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                }
            },300);

        }

        private boolean isPraise(ArrayList<LikeBean> praises) {
            UserInfo user = new UserInfo();
            user.readData(context);
            for (int i = 0; i < praises.size(); i++) {
                if (praises.get(i).getUserid().equals(user.getUserId())) {
                    return true;
                }
            }
            return false;
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
            LinearLayout linearLayout_homework_item_praise, comment_view,head_layout;
            private NoScrollGridView img_gridview;
            private ListView comment_list;
            RoundImageView item_head;


            public ViewHolder(View convertView) {
                head_layout = (LinearLayout) convertView.findViewById(R.id.head_layout);
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

    // 点赞
    private void workPraise(String workBindId) {
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("点赞中...")
                .setCancellable(true).show();
        new ClassCircleRequest(mContext, handler).Praise(workBindId, WORK_PRAISE_KEY);
    }

    // 取消点赞
    private void delPraise(String workBindId) {
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("取消赞中...")
                .setCancellable(true).show();
        new ClassCircleRequest(mContext, handler).DelPraise(workBindId, DEL_WORK_PRAISE_KEY);
    }

    //    // 获取工作圈列表
//    private void getCircleList(String startPage) {
//        LogUtils.d("weixiaotonggetCircleList","dddi");
//        if (NetUtil.isConnnected(mContext)) {
//            LogUtils.d("weixiaotonggetCircleList","aaaa");
//            LogUtils.d("weixiaotonggetCircleList",startPage);
//            new ClassCircleRequest(mContext, handler).getCircleList(startPage, "1","1",GET_CIRCLE_LIST_KEY);
//            //indexNewsId,"1","1",GET_CIRCLE_LIST_KEY
//        } else {
////            ToastUtils.ToastShort(mContext, "网络问题，请稍后重试！");
////            if (startPage.equals("0")) {
////                new Thread() {
////                    Message msg = Message.obtain();
////
////                    public void run() {
////                        String result = mACache.getAsString("ProfessionCircle");
////                        if (result != null) {
////                            msg.what = GET_WORK_LIST_KEY;
////                            msg.obj = result;
////                            handler.sendMessage(msg);
////                        }
////                    };
////                }.start();
////            }
//        }
//
//    }
    protected void addCirCleList(ArrayList<ClassCricleInfo> cricleInfos, ClassCricleInfo work) {
        for (int i = 0; i < cricleInfos.size(); i++) {
            if (cricleInfos.get(i).getId().equals(work.getId())) {
                cricleInfos.remove(i);
                cricleInfos.add(i, work);
                return;
            }
        }
        cricleInfos.add(work);
    }
}