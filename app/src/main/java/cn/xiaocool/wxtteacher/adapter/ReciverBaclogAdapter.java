package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Backlog;

/**
 * Created by Administrator on 2016/7/14.
 */
public class ReciverBaclogAdapter extends BaseAdapter {

    private ImageLoader imageLoader= ImageLoader.getInstance();
    private Context mContext;
    private ArrayList<Backlog.BacklogData.Reciverlist> list;
    private LayoutInflater inflater;
    private DisplayImageOptions displayImage;
    public ReciverBaclogAdapter(ArrayList<Backlog.BacklogData.Reciverlist> list, Context context) {
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
            convertView = inflater.inflate(R.layout.item_reciver_backlog,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.reciver_name_text.setText(list.get(position).getName());

        Date date = new Date();
        date.setTime(Long.parseLong(list.get(position).getCreate_time()) * 1000);
        holder.reciver_time_text.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));
        holder.reciver_feedback_text.setText(list.get(position).getFeedback());


        return convertView;
    }

    public class ViewHolder{
        TextView reciver_name_text,reciver_time_text,reciver_feedback_text;
        public ViewHolder(View convertView){

            reciver_name_text = (TextView) convertView.findViewById(R.id.reciver_name_text);
            reciver_time_text = (TextView) convertView.findViewById(R.id.reciver_time_text);
            reciver_feedback_text = (TextView) convertView.findViewById(R.id.reciver_feedback_text);
        }
    }
}
