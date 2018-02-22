/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadl
 */
public class TokenManager implements Runnable {

    static TokenManager tokenManager = null;
    static Random random = null;
    Long token = random.nextLong();
    Map<Long, Token> map = new HashMap<>();

    
    public TokenManager() {
        random = new Random(System.currentTimeMillis());
    }

    synchronized public void register(int userId) {
        Token token = new Token(userId);
        map.put(token.getToken(), token);
    }

    synchronized public int getUserId(long tokenKey) {
        Token token = map.get(tokenKey);
        if (token != null) {
            return token.getIdUser();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void run() {
        boolean done = false;

        while (!done) {
            Long currentTime = System.currentTimeMillis();
            List<Long> removeList = new ArrayList<>();

            synchronized (this) {
                for (Long tokenId : map.keySet()) {
                    Token token = map.get(tokenId);
                    if (token.isExpired()) {
                        removeList.add(tokenId);
                    }
                }

                for (Long tokenId : removeList) {
                    map.remove(tokenId);
                }
            }
            try {
                Thread.sleep(5* 60 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TokenManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
