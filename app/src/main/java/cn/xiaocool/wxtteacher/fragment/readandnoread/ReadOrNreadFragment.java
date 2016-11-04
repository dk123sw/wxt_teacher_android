package cn.xiaocool.wxtteacher.fragment.readandnoread;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Classevents;

import cn.xiaocool.wxtteacher.ui.NoScrollListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadOrNreadFragment extends Fragment {

    private Context context;
    public ArrayList<Classevents.ClassEventData.ReciverlistInfo> notReads,alreadyReads;
    private NoScrollListView already_list,notready_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        context = getActivity();
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        already_list = (NoScrollListView) getView().findViewById(R.id.already_list);
        notready_list = (NoScrollListView) getView().findViewById(R.id.notready_list);
        already_list.setAdapter(new ReadOrNotReadAdapter(alreadyReads,context,"0"));
        notready_list.setAdapter(new ReadOrNotReadAdapter(notReads,context,"1"));
    }

    public class ReadOrNotReadAdapter extends BaseAdapter {

        private ArrayList<Classevents.ClassEventData.ReciverlistInfo> classListDataList;
        private LayoutInflater inflater;
        private Context mContext;
        private String type;
        public ReadOrNotReadAdapter(ArrayList<Classevents.ClassEventData.ReciverlistInfo> classListDataList, Context context,String type) {
            this.classListDataList = classListDataList;
            this.inflater = LayoutInflater.from(context);
            this.mContext = context;
            this.type = type;

        }

        @Override
        public int getCount() {
            return classListDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.teachers_addresses,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Classevents.ClassEventData.ReciverlistInfo data = classListDataList.get(position);

            if (type.equals("1")){
                holder.sms.setVisibility(View.VISIBLE);
                holder.call.setVisibility(View.VISIBLE);

            }else {
                holder.sms.setVisibility(View.GONE);
                holder.call.setVisibility(View.GONE);

            }

            holder.name.setText(data.getName());
            holder.sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext,TestChatActivity.class);
//                    intent.putExtra("teacherID", data.getReceiverid());
//                    intent.putExtra("teacherName",data.getName());
//                    mContext.startActivity(intent);
                }
            });

            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + data.getPhone()));
                    mContext.startActivity(intent);
                }
            });
            return convertView;
        }
        class ViewHolder{
            TextView name;
            ImageView sms,call;
            public ViewHolder(View convertView) {
                name = (TextView) convertView.findViewById(R.id.text_teacherName);
                sms = (ImageView) convertView.findViewById(R.id.sms);
                call = (ImageView) convertView.findViewById(R.id.call);
            }
        }
    }
}

