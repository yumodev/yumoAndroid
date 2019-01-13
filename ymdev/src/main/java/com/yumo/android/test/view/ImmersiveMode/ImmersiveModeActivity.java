package com.yumo.android.test.view.ImmersiveMode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yumo.android.R;
import com.yumo.android.common.widget.HeaderLayout;

/**
 * Created by guo on 15/9/29.
 */
public class ImmersiveModeActivity extends Activity {
    private static final String TAG = "ImmersiveModeActivity";

    FrameLayout mRootView;
    LinearLayout mMainContent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        ImmersiveModeHelper.getInstance().hideSystemUI(getWindow().getDecorView());

        mRootView = new FrameLayout(this);
        mRootView.setFitsSystemWindows(true);
        mMainContent = new LinearLayout(this);
        mMainContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMainContent.setOrientation(LinearLayout.VERTICAL);

        HeaderLayout headerLayout = (HeaderLayout)(getLayoutInflater().inflate(R.layout.header_layout_custom, mRootView, false));
        headerLayout.setTitleView("ImmersiveMode");
        headerLayout.getBackView().setVisibility(View.VISIBLE);
        headerLayout.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMainContent.addView(headerLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TestPopWindowView();
        testAutoCompleteTextView();
        testEditText();
        mRootView.addView(mMainContent);
        setContentView(mRootView);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.d(TAG,"onSystemUiVisibilityChange");
                ImmersiveModeHelper.getInstance().hideSystemUI(getWindow().getDecorView());
            }
        });

        return true;
    }

    private void TestPopWindowView(){
        final Button btn = new Button(this);
        btn.setText("TextPopWindow");

        mMainContent.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tv = new TextView(getApplicationContext());

                tv.setSystemUiVisibility(getWindow().getDecorView().getVisibility());
                PopupWindow popupWindow = new PopupWindow(tv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.black));
                popupWindow.showAsDropDown(btn);

                tv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImmersiveModeHelper.getInstance().hideSystemUI((View) tv.getParent());
                    }
                }, 100);


            }
        });
    }

    private void testAutoCompleteTextView(){
        final AutoCompleteTextView textView = new AutoCompleteTextView(this);

        mMainContent.addView(textView);

        //设置数据源
        String[] autoStrings=new String[]{"New York","Tokyo","beijing","london","Seoul Special","Los Angeles"};
       //设置ArrayAdapter，并且设定以单行下拉列表风格展示（第二个参数设定）。
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ImmersiveModeActivity.this,
        android.R.layout.simple_dropdown_item_1line, autoStrings);
        //设置AutoCompleteTextView的Adapter
        textView.setAdapter(adapter);

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        WindowManager.LayoutParams lp = getWindow().getAttributes();
//                        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//                        lp.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
//                        getWindow().setAttributes(lp);
//                    }
//                }, 2000);
//            }
//        });
    }

    private void testEditText(){
        final EditText editText = new EditText(this);
        mMainContent.addView(editText);

        editText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //ImmersiveModeHelper.getInstance().hideSystemUI(getWindow().getDecorView());
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                        getWindow().setAttributes(lp);
                    }
                }, 100);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged");
        ImmersiveModeHelper.getInstance().hideSystemUI(getWindow().getDecorView());
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
