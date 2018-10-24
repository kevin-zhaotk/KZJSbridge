package com.jianlc.kzjsbridge;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jianlc.jsbridge.KZJSBridgeWebViewClient;


public class WebviewActivity extends Activity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebview = (WebView) findViewById(R.id.webview);

        initWebview();
        loadUrl("file:///android_asset/index.html");
    }

    private void initWebview() {

        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new KZJSBridgeWebViewClient(mWebview, JSBridgeUtil.class));
        
    }

    private void loadUrl(String url) {
        mWebview.loadUrl(url);
    }
}
