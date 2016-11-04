package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassChildren;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ClassChildrenListAdapter extends BaseAdapter {
    private List<ClassChildren.ClassChildrenData> childrenDataList;
    private LayoutInflater inflater;
    private ClassChildren.ClassChildrenData classChildrenData;
    public ClassChildrenListAdapter(List<ClassChildren.ClassChildrenData> childrenDataList, Context context) {
        this.childrenDataList = childrenDataList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return childrenDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return childrenDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_children_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.childName.setText(childrenDataList.get(position).getName());
        classChildrenData = childrenDataList.get(position);

        holder.news_group_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView childName;
        CheckBox news_group_checkBox;

        public ViewHolder(View convertView) {
            childName = (TextView) convertView.findViewById(R.id.class_childName);
            news_group_checkBox = (CheckBox) convertView.findViewById(R.id.news_group_checkBox);
        }
    }
}
