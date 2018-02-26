/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.controllers;

import com.shadley000.userManagerClient.beans.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleController {

    public static final String SQL_GET_ROLE = "SELECT r.role_id, r.application_id, r.role_type_id, r.role_type_name, r.nname, r.ud1 FROM role r ";
    public static final String SQL_GET_ROLE_BY_ID = SQL_GET_ROLE + " WHERE r.role_id = ?";
    public static final String SQL_GET_ROLE_BY_APPLICATION = SQL_GET_ROLE + " WHERE r.application_id = ?";
    
    public static final String SQL_GET_ROLE_BY_APPLICATION_USER = SQL_GET_ROLE + ", role_to_users j WHERE r.application_id = ? AND j.user_id = ?";
    public static final String SQL_GET_ROLE_BY_USER = SQL_GET_ROLE + ", role_to_users j WHERE j.user_id = ?";

    Connection connection;
    
    public RoleController(Connection connection)
    {
        this.connection =connection;
    }
    private List<Role> getRoles(PreparedStatement stmt) throws SQLException {
        List<Role> list = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(new Role(rs));
        }
        return list;
    }

    public List<Role> getRoles() throws SQLException {
        return getRoles(connection.prepareStatement(SQL_GET_ROLE));
    }

    public Role getRole(long roleId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_ROLE_BY_ID);
        stmt.setLong(0, roleId);
        List<Role> rolesList = getRoles(stmt);
        return rolesList.get(0);
    }

    public List<Role> getRolesByApplication(long applicationId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_ROLE_BY_APPLICATION);
        stmt.setLong(0, applicationId);
        return getRoles(stmt);
    }

    public List<Role> getRolesByApplicationAndUser(long applicationId, long userId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_ROLE_BY_APPLICATION_USER);
        stmt.setLong(0, applicationId);
        stmt.setLong(0, userId);
        return getRoles(stmt);

    }

    public List<Role> getRolesByUser(long userId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_ROLE_BY_USER);
        stmt.setLong(0, userId);
        return getRoles(stmt);
    }

}
