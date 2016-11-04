package cn.xiaocool.wxtteacher.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2016/6/29.
 */
public class NoScrollExpandableListView extends ExpandableListView {

    public NoScrollExpandableListView(Context context) {
        super(context);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
    }
}
