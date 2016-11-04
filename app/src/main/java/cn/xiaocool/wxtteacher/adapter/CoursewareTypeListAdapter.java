package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassCourseWare;

/**
 * Created by Administrator on 2016/6/9.
 */
public class CoursewareTypeListAdapter extends BaseAdapter{

    private List<ClassCourseWare> classCourseWares;
    private LayoutInflater inflater;
    private Context context;
    public CoursewareTypeListAdapter( Context mContext,List<ClassCourseWare> classCourseWares) {
        this.context = mContext;
        this.classCourseWares = classCourseWares;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return classCourseWares.size();
    }

    @Override
    public Object getItem(int position) {
        return classCourseWares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.courseware_type,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.courseware_name.setText(classCourseWares.get(position).getSubject());

        holder.courseware_num.setText(classCourseWares.get(position).getCourseware_info().size()+"条");

        return convertView;
    }
    class ViewHolder{
        TextView courseware_name,courseware_num;

        public ViewHolder(View convertView) {
            courseware_name = (TextView) convertView.findViewById(R.id.courseware_name);
            courseware_num = (TextView) convertView.findViewById(R.id.courseware_num);


        }
    }

}
