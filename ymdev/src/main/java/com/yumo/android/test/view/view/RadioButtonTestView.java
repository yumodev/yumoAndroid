package com.yumo.android.test.view.view;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 3/27/16.
 */
public class RadioButtonTestView extends YmTestFragment {
    private final String LOG_TAG = "RadioButtonTestView";

    public void testShowRadioButtonTestView(){
        final View rootView = getActivity().getLayoutInflater().inflate(R.layout.radiobutton_test,null);
        showTestView(rootView);

        final RadioGroup radioGroup = (RadioGroup)rootView.findViewById(R.id.radiogroup_id);
                (rootView.findViewById(R.id.btn_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)rootView.findViewById(id);
                showToastMessage(radioButton.getText().toString());
            }
        });
    }

    public void testShowButton(){
        Button button = new Button(getContext());
        button.setText("hellp");
        showTestView(button);
    }
}
