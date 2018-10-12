package com.jianlc.jsbridge;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class KZJSBridgeResponseModel {


    public String data;
    public String callbackId;

    public KZJSBridgeResponseModel(String callbackId, String data) {
        this.data = data;
        this.callbackId = callbackId;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.DATA, data == null? "" : data);
            json.put(Constants.CALLBACK_ID, callbackId == null? "" : callbackId);
        } catch (JSONException e) {

        }

        return json.toString();
    }
}
