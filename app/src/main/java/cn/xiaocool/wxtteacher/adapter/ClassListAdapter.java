package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassList;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ClassListAdapter extends BaseAdapter {
    private List<ClassList.ClassListData> classListDataList;
    private LayoutInflater inflater;

    public ClassListAdapter(List<ClassList.ClassListData> classListDataList, Context context) {
        this.classListDataList = classListDataList;
        this.inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.item_class_list,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.class_name.setText(classListDataList.get(position).getClassname());
        return convertView;
    }
    class ViewHolder{
        TextView class_name;
        CheckBox check_class;
        public ViewHolder(View convertView) {
            class_name = (TextView) convertView.findViewById(R.id.class_name);
        }
    }
}
