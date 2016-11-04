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
import cn.xiaocool.wxtteacher.bean.Attendance;

/**
 * Created by Administrator on 2016/7/15.
 */
public class TeacherAttendanceAdapter extends BaseAdapter {

    private List<Attendance> classListDataList;
    private LayoutInflater inflater;

    public TeacherAttendanceAdapter(List<Attendance> classListDataList, Context context) {
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
            convertView = inflater.inflate(R.layout.item_teacher_attend,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        if (classListDataList.get(position).getArrivetime()!="null"){
            date.setTime(Long.parseLong(classListDataList.get(position).getArrivetime())*1000);
            holder.arrvie_time_text.setText("签到: " + format.format(date));
        }else {
            holder.arrvie_time_text.setText("签到: 无" );
        }

        if (classListDataList.get(position).getLeavetime()!="null"){
            date.setTime(Long.parseLong(classListDataList.get(position).getLeavetime())*1000);
            holder.leave_time_text.setText("签退: "+format.format(date));
        }else {
            holder.leave_time_text.setText("签退: 无");
        }

        if (classListDataList.get(position).getCreate_time()!="null"){
            date.setTime(Long.parseLong(classListDataList.get(position).getCreate_time())*1000);
            SimpleDateFormat formatd = new SimpleDateFormat("yyyy-MM-dd");
            holder.date_text.setText(formatd.format(date));
        }else {
            holder.date_text.setText("无");
        }

        return convertView;
    }
    class ViewHolder{
        TextView arrvie_time_text,leave_time_text,date_text;
        public ViewHolder(View convertView) {
            date_text = (TextView) convertView.findViewById(R.id.date_text);
            leave_time_text = (TextView) convertView.findViewById(R.id.leave_time_text);
            arrvie_time_text = (TextView) convertView.findViewById(R.id.arrvie_time_text);
        }
    }
}
