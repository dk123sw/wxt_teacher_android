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
 * Created by Administrator on 2016/7/20.
 */
public class SchoolIntrduceAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> list;
    private LayoutInflater inflater;

    public SchoolIntrduceAdapter(ArrayList<String> list, Context context) {
        this.mContext = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);

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
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_sch_introduce,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_title.setText(list.get(position));

        return convertView;
    }

    class ViewHolder{

        TextView item_title, number_text;

        public ViewHolder(View convertView) {

            item_title = (TextView) convertView.findViewById(R.id.item_title);
            number_text = (TextView) convertView.findViewById(R.id.number_text);

        }
    }
}
