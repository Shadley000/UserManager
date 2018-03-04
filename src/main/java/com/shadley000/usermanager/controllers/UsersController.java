/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.controllers;

import com.shadley000.userManagerClient.beans.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersController {

    public static final String SQL_GET_USERS = "SELECT u.users_id, u.login, u.user_password, u.first_name, u.last_name, u.email, u.confirmed FROM users u ";
    public static final String SQL_GET_USERS_BY_APPLICATION = SQL_GET_USERS + ", application a, role r,  role_to_users j "
            + " WHERE a.application_id = r.application_id\n"
            + "        and a.application_id = j.application_id       \n"
            + "        and a.application_id = ?\n"
            + "        and u.users_id = j.users_id\n"
            + "        and r.role_id = j.role_id";
    public static final String SQL_GET_USERS_BY_ROLE = SQL_GET_USERS_BY_APPLICATION + " and r.role_id = ? ";
    public static final String SQL_GET_USERS_BY_LOGIN_PASSWORD = SQL_GET_USERS + " WHERE login = ? and user_password = ? ";
    public static final String SQL_GET_USERS_BY_ID = SQL_GET_USERS + " WHERE users_id = ? ";

    public static final String SQL_LOG_LOGIN_ATTEMPT = "INSERT INTO login_record (ip_address, login, user_password, login_date, success) values (?,?,?,now(),?)";

    Connection connection;

    public UsersController(Connection connection) {
        this.connection = connection;
    }

    public List<Users> getUsers(PreparedStatement stmt) throws SQLException {
        List<Users> list = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(new Users(rs));
        }
        return list;
    }

    public List<Users> getUsers() throws SQLException {
        return getUsers(connection.prepareStatement(SQL_GET_USERS));
    }

    public List<Users> getUsersByApplication(long applicationId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_USERS_BY_APPLICATION);
        stmt.setLong(1, applicationId);
        return getUsers(stmt);
    }

    public List<Users> getUsersByRole(long applicationId, long roleId) throws SQLException {
        List<Users> list = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_USERS_BY_ROLE);
        stmt.setLong(1, applicationId);
        stmt.setLong(2, roleId);
        return getUsers(stmt);
    }

    public Users getUsers(String ipAddress, String login, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_USERS_BY_LOGIN_PASSWORD);
        stmt.setString(1, login);
        stmt.setString(2, password);
        Users users = getUsers(stmt).get(0);
        recordLoginAttempt(ipAddress, login, password, users != null);
        return users;
    }

    public Users getUsers(long userId) throws SQLException {
        Users user = null;
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_USERS_BY_ID);
        stmt.setLong(1, userId);
        List<Users> usersList = getUsers(stmt);
        if (usersList.size() == 1) {
            return usersList.get(0);
        } else {
            return null;
        }
    }

    public void recordLoginAttempt(String ipAddress, String login, String password, boolean success) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_LOG_LOGIN_ATTEMPT);
        stmt.setString(1, ipAddress);
        stmt.setString(2, login);
        stmt.setString(3, password);
        stmt.setBoolean(4, success);

        stmt.execute();
        Logger.getLogger(UsersController.class.getName()).log(Level.INFO, "login attempt " + login + " from " + ipAddress + " success=" + success);

    }

    public Object getUsersRoles(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getUsersRoles(long userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
