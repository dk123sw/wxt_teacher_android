package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.TeacherInfosAdapter;
import cn.xiaocool.wxtteacher.bean.TeacherInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.NewsRequest;
import cn.xiaocool.wxtteacher.net.request.constant.ClassCircleRequest;
import cn.xiaocool.wxtteacher.ui.NoScrollListView;
import cn.xiaocool.wxtteacher.utils.IntentUtils;

/**
 * Created by 潘 on 2016/4/5.
 */
public class SpeaceSchoolWebActivity extends Activity implements View.OnClickListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private RelativeLayout building, people, nearby, web_teacher_style;
    private TextView tv_red;
    private RelativeLayout up_jiantou;
    private Context mContext;
    private SliderLayout sliderLayout;
    private static final int GET_VIEWPAPER_LIST_KEY = 1;
    private HashMap<String, String> hashMap;
    private ArrayList<TeacherInfo> gonggaoInfos,newsInfos,yuerInfos;
    //定义八个按钮的布局
    private RelativeLayout rlIntroduce, rlStyle, rlRecipes, rlRecruit, rlClass, rlcroll, rlemail;
    //三个list(公告、新闻动态、育儿知识)
    private NoScrollListView web_gonggao_list,web_news_list,web_yuer_list;
    private LinearLayout gonggao_more,yuer_more,news_more;
    private int tag=0;

    private Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_VIEWPAPER_LIST_KEY:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (jsonObject.optString("status").equals("success")) {
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        int length = jsonArray.length();
                        hashMap = new HashMap<>();
                        for (int i = 0; i < length; i++) {
                            String name = jsonArray.optJSONObject(i).optString("ap_id");
                            String url ="http://wxt.xiaocool.net/uploads/Viwepager/"+jsonArray.optJSONObject(i).optString("picture_name");
                            hashMap.put(name,url);
                        }
//                        showViewPager(hashMap);
                    }
                    break;
                //校园通知
                case 5:
                    JSONObject obj = (JSONObject) msg.obj;
                    if (obj.optString("status").equals(CommunalInterfaces._STATE)) {
                        gonggaoInfos.clear();
                        JSONArray hwArray = obj.optJSONArray("data");
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            TeacherInfo teacherInfo = new TeacherInfo();
                            teacherInfo.setId(itemObject.optString("id"));
                            teacherInfo.setPost_date(itemObject.optString("post_date"));
                            teacherInfo.setPost_keywords(itemObject.optString("post_keywords"));
                            teacherInfo.setPost_title(itemObject.optString("post_title"));
                            teacherInfo.setPost_excerpt(itemObject.optString("post_excerpt"));
                            teacherInfo.setSchoolid(itemObject.optString("create_time"));
                            teacherInfo.setThumb(itemObject.optString("thumb"));

                            gonggaoInfos.add(teacherInfo);
                        }

                        web_gonggao_list.setAdapter(new TeacherInfosAdapter(gonggaoInfos,getApplicationContext(),"3"));
                        web_gonggao_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(),TeacherInfoWebDetailActivity.class);
                                intent.putExtra("itemid",gonggaoInfos.get(position).getId());
                                intent.putExtra("title",gonggaoInfos.get(position).getPost_title());
                                intent.putExtra("type","6");
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                //新闻动态
                case 6:
                    JSONObject obj6 = (JSONObject) msg.obj;
                    if (obj6.optString("status").equals(CommunalInterfaces._STATE)) {
                        newsInfos.clear();
                        JSONArray hwArray = obj6.optJSONArray("data");
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            TeacherInfo teacherInfo = new TeacherInfo();
                            teacherInfo.setId(itemObject.optString("id"));
                            teacherInfo.setPost_date(itemObject.optString("post_date"));
                            teacherInfo.setPost_keywords(itemObject.optString("post_keywords"));
                            teacherInfo.setPost_title(itemObject.optString("post_title"));
                            teacherInfo.setPost_excerpt(itemObject.optString("post_excerpt"));
                            teacherInfo.setSchoolid(itemObject.optString("create_time"));
                            teacherInfo.setThumb(itemObject.optString("thumb"));

                            newsInfos.add(teacherInfo);
                        }

                        web_news_list.setAdapter(new TeacherInfosAdapter(newsInfos,getApplicationContext(),"3"));
                        web_news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(),TeacherInfoWebDetailActivity.class);
                                intent.putExtra("itemid",newsInfos.get(position).getId());
                                intent.putExtra("title",newsInfos.get(position).getPost_title());
                                intent.putExtra("type","7");
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                //育儿知识
                case 7:
                    JSONObject obj7 = (JSONObject) msg.obj;
                    if (obj7.optString("status").equals(CommunalInterfaces._STATE)) {
                        yuerInfos.clear();
                        JSONArray hwArray = obj7.optJSONArray("data");
                        JSONObject itemObject;
                        for (int i = 0; i < hwArray.length(); i++) {
                            itemObject = hwArray.optJSONObject(i);

                            TeacherInfo teacherInfo = new TeacherInfo();
                            teacherInfo.setId(itemObject.optString("id"));
                            teacherInfo.setPost_date(itemObject.optString("post_date"));
                            teacherInfo.setPost_keywords(itemObject.optString("post_keywords"));
                            teacherInfo.setPost_title(itemObject.optString("post_title"));
                            teacherInfo.setPost_excerpt(itemObject.optString("post_excerpt"));
                            teacherInfo.setSchoolid(itemObject.optString("create_time"));
                            teacherInfo.setThumb(itemObject.optString("thumb"));

                            yuerInfos.add(teacherInfo);
                        }

                        web_yuer_list.setAdapter(new TeacherInfosAdapter(yuerInfos,getApplicationContext(),"3"));
                        web_yuer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(),TeacherInfoWebDetailActivity.class);
                                intent.putExtra("itemid",yuerInfos.get(position).getId());
                                intent.putExtra("title",yuerInfos.get(position).getPost_title());
                                intent.putExtra("type","8");
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_web);
        mContext = this;
        initView();

    }

    private void initView() {
        gonggaoInfos = new ArrayList<>();
        newsInfos = new ArrayList<>();
        yuerInfos = new ArrayList<>();
        //初始化RL按钮组件
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        rlIntroduce = (RelativeLayout) findViewById(R.id.web_introduce);
        rlIntroduce.setOnClickListener(this);
        rlStyle = (RelativeLayout) findViewById(R.id.web_baby_show);
        rlStyle.setOnClickListener(this);
        rlRecipes = (RelativeLayout) findViewById(R.id.web_recipes);
        rlRecipes.setOnClickListener(this);
        rlRecruit = (RelativeLayout) findViewById(R.id.web_recruit);
        rlRecruit.setOnClickListener(this);
        rlemail = (RelativeLayout) findViewById(R.id.web_email);
        rlemail.setOnClickListener(this);
        rlClass = (RelativeLayout) findViewById(R.id.web_class);
        rlClass.setOnClickListener(this);
        rlcroll = (RelativeLayout) findViewById(R.id.web_enroll);
        rlcroll.setOnClickListener(this);
        up_jiantou = (RelativeLayout) findViewById(R.id.up_jiantou);
        up_jiantou.setOnClickListener(this);
        web_teacher_style = (RelativeLayout) findViewById(R.id.web_teacher_style);
        web_teacher_style.setOnClickListener(this);
        RelativeLayout rl_announcement = (RelativeLayout) findViewById(R.id.announcement);
        rl_announcement.setOnClickListener(this);
        RelativeLayout rl_rearing_child = (RelativeLayout) findViewById(R.id.rearing_child);
        rl_rearing_child.setOnClickListener(this);

        //初始化列表部分
        web_gonggao_list = (NoScrollListView) findViewById(R.id.web_gonggao_list);
        web_news_list = (NoScrollListView) findViewById(R.id.web_news_list);
        web_yuer_list = (NoScrollListView) findViewById(R.id.web_yuer_list);

        gonggao_more = (LinearLayout) findViewById(R.id.gonggao_more);
        gonggao_more.setOnClickListener(this);
        news_more = (LinearLayout) findViewById(R.id.news_more);
        news_more.setOnClickListener(this);
        yuer_more = (LinearLayout) findViewById(R.id.yuer_more);
        yuer_more.setOnClickListener(this);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.ll1);
        file_maps.put("Big Bang Theory",R.drawable.ll2);
        file_maps.put("House of Cards", R.drawable.ll4);
        file_maps.put("Game of Thrones", R.drawable.ll4);
        showViewPager(file_maps);

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    //轮播图片
    private void showViewPager(HashMap<String,Integer> file_maps) {
        if(tag==0) {
            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
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
            tag = 1;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出
            case R.id.up_jiantou:
                finish();
                break;
            //教师风采
            case R.id.web_teacher_style:
                Intent intent1 = new Intent(mContext,TeacherStyleActivity.class);
                intent1.putExtra("type","1");
                intent1.putExtra("title","教师风采");
                startActivity(intent1);
                break;
            //园区介绍
            case R.id.web_introduce:
                Intent intent5 = new Intent(mContext,TeacherStyleActivity.class);
                intent5.putExtra("type","5");
                intent5.putExtra("title","园区介绍");
                startActivity(intent5);
                break;
            //宝宝秀场
            case R.id.web_baby_show:
                Intent intent2 = new Intent(mContext,TeacherStyleActivity.class);
                intent2.putExtra("type","2");
                intent2.putExtra("title","宝宝秀场");
                startActivity(intent2);
                break;
            //校园招聘
            case R.id.web_recruit:
                Intent intent3 = new Intent(mContext,TeacherStyleActivity.class);
                intent3.putExtra("type","3");
                intent3.putExtra("title","校园招聘");
                startActivity(intent3);
                break;
            //院长信箱
            case R.id.web_email:
                IntentUtils.getIntents(mContext,ChildStarActivity.class);
                break;
            //兴趣班
            case R.id.web_class:
                Intent intent4 = new Intent(mContext,TeacherStyleActivity.class);
                intent4.putExtra("type","4");
                intent4.putExtra("title","兴趣班");
                startActivity(intent4);
                break;
            //入学报名
            case R.id.web_enroll:

                IntentUtils.getIntents(mContext,ApplySchoolActivity.class);
                break;
            //今日食谱
            case R.id.web_recipes:
                IntentUtils.getIntents(mContext,SpaceClickRecipesActivity.class);
                break;

            //更多公告
            case R.id.gonggao_more:
                Intent gonggao = new Intent(mContext,TeacherStyleActivity.class);
                gonggao.putExtra("type","6");
                gonggao.putExtra("title","校园通知");
                startActivity(gonggao);
                break;
            //更多新闻
            case R.id.news_more:
                Intent news = new Intent(mContext,TeacherStyleActivity.class);
                news.putExtra("type","7");
                news.putExtra("title","新闻动态");
                startActivity(news);
                break;
            //更多育儿知识
            case R.id.yuer_more:
                Intent yuer = new Intent(mContext,TeacherStyleActivity.class);
                yuer.putExtra("type","8");
                yuer.putExtra("title","育儿知识");
                startActivity(yuer);
                break;
        }

    }

    public void refresh() {
        // TODO Auto-generated method stub
//        if (WxtApplication.professionCircleCount > 0) {
//            tv_red.setVisibility(View.VISIBLE);
//        } else {
//            tv_red.setVisibility(View.INVISIBLE);
//        }
      /* 幻灯片 */
        new ClassCircleRequest(mContext, handler).getIndexSlideNewsList(GET_VIEWPAPER_LIST_KEY);
        //获取下面列表数据
        new NewsRequest(getApplicationContext(),handler).getSchoolListInfo("getSchoolNotices", 5);
        new NewsRequest(getApplicationContext(),handler).getSchoolListInfo("getSchoolNews",6);
        new NewsRequest(getApplicationContext(),handler).getSchoolListInfo("getParentsThings",7);
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
}

