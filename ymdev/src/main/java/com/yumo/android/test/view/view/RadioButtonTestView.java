package com.yumo.android.test.view.view;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yumo.android.R;
import com.yumo.android.test.view.radio.RadioFragment;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 3/27/16.
 */
public class RadioButtonTestView extends YmTestFragment {
    private final String LOG_TAG = "RadioButtonTestView";


    public void testShowRadioButtonTestView(){
        final View rootView = getActivity().getLayoutInflater().inflate(R.layout.radiobutton_test,null);
        showTestView(rootView);
    }

    public void testShowButton(){
        Button button = new Button(getContext());
        button.setText("hellp");
        showTestView(button);
    }

    public void testShowRadioFragment(){
        showFragment(new RadioFragment());
    }
}
