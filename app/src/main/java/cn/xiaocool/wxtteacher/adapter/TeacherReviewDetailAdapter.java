package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.ClassList;
import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;
import cn.xiaocool.wxtteacher.ui.RoundImageView;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;

/**
 * Created by Administrator on 2016/5/28.
 */
public class TeacherReviewDetailAdapter extends BaseAdapter {



    private UserInfo user = new UserInfo();
    private Context context;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImage;
    ArrayList<ClassList.ClassStudentData.TeacherComment> list;
    public TeacherReviewDetailAdapter(Context context,ArrayList<ClassList.ClassStudentData.TeacherComment> list){
        this.context = context;
        user.readData(context);
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
        final ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_teacher_review_detail,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item_title.setText(list.get(position).getName());
        holder.item_time.setText(TimeToolUtils.fromateTimeShowByRule(Long.parseLong(list.get(position).getComment_time())*1000));

        if (list.get(position).getLearn().equals("1")){
            holder.xuexi_1.setSelected(true);
        }else {
            holder.xuexi_1.setSelected(false);
        }
        if (list.get(position).getLearn().equals("2")){
            holder.xuexi_2.setSelected(true);
        }else {
            holder.xuexi_2.setSelected(false);
        }
        if (list.get(position).getLearn().equals("3")){
            holder.xuexi_3.setSelected(true);
        }else {
            holder.xuexi_3.setSelected(false);
        }



        if (list.get(position).getWork().equals("1")){
            holder.dongshou_1.setSelected(true);
        }else {
            holder.dongshou_1.setSelected(false);
        }
        if (list.get(position).getWork().equals("2")){
            holder.dongshou_2.setSelected(true);
        }else {
            holder.dongshou_2.setSelected(false);
        }
        if (list.get(position).getWork().equals("3")){
            holder.dongshou_3.setSelected(true);
        }else {
            holder.dongshou_3.setSelected(false);
        }


        if (list.get(position).getSing().equals("1")){
            holder.sing_1.setSelected(true);
        }else {
            holder.sing_1.setSelected(false);
        }
        if (list.get(position).getSing().equals("2")){
            holder.sing_2.setSelected(true);
        }else {
            holder.sing_2.setSelected(false);
        }
        if (list.get(position).getSing().equals("3")){
            holder.sing_3.setSelected(true);
        }else {
            holder.sing_3.setSelected(false);
        }


        if (list.get(position).getLabour().equals("1")){
            holder.laodong_1.setSelected(true);
        }else {
            holder.laodong_1.setSelected(false);
        }
        if (list.get(position).getLabour().equals("2")){
            holder.laodong_2.setSelected(true);
        }else {
            holder.laodong_2.setSelected(false);
        }
        if (list.get(position).getLabour().equals("3")){
            holder.laodong_3.setSelected(true);
        }else {
            holder.laodong_3.setSelected(false);
        }


        if (list.get(position).getStrain().equals("1")){
            holder.yingbian_1.setSelected(true);
        }else {
            holder.yingbian_1.setSelected(false);
        }
        if (list.get(position).getStrain().equals("2")){
            holder.yingbian_2.setSelected(true);
        }else {
            holder.yingbian_2.setSelected(false);
        }
        if (list.get(position).getStrain().equals("3")){
            holder.yingbian_3.setSelected(true);
        }else {
            holder.yingbian_3.setSelected(false);
        }



        holder.item_content.setText(list.get(position).getComment_content());

        ImageLoader.getInstance().displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST+list.get(position).getPhoto(),holder.item_head,displayImage);

        return convertView;
    }


    class ViewHolder{
        TextView item_title,item_time,item_content;
        RoundImageView item_head;
        LinearLayout xuexi_1,xuexi_2,xuexi_3,dongshou_1,dongshou_2,dongshou_3,sing_1,sing_2,sing_3,
                laodong_1,laodong_2,laodong_3,yingbian_1,yingbian_2,yingbian_3;

        public ViewHolder(View convertView) {
            item_head = (RoundImageView) convertView.findViewById(R.id.item_head);
            item_title = (TextView) convertView.findViewById(R.id.item_title);
            item_time = (TextView) convertView.findViewById(R.id.item_time);
            item_content = (TextView) convertView.findViewById(R.id.item_content);


            xuexi_1 = (LinearLayout) convertView.findViewById(R.id.xuexi_1);
            xuexi_2 = (LinearLayout) convertView.findViewById(R.id.xuexi_2);
            xuexi_3 = (LinearLayout) convertView.findViewById(R.id.xuexi_3);


            dongshou_1 = (LinearLayout) convertView.findViewById(R.id.dongshou_1);
            dongshou_2 = (LinearLayout) convertView.findViewById(R.id.dongshou_2);
            dongshou_3 = (LinearLayout) convertView.findViewById(R.id.dongshou_3);


            sing_1 = (LinearLayout) convertView.findViewById(R.id.sing_1);
            sing_2 = (LinearLayout) convertView.findViewById(R.id.sing_2);
            sing_3 = (LinearLayout) convertView.findViewById(R.id.sing_3);


            laodong_1 = (LinearLayout) convertView.findViewById(R.id.laodong_1);
            laodong_2 = (LinearLayout) convertView.findViewById(R.id.laodong_2);
            laodong_3 = (LinearLayout) convertView.findViewById(R.id.laodong_3);


            yingbian_1 = (LinearLayout) convertView.findViewById(R.id.yingbian_1);
            yingbian_2 = (LinearLayout) convertView.findViewById(R.id.yingbian_2);
            yingbian_3 = (LinearLayout) convertView.findViewById(R.id.yingbian_3);


        }
    }
}
