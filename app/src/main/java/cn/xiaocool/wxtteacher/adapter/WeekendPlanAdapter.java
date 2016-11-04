package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.WeekendPlan;

/**
 * Created by 潘 on 2016/4/10.
 */
public class WeekendPlanAdapter extends BaseAdapter {
    private Context mContext;
    private List<WeekendPlan> list;
    private LayoutInflater inflater;

    public WeekendPlanAdapter(List<WeekendPlan> list, Context context) {
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_weekendplan, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Date date = new Date();
        SimpleDateFormat formatd = new SimpleDateFormat("MM-dd");
        date.setTime(Long.parseLong(list.get(position).getBegintime()) * 1000);
        String begintime = formatd.format(date);

        date.setTime(Long.parseLong(list.get(position).getEndtime()) * 1000);
        String endtime = formatd.format(date);
        holder.weekplan_time.setText(begintime+" - "+endtime);
        holder.content.setText(list.get(position).getTitle());



        return convertView;
    }

    public class ViewHolder {
        TextView content, weekplan_time;

        public ViewHolder(View convertView) {
            content = (TextView) convertView.findViewById(R.id.weekendplan_title);
            weekplan_time = (TextView) convertView.findViewById(R.id.weekplan_time);

        }
    }
}
