package com.yumo.android.test.view.view;

import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 7/27/16.
 */
public class EditTextTestView extends YmTestFragment {

    private void showEditText(EditText et){
        LinearLayout rootView = new LinearLayout(getActivity());
        rootView.setBackgroundResource(R.color.common_page_bg);
        rootView.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        showTestView(rootView);
    }

    /**
     * 测试多行编辑框的hit
     */
    public void testMultiLineHitEditText(){
        EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setMaxLines(5);
        editText.setMinLines(5);
        editText.setGravity(Gravity.LEFT|Gravity.TOP);
        editText.setTextColor(getContext().getResources().getColor(R.color.common_text_color));
        editText.setText("text");
        editText.setHint("hit");
        showEditText(editText);
    }

    /**
     * 测试多行编辑框的hit
     */
    public void testEditText(){
        EditText editText = new EditText(getActivity());
        editText.setGravity(Gravity.LEFT|Gravity.TOP);
        editText.setTextColor(getContext().getResources().getColor(R.color.common_text_color));
        editText.setText("djjdkdkdd");
        editText.setHint("hit");
        showEditText(editText);
    }
}
