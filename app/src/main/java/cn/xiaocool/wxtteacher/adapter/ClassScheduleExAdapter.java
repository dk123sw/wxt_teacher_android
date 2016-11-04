package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ClassScheduleExAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> arrayLists ;
    private LayoutInflater inflater;
    private Context mContext;

    public ClassScheduleExAdapter(ArrayList<ArrayList<String>> classAddresses, Context mContext) {
        this.arrayLists = classAddresses;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return arrayLists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayLists.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayLists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayLists.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder viewHolder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.space_class_mon, parent, false);
            viewHolder = new GroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        isExpanded = true;
        viewHolder.item_text.setText("周"+(groupPosition+1));

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder viewHolder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.class_schedule_item, parent, false);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        if (arrayLists.get(groupPosition).get(childPosition).equals("null")){
            viewHolder.item_text.setText("第"+(childPosition+1)+"节课:    "+ "");
        }else {
            viewHolder.item_text.setText("第"+(childPosition+1)+"节课:    "+ arrayLists.get(groupPosition).get(childPosition));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class GroupViewHolder {
        TextView item_text;
        public GroupViewHolder (View convertView){
            item_text = (TextView) convertView.findViewById(R.id.item_text);
        }
    }

    private class ChildViewHolder {
        TextView item_text;
        public ChildViewHolder(View convertView) {
            item_text = (TextView) convertView.findViewById(R.id.item_text);
        }
    }
}
