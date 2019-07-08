package com.yumo.android.test.web.browser;

import android.view.View;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.yumo.demo.view.YmTestFragment;

public class JsBridgeTestView extends YmTestFragment {

    BridgeWebView mBridgeWebView = null;
    @Override
    protected View getHeaderView() {
        return initBridgeWebView();
    }


    private BridgeWebView initBridgeWebView(){
        mBridgeWebView = new BridgeWebView(getContext());
        mBridgeWebView.getSettings().setJavaScriptEnabled(true);

        mBridgeWebView.loadUrl("file:///android_asset/test_html/test_wx.html");

        mBridgeWebView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showToastMessage("收到DefaultHandler web端的消息");

                function.onCallBack("回传defaultHandler的数据");
            }
        });

        mBridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showToastMessage("收到指定Handler web端的消息");

                function.onCallBack("回传来自指定Hanlder的数据");
            }
        });

        return mBridgeWebView;
    }


    public void testWebDefaluter(){
        showToastMessage("testWebDefaulter");
        mBridgeWebView.send("发送default", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                showToastMessage("来自Web的回传数据");
            }
        });

    }


    public void toWeb(){
        showToastMessage("toWeb");
        mBridgeWebView.callHandler("functionJs", "发送事件给Web指定接收。", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                showToastMessage("来自Web的回传数据");
            }
        });
    }



}
