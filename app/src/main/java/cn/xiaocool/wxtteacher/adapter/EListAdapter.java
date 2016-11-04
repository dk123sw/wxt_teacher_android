package cn.xiaocool.wxtteacher.adapter;

/**
 * Created by THB on 2016/6/27.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.bean.Child;
import cn.xiaocool.wxtteacher.bean.Group;


public class EListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
    private CheckBox checkBox;
    private Context context;
    private TextView textView;
    private int size;

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    private ArrayList<Group> groups;

    public EListAdapter(Context context, ArrayList<Group> groups, CheckBox checkBox, TextView textView) {
        this.textView = textView;
        this.checkBox = checkBox;
        this.context = context;
        this.groups = groups;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildItem(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildrenCount();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 設定 Group 資料
     */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_layout, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(group.getTitle());
        ImageView jiantou =  (ImageView) convertView.findViewById(R.id.jiantou);
        if (isExpanded){
            Log.e("55550", "展开");
            jiantou.setImageResource(R.drawable.address_triangle_down);

        }else {
            Log.e("55550","闭合");
            jiantou.setImageResource(R.drawable.address_triangle);
        }

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbGroup);
        checkBox.setChecked(group.getChecked());

        // 點擊 CheckBox 時，將狀態存起來
        checkBox.setOnClickListener(new Group_CheckBox_Click(groupPosition));

        return convertView;
    }

    /**
     * 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態
     */
    class Group_CheckBox_Click implements OnClickListener {
        private int groupPosition;

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public void onClick(View v) {
            groups.get(groupPosition).toggle();

            // 將 Children 的 isChecked 全面設成跟 Group 一樣
            int childrenCount = groups.get(groupPosition).getChildrenCount();
            boolean groupIsChecked = groups.get(groupPosition).getChecked();
            for (int i = 0; i < childrenCount; i++)
                groups.get(groupPosition).getChildItem(i).setChecked(groupIsChecked);
            isAllChecked();
            checkSize();
            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
            notifyDataSetChanged();
        }
    }

    private void isAllChecked() {
        int tag = 1;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getChecked() == false) {
                tag = 0;
                break;
            }
        }
        if (tag == 1) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    private void checkSize() {
        size = 0;
        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).getChildrenCount(); j++) {
                if (groups.get(i).getChildItem(j).getChecked() == true) {
                    size++;
                }
            }
        }
        textView.setText("已选择" + size + "人");
    }

    /**
     * 設定 Children 資料
     */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Child child = groups.get(groupPosition).getChildItem(childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText(child.getFullname());

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbChild);
        checkBox.setChecked(child.getChecked());

        // 點擊 CheckBox 時，將狀態存起來
        checkBox.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));

        return convertView;
    }

    /**
     * 勾選 Child CheckBox 時，存 Child CheckBox 的狀態
     */
    class Child_CheckBox_Click implements OnClickListener {
        private int groupPosition;
        private int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public void onClick(View v) {
            handleClick(childPosition, groupPosition);
        }

    }

    public void handleClick(int childPosition, int groupPosition) {
        groups.get(groupPosition).getChildItem(childPosition).toggle();

        // 檢查 Child CheckBox 是否有全部勾選，以控制 Group CheckBox
        int childrenCount = groups.get(groupPosition).getChildrenCount();
        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (!groups.get(groupPosition).getChildItem(i).getChecked()){
                childrenAllIsChecked = false;
            }

        }

        groups.get(groupPosition).setChecked(childrenAllIsChecked);
        isAllChecked();
        checkSize();
        // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
        notifyDataSetChanged();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        handleClick(childPosition, groupPosition);
        return true;
    }

    public ArrayList<Group> getterGroups() {


        return groups;
    }

}