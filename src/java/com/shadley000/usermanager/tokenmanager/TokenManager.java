package com.shadley000.usermanager.tokenmanager;

import com.shadley000.userManagerClient.beans.Users;
import com.shadley000.usermanager.SQLConnectionFactory;
import com.shadley000.usermanager.controllers.UsersController;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenManager {

    public final static long EXPIRE_TIME = 30 * 60 * 1000;
    public final static long DEATH_TIME = 4 * 60 * 60 * 1000;
    public final static long SLEEP_TIME = 1000 * 60 * 60 * 5;

    protected static TokenManager tokenManager = null;
    protected static Random random = null;

    protected Map<String, Token> tokenIdToToken = null;
    protected Map<String, Token> userIdToToken = null;

    UsersController userController;

    public static TokenManager getTokenManager() {
        if (tokenManager == null) {
            tokenManager = new TokenManager();
        }
        return tokenManager;
    }

    protected TokenManager() {
        tokenIdToToken = new HashMap<>();
        userIdToToken = new HashMap<>();
        random = new Random(System.currentTimeMillis());

        Connection connection = null;
        try {
            connection = SQLConnectionFactory.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TokenManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        userController = new UsersController(connection);
    }

    synchronized public Token getToken(String ipAddress, String login, String password) throws SQLException {
        Users users = userController.getUsers(ipAddress, login, password);
        if (users != null) {
            Token token = userIdToToken.get(""+users.getUsersId());
            if (token != null) {//user is already registered            
                token.rebirth();
            } else {
                token = new Token(ipAddress,""+users.getUsersId());
                tokenIdToToken.put(token.getToken(), token);
                userIdToToken.put(token.getUserId(), token);
                Logger.getLogger(TokenManager.class.getName()).log(Level.INFO, "Token created " + token.getToken());
            }
            return token;
        }
        Logger.getLogger(TokenManager.class.getName()).log(Level.INFO, "User not found " + login);
        return null;
    }

    synchronized public Token getToken(String tokenId) {
        Token token = tokenIdToToken.get(tokenId);
        if (token == null) {
            Logger.getLogger(TokenManager.class.getName()).log(Level.INFO, "Token not found " + tokenId);
            return null;
        }
        if (token.isExpired()) {
            Logger.getLogger(TokenManager.class.getName()).log(Level.INFO, "Token expired " + tokenId);
            clean();
            return null;
        }
        token.touch();
        return token;
    }

    synchronized public Long getTimeLeft(String tokenId) {
        Token token = tokenIdToToken.get(tokenId);
        if (token == null || token.isExpired()) {
            return null;
        }
        return token.getTimeLeft();
    }

    synchronized public void clean() {
        List<String> removeList = new ArrayList<>();
        for (String tokenId : tokenIdToToken.keySet()) {
            Token token = tokenIdToToken.get(tokenId);
            if (token.isExpired()) {
                removeList.add(tokenId);
            }
        }
        for (String tokenId : removeList) {
            Token token = tokenIdToToken.get(tokenId);
            if (token != null) {
                tokenIdToToken.remove(tokenId);
                userIdToToken.remove(token.getUserId());
            }
        }
    }

    public Object getUsersCount() {
        return tokenIdToToken.size();
    }
}
