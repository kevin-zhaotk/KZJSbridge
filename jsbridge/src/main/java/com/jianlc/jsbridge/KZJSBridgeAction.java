package com.jianlc.jsbridge;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class KZJSBridgeAction {

    private static final String TAG = KZJSBridgeAction.class.getSimpleName();

    private Class mProxy;
    private boolean haveParameter;

    public KZJSBridgeAction(Class clazz) {
        mProxy = clazz;
    }

    public Object action(String method, String parameters) {

        Method mtd = null;

        if (method == null) {
            return null;
        }

        if (parameters == null) {
            mtd = getMethod(method);
            if (mtd == null) {
                mtd = getMethod(method, null);
            }
        } else {
            mtd = getMethod(method, parameters);
            if (mtd == null) {
                mtd = getMethod(method);
            }
        }

        if (mtd == null) {
            return null;
        }
        try {
            Object object = mProxy.newInstance();
            mtd.setAccessible(true);
            if (haveParameter) {
                return mtd.invoke(object, parameters);
            } else {
                return mtd.invoke(object);
            }
        } catch (InstantiationException e) {
            Log.i(TAG, "--->InstantiationException e: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.i(TAG, "--->IllegalAccessException e: " + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.i(TAG, "--->InvocationTargetException e: " + e.getMessage());
        }

        return null;
    }

    private Method getMethod(String method) {
        try {
            Method mtd = mProxy.getMethod(method);
            haveParameter = false;
            return mtd;
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "--->getMethod: " + e.getMessage());
        }

        return null;
    }

    private Method getMethod(String method, String parameter) {
        try {
            Method mtd = mProxy.getMethod(method, String.class);
            haveParameter = true;
            return mtd;
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "--->getMethod 2: " + e.getMessage());
        }
        return null;
    }
}
