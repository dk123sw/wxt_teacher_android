package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.CommunicateModel;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.ImgLoadUtil;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CommunicationAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommunicateModel> communicateModelList;
    private String userid;
    public CommunicationAdapter(Context context,List<CommunicateModel> communicateModels,String userid){
        this.mContext = context;
        this.communicateModelList = communicateModels;
        this.userid = userid;
    }

    @Override
    public int getCount() {
        return communicateModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return communicateModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommunicateModel communicateModel = communicateModelList.get(position);
        //判断是否自己发送的
        if (communicateModel.getSend_uid().equals(userid)){
            holder.left_layout.setVisibility(View.GONE);
            holder.right_layout.setVisibility(View.VISIBLE);
            ImgLoadUtil.display(NetBaseConstant.NET_CIRCLEPIC_HOST + communicateModel.getSend_face(), holder.chat_avatar_right);
            holder.chat_text_right.setText(communicateModel.getContent());
        }else {
            holder.left_layout.setVisibility(View.VISIBLE);
            holder.right_layout.setVisibility(View.GONE);
            ImgLoadUtil.display(NetBaseConstant.NET_CIRCLEPIC_HOST + communicateModel.getReceive_face(), holder.chat_avatar_left);
            holder.send_name.setText(communicateModel.getReceive_nickname());
            holder.chat_text_left.setText(communicateModel.getContent());
        }
        holder.item_time_text.setText(TimeToolUtils.fromateTimeShowByRule(Long.parseLong(communicateModel.getCreate_time())*1000));
        if (position>0){
            long time = Integer.valueOf(communicateModel.getCreate_time()) - Integer.valueOf(communicateModelList.get(position-1).getCreate_time());
            if (time/60/5>1){
                holder.item_time_text.setVisibility(View.VISIBLE);
            }else {
                holder.item_time_text.setVisibility(View.GONE);
            }
        }



        return convertView;
    }

    class ViewHolder{

        RelativeLayout left_layout,right_layout;
        RoundImageView chat_avatar_left,chat_avatar_right;
        TextView item_time_text,send_name,chat_text_left,chat_text_right;
        public ViewHolder(View convertView) {
            left_layout = (RelativeLayout) convertView.findViewById(R.id.left_layout);
            right_layout = (RelativeLayout) convertView.findViewById(R.id.right_layout);
            chat_avatar_left = (RoundImageView) convertView.findViewById(R.id.chat_avatar_left);
            chat_avatar_right = (RoundImageView) convertView.findViewById(R.id.chat_avatar_right);
            send_name = (TextView) convertView.findViewById(R.id.send_name);
            chat_text_left = (TextView) convertView.findViewById(R.id.chat_text_left);
            chat_text_right = (TextView) convertView.findViewById(R.id.chat_text_right);
            item_time_text =  (TextView)convertView.findViewById(R.id.item_time_text);

        }
    }
}
