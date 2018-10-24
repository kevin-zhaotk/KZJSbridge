package com.jianlc.jsbridge2;

import com.jianlc.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

class JSBridgeMessage {

    private static final String TAG = JSBridgeMessage.class.getSimpleName();

    private static final String HANDLER = "handler";
    private static final String PARAMETER = "parameter";
    private static final String CALLBACK = "callback";

    // method name
    public String handler;

    // parameter of the method name by handler
    public String parameter;

    // result receiver in javascript side if necessary
    public String callback;

    public static JSBridgeMessage parseJson(String json) {
        JSBridgeMessage jsMessage = new JSBridgeMessage();
        try {
            JSONObject object = new JSONObject(json);
            jsMessage.handler = object.getString(HANDLER);
            jsMessage.parameter = object.getString(PARAMETER);
            jsMessage.callback = object.getString(CALLBACK);
        } catch (JSONException e) {
            LogUtil.w(TAG, "parse javascript message error: " + e.getMessage());
        }
        return jsMessage;
    }
}
