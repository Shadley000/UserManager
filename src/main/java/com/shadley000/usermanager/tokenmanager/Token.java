package com.shadley000.usermanager.tokenmanager;

public class Token {

    String token;
    String userId;
    long createTime;
    long lastTouchTime;
    String ipAddress;

    public Token(String ipAddress, String userId) {
        this.ipAddress = ipAddress;
        this.userId = userId;
        this.token = "z"+TokenManager.random.nextLong();
        createTime = System.currentTimeMillis();
        lastTouchTime = System.currentTimeMillis();
    }

    public void rebirth() {
        createTime = System.currentTimeMillis();
        touch();
    }

    public void touch() {
        lastTouchTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        // if((System.currentTimeMillis()-createTime) > TokenManager.DEATH_TIME) return true;
        return getTimeLeft() > 0;
        //return false;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public long getTimeLeft() {
        return (TokenManager.EXPIRE_TIME - System.currentTimeMillis() - lastTouchTime);
    }
}
