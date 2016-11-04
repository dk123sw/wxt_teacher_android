package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.wxtteacher.R;

/**
 * Created by wzh on 2016/3/27.
 */
public class SpaceRecipesAdapter extends BaseAdapter{
    private Context context;
    private String picName;
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String,Object>> hashMapList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public SpaceRecipesAdapter(Context context, ArrayList<HashMap<String, Object>> hashMapList){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.hashMapList = hashMapList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }
    public String getPic(String pic){
        this.picName = pic;
        return picName;
    }
    @Override
    public int getCount() {
        return hashMapList.size();
    }

    @Override
    public Object getItem(int position) {
        return hashMapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //根据布局文件实例化View
        convertView = layoutInflater.inflate(R.layout.web_recipes_item,null);
        //找到控件
        ImageView img = (ImageView) convertView.findViewById(R.id.recipes_picture);
        TextView title = (TextView) convertView.findViewById(R.id.recipes_title);
        TextView info = (TextView) convertView.findViewById(R.id.recipes_info);

        //向控件内添加数据
        title.setText(hashMapList.get(position).get("title").toString());
        info.setText(hashMapList.get(position).get("info").toString());
        String pic = hashMapList.get(position).get("pic").toString();
        Log.e("hashmap",hashMapList.get(position).get("pic").toString());
//        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage("http://www.xiaocool.cn:8016/uploads/recipe/" + pic,img,displayImageOptions);
        return convertView;
    }
}
