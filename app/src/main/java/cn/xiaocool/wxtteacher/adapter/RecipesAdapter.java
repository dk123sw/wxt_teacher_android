package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.RecipeInfo;


/**
 * Created by THB on 2016/5/27.
 */
public class RecipesAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<RecipeInfo> recipeDatas;
    private ArrayList<String> pics;
    public RecipesAdapter(ArrayList<RecipeInfo> recipeDatas,Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.recipeDatas = recipeDatas;
    }
    @Override
    public int getCount() {
        return recipeDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.recipes_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_week.setText(recipeDatas.get(position).getWeek());
        RecipesItemAdapter adapter = new RecipesItemAdapter(recipeDatas.get(position).getData(),context);
        holder.lv_recipe.setAdapter(adapter);
        return convertView;


    }
    class ViewHolder{
        TextView tv_week;
        ListView lv_recipe;
        public ViewHolder(View convertView){
            tv_week = (TextView) convertView.findViewById(R.id.recipes_item_week);
            lv_recipe = (ListView) convertView.findViewById(R.id.recipes_item_list);
        }
    }
}
