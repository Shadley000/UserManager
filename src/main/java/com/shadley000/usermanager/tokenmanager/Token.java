package com.shadley000.usermanager.tokenmanager;

public class Token {
    long token;
    long userId;
    long createTime;
    long lastTouchTime;


    public Token(long userId){
        this.userId = userId;
        this.token = (long)(Math.random()*Long.MAX_VALUE);
        rebirth();
    }

    public void rebirth()  {
        createTime = System.currentTimeMillis();
        touch();
    }

    public void touch()  {
        lastTouchTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        if((System.currentTimeMillis()-createTime) > TokenManager.DEATH_TIME) return true;
        return (System.currentTimeMillis()-lastTouchTime)>TokenManager.EXPIRE_TIME;
    }

    public long getToken() {return token;}
    public long getUserId() {return userId;}
    public long getTimeLeft() {return (TokenManager.EXPIRE_TIME-System.currentTimeMillis()-lastTouchTime);}
}
