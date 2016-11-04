package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
import cn.xiaocool.wxtteacher.bean.ClassAttendance;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

/**
 * Created by Administrator on 2016/5/23.
 */
public class StudentGridListAdapter extends BaseAdapter{
    private ImageLoader imageLoader= ImageLoader.getInstance();
    private DisplayImageOptions displayImage;

    private ArrayList<ClassAttendance> classListDataList;
    private LayoutInflater inflater;
    private Context context;
    private String what;
    public StudentGridListAdapter(ArrayList<ClassAttendance> classListDataList, Context mContext,String what) {
        this.context = mContext;
        this.classListDataList = classListDataList;
        this.inflater = LayoutInflater.from(context);
        this.what = what;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.default_square)
                .showImageOnFail(R.drawable.default_square)
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
            convertView = inflater.inflate(R.layout.item_class_student,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.student_name.setText(classListDataList.get(position).getName());

        imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST + classListDataList.get(position).getPhoto(), holder.student_avatar, displayImage);


        Log.e("classListDataList",classListDataList.get(position).getCheckedType());

       if (what.equals("1")){

           if (classListDataList.get(position).getCheckedType().equals("0")){
               holder.type_show.setVisibility(View.GONE);
           }else if (classListDataList.get(position).getCheckedType().equals("1")){
               holder.type_show.setVisibility(View.VISIBLE);
               holder.type_show.setImageResource(R.drawable.ic_wei);
           }else if (classListDataList.get(position).getCheckedType().equals("2")){
               holder.type_show.setVisibility(View.VISIBLE);
               holder.type_show.setImageResource(R.drawable.ic_qian);
           }else {
               holder.type_show.setVisibility(View.VISIBLE);
               holder.type_show.setImageResource(R.drawable.ic_jia);
           }

       }else {

           if (classListDataList.get(position).getCheckedTypeByGo().equals("0")){
               holder.type_show.setVisibility(View.GONE);
           }else if (classListDataList.get(position).getCheckedTypeByGo().equals("1")){
               holder.type_show.setVisibility(View.VISIBLE);
               holder.type_show.setImageResource(R.drawable.ic_wei);
           }else if (classListDataList.get(position).getCheckedTypeByGo().equals("2")){
               holder.type_show.setVisibility(View.VISIBLE);
               holder.type_show.setImageResource(R.drawable.ic_qian);
           }else {
               holder.type_show.setVisibility(View.VISIBLE);
               holder.type_show.setImageResource(R.drawable.ic_jia);
           }
       }


        return convertView;
    }
    class ViewHolder{
        TextView student_name;
        ImageView type_show;
        RoundImageView student_avatar;
        public ViewHolder(View convertView) {
            student_name = (TextView) convertView.findViewById(R.id.student_name);
            type_show = (ImageView) convertView.findViewById(R.id.type_show);
            student_avatar = (RoundImageView) convertView.findViewById(R.id.student_avatar);
        }
    }
}
