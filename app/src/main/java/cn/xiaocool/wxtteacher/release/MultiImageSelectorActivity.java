package cn.xiaocool.wxtteacher.release;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import cn.xiaocool.wxtteacher.R;

/**
 * Created by Nereo on 2015/4/7. Updated by nereo on 2016/1/19.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {

	public static final String EXTRA_SELECT_COUNT = "max_select_count";
	public static final String EXTRA_SELECT_MODE = "select_count_mode";
	public static final String EXTRA_SHOW_CAMERA = "show_camera";
	public static final String EXTRA_RESULT = "select_result";
	public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

	public static final int MODE_SINGLE = 0;
	public static final int MODE_MULTI = 1;

	private ArrayList<String> resultList = new ArrayList<String>();
	private Button mSubmitButton;
	private int mDefaultCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_default);
		Intent intent = getIntent();
		mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
		int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
		boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
		if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
			resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
		}

		Bundle bundle = new Bundle();
		bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
		bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
		bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
		bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

		getSupportFragmentManager().beginTransaction().add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle)).commit();

		findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		mSubmitButton = (Button) findViewById(R.id.commit);
		if (resultList == null || resultList.size() <= 0) {
			mSubmitButton.setText(R.string.action_done);
			mSubmitButton.setEnabled(false);
		} else {
			updateDoneText();
			mSubmitButton.setEnabled(true);
		}
		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (resultList != null && resultList.size() > 0) {
					Intent data = new Intent();
					data.putStringArrayListExtra(EXTRA_RESULT, resultList);
					setResult(RESULT_OK, data);
					finish();
				}
			}
		});
	}

	private void updateDoneText() {
		mSubmitButton.setText(String.format("%s(%d/%d)", getString(R.string.action_done), resultList.size(), mDefaultCount));
	}

	@Override
	public void onSingleImageSelected(String path) {
		Intent data = new Intent();
		resultList.add(path);
		data.putStringArrayListExtra(EXTRA_RESULT, resultList);
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public void onImageSelected(String path) {
		if (!resultList.contains(path)) {
			resultList.add(path);
		}
		if (resultList.size() > 0) {
			updateDoneText();
			if (!mSubmitButton.isEnabled()) {
				mSubmitButton.setEnabled(true);
			}
		}
	}

	@Override
	public void onImageUnselected(String path) {
		if (resultList.contains(path)) {
			resultList.remove(path);
		}
		updateDoneText();
		if (resultList.size() > 0) {
			mSubmitButton.setEnabled(true);
		} else {
			mSubmitButton.setEnabled(false);
		}
		// mSubmitButton.setText(R.string.action_done);
	}

	@Override
	public void onCameraShot(File imageFile) {
		if (imageFile != null) {

			// notify system
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

			Intent data = new Intent();
			resultList.add(imageFile.getAbsolutePath());
			data.putStringArrayListExtra(EXTRA_RESULT, resultList);
			setResult(RESULT_OK, data);
			finish();
		}
	}
}
