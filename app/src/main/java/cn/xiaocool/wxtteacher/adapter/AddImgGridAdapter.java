package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by Administrator on 2016/5/19.
 */
public class AddImgGridAdapter extends BaseAdapter {

    private ArrayList<Drawable> tags;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    public AddImgGridAdapter(ArrayList<Drawable> tag, Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        if (tag == null) {
            tag = new ArrayList<Drawable>();
        }
        this.tags = tag;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Drawable getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Drawable tag = tags.get(position);
        LogUtils.i("TagRequest", "adpter" + tag);
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_gridimg, parent, false);
            viewHolder.table = (ImageView) convertView.findViewById(R.id.tv_me_tag_list_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        viewHolder.table.setImageDrawable(getItem(position));
        return convertView;
    }

    private static class MyGridViewHolder {
        ImageView table;
    }
}
