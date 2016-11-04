package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassList;

/**
 * Created by THB on 2016/6/17.
 */
public class SelectClassAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ClassList.ClassListData> list;
    public SelectClassAdapter(Context context,ArrayList<ClassList.ClassListData> llist) {
        this.list = llist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_class_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_className.setText(list.get(position).getClassname());
        return convertView;
    }

    class ViewHolder {
        TextView tv_className;

        public ViewHolder(View convertView) {
            tv_className = (TextView) convertView.findViewById(R.id.tv_class_name);
        }
    }
}
