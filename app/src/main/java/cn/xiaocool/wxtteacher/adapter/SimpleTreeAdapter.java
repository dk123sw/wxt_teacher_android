package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.FileBean;
import cn.xiaocool.wxtteacher.bean.bean.Node;
import cn.xiaocool.wxtteacher.bean.bean.TreeListViewAdapter;

import cn.xiaocool.wxtteacher.main.TeacherCommunicationActivity;
import cn.xiaocool.wxtteacher.ui.RoundImageView;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T>
{
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions displayImageOptions;
	private List<FileBean> fileBeanList;
	public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,List<FileBean> fileBeanList,
							 int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		super(mTree, context, datas, defaultExpandLevel);
		this.fileBeanList = fileBeanList;
		displayImageOptions = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}

	@Override
	public View getConvertView(Node node , int position, View convertView, ViewGroup parent)
	{
	 	FileBean model = null;
		for (int i = 0;i<fileBeanList.size();i++){
			FileBean fileBean = fileBeanList.get(i);
			if (node.getId() == fileBean.get_id()){
				model = fileBeanList.get(i);
				break;
			}
		}
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
				.findViewById(R.id.id_treenode_icon);
			viewHolder.sms = (ImageView) convertView
					.findViewById(R.id.sms);
			viewHolder.call = (ImageView) convertView
					.findViewById(R.id.call);
			viewHolder.avatar = (RoundImageView) convertView
					.findViewById(R.id.id_treenode_avatar);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.id_treenode_label);
			viewHolder.tree_divider = (LinearLayout) convertView
					.findViewById(R.id.tree_divider);
			viewHolder.tree_divider1 = (LinearLayout) convertView
					.findViewById(R.id.tree_divider1);
			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -1)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
		} else
		{
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
		}

		if (node.getpId()==0){
			viewHolder.avatar.setVisibility(View.GONE);
			viewHolder.call.setVisibility(View.GONE);
			viewHolder.sms.setVisibility(View.GONE);
			viewHolder.tree_divider.setVisibility(View.GONE);
			viewHolder.tree_divider1.setVisibility(View.GONE);
		}else {
			viewHolder.avatar.setVisibility(View.VISIBLE);
		}
		if (node.getLevel()==2){
			viewHolder.tree_divider.setVisibility(View.VISIBLE);
			viewHolder.tree_divider1.setVisibility(View.VISIBLE);
			viewHolder.avatar.setVisibility(View.GONE);
			viewHolder.call.setVisibility(View.VISIBLE);
			viewHolder.sms.setVisibility(View.VISIBLE);
		}else if(node.getLevel()==1){
			viewHolder.tree_divider.setVisibility(View.GONE);
			viewHolder.tree_divider1.setVisibility(View.GONE);
			viewHolder.avatar.setVisibility(View.VISIBLE);
			viewHolder.call.setVisibility(View.GONE);
			viewHolder.sms.setVisibility(View.GONE);
		}
		if (node.getLevel()==1){
			viewHolder.tree_divider1.setVisibility(View.VISIBLE);
		}

		viewHolder.label.setText(node.getName());
		imageLoader.displayImage("http://wxt.xiaocool.net/uploads/microblog/" + model.getAvatar(), viewHolder.avatar, displayImageOptions);



		//聊天
		final FileBean finalModel = model;
		viewHolder.sms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,TeacherCommunicationActivity.class);
				intent.putExtra("reciver_id", String.valueOf(finalModel.get_id()));
				intent.putExtra("chat_name",finalModel.getName());
				intent.putExtra("usertype","0");
				mContext.startActivity(intent);
			}
		});

		//打电话
		viewHolder.call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + finalModel.getTelephone()));
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	private final class ViewHolder {
		ImageView icon,avatar,sms,call;
		TextView label;
		LinearLayout tree_divider,tree_divider1;
	}

}
