package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.NoticeRecive;

import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

/**
 * Created by Administrator on 2016/7/7.
 */
public class NoticeReciveReadOrNotReadAdapter extends BaseAdapter {

    private ArrayList<NoticeRecive.DataBean.ReceivListBean> classListDataList;
    private LayoutInflater inflater;
    private Context mContext;
    private String type;
    private DisplayImageOptions displayImageOptions;
    public NoticeReciveReadOrNotReadAdapter(ArrayList<NoticeRecive.DataBean.ReceivListBean> classListDataList, Context context, String type) {
        this.classListDataList = classListDataList;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.type = type;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square).showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();

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
            convertView = inflater.inflate(R.layout.teachers_addresses,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NoticeRecive.DataBean.ReceivListBean data = classListDataList.get(position);

        if (type.equals("1")){
            holder.sms.setVisibility(View.VISIBLE);
            holder.call.setVisibility(View.VISIBLE);

        }else {
            holder.sms.setVisibility(View.GONE);
            holder.call.setVisibility(View.GONE);

        }

        holder.name.setText(data.getName());
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, TestChatActivity.class);
//                intent.putExtra("teacherID", data.getReceiverid());
//                intent.putExtra("teacherName", data.getReceiverid());
//                mContext.startActivity(intent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + data.getPhone()));
                mContext.startActivity(intent);
            }
        });

        ImageLoader.getInstance().displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+data.getPhoto(),holder.teacher_avatar,displayImageOptions);
        return convertView;
    }
    class ViewHolder{
        TextView name;
        ImageView sms,call;
        RoundImageView teacher_avatar;
        public ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.text_teacherName);
            sms = (ImageView) convertView.findViewById(R.id.sms);
            call = (ImageView) convertView.findViewById(R.id.call);
            teacher_avatar = (RoundImageView) convertView.findViewById(R.id.teacher_avatar);
        }
    }
}
