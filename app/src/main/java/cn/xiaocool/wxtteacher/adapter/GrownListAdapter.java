package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;

/**
 * Created by Administrator on 2016/5/28.
 */
public class GrownListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> list;
    public GrownListAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_one_text,null);

        return convertView;
    }
}
