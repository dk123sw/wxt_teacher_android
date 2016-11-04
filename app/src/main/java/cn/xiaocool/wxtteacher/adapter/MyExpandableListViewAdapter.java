package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.xiaocool.wxtteacher.R;

import cn.xiaocool.wxtteacher.main.TeacherCommunicationActivity;
import cn.xiaocool.wxtteacher.ui.RoundImageView;


/**
 * Created by Administrator on 2016/4/6.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<Map<String, String>> classAddresses = new ArrayList<>();
    private List<List<Map<String, String>>> teachersAddresses = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions displayImageOptions;
    public MyExpandableListViewAdapter(List<Map<String, String>> classAddresses, List<List<Map<String, String>>> teachersAddresses, Context mContext) {
        this.classAddresses = classAddresses;
        this.teachersAddresses = teachersAddresses;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getGroupCount() {
        return classAddresses.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return teachersAddresses.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classAddresses.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return teachersAddresses.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView title;
        ImageView jiantou;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.class_addresses, parent, false);
        }


        jiantou = (ImageView) convertView.findViewById(R.id.jiantou);
        title = (TextView) convertView.findViewById(R.id.textGroup);

        if (isExpanded){
            Log.e("55550","展开");
            jiantou.setImageResource(R.drawable.address_triangle_down);

        }else {
            Log.e("55550","闭合");
            jiantou.setImageResource(R.drawable.address_triangle);
        }
        title.setText(classAddresses.get(groupPosition).get("group"));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView name;
        final ImageView sms,call;
        RoundImageView teacher_avatar;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.teachers_addresses,parent,false);
        }
        name = (TextView) convertView.findViewById(R.id.text_teacherName);
        sms = (ImageView) convertView.findViewById(R.id.sms);
        call = (ImageView) convertView.findViewById(R.id.call);
        teacher_avatar = (RoundImageView) convertView.findViewById(R.id.teacher_avatar);
        imageLoader.displayImage(teachersAddresses.get(groupPosition).get(childPosition).get("teacherAvatar"), teacher_avatar, displayImageOptions);
        final String teacherPhone = teachersAddresses.get(groupPosition).get(childPosition).get("teacherPhone");
        Log.i("Info_teacherPhone", teacherPhone);
        if (teachersAddresses.get(groupPosition).get(childPosition).get("studentName")!=null){
            name.setText(teachersAddresses.get(groupPosition).get(childPosition).get("teacherName")+"("+teachersAddresses.get(groupPosition).get(childPosition).get("studentName")+"的家长)");
        }else {
            name.setText(teachersAddresses.get(groupPosition).get(childPosition).get("teacherName"));
        }
        final String teacherID = teachersAddresses.get(groupPosition).get(childPosition).get("teacherID");
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TeacherCommunicationActivity.class);
                intent.putExtra("reciver_id", teacherID);
                intent.putExtra("chat_name",teachersAddresses.get(groupPosition).get(childPosition).get("teacherName"));
                mContext.startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + teacherPhone));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
