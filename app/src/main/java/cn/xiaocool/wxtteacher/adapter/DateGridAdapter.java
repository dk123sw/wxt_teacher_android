package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.DayModel;

/**
 * Created by Administrator on 2016/6/16.
 */
public class DateGridAdapter extends BaseAdapter{

    private int i;
    private LayoutInflater mLayoutInflater;
    private ArrayList<DayModel> mWorkImgs;
    private Context mContext;



    public DateGridAdapter(ArrayList<DayModel> workImgs, Context context) {
        this.mContext = context;
        this.mWorkImgs = workImgs;

        mLayoutInflater = LayoutInflater.from(mContext);
        this.i = mWorkImgs.size();

    }

    @Override
    public int getCount() {
        return mWorkImgs.size();
    }

    @Override
    public Object getItem(int position) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.date_item_text, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.date_text);
            viewHolder.date_type = (TextView) convertView.findViewById(R.id.date_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }

        //判断颜色(0 白色 1粉色 2 红色 3 绿色)
        if (mWorkImgs.get(position).getType().equals("0")){
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.date_type.setText("");
        }else if (mWorkImgs.get(position).getType().equals("1")){
            convertView.setBackgroundColor(Color.parseColor("#ec7166"));
            viewHolder.date_type.setText("缺勤");
        }else if (mWorkImgs.get(position).getType().equals("2")){
            convertView.setBackgroundColor(Color.parseColor("#e84e40"));
            viewHolder.date_type.setText("");
        }else if (mWorkImgs.get(position).getType().equals("3")){
            convertView.setBackgroundColor(Color.parseColor("#9ccc65"));
            viewHolder.date_type.setText("");
        }else if (mWorkImgs.get(position).getType().equals("4")){
            convertView.setBackgroundColor(Color.parseColor("#2db3f8"));
            viewHolder.date_type.setText("");
        }

        viewHolder.textView.setText(mWorkImgs.get(position).getDate());
        return convertView;
    }

    private static class MyGridViewHolder {
        TextView textView,date_type;
    }
}
