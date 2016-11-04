package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassCourseWare;

import cn.xiaocool.wxtteacher.main.CircleImagesActivity;
import cn.xiaocool.wxtteacher.ui.NoScrollGridView;

/**
 * Created by Administrator on 2016/6/9.
 */
public class CoursewareAdapter extends BaseAdapter{

    private List<ClassCourseWare.CoursewareInfoBean> coursewareInfoBeanList;
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImage;
    public CoursewareAdapter( Context mContext,List<ClassCourseWare.CoursewareInfoBean> coursewareInfoBeanList) {
        this.context = mContext;
        this.coursewareInfoBeanList = coursewareInfoBeanList;
        this.inflater = LayoutInflater.from(mContext);
        displayImage = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return coursewareInfoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return coursewareInfoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_courseware,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.homework_title.setText(coursewareInfoBeanList.get(position).getCourseware_title());
        holder.homework_content.setText(coursewareInfoBeanList.get(position).getCourseware_content());
        Date date = new Date();
        date.setTime(Long.parseLong(coursewareInfoBeanList.get(position).getCourseware_time())*1000);
        holder.homework_time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(date));

        holder.teacher_name.setText(coursewareInfoBeanList.get(position).getTeacher_name());
        if (coursewareInfoBeanList.get(position).getPic().size()>1){
            holder.homework_img.setVisibility(View.GONE);
            holder.img_gridview.setVisibility(View.VISIBLE);
            final ArrayList<String> pics = new ArrayList<>();
            for (int i=0;i< coursewareInfoBeanList.get(position).getPic().size();i++){
                pics.add(coursewareInfoBeanList.get(position).getPic().get(i).getPicture_url());
            }
            ImgGridAdapter parWarnImgGridAdapter = new ImgGridAdapter(pics,context);
            holder.img_gridview.setAdapter(parWarnImgGridAdapter);
            holder.img_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", pics);
                    intent.putExtra("type", "newsgroup");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });


        }else if (coursewareInfoBeanList.get(position).getPic().size()==1&&!coursewareInfoBeanList.get(position).getPic().get(0).getPicture_url().equals("null")&&!coursewareInfoBeanList.get(position).getPic().get(0).getPicture_url().equals("")){

            holder.homework_img.setVisibility(View.VISIBLE);
            holder.img_gridview.setVisibility(View.GONE);
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
            ImageLoader.getInstance().displayImage("http://wxt.xiaocool.net/uploads/microblog/" + coursewareInfoBeanList.get(position).getPic().get(0).getPicture_url(), holder.homework_img, displayImage);
            Log.d("img", "http://wxt.xiaocool.net/uploads/microblog/" + coursewareInfoBeanList.get(position).getPic().get(0).getPicture_url());
            holder.homework_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.homework_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> imgs = new ArrayList<>();
                    imgs.add( coursewareInfoBeanList.get(position).getPic().get(0).getPicture_url().toString());
                    Intent intent = new Intent(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", imgs);
                    context.startActivity(intent);
                }
            });
        }else {
            holder.homework_img.setVisibility(View.GONE);
            holder.img_gridview.setVisibility(View.GONE);
        }

        return convertView;
    }
    class ViewHolder{
        TextView homework_title,homework_content,teacher_name,homework_time,alread_text,not_read_text;
        ImageView homework_img;
        NoScrollGridView img_gridview;
        public ViewHolder(View convertView) {

            img_gridview = (NoScrollGridView) convertView.findViewById(R.id.img_gridview);
            homework_title = (TextView) convertView.findViewById(R.id.myhomework_title);
            homework_content = (TextView) convertView.findViewById(R.id.myhomework_content);
            teacher_name = (TextView) convertView.findViewById(R.id.myhomework_teacher_name);
            homework_time = (TextView) convertView.findViewById(R.id.myhomework_time);
            not_read_text = (TextView) convertView.findViewById(R.id.not_read_text);
            alread_text = (TextView) convertView.findViewById(R.id.alread_text);
            homework_img = (ImageView) convertView.findViewById(R.id.homework_img);


        }
    }
}
