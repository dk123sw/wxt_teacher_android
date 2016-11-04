package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.StudentData;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

/**
 * Created by THB on 2016/6/16.
 */
public class StuAttendanceInfoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<StudentData> studentDatas;
    private int days;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions displayImage;
    public StuAttendanceInfoAdapter(ArrayList<StudentData> studentDatas,Context context,int days) {
        this.context = context;
        this.days = days;
        this.studentDatas = studentDatas;
        inflater = LayoutInflater.from(context);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square)
                .showImageOnFail(R.drawable.default_square)
                .cacheInMemory(true).cacheOnDisc(true).build();

    }

    @Override
    public int getCount() {
        return studentDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return studentDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_stu_attendance,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(studentDatas.get(position).getName());

        holder.tv_content.setText("签到:"+(days-Integer.valueOf(studentDatas.get(position).getArrive_count()))+"天"+"  缺勤:"+studentDatas.get(position).getArrive_count()+"天");

        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+studentDatas.get(position).getPhoto(),holder.item_head,displayImage);
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_content;
        RoundImageView item_head;
        public ViewHolder(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.tv_stu_name);
            tv_content = (TextView) convertView.findViewById(R.id.tv_stu_content);
            item_head = (RoundImageView) convertView.findViewById(R.id.item_head);
        }
    }
}
