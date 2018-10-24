package com.jianlc.kzjsbridge.dproxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DProxyHandler implements InvocationHandler {

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.d("DProxyHandler", "--->obj: " + o.getClass().getName() + "  method: " + method.getName());
        return null;
    }
}
