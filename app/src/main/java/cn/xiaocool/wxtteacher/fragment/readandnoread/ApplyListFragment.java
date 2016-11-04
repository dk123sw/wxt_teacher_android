package cn.xiaocool.wxtteacher.fragment.readandnoread;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.adapter.ChildrenCollectAdapter;
import cn.xiaocool.wxtteacher.bean.Classevents;
import cn.xiaocool.wxtteacher.net.request.constant.NetBaseConstant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyListFragment extends Fragment {



    private ListView children_list;
    public List<Classevents.ClassEventData.IsApplyList> isapplylist;
    private ChildrenCollectAdapter childrenCollectAdapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_no_read, container, false);
        context = getActivity();
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }



    private void initView() {

        children_list = (ListView) getView().findViewById(R.id.children_list);
        children_list.setAdapter(new ReadAdapter());
    }

    public class ReadAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ImageLoader imageLoader = ImageLoader.getInstance();
        private DisplayImageOptions displayImage;

        public ReadAdapter() {

            inflater = LayoutInflater.from(context);
            displayImage = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .showImageOnLoading(R.drawable.katong).showImageOnFail(R.drawable.katong)
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }




        @Override
        public int getCount() {
            return isapplylist.size();
        }

        @Override
        public Object getItem(int position) {
            return isapplylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_no_read_list,null);
                holder = new ViewHolder(convertView);
                convertView.setBackground(new ColorDrawable(Color.parseColor("#ffffff")));
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            holder.item_title.setText(isapplylist.get(position).getName());
            holder.item_content.setText(isapplylist.get(position).getContactphone());



            //头像
            imageLoader.displayImage(NetBaseConstant.NET_CIRCLEPIC_HOST +"/"+ isapplylist.get(position).getAvatar(), holder.item_head, displayImage);
            return convertView;
        }

        class ViewHolder{

            TextView item_title,item_content,item_time;
            ImageView item_head,item_information;
            public ViewHolder(View convertView) {
                item_title = (TextView) convertView.findViewById(R.id.item_title);
                item_content = (TextView) convertView.findViewById(R.id.item_content);
                item_time = (TextView) convertView.findViewById(R.id.item_time);
                item_head = (ImageView) convertView.findViewById(R.id.item_head);
                item_information = (ImageView) convertView.findViewById(R.id.item_information);
            }



        }
    }

}
