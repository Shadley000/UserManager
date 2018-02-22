package com.shadley000.usermanager.tokenmanager;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TokenManager implements Runnable {

    public final static long EXPIRE_TIME = 30 * 60 * 1000;
    public final static long DEATH_TIME = 4 * 60 * 60 * 1000;
    public final static long SLEEP_TIME = 1000 * 60 * 60 * 5;

    protected static TokenManager tokenManager = null;
    static Random random = null;
    protected static boolean done = false;

    protected Map<Long, Token> tokenIdToToken = null;
    protected Map<Long, Token> userIdToToken = null;

    public static void gracefulStop() {
        done = true;
    }

    public static TokenManager getTokenManager() {
        if (tokenManager == null) {
            tokenManager = new TokenManager();
            random = new Random(System.currentTimeMillis());
            //start monitoring thread to find and remove expired tokens
            new Thread(new TokenManager()).start();
        }
        return tokenManager;
    }

    public TokenManager() {
        tokenIdToToken = new HashMap<>();
        userIdToToken = new HashMap<>();
    }

    synchronized public Long getToken(String login, String password) {

        Long userId = loadUserId(login, password);
        //if valid create and register token
        if (userId != null) {
            Token token = userIdToToken.get(userId);
            if (token != null) {//user is already registered            
                token.rebirth();
                return token.getToken();
            } else {
                token = new Token(userId);
                tokenIdToToken.put(token.getToken(), token);
                userIdToToken.put(token.getUserId(), token);
                return token.getToken();
            }
        }
        return null;
    }

    synchronized public Long getUserId(long tokenId) {
        Token token = tokenIdToToken.get(tokenId);
        if (token == null || token.isExpired()) {
            return null;
        }
        token.touch();

        return token.getUserId();

    }

    synchronized public Long getTimeLeft(long tokenId) {
        Token token = tokenIdToToken.get(tokenId);

        if (token == null || token.isExpired()) {
            return null;
        }

        return token.getTimeLeft();
    }

    private Long loadUserId(String login, String password) {   //lookup and validate user from database
        if (login.equals("shadley000") && password.equals("password")) {
            return 9L;
        } else {
            return null;
        }
    }

    @Override
    public void run() {
        while (!done) {
            synchronized (this) {
                List<Long> removeList = new ArrayList<>();
                for (Long tokenId : tokenIdToToken.keySet()) {
                    Token token = tokenIdToToken.get(tokenId);
                    if (token.isExpired()) {
                        removeList.add(tokenId);
                    }
                }

                for (Long tokenId : removeList) {
                    tokenIdToToken.remove(tokenId);
                }
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
