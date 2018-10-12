package com.jianlc.jsbridge;


import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


public class KZJSBridgeWebViewClient extends WebViewClient {

    private static final String TAG = KZJSBridgeWebViewClient.class.getSimpleName();


    private Class proxy;
    private WeakReference<WebView> wmReference;

    public KZJSBridgeWebViewClient(WebView webview, Class proxy) {
        this.wmReference = new WeakReference<>(webview);
        this.proxy = proxy;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        return handleInterept(uri);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        return handleInterept(uri);
    }

    private boolean handleInterept(Uri uri) {
        Log.i(TAG, "--->handleInterept: " + uri.toString());
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (!Constants.Scheme.equalsIgnoreCase(scheme)) {
            return false;
        }

        if (Constants.JSBridgeLoad.equalsIgnoreCase(host)) {
            handleJsBridgeLoad();
            return true;
        } else  if (Constants.JSBridgeAction.equalsIgnoreCase(host)) {
            handleJsBridgeAction();
            return true;
        } else {
            return false;
        }
    }

    private void handleJsBridgeLoad() {
        WebView webView = wmReference.get();
        if (webView == null) {
            return;
        }
        Log.i(TAG, "--->handleJsBridgeLoad: " + KZJSBridgeJsCommand.jsCmdBridgeLoad());
        webView.evaluateJavascript(KZJSBridgeJsCommand.jsCmdBridgeLoad(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.i(TAG, "--->handleJsBridgeLoad: " + s);
            }
        });
    }

    private void handleJsBridgeAction() {
        WebView webView = wmReference.get();
        if (webView == null) {
            return;
        }
        webView.evaluateJavascript(KZJSBridgeJsCommand.jsCmdfetchMessage(), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.i(TAG, "--->handleJsBridgeAction: " + s);

                if (s == null) {
                    return;
                }
                try {
                    /* Generally, the return String value from javascript always surrounded by a couple of quotation marks.
                     * But technically, a judgement should be involved. So a judgement referring to prefix and suffix are occurrence here
                     */

                    // prefix
                    if (s.startsWith("\"")) {
                        s = s.substring(1);
                    }
                    // suffix
                    if (s.endsWith("\"")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    s = s.replaceAll("\\\\", "");
                    Log.i(TAG, "--->handleJsBridgeAction: " + s);
                    JSONArray jsonArray = new JSONArray(s);
                    if (jsonArray.length() == 0) {
                        return;
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);

                        handleCertainAction(json);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "--->exception: " + e.getMessage());
                }
            }
        });
    }

    private void handleCertainAction(JSONObject json) {
        try {
            if (!json.has(Constants.HANDLER)) {
                return;
            }
            String method = json.getString(Constants.HANDLER);

            String parameters = null;
            String callbackId = null;

            if (json.has(Constants.PARAM)) {
                parameters = json.getString(Constants.PARAM);
            }

            if (json.has(Constants.CALLBACK_ID)) {
                callbackId = json.getString(Constants.CALLBACK_ID);
            }
            KZJSBridgeAction action = new KZJSBridgeAction(proxy);
            Object result = action.action(method, parameters);

            if (callbackId != null) {
                KZJSBridgeResponseModel model = new KZJSBridgeResponseModel(callbackId, result.toString());
                handleJsBridgeResponse(model.toString());
            }
        } catch (Exception e) {
            Log.i(TAG, "--->handleCertainAction e: " + e.getMessage());
        }
    }

    private void handleJsBridgeResponse(String response) {
        WebView webView = wmReference.get();
        if (webView == null) {
            return;
        }
        String js = String.format(KZJSBridgeJsCommand.jsCmdReceiveResponse(), response);
        webView.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.i(TAG, "--->handleJsBridgeResponse: " + s);
            }
        });
    }
}
