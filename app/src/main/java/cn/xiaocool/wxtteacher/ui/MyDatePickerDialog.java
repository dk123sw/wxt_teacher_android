package cn.xiaocool.wxtteacher.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by THB on 2016/6/18.
 */
public class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context,
                              OnDateSetListener callBack, int year, int monthOfYear,
                              int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        // TODO Auto-generated constructor stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
