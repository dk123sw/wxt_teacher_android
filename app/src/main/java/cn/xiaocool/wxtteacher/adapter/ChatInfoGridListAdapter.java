package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.SortModel;

/**
 * Created by THB on 2016/6/13.
 */
public class ChatInfoGridListAdapter extends BaseAdapter{
    private ArrayList<SortModel> classListDataList;
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImage;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public ChatInfoGridListAdapter(ArrayList<SortModel> classListDataList, Context mContext) {
        this.context = mContext;
        this.classListDataList = classListDataList;
        this.inflater = LayoutInflater.from(context);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return classListDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_chat_info,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

            holder.student_name.setText(classListDataList.get(position).getName());
            imageLoader.displayImage(classListDataList.get(position).getPhoto(), holder.iv_head,displayImage);


//        if(position == getCount()-1){
//            convertView = inflater.inflate(R.layout.item_chat_add,null);
//        }
        return convertView;
    }
    class ViewHolder{
        TextView student_name;
        ImageView iv_head;
        CheckBox check_class;
        public ViewHolder(View convertView) {
            student_name = (TextView) convertView.findViewById(R.id.student_name);
            iv_head = (ImageView) convertView.findViewById(R.id.student_avatar);
        }
    }
}

