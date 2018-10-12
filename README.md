# KZJSbridge
This is a interaction framework between native and javascript running on android!  

Android Version Requirement:
  Because this framework is based upon Webview's evaluateJavascript API, this API only supported on Android version 4.4 and above, 
this framework doesnt work on android devices bellow android4.4. atention please.

Core function:
  The goal of this framework is supplying a way by which javascript can native methods efficiently. invokation from native to javascript 
  doesnt be referred in this framework right now. I will fill in this funciton later if nessissary.


How to migrate to your project?

1. add dependency in your app's build.gradle

 compile 'com.jianlc.jsbridge:KZJSBCore:1.0.0'

2. modify your WebView initillization, replace the original WebViewClient with a new one, a instance of class KZJSBridgeWebViewClient or it's subclass;  
  
  mWebview.setWebViewClient(new KZJSBridgeWebViewClient(mWebview, JSBridgeUtil.class));
  
  1) JSBridgeUtil.class, this is the class where you should implement your methods invoked by js side;
  2) mWebView, a WebView's instance;
  3) If you have some other works in your webviewclient, javascript interception for example, you should subclass the KZJSBridgeWebViewClient and accomplish your code here. attention, if you ovrrided the VebViewClient method shouldOverrideUrlLoading, remember that you must invoke super.shouldOverrideUrlLoading(), otherwise this jsbridge will work unexpectativly.
  
3. modify your H5 code.
   Initillize the jsbridge by plugin the following code snippet:
   
   <script language="javascript">
    .....
      function setUpJSBridge() {
                    if (window.WVJavaScriptBridge) {return;}
                    var messageFramge = document.createElement("iframe");
                    messageFramge.style.display = "none";
                    messageFramge.src = "kzjsbridge://__kz_jsbridge_load";
                    
                    document.documentElement.appendChild(messageFramge);
                    setTimeout(function(){document.removeChild(messageFramge)}, 0);
                    
                }
            
      setUpJSBridge()
      .....
   </script>
   
   
Inspired by the known opensource project 'WebViewJavascriptBridge', I decided to build this jsbridge. I Copied all signicant theory from WebViewJavascriptBridge except method registration. I think the method registration strategy is unefficient, so i replaced this part by refection.
