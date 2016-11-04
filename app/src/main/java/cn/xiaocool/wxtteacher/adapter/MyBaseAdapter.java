package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> t;
    public LayoutInflater inflater;
    public Context context;
    public MyBaseAdapter(List<T> t, Context context) {
        this.t = t;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return t.size();
    }

    @Override
    public Object getItem(int position) {
        return t.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getHolderView(position,convertView,parent);
    }
     public abstract View getHolderView(int position, View convertView, ViewGroup parent);
}
