package com.jianlc.kzjsbridge.dproxy;

public class UserInfo implements IProxyInterface {

    @Override
    public String getUid(String user) {
        return "userid-" + user;
    }
}
