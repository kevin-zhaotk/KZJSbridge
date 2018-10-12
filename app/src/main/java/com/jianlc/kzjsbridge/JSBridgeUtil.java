package com.jianlc.kzjsbridge;

import android.util.Log;

public class JSBridgeUtil {

    public String userId(String user) {
        return "user_id_android";
    }

    public String macAddr() {
        return "00:00:00:00:00:00";
    }

    public void showToast(String tip) {
        Log.i("XXX", "--->tips: " + tip);
    }
}
