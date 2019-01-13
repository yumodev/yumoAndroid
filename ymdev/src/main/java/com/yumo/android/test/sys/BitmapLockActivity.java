/**
 * BitmapLockActivity.java
 * yumo
 * 2015-2-2
 * TODO 图案解锁
 */
package com.yumo.android.test.sys;

import com.yumo.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * yumo
 *
 */
public class BitmapLockActivity extends Activity implements View.OnClickListener{
	private final String TAG = BitmapLockActivity.class.getSimpleName() + "  ";
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bitmap_lock_page);
		initView();
	}
	
	/**
	 * TODO 初始化界面
	 * yumo 
	 * @return
	 * boolean
	 * 2014-12-3
	 */
	private boolean initView()
	{
		ImageView backImg = (ImageView) findViewById(R.id.back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(this);
		
		TextView titleText = (TextView) findViewById(R.id.title);
		titleText.setText("图案解锁");
		
		return true;
	}
	
	@Override  
    protected void onStart() {  
        Log.d(TAG, TAG+"onStart");  
        super.onStart();  
    }  
    @Override  
    protected void onRestart() {  
        Log.d(TAG, TAG+"onRestart");  
        super.onRestart();  
    }  
    @Override  
    protected void onResume() {  
        Log.d(TAG, TAG+"onResume");  
        super.onResume();  
    }  
    @Override  
    protected void onPause() {  
        Log.d(TAG, TAG+"onPause");  
        super.onPause();  
    }  
    @Override  
    protected void onStop() {  
        Log.d(TAG, TAG+"onStop");  
        super.onStop();  
    }  
    @Override  
    protected void onDestroy() {  
        Log.d(TAG, TAG+"onDestroy");  
        super.onDestroy();  
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, TAG+"onClick v.id:"+v.getId());
		switch(v.getId())
		{
			case R.id.back_img:
			{
				Log.d(TAG, TAG+"onclick backimg");
				finish();
				break;
			}
		}
	}

}
