package cn.xiaocool.wxtteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.RecipeInfo;
import cn.xiaocool.wxtteacher.main.CircleImagesActivity;


/**
 * Created by THB on 2016/6/20.
 */
public class  RecipesItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<RecipeInfo.RecipeData> recipeDatas;
    private ArrayList<String> pics;

    public RecipesItemAdapter(ArrayList<RecipeInfo.RecipeData> recipeDatas, Context context) {
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_recipe_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(recipeDatas.get(position).getTitle());
        holder.tv_content.setText(recipeDatas.get(position).getContent());
        if (recipeDatas.get(position).getPhoto() != null){
            pics =new ArrayList<String>();
            pics.add(recipeDatas.get(position).getPhoto());
            holder.gv_image.setVisibility(View.VISIBLE);
            ParWarnImgGridAdapter parWarnImgGridAdapter = new ParWarnImgGridAdapter( pics,context);
            holder.gv_image.setAdapter(parWarnImgGridAdapter);
            holder.gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                    // 图片浏览
                    /*ArrayList<String> pics = new ArrayList<String>();
                    pics.add(homeworkDataList.get(position).getPhoto());*/
                    Intent intent = new Intent();
                    intent.setClass(context, CircleImagesActivity.class);
                    intent.putStringArrayListExtra("Imgs", pics);
                    intent.putExtra("type", "9");
                    intent.putExtra("position", a);
                    context.startActivity(intent);
                }
            });
        }else {
            holder.gv_image.setVisibility(View.GONE);

        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_title,tv_content;
        GridView gv_image;

        public ViewHolder(View convertView) {
            tv_title = (TextView) convertView.findViewById(R.id.recipes_type);
            tv_content = (TextView) convertView.findViewById(R.id.recipes_content);
            gv_image = (GridView) convertView.findViewById(R.id.item_recipe_gridview);
        }
    }
}
