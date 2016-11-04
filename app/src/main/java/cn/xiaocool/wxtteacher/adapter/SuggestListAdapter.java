package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.SuggestModel;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;

/**
 * Created by Administrator on 2016/10/27.
 */
public class SuggestListAdapter extends BaseAdapter {

    private List<SuggestModel> classListDataList;
    private LayoutInflater inflater;

    public SuggestListAdapter(List<SuggestModel> classListDataList, Context context) {
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
            convertView = inflater.inflate(R.layout.item_suggest,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SuggestModel suggestModel = classListDataList.get(position);

        holder.tv_comment.setText(suggestModel.getMessage());
        holder.tv_comment_time.setText(TimeToolUtils.fromateTimeShowByRule(Long.parseLong(suggestModel.getCreate_time()) * 1000));
        holder.tv_feedback.setText(suggestModel.getFeed_back().equals("")?"客服暂未回复！":suggestModel.getFeed_back());
        holder.tv_feedback_time.setText(suggestModel.getFeed_back().equals("")?"":TimeToolUtils.fromateTimeShowByRule(Long.parseLong(suggestModel.getFeed_time())*1000));

        return convertView;
    }
    class ViewHolder{
        TextView tv_comment,tv_comment_time,tv_feedback,tv_feedback_time;

        public ViewHolder(View convertView) {
            tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            tv_comment_time = (TextView) convertView.findViewById(R.id.tv_comment_time);
            tv_feedback = (TextView) convertView.findViewById(R.id.tv_feedback);
            tv_feedback_time = (TextView) convertView.findViewById(R.id.tv_feedback_time);
        }
    }
}
