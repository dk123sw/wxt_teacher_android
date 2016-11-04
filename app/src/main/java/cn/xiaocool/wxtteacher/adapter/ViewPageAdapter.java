package cn.xiaocool.wxtteacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.finalteam.galleryfinal.widget.zoonview.PhotoViewAttacher;
import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;



public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<String> images;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public ViewPageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if(containerTemp == null) containerTemp = container;

        View view = cacheView.get(position);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.vp_item_image,container,false);
            view.setTag(position);
            final ImageView image = (ImageView) view.findViewById(R.id.image);
            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            Activity activity = (Activity)context;
            String type =activity.getIntent().getStringExtra("type");
            String imagesUrl = NetBaseConstant.NET_CIRCLEPIC_HOST+images.get(position);
            if(type.equals("6")){
                imagesUrl = "http://wxt.xiaocool.net/data/upload/" + images.get(position);
            }

            Picasso.with(context).load(imagesUrl).config(Bitmap.Config.RGB_565).into(image, new Callback() {
                @Override
                public void onSuccess() {
                    photoViewAttacher.update();
                }

                @Override
                public void onError() {

                }
            });

            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            cacheView.put(position,view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
