package com.yumo.android.test.web.browser;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.widget.PopupMenu;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yumo.android.R;
import com.yumo.android.common.YumoConfig;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by wks on 6/28/16.
 * WebView.HitTestResult.UNKNOWN_TYPE    未知类型
 WebView.HitTestResult.PHONE_TYPE    电话类型
 WebView.HitTestResult.EMAIL_TYPE    电子邮件类型
 WebView.HitTestResult.GEO_TYPE    地图类型
 WebView.HitTestResult.SRC_ANCHOR_TYPE    超链接类型
 WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE    带有链接的图片类型
 WebView.HitTestResult.IMAGE_TYPE    单纯的图片类型
 WebView.HitTestResult.EDIT_TEXT_TYPE    选中的文字类型


 WebView有一个getHitTestResult():返回的是一个HitTestResult，一般会根据打开的链接的类型，返回一个extra的信息，
 如果打开链接不是一个url，或者打开的链接是JavaScript的url，他的类型是UNKNOWN_TYPE，
 这个url就会通过requestFocusNodeHref(Message)异步重定向。
 返回的extra为null，或者没有返回extra。根据此方法的返回值，判断是否为null，可以用于解决网页重定向问题。
 */
public class WebViewMenuTestView extends YmTestFragment {
    public final String LOG_TAG = "WebViewMenuTestView";
    private MxWebView mWebView = null;

    public void testCustomMenu() {
        mWebView = new MxWebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://news.sina.cn/gn/2016-05-09/detail-ifxryhhi8536395.d.html?vt=4&wm=9212_0001&sid=172021");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        showTestView(mWebView);
    }

    class MxWebView extends WebView {
        public MxWebView(Context context) {
            super(context);
        }

        public MxWebView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MxWebView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        @Override
        public ActionMode startActionMode(android.view.ActionMode.Callback callback) {
            CustomizedSelectActionModeCallback customizedSelectActionModeCallback = new CustomizedSelectActionModeCallback(
                    callback);
            return super.startActionMode(customizedSelectActionModeCallback);
        }

        public class CustomizedSelectActionModeCallback implements ActionMode.Callback {
            private ActionMode.Callback callback;


            public CustomizedSelectActionModeCallback(ActionMode.Callback callback) {
                this.callback = callback;
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.d(LOG_TAG, "size=" + menu.size());
                MenuInflater inflater = new MenuInflater(getContext());
                inflater.inflate(R.menu.webview_menu_url, menu);
                return callback.onCreateActionMode(mode, menu);
            }


            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return callback.onPrepareActionMode(mode, menu);
            }


            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item == null || TextUtils.isEmpty(item.getTitle())) {
                    return callback.onActionItemClicked(mode, item);
                }
                if (item.getItemId() != R.id.webview_open_url) {
                    return callback.onActionItemClicked(mode, item);
                } else {
                    showToastMessage("test menu");
                }

                return true;
            }


            @Override
            public void onDestroyActionMode(ActionMode mode) {
                callback.onDestroyActionMode(mode);
            }
        }
    }


    public void testImageContext() {
        mWebView = new MxWebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("https://m.hao123.com/");
        mWebView.loadUrl("http://nav.maxthon.cn/android/");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View arg1,
                                            ContextMenu.ContextMenuInfo arg2) {
                MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        // do the menu action
                        WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        switch (item.getItemId()) {
                            case 1:
                                Log.d("you click", "分享");
                                break;
                            case 2:
                                Log.i("you click", "下载");
                                break;
                        }
                        return true;
                    }
                };

                WebView.HitTestResult result = ((WebView) arg1).getHitTestResult();
                int resultType = result.getType();
                Log.i(YumoConfig.LOG_TAG, "onLongClick:" + result.getType() + " " + result.getExtra());
                switch (resultType) {
                    case WebView.HitTestResult.IMAGE_TYPE: {
                        menu.setHeaderTitle(result.getExtra());

                        Intent i = new Intent();
                        MenuItem item = menu.add(0, 1, 0, "分享")
                                .setOnMenuItemClickListener(handler);
                        item.setIntent(i);

                        item = menu.add(0, 2, 0, "下载").setOnMenuItemClickListener(
                                handler);
                        item.setIntent(i);
                        break;
                    }
                    case WebView.HitTestResult.EMAIL_TYPE:
                    case WebView.HitTestResult.PHONE_TYPE:
                    case WebView.HitTestResult.GEO_TYPE:
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: {
                        Intent i = new Intent();
                        MenuItem item = menu.add(0, 1, 0, "分享")
                                .setOnMenuItemClickListener(handler);
                        item.setIntent(i);

                        item = menu.add(0, 2, 0, "下载").setOnMenuItemClickListener(
                                handler);
                        item.setIntent(i);
                        break;
                    }
                    case WebView.HitTestResult.UNKNOWN_TYPE:{
                        showToastMessage(result.getType()+"");
                    }
                }

            }
        });


        showTestView(mWebView);
    }

    public void testPopupMenu(){
        Button button = new Button(getContext());
        button.setText("打开PopupMenu");
        button.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                //return false;
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.webview_menu_url, popupMenu.getMenu());
                popupMenu.show();
                return false;
            }
        });
        showTestView(button, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void testShowPopMenu() {
        mWebView = new MxWebView(getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setOnLongClickListener(longClickListener);
        showTestView(mWebView);
    }

    private void showPopMenu(WebView webView) {
        PopupMenu popupMenu = new PopupMenu(getContext(), getActivity().getWindow().getDecorView());
        final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
        int type = hitTestResult.getType();
        if (type == WebView.HitTestResult.IMAGE_TYPE){
            popupMenu.getMenuInflater().inflate(R.menu.webview_menu_image, popupMenu.getMenu());
            requestImageRef(null);
        }else if (type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
            popupMenu.getMenuInflater().inflate(R.menu.webview_menu_url, popupMenu.getMenu());
            popupMenu.getMenuInflater().inflate(R.menu.webview_menu_image, popupMenu.getMenu());
            requestFocusNodeHref(null);
        }else if (type == WebView.HitTestResult.SRC_ANCHOR_TYPE){
            popupMenu.getMenuInflater().inflate(R.menu.webview_menu_url, popupMenu.getMenu());
            requestFocusNodeHref(null);
        }else if (type == WebView.HitTestResult.UNKNOWN_TYPE){
            popupMenu.getMenuInflater().inflate(R.menu.webview_menu_url, popupMenu.getMenu());
            requestFocusNodeHref(null);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.webview_open_url: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_open_new_url: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_open_background_url: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_page_save: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_page_share: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_image_download: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_image_look: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                    case R.id.webview_image_share: {
                        showToastMessage(hitTestResult.getType()+" "+hitTestResult.getExtra());
                        break;
                    }
                }
                requestFocusNodeHref(item);
                return false;
            }
        });
        popupMenu.setGravity(Gravity.CENTER);
        popupMenu.show();
    }

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            WebView webView = WebView.class.cast(v);
            WebView.HitTestResult result = webView.getHitTestResult();
            Log.i(YumoConfig.LOG_TAG, "onLongClick:" + result.getType() + " " + result.getExtra());
            switch (webView.getHitTestResult().getType()) {
                case WebView.HitTestResult.EMAIL_TYPE:
                    break;
                case WebView.HitTestResult.GEO_TYPE:
                    break;
                case WebView.HitTestResult.IMAGE_TYPE:
                    break;
                case WebView.HitTestResult.EDIT_TEXT_TYPE:
                    break;
                case WebView.HitTestResult.PHONE_TYPE:
                    break;
                case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                    break;
                case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                    break;
                case WebView.HitTestResult.UNKNOWN_TYPE:
                    break;
            }
            showPopMenu(webView);
            return false;
        }
    };


    public static final int MESSAGE_REQUEST_NODE_HREF = 0;
    public static final int MESSAGE_REQUEST_IMAGE_REF = 1;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_REQUEST_NODE_HREF:{
                    String url = (String) msg.getData().get("url");
                    String title = (String) msg.getData().get("title");
                    String src = (String) msg.getData().get("src");
                    showToastMessage(url+"   "+title+" "+src+" ");
                    break;
                }
                case MESSAGE_REQUEST_IMAGE_REF:{
                    String url = (String) msg.getData().get("url");
                    showToastMessage("url:"+url);
                }
            }
        }
    };

    private void requestFocusNodeHref(MenuItem item) {
        int id = item != null?item.getItemId():0;
        final Message msg2 = mHandler.obtainMessage(MESSAGE_REQUEST_NODE_HREF, id, 0);
        mWebView.requestFocusNodeHref(msg2);
    }

    private void requestImageRef(MenuItem item) {
        int id = item != null?item.getItemId():0;
        final Message msg2 = mHandler.obtainMessage(MESSAGE_REQUEST_IMAGE_REF, id, 0, null);
        mWebView.requestImageRef(msg2);
    }

}


