package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassEventCount;

/**
 * Created by THB on 2016/6/16.
 */
public class ClassEventCountAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ClassEventCount> studentDatas;
    private ArrayList<ClassEventCount.TeacherInfoBean> teacherInfoBeans;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions displayImage;
    public ClassEventCountAdapter(ArrayList<ClassEventCount> studentDatas, ArrayList<ClassEventCount.TeacherInfoBean> teacherInfoBeans,Context context) {
        this.context = context;
        this.studentDatas = studentDatas;
        this.teacherInfoBeans = teacherInfoBeans;

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
        return studentDatas!=null?studentDatas.size():teacherInfoBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return studentDatas!=null?studentDatas.get(position):teacherInfoBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_classevent,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.tv_name.setText(studentDatas!=null?studentDatas.get(position).getClassname():teacherInfoBeans.get(position).getName());

        holder.tv_content.setText(studentDatas!=null?studentDatas.get(position).getClass_count():teacherInfoBeans.get(position).getTeacher_count() + "条");

        if (studentDatas==null)holder.right_image.setVisibility(View.GONE);
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_content;
        ImageView right_image;
        public ViewHolder(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.tv_stu_name);
            tv_content = (TextView) convertView.findViewById(R.id.tv_stu_content);
            right_image = (ImageView) convertView.findViewById(R.id.right_image);
        }
    }
}
