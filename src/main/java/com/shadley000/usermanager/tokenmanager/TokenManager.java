package com.shadley000.usermanager.tokenmanager;

import com.shadley000.usermanager.autogen.controller.AppUserJpaController;
import com.shadley000.usermanager.autogen.entities.AppUser;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

public class TokenManager implements Runnable {

    public final static long EXPIRE_TIME = 30 * 60 * 1000;
    public final static long DEATH_TIME = 4 * 60 * 60 * 1000;
    public final static long SLEEP_TIME = 1000 * 60 * 60 * 5;

    protected static TokenManager tokenManager = null;
    static Random random = null;
    protected static boolean done = false;

    protected Map<Long, Token> tokenIdToToken = null;
    protected Map<Long, Token> userIdToToken = null;

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private AppUserJpaController getUserJpaController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new AppUserJpaController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

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

        List<AppUser> matchingAppUsers = getUserJpaController().findAppUseIDByLoginPassword(login, password);
        if (matchingAppUsers != null) {
            if (matchingAppUsers.size() == 0) {
                return null;
            } else if (matchingAppUsers.size() == 1) {
                return new Long(matchingAppUsers.get(0).getId());
            } else if (matchingAppUsers.size() > 1) {
                System.out.println("Error: multiple logins for " + login);
                return null;
            }
        }
        System.out.println("Error: no resultset for TokenManager.loadUserId(" + login + ")");
        return null;
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
