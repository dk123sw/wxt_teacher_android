package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;

/**
 * Created by Administrator on 2016/7/16.
 */
public class TextAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> list;
    public TextAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.text_layout,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.textView.setText(list.get(position));

        return convertView;
    }

    public class ViewHolder {

        TextView textView;

        public ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.text);

        }
    }
}
