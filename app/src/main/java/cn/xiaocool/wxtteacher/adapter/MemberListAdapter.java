package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;

/**
 * Created by THB on 2016/6/13.
 */
public class MemberListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    public MemberListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_member_info,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        public ViewHolder(View convertView) {

        }
    }
}
