package cn.xiaocool.wxtteacher.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.main.TeacherInfoWebDetailActivity;
import cn.xiaocool.wxtteacher.net.request.constant.ClassCircleRequest;
import cn.xiaocool.wxtteacher.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParadiseFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private FragmentActivity mContext;
    private SliderLayout sliderLayout;
    private static final int GET_VIEWPAPER_LIST_KEY = 1;
    private HashMap<String, String> hashMap;
    private LinearLayout hd_layout,mlxt_layout,yysp_layout,sqgs_layout,jdeg_layout,yebk_layout,egxt_layout,qzly_layout;
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
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paradise, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mContext = (FragmentActivity) getActivity();
        initView();
    }

    /**
     * 初始化控件
     * */
    private void initView() {
        sliderLayout = (SliderLayout) getView().findViewById(R.id.slider);


        hd_layout = (LinearLayout) getView().findViewById(R.id.hd_layout);
        hd_layout.setOnClickListener(this);

        mlxt_layout = (LinearLayout) getView().findViewById(R.id.mlxt_layout);
        mlxt_layout.setOnClickListener(this);

        yysp_layout = (LinearLayout) getView().findViewById(R.id.yysp_layout);
        yysp_layout.setOnClickListener(this);

        sqgs_layout = (LinearLayout) getView().findViewById(R.id.sqgs_layout);
        sqgs_layout.setOnClickListener(this);

        jdeg_layout = (LinearLayout) getView().findViewById(R.id.jdeg_layout);
        jdeg_layout.setOnClickListener(this);

        yebk_layout = (LinearLayout) getView().findViewById(R.id.yebk_layout);
        yebk_layout.setOnClickListener(this);

        egxt_layout = (LinearLayout) getView().findViewById(R.id.egxt_layout);
        egxt_layout.setOnClickListener(this);

        qzly_layout = (LinearLayout) getView().findViewById(R.id.qzly_layout);
        qzly_layout.setOnClickListener(this);



        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.ll1);
        file_maps.put("Big Bang Theory",R.drawable.ll2);
        file_maps.put("House of Cards",R.drawable.ll3);
        file_maps.put("Game of Thrones", R.drawable.ll4);
        showViewPager(file_maps);
    }

    //轮播图片
    private void showViewPager(HashMap<String,Integer> file_maps) {
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
        super.onResume();
        new ClassCircleRequest(mContext, handler).getIndexSlideNewsList(GET_VIEWPAPER_LIST_KEY);
    }

    @Override
    public void onClick(View v) {
        Intent webIntent = new Intent();
        switch (v.getId()){
            //活动
            case R.id.hd_layout:
                ToastUtils.ToastShort(mContext,"该功能暂未上线！");
//                Intent intent = new Intent(getActivity(), ParadiseEventActivity.class);
//                getActivity().startActivity(intent);
                break;
            //麻辣学堂
            case R.id.mlxt_layout:
                ToastUtils.ToastShort(mContext,"该功能暂未上线！");
//                Intent intent1 = new Intent(getActivity(), ParadiseMlxtActivity.class);
//                getActivity().startActivity(intent1);
                break;
            //营养食谱
            case R.id.yysp_layout:
                webIntent.setClass(mContext, TeacherInfoWebDetailActivity.class);
                webIntent.putExtra("name", "营养食谱");
                webIntent.putExtra("title", "营养食谱");
                startActivity(webIntent);
                break;
            //睡前故事
            case R.id.sqgs_layout:
                webIntent.setClass(mContext, TeacherInfoWebDetailActivity.class);
                webIntent.putExtra("name", "睡前故事");
                webIntent.putExtra("title","睡前故事");
                startActivity(webIntent);
                break;
            //经典儿歌
            case R.id.jdeg_layout:
                webIntent.setClass(mContext, TeacherInfoWebDetailActivity.class);
                webIntent.putExtra("name", "经典儿歌");
                webIntent.putExtra("title","经典儿歌");
                startActivity(webIntent);
                break;
            //育儿百科
            case R.id.yebk_layout:
                webIntent.setClass(mContext, TeacherInfoWebDetailActivity.class);
                webIntent.putExtra("name", "育儿百科");
                webIntent.putExtra("title","育儿百科");
                startActivity(webIntent);
                break;
            //儿歌学堂
            case R.id.egxt_layout:
                ToastUtils.ToastShort(mContext,"该功能暂未上线！");
//                webIntent.setClass(mContext, TeacherInfoWebDetailActivity.class);
//                webIntent.putExtra("name", "儿歌学堂");
//                webIntent.putExtra("title","儿歌学堂");
//                startActivity(webIntent);
                break;
            //亲子乐园
            case R.id.qzly_layout:
                webIntent.setClass(mContext, TeacherInfoWebDetailActivity.class);
                webIntent.putExtra("name", "亲子乐园");
                webIntent.putExtra("title","亲子乐园");
                startActivity(webIntent);
                break;


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
}
