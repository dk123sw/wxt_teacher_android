package cn.xiaocool.wxtteacher.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import cn.xiaocool.wxtteacher.R;
import cn.xiaocool.wxtteacher.fragment.AddressFragment;

public class AddressActivity extends Activity {

    private AddressFragment addressFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_address);
        addressFragment = new AddressFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, addressFragment);
        transaction.commit();
    }
}
