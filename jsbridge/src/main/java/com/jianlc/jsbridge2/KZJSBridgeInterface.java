package com.jianlc.jsbridge2;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.jianlc.jsbridge.KZJSBridgeAction;
import com.jianlc.util.LogUtil;

import java.lang.ref.WeakReference;

public class KZJSBridgeInterface {

    private static final String TAG = KZJSBridgeInterface.class.getSimpleName();

    public static final String INTERFACE_NAME = "AndroidJSBridge";

    private Class mClazz;
    private WeakReference<WebView> mWebview;

    public KZJSBridgeInterface(WebView webView, Class clazz) {
        this.mClazz = clazz;
        this.mWebview = new WeakReference<>(webView);
    }

    @JavascriptInterface
    public void postMessage(String message) {

        JSBridgeMessage msg = JSBridgeMessage.parseJson(message);
        if (mClazz == null) {
            LogUtil.e(TAG, "postMessage error: clazz can not be null");
            return;
        }
        KZJSBridgeAction action = new KZJSBridgeAction(mClazz);
        if (TextUtils.isEmpty(msg.handler)) {
            LogUtil.e(TAG, "postMessage error: handler must be specified");
            return;
        }
        Object result = action.action(msg.handler, msg.parameter);

        final WebView webview = mWebview.get();
        if (!TextUtils.isEmpty(msg.callback) && webview != null) {
            final String javascript = String.format("javascript:%s(\"%s\");", msg.callback, result.toString());
            webview.post(new Runnable() {
                @Override
                public void run() {
                    webview.evaluateJavascript(javascript, null);
                }
            });

        }

    }

}
