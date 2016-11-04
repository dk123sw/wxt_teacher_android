package cn.xiaocool.wxtteacher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cn.xiaocool.wxtteacher.app.ExitApplication;

public class BaseActivity extends FragmentActivity implements ReceiverInterface {
    private IntentFilter myIntentFilter;
    GestureDetector mGestureDetector;
    private boolean mNeedBackGesture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGestureDetector();
    }

    private void initGestureDetector() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
//        destroyRadio();
        ExitApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void destroyRadio() {
        getApplicationContext().unregisterReceiver(IntentReceicer);
    }

    @Override
    public void dealWithRadio(Intent intent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void regiserRadio(String[] actions) {
        myIntentFilter = new IntentFilter();
        for (int i = 0; i < actions.length; i++) {
            myIntentFilter.addAction(actions[i]);
        }
        getApplicationContext().registerReceiver(IntentReceicer, myIntentFilter);
    }

    private BroadcastReceiver IntentReceicer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dealWithRadio(intent);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (mNeedBackGesture) {
            return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setNeedBackGesture(boolean mNeedBackGesture) {
        this.mNeedBackGesture = mNeedBackGesture;
    }

    public void doBack(View view) {
        onBackPressed();
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
}