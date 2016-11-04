package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.TagInfo;
import cn.xiaocool.wxtteacher.utils.LogUtils;

/**
 * Created by wzh on 2016/2/20.
 */
public class MeGridAdapter extends BaseAdapter {
    private ArrayList<TagInfo> tags;
    private LayoutInflater mLayoutInflater;

    public MeGridAdapter(ArrayList<TagInfo> tag, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        if (tag == null) {
            tag = new ArrayList<TagInfo>();
        }
        this.tags = tag;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public TagInfo getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TagInfo tag = tags.get(position);
        LogUtils.i("TagRequest", "adpter" + tag);
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.me_tag_list_item, parent, false);
            viewHolder.table = (TextView) convertView.findViewById(R.id.tv_me_tag_list_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        viewHolder.table.setText(tag.getTagName());
        return convertView;
    }

    private static class MyGridViewHolder {
        TextView table;
    }
}
