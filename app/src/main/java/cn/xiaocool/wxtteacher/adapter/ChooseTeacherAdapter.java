package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.SortModel;

/**
 * Created by Administrator on 2016/5/14.
 */
public class ChooseTeacherAdapter extends BaseAdapter implements SectionIndexer {

    private ArrayList<SortModel> list = null;
    private Context mContext;
    private ImageLoader imageLoader= ImageLoader.getInstance();
    private DisplayImageOptions displayImage;
    public ChooseTeacherAdapter(ArrayList<SortModel> list,Context mContext) {
        this.mContext = mContext;
        this.list = list;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }


    public void updateListView(ArrayList<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.iv_image = (ImageView) view.findViewById(R.id.image);
            viewHolder.iv_call = (ImageView) view.findViewById(R.id.call);
            viewHolder.iv_call.setVisibility(View.GONE);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());
//        ImageLoader.getInstance().displayImage(list.get(position).getPhoto(), viewHolder.iv_image);
        imageLoader.displayImage(list.get(position).getPhoto(), viewHolder.iv_image, displayImage);


        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView iv_image, iv_call;
    }


    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
