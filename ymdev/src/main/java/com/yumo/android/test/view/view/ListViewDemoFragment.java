/**
 * ListViewDemoActivity.java
 * yumo
 * 2014-12-3
 */
package com.yumo.android.test.view.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yumo.android.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * yumodev
 */
public class ListViewDemoFragment extends Fragment {
    private final String TAG = "ListViewDemoFragment";

    private Spinner mSpinner = null;

    private ListView mList = null;

    private TextView mHeadView = null;
    private TextView mFooterView = null;

    private boolean mPopDialog = false;
    private int mLastNum = 0;
    private WindowManager mWindowManager = null;
    private TextView mDialogText = null;
    private boolean mShowing = false;
    private RemoveWindow mRemoveWindow = new RemoveWindow();

    private final String LIST_SIMPLE = "Simple";

    private final String LIST_ARRAY = "Array";

    private final String LIST_ARRAY_CHECK = "Array_Checked";

    private final String LIST_ARRAY_CHECK_MODEL = "Array_Checked_Model";

    private final String LIST_ARRAY_MULTIPLE_CHOICE = "Array_Mulitple_choice";

    private final String LIST_ARRAY_SINGLE_CHOICE = "Array_single_choice";

    private final String LIST_HEAD_FOOT = "Head_Foot";

    private final String LIST_CONTACT = "list_contact";

    private final String LIST_SIMPLE_ITEM2 = "simple_list_item2";

    private String[] mListArrs = {LIST_SIMPLE, LIST_ARRAY, LIST_ARRAY_CHECK, LIST_ARRAY_CHECK_MODEL, LIST_ARRAY_MULTIPLE_CHOICE, LIST_ARRAY_SINGLE_CHOICE, LIST_HEAD_FOOT, LIST_CONTACT, LIST_SIMPLE_ITEM2};


    private final class RemoveWindow implements Runnable {

        @Override
        public void run() {

        }
    }

    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_activity, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWindowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    /**
     * TODO 初始化界面
     * yumodev
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        initList();
        initSpinner();

        mDialogText = new TextView(getContext());
        mDialogText.setVisibility(View.VISIBLE);
        mHandler.post(new Runnable() {
            public void run() {

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                mWindowManager.addView(mDialogText, lp);
            }
        });

        return true;
    }

    private boolean initList() {
        mHeadView = new TextView(getContext());
        mHeadView.setText("this is head");

        mFooterView = new TextView(getContext());
        mFooterView.setText("this is foot");

        mList = (ListView) getView().findViewById(R.id.list);
        mList.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG, "onItemSelected :positon" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onItemSelected onNothingSelected");
            }
        });

        mList.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(TAG, "onScrollStateChanged:scrollState:" + scrollState);
                switch (scrollState) {
                    case OnScrollListener.SCROLL_STATE_FLING: {
                        //华东中
                        Log.d(TAG, "scroll_state_flig");
                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                            Toast.makeText(getContext(), "last view", Toast.LENGTH_SHORT).show();
                        }

                        if (view.getFirstVisiblePosition() == 0) {
                            Toast.makeText(getContext(), "fist view", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case OnScrollListener.SCROLL_STATE_IDLE: {
                        //不滑动
                        Log.d(TAG, "scroll_state_idle");
                        break;
                    }

                    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: {
                        //手指触摸屏幕滑动
                        Log.d(TAG, "scroll_state_touch_scroll");
                        break;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "onScroll:firstVisibleItem:" + firstVisibleItem + " visibleItemCount:" + visibleItemCount + " totalItemCount:" + totalItemCount);
                int currentNum = firstVisibleItem / 10;
                if (currentNum != mLastNum && mPopDialog) {
                    Log.d(TAG, TAG + "onScroll:lastNum = " + mLastNum);
                    mLastNum = currentNum;
                    if (!mShowing) {
                        mDialogText.setVisibility(View.VISIBLE);
                        mShowing = true;
                    }
                    mDialogText.setText(String.valueOf(mLastNum));
                    mHandler.removeCallbacks(mRemoveWindow);
                    mHandler.postDelayed(mRemoveWindow, 3000);
                }
            }
        });
        return true;
    }

    private boolean initSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, mListArrs);
        mSpinner = (Spinner) getView().findViewById(R.id.spinner);
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                mList.removeHeaderView(mHeadView);
                mList.removeFooterView(mFooterView);
                mPopDialog = false;
                RemoveWindow();
                Log.d(TAG, pos + "");
                if (mListArrs[pos].equals(LIST_ARRAY)) {
                    mPopDialog = true;
                    initArrayList();
                } else if (mListArrs[pos].equals(LIST_SIMPLE)) {
                    initSimpleList();
                } else if (mListArrs[pos].equals(LIST_ARRAY_CHECK)) {
                    initArrayCheckedList();
                } else if (mListArrs[pos].equals(LIST_ARRAY_CHECK)) {
                    initArrayCheckedModelList();
                } else if (mListArrs[pos].equals(LIST_ARRAY_MULTIPLE_CHOICE)) {
                    initArrayMulitpleList();
                } else if (mListArrs[pos].equals(LIST_ARRAY_SINGLE_CHOICE)) {
                    initArraySingleChoiceList();
                } else if (mListArrs[pos].equals(LIST_HEAD_FOOT)) {
                    initHeadFoot();
                } else if (mListArrs[pos].equals(LIST_CONTACT)) {
                    initListContact();
                } else if (mListArrs[pos].equals(LIST_SIMPLE_ITEM2)) {
                    initSimpleListItem2();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        return true;
    }

    /**
     * TODO 普通的数组列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initArrayList() {
        List<String> arrayList = new ArrayList<String>();
        for (int n = 0; n < 50; n++) {
            arrayList.add("ArrayAdapter list " + n);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        mList.setAdapter(adapter);
    }

    /**
     * TODO 普通的简单列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initSimpleList() {
        List<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();
        for (int n = 0; n < 50; n++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", "simple value:" + n);
            arrayList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, android.R.layout.simple_list_item_1, new String[]{"key"}, new int[]{android.R.id.text1});
        mList.setAdapter(adapter);
    }

    /**
     * TODO 复选列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initArrayCheckedList() {
        List<String> arrayList = new ArrayList<String>();
        for (int n = 0; n < 50; n++) {
            arrayList.add("ArrayAdapter list " + n);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, arrayList);
        mList.setAdapter(adapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    /**
     * TODO 复选列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initArrayCheckedModelList() {
        List<String> arrayList = new ArrayList<String>();
        for (int n = 0; n < 50; n++) {
            arrayList.add("ArrayAdapter list " + n);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, arrayList);
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mList.setAdapter(adapter);

    }

    /**
     * TODO 多选列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initArrayMulitpleList() {
        List<String> arrayList = new ArrayList<String>();
        for (int n = 0; n < 50; n++) {
            arrayList.add("ArrayAdapter list " + n);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayList);
        mList.setAdapter(adapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    /**
     * TODO 单选列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initArraySingleChoiceList() {
        List<String> arrayList = new ArrayList<String>();
        for (int n = 0; n < 50; n++) {
            arrayList.add("ArrayAdapter list " + n);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_single_choice, arrayList);
        mList.setAdapter(adapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void initHeadFoot() {
        List<String> arrayList = new ArrayList<String>();
        for (int n = 0; n < 50; n++) {
            arrayList.add("ArrayAdapter list " + n);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_single_choice, arrayList);
        mList.setAdapter(adapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mList.addHeaderView(mHeadView);
        mList.addFooterView(mFooterView);

    }

    /**
     * TODO 现实联系人列表
     * yumo
     * void
     * 2015-1-30
     */
    private void initListContact() {
        String[] columns = {Contacts._ID, Contacts.DISPLAY_NAME};
        Cursor cursor = getActivity().managedQuery(Contacts.CONTENT_URI, columns, null, null, null);
        CursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, cursor, new String[]{Contacts.DISPLAY_NAME}, new int[]{android.R.id.text1});
        mList.setAdapter(adapter);
    }

    /**
     * TODO 现实联系人列表
     * yumodev
     * void
     * 2015-1-30
     */
    private void initSimpleListItem2() {
        String[] columns = {Phone._ID, Phone.LABEL, Phone.TYPE, Phone.NUMBER};
        Cursor cursor = getActivity().managedQuery(Phone.CONTENT_URI, columns, null, null, null);

        final int labelIndex = cursor.getColumnIndex(Phone.LABEL);
        final int typeIndex = cursor.getColumnIndex(Phone.TYPE);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2, cursor, new String[]{Phone.TYPE, Phone.NUMBER}, new int[]{android.R.id.text1, android.R.id.text2});
        adapter.setViewBinder(new ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                // TODO Auto-generated method stub
                if (columnIndex != typeIndex) {
                    return false;
                }
                int type = cursor.getInt(typeIndex);
                String label = null;
                if (type == Phone.TYPE_CUSTOM) {
                    label = cursor.getString(labelIndex);
                }
                String text = (String) Phone.getTypeLabel(getResources(), type, label);
                ((TextView) view).setText(text);

                return true;
            }
        });
        mList.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        RemoveWindow();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if (mWindowManager != null) {
            mWindowManager.removeView(mDialogText);
        }

    }

    public void RemoveWindow() {
        //mWindowManager.removeView(mDialogText);
        if (mShowing) {
            mShowing = false;
            mDialogText.setVisibility(View.INVISIBLE);
        }
    }

}

