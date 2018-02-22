package com.shadley000.usermanager;

public class Token {
    int idUser;
    Long token;
    long creationTime;
    long lastTouchTime;
    long maxIdle;
    long maxAge;
    
    public Token()
    {
        
    }

    public Token(int idUser) {
        this.idUser = idUser;
        this.token = 0L + (long) (Math.random() * (Long.MAX_VALUE - 0L));
        this.creationTime = System.currentTimeMillis();
        this.lastTouchTime = System.currentTimeMillis();
        this.maxIdle = 1000*60*30;
        this.maxAge = 1000*60*60*4;
    }
    
    public int getIdUser() {
        return idUser;
    }
   
    public long getToken() {
        return token;
    }

    public void setMaxIdle(long maxIdle)    {
        this.maxIdle = maxIdle;
    }

    public void touch()    {
        this.lastTouchTime = System.currentTimeMillis();
    }
    
    public long age()    {
        return System.currentTimeMillis() - creationTime;
    }
    
    public long idleTime()    {
        return System.currentTimeMillis() - lastTouchTime;
    }
    
    public boolean isExpired()    {
        return (idleTime() > maxIdle || age() > maxAge);
    }
}
