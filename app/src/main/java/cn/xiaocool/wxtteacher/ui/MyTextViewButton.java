package cn.xiaocool.wxtteacher.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextViewButton extends TextView {

	public MyTextViewButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private int index = -1;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public MyTextViewButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO: do something here if you want
	}

	public MyTextViewButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO: do something here if you want
	}
}
