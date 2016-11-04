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
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

/**
 * Created by Administrator on 2016/5/28.
 */
public class TeacherReviewAdat extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<ClassList.ClassStudentData> list;
    private DisplayImageOptions displayImage;
    public TeacherReviewAdat(Context context,ArrayList<ClassList.ClassStudentData> list){
        this.context = context;
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;
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
            convertView = layoutInflater.inflate(R.layout.item_teacher_review_tclist,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ImageLoader.getInstance().displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+list.get(position).getPhoto(),holder.teacher_avatar,displayImage);

        holder.teacher_name.setText(list.get(position).getName());
        holder.comments_size.setText("本月已经点评"+list.get(position).getTeacherComments().size()+"条");
        return convertView;
    }

    public class ViewHolder {

        TextView teacher_name,comments_size;
        RoundImageView teacher_avatar;
        public ViewHolder(View convertView) {
            teacher_name = (TextView) convertView.findViewById(R.id.teacher_name);
            comments_size = (TextView) convertView.findViewById(R.id.comments_size);
            teacher_avatar = (RoundImageView) convertView.findViewById(R.id.teacher_avatar);
        }
    }
}
