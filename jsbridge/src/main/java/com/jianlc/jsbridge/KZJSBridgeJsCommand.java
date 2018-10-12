package com.jianlc.jsbridge;

public class KZJSBridgeJsCommand {


    private static final String js_load_bridge = "javascript:;(function() {\n" +
            "    if(window.WVJavaScriptBridge) {\n" +
            "        return;\n" +
            "    }\n" +
            "        \n" +
            "    window.WVJavaScriptBridge = {\n" +
            "        callHandler: callHandler,\n" +
            "        fetchMessage: fetchMessage,\n" +
            "        receiveResponse:receiveResponse,\n" +
            "    };\n" +
            "        \n" +
            "    var JSBRIDGE_SCHEME = 'kzjsbridge';\n" +
            "    var JSBRIDGE_CALL_HOST = '__kz_jsbridge_action';\n" +
            "    var JS_BRIDGE_CALL_ACTION = JSBRIDGE_SCHEME + '://' + JSBRIDGE_CALL_HOST;\n" +
            "    var uniqueId = 1;\n" +
            "    var jsbIFrame;\n" +
            "    var responseCallbacks = {};\n" +
            "    var callbackIds = {};\n" +
            "    var sendMessageQueue = [];\n" +
            "    \n" +
            "      \n" +
            "    function callHandler(handlerName, data, responseCallback) {\n" +
            "        if (arguments.length == 2 && typeof data == 'function') {\n" +
            "            responseCallback = data;\n" +
            "            data = null;\n" +
            "        }\n" +
            "        \n" +
            "        _doCall({handler: handlerName, param:data}, responseCallback);\n" +
            "    }\n" +
            "    \n" +
            "    function _doCall(message, responseCallback) {\n" +
            "        if (responseCallback) {\n" +
            "            var callbackId = 'cb_' + (uniqueId++) + '_' + new Date().getTime();\n" +
            "            responseCallbacks[callbackId] = responseCallback;\n" +
            "            message['callbackId'] = callbackId;\n" +
            "        }\n" +
            "        sendMessageQueue.push(message);\n" +
            "        jsbIFrame.src = JS_BRIDGE_CALL_ACTION;\n" +
            "    }\n" +
            "        \n" +
            "    function fetchMessage() {\n" +
            "        message = JSON.stringify(sendMessageQueue);\n" +
            "        sendMessageQueue = [];\n" +
            "        return message;\n" +
            "    }\n" +
            "    \n" +
            "    function receiveResponse(result) {\n" +
            "        var message = result;//JSON.parse(result);\n" +
            "        var callbackId = message.callbackId;\n" +
            "        \n" +
            "        if (callbackId) {\n" +
            "            var responseCallback = responseCallbacks[callbackId];\n" +
            "            if (responseCallback) {\n" +
            "                responseCallback(message.data);\n" +
            "            }\n" +
            "            delete responseCallbacks[callbackId];\n" +
            "        }\n" +
            "    }\n" +
            "    \n" +
            "    jsbIFrame = document.createElement('iFrame');\n" +
            "    jsbIFrame.style.display = 'none';\n" +
            "    jsbIFrame.src = JS_BRIDGE_CALL_ACTION;\n" +
            "    document.documentElement.appendChild(jsbIFrame);\n" +
            "        \n" +
            "})();";

    /**
     * get javascript bridge load code
     * @return
     */
    public static String jsCmdBridgeLoad() {
        return js_load_bridge;
    }

    /**
     * javascript code: fetchMessage
     * @return
     */
    public static String jsCmdfetchMessage() {
        return "javascript:WVJavaScriptBridge.fetchMessage();";
    }

    /**
     * javascript code: receiveResponse
     * @return
     */
    public static String jsCmdReceiveResponse() {

        return "javascript:WVJavaScriptBridge.receiveResponse(%s);";
    }

}
