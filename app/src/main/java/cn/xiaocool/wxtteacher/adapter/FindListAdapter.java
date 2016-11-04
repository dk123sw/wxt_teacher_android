package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.find.IndexNewsListInfo;
import cn.xiaocool.wxtteacher.bean.find.NewsImgInfo;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;



/**
 * Created by wzh on 2016/2/20.
 */
public class FindListAdapter extends BaseAdapter {
    private static final int ITEM_LAYOUT_TYPE_COUNT = 3;
    private ArrayList<IndexNewsListInfo> indexLists;
    private Context mContext;
    // private RequestQueue queue;
    // private ImageLoader imageLoader;
    private static final String TAG = "InformationListAdapter";
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public FindListAdapter(Context context,
                           ArrayList<IndexNewsListInfo> index) {
        this.mContext = context;
        this.indexLists = index;
        // queue = Volley.newRequestQueue(mContext);
        // imageLoader = new ImageLoader(queue, new BitmapCache());
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.article_img)
                .showImageOnFail(R.drawable.article_img).cacheInMemory(true)
                .cacheOnDisc(true).build();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_LAYOUT_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(indexLists.get(position).getNewsPicState()
                .toString());
    }

    @Override
    public int getCount() {
        return indexLists.size();
    }

    @Override
    public Object getItem(int position) {
        return indexLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("static-access")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int layoutType = getItemViewType(position);
        CommonViewHolder commonHolder = null;
        ImagesViewHolder imagesHolder = null;
        VideoViewHolder videoHolder = null;
        LayoutInflater inflater = null;
        // 无convertView，需要new出各个控件
        if (convertView == null) {
            // 按当前所需的样式，确定new的布局
            switch (layoutType) {
                case 0:
                    inflater = (LayoutInflater) mContext
                            .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(
                            R.layout.recommend_information_article, null);
                    commonHolder = new CommonViewHolder();
                    commonHolder.iv_article_img = (ImageView) convertView
                            .findViewById(R.id.iv_article_img);
                    commonHolder.tv_article_content = (TextView) convertView
                            .findViewById(R.id.tv_article_content);
                    commonHolder.tv_article_title = (TextView) convertView
                            .findViewById(R.id.tv_article_title);
                    commonHolder.tv_article_pinlun = (TextView) convertView
                            .findViewById(R.id.tv_article_pinlun);
                    commonHolder.tv_article_time = (TextView) convertView
                            .findViewById(R.id.tv_article_time);
                    commonHolder.layout_recommend_article = (LinearLayout) convertView
                            .findViewById(R.id.layout_recommend_article);
                    commonHolder.tv_article_zhuanti = (TextView) convertView
                            .findViewById(R.id.tv_article_zhuanti);
                    convertView.setTag(commonHolder);
                    break;
                case 2:
                    inflater = (LayoutInflater) mContext
                            .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(
                            R.layout.recommend_information_video, null);
                    videoHolder = new VideoViewHolder();
                    videoHolder.tv_video_content = (TextView) convertView
                            .findViewById(R.id.tv_video_content);
                    videoHolder.tv_video_title = (TextView) convertView
                            .findViewById(R.id.tv_video_title);
                    videoHolder.tv_video_pinlun = (TextView) convertView
                            .findViewById(R.id.tv_video_pinlun);
                    videoHolder.tv_video_time = (TextView) convertView
                            .findViewById(R.id.tv_video_time);
                    videoHolder.iv_recommend_video_img = (ImageView) convertView
                            .findViewById(R.id.iv_recommend_video_img);
                    videoHolder.layout_recommend_video = (LinearLayout) convertView
                            .findViewById(R.id.layout_recommend_video);
                    videoHolder.tv_video_zhuanti = (TextView) convertView
                            .findViewById(R.id.tv_video_zhuanti);
                    convertView.setTag(videoHolder);
                    break;
                case 1:
                    inflater = (LayoutInflater) mContext
                            .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(
                            R.layout.recommend_information_picture, null);

                    imagesHolder = new ImagesViewHolder();
                    imagesHolder.tv_picture_title = (TextView) convertView
                            .findViewById(R.id.tv_picture_title);
                    imagesHolder.iv_picture1_img = (ImageView) convertView
                            .findViewById(R.id.iv_picture1_img);
                    imagesHolder.iv_picture2_img = (ImageView) convertView
                            .findViewById(R.id.iv_picture2_img);
                    imagesHolder.iv_picture3_img = (ImageView) convertView
                            .findViewById(R.id.iv_picture3_img);
                    imagesHolder.tv_picture_canyin = (TextView) convertView
                            .findViewById(R.id.tv_picture_canyin);
                    imagesHolder.tv_picture_pinlun = (TextView) convertView
                            .findViewById(R.id.tv_picture_pinlun);
                    imagesHolder.tv_picture_time = (TextView) convertView
                            .findViewById(R.id.tv_picture_time);
                    imagesHolder.layout_recommend_picture = (LinearLayout) convertView
                            .findViewById(R.id.layout_recommend_picture);
                    imagesHolder.tv_picture_zhuanti = (TextView) convertView
                            .findViewById(R.id.tv_picture_zhuanti);
                    convertView.setTag(imagesHolder);
                    break;
            }
        } else {
            // 有convertView，按样式，取得不用的布局
            switch (layoutType) {
                case 0:
                    commonHolder = (CommonViewHolder) convertView.getTag();
                    break;
                case 2:
                    videoHolder = (VideoViewHolder) convertView.getTag();
                    break;
                case 1:
                    imagesHolder = (ImagesViewHolder) convertView.getTag();
                    break;
            }
        }
        /**
         * 返回不同的类型设置不同类型的资源
         */
        switch (layoutType) {
            case 0:
                commonHolder.tv_article_content.setText(indexLists.get(position)
                        .getNewsTitle().toString());
                commonHolder.tv_article_title.setText(indexLists.get(position)
                        .getNewsAuthor().toString());
                commonHolder.tv_article_pinlun.setText(indexLists.get(position)
                        .getNewsNum().toString()
                        + "评论");

                Long commonTime = Long.parseLong(indexLists.get(position).getNewsTime()
                        .toString());
                String commonDay = TimeToolUtils.fromateTimeShowByRule(commonTime * 1000);
                commonHolder.tv_article_time.setText(commonDay);
                if (indexLists.get(position).getNewsClass().equals("0")
                        || indexLists.get(position).getNewsClass().equals("null")) {
                    commonHolder.tv_article_zhuanti.setVisibility(View.GONE);
                } else {
                    commonHolder.tv_article_zhuanti.setVisibility(View.VISIBLE);
                }
                if (indexLists.get(position).getNewsImg().size() == 0) {
                    commonHolder.iv_article_img
                            .setImageResource(R.drawable.article_img);
                } else {
                    final NewsImgInfo newsImgInfo = indexLists.get(position)
                            .getNewsImg().get(0);
                    final String imgUrl = NetBaseConstant.NET_HOST
                            + newsImgInfo.getPath();
                    imageLoader.displayImage(imgUrl, commonHolder.iv_article_img,
                            options);
                    // if (imgUrl != null && !imgUrl.equals("")) {
                    // commonHolder.iv_article_img
                    // .setDefaultImageResId(R.drawable.article_img);
                    // commonHolder.iv_article_img
                    // .setImageUrl(imgUrl, imageLoader);
                    // }
                }
                commonHolder.layout_recommend_article
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (indexLists.get(position).getNewsClass()
                                        .equals("0")
                                        || indexLists.get(position).getNewsClass()
                                        .equals("null")) {
//                                    getBundle(position, "article",
//                                            ShareActivity.class, "普通资讯");
                                } else {
//                                    getBundleNewCultivate(indexLists.get(position)
//                                                    .getNewsClass(),
//                                            NewCultivateActivity.class);
                                }
                            }
                        });
                break;
            case 2:
                videoHolder.tv_video_content.setText(indexLists.get(position)
                        .getNewsTitle().toString());
                videoHolder.tv_video_title.setText(indexLists.get(position)
                        .getNewsAuthor().toString());
                videoHolder.tv_video_pinlun.setText(indexLists.get(position)
                        .getNewsNum().toString()
                        + "评论");
                Long videoTime = Long.parseLong(indexLists.get(position).getNewsTime()
                        .toString());
                String videoDay = TimeToolUtils.fromateTimeShowByRule(videoTime * 1000);
                videoHolder.tv_video_time.setText(videoDay);
                if (indexLists.get(position).getNewsClass().equals("0")
                        || indexLists.get(position).getNewsClass().equals("null")) {
                    videoHolder.tv_video_zhuanti.setVisibility(View.GONE);
                } else {
                    videoHolder.tv_video_zhuanti.setVisibility(View.VISIBLE);
                }

                if (indexLists.get(position).getNewsImg().size() == 0) {
                    videoHolder.iv_recommend_video_img
                            .setImageResource(R.drawable.article_img);
                    ;
                } else {
                    final NewsImgInfo newsImgInfo1 = indexLists.get(position)
                            .getNewsImg().get(0);
                    final String imgUrl1 = NetBaseConstant.NET_HOST
                            + newsImgInfo1.getPath();
                    imageLoader.displayImage(imgUrl1,
                            videoHolder.iv_recommend_video_img, options);
                    // if (imgUrl1 != null && !imgUrl1.equals("")) {
                    // videoHolder.iv_recommend_video_img
                    // .setDefaultImageResId(R.drawable.article_img);
                    // videoHolder.iv_recommend_video_img.setImageUrl(imgUrl1,
                    // imageLoader);
                    // }
                }
                videoHolder.layout_recommend_video
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (indexLists.get(position).getNewsClass()
                                        .equals("0")
                                        || indexLists.get(position).getNewsClass()
                                        .equals("null")) {
//                                    getBundle(
//                                            position,
//                                            "video",
//                                            InformationRecommendVideoActivity.class,
//                                            "视频");
                                } else {
//                                    getBundleNewCultivate(indexLists.get(position)
//                                                    .getNewsClass(),
//                                            NewCultivateActivity.class);
                                }
                            }
                        });
                break;
            case 1:
                imagesHolder.tv_picture_title.setText(indexLists.get(position)
                        .getNewsTitle().toString());
                imagesHolder.tv_picture_canyin.setText(indexLists.get(position)
                        .getNewsAuthor().toString());
                imagesHolder.tv_picture_pinlun.setText(indexLists.get(position)
                        .getNewsNum().toString()
                        + "评论");
                Long imagesTime = Long.parseLong(indexLists.get(position).getNewsTime()
                        .toString());
                String imagesDay = TimeToolUtils.fromateTimeShowByRule(imagesTime * 1000);
                imagesHolder.tv_picture_time.setText(imagesDay);
                if (indexLists.get(position).getNewsClass().equals("0")
                        || indexLists.get(position).getNewsClass().equals(null)) {
                    imagesHolder.tv_picture_zhuanti.setVisibility(View.GONE);
                } else {
                    imagesHolder.tv_picture_zhuanti.setVisibility(View.VISIBLE);
                }

                NewsImgInfo images1 = null;
                NewsImgInfo images2 = null;
                NewsImgInfo images3 = null;
                String imagesUrl1 = null;
                String imagesUrl2 = null;
                String imagesUrl3 = null;
                int size = indexLists.get(position).getNewsImgInfoBoolean();
                if (size > 3) {
                    images1 = indexLists.get(position).getNewsImg().get(0);
                    imagesUrl1 = NetBaseConstant.NET_HOST + images1.getPath();
                    if (imagesUrl1 != null && !imagesUrl1.equals("")) {
                        imageLoader.displayImage(imagesUrl1,
                                imagesHolder.iv_picture1_img, options);
                        // imagesHolder.iv_picture1_img
                        // .setDefaultImageResId(R.drawable.article_img);
                        // imagesHolder.iv_picture1_img.setImageUrl(imagesUrl1,
                        // imageLoader);
                    }
                    images2 = indexLists.get(position).getNewsImg().get(1);
                    imagesUrl2 = NetBaseConstant.NET_HOST + images2.getPath();
                    if (imagesUrl2 != null && !imagesUrl2.equals("")) {
                        // imagesHolder.iv_picture2_img
                        // .setDefaultImageResId(R.drawable.article_img);
                        // imagesHolder.iv_picture2_img.setImageUrl(imagesUrl2,
                        // imageLoader);
                        imageLoader.displayImage(imagesUrl2,
                                imagesHolder.iv_picture2_img, options);
                    }
                    images3 = indexLists.get(position).getNewsImg().get(2);
                    imagesUrl3 = NetBaseConstant.NET_HOST + images3.getPath();
                    if (imagesUrl3 != null && !imagesUrl3.equals("")) {
                        // imagesHolder.iv_picture3_img
                        // .setDefaultImageResId(R.drawable.article_img);
                        // imagesHolder.iv_picture3_img.setImageUrl(imagesUrl3,
                        // imageLoader);
                        imageLoader.displayImage(imagesUrl3,
                                imagesHolder.iv_picture3_img, options);
                    }
                } else {
                    /**
                     * 根据 图片返回的数量进行判断
                     */
                    switch (size) {
                        case 1:
                            images1 = indexLists.get(position).getNewsImg().get(0);
                            imagesUrl1 = NetBaseConstant.NET_HOST + images1.getPath();
                            if (imagesUrl1 != null && !imagesUrl1.equals("")) {
                                // imagesHolder.iv_picture1_img
                                // .setDefaultImageResId(R.drawable.article_img);
                                // imagesHolder.iv_picture1_img.setImageUrl(imagesUrl1,
                                // imageLoader);
                                imageLoader.displayImage(imagesUrl1,
                                        imagesHolder.iv_picture1_img, options);
                            }
                            imagesHolder.iv_picture2_img
                                    .setImageResource(R.drawable.article_img);
                            imagesHolder.iv_picture3_img
                                    .setImageResource(R.drawable.article_img);
                            break;
                        case 2:
                            images1 = indexLists.get(position).getNewsImg().get(0);
                            imagesUrl1 = NetBaseConstant.NET_HOST + images1.getPath();
                            if (imagesUrl1 != null && !imagesUrl1.equals("")) {
                                // imagesHolder.iv_picture1_img
                                // .setDefaultImageResId(R.drawable.article_img);
                                // imagesHolder.iv_picture1_img.setImageUrl(imagesUrl1,
                                // imageLoader);
                                imageLoader.displayImage(imagesUrl1,
                                        imagesHolder.iv_picture1_img, options);
                            }
                            images2 = indexLists.get(position).getNewsImg().get(1);
                            imagesUrl2 = NetBaseConstant.NET_HOST + images2.getPath();
                            if (imagesUrl2 != null && !imagesUrl2.equals("")) {
                                // imagesHolder.iv_picture2_img
                                // .setDefaultImageResId(R.drawable.article_img);
                                // imagesHolder.iv_picture2_img.setImageUrl(imagesUrl2,
                                // imageLoader);
                                imageLoader.displayImage(imagesUrl2,
                                        imagesHolder.iv_picture2_img, options);
                            }
                            imagesHolder.iv_picture3_img
                                    .setImageResource(R.drawable.article_img);
                            ;
                            break;
                        case 3:
                            images1 = indexLists.get(position).getNewsImg().get(0);
                            imagesUrl1 = NetBaseConstant.NET_HOST + images1.getPath();
                            if (imagesUrl1 != null && !imagesUrl1.equals("")) {
                                // imagesHolder.iv_picture1_img
                                // .setDefaultImageResId(R.drawable.article_img);
                                // imagesHolder.iv_picture1_img.setImageUrl(imagesUrl1,
                                // imageLoader);
                                imageLoader.displayImage(imagesUrl1,
                                        imagesHolder.iv_picture1_img, options);
                            }
                            images2 = indexLists.get(position).getNewsImg().get(1);
                            imagesUrl2 = NetBaseConstant.NET_HOST + images2.getPath();
                            if (imagesUrl2 != null && !imagesUrl2.equals("")) {
                                // imagesHolder.iv_picture2_img
                                // .setDefaultImageResId(R.drawable.article_img);
                                // imagesHolder.iv_picture2_img.setImageUrl(imagesUrl2,
                                // imageLoader);
                                imageLoader.displayImage(imagesUrl2,
                                        imagesHolder.iv_picture2_img, options);
                            }
                            images3 = indexLists.get(position).getNewsImg().get(2);
                            imagesUrl3 = NetBaseConstant.NET_HOST + images3.getPath();
                            if (imagesUrl3 != null && !imagesUrl3.equals("")) {
                                // imagesHolder.iv_picture3_img
                                // .setDefaultImageResId(R.drawable.article_img);
                                // imagesHolder.iv_picture3_img.setImageUrl(imagesUrl3,
                                // imageLoader);
                                imageLoader.displayImage(imagesUrl3,
                                        imagesHolder.iv_picture3_img, options);
                            }
                            break;
                        case 0:
                            imagesHolder.iv_picture1_img
                                    .setImageResource(R.drawable.article_img);
                            ;
                            imagesHolder.iv_picture2_img
                                    .setImageResource(R.drawable.article_img);
                            imagesHolder.iv_picture3_img
                                    .setImageResource(R.drawable.article_img);
                            ;
                            break;
                    }
                }

                imagesHolder.layout_recommend_picture
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (indexLists.get(position).getNewsClass()
                                        .equals("0")
                                        || indexLists.get(position).getNewsClass()
                                        .equals("null")) {
//                                    getBundle(position, "picture",
//                                            ImagesInformationItemActivity.class,
//                                            "多图文");
                                } else {
//                                    getBundleNewCultivate(indexLists.get(position)
//                                                    .getNewsClass(),
//                                            NewCultivateActivity.class);
                                }
                            }
                        });
                break;
        }
        return convertView;
    }

    @SuppressWarnings("rawtypes")
    public void getBundle(final int position, String key, Class clazz,
                          String str) {
        IndexNewsListInfo indexNewsList = indexLists.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, indexNewsList);
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
        LogUtils.e(str, indexNewsList.toString());
    }

    @SuppressWarnings("rawtypes")
    public void getBundleNewCultivate(String newsClass, Class clazz) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("NewsClass", newsClass);
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 普通
     */
    static class CommonViewHolder {
        LinearLayout layout_recommend_article;
        ImageView iv_article_img;
        TextView tv_article_content;
        TextView tv_article_title;
        TextView tv_article_pinlun;
        TextView tv_article_time;
        TextView tv_article_zhuanti;
    }

    /**
     * 多图文
     */
    static class ImagesViewHolder {
        TextView tv_picture_title;
        ImageView iv_picture1_img;
        ImageView iv_picture2_img;
        ImageView iv_picture3_img;
        TextView tv_picture_canyin;
        TextView tv_picture_pinlun;
        TextView tv_picture_time;
        TextView tv_picture_zhuanti;
        LinearLayout layout_recommend_picture;
    }

    /**
     * 视频
     */
    static class VideoViewHolder {
        ImageView iv_recommend_video_img;
        TextView tv_video_content;
        TextView tv_video_title;
        TextView tv_video_pinlun;
        TextView tv_video_time;
        TextView tv_video_zhuanti;
        LinearLayout layout_recommend_video;
    }

}