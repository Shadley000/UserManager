/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.controllers;

import com.shadley000.userManagerClient.beans.Application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shadl
 */
public class ApplicationController {

    
     public static final String SQL_GET_APPLICATIONS = "SELECT a.application_id, a.nname, a.description FROM application a ";
   
     public static final String SQL_GET_APPLICATIONS_BY_NAME = SQL_GET_APPLICATIONS + " WHERE nname = ? ";
     
     public static final String SQL_GET_APPLICATIONS_BY_ID = SQL_GET_APPLICATIONS + " WHERE application_id = ? ";
     
    public static final String SQL_GET_APPLICATIONS_BY_USER_ID = SQL_GET_APPLICATIONS + ", users u, role r,  role_to_users j"
            + "WHERE a.application_id = r.application_id\n"
            + "        and a.application_id = j.application_id       \n"
            + "        and u.users_id = j.users_id\n"
            + "        and r.role_id = j.role_id"
            + "        and u.users_id = ? ";
    
    Connection connection;
    
    public ApplicationController(Connection connection)
    {
        this.connection = connection;
    }
    
    private List<Application> getApplications(PreparedStatement stmt) throws SQLException {
        List<Application> list = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(new Application(rs));
        }
        return list;
    }
    
    public List<Application> getApplications() throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_APPLICATIONS);
        return getApplications(stmt);
    }
    public Application getApplication(String name)  throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_APPLICATIONS_BY_NAME);
        stmt.setString(1, name);
        return getApplications(stmt).get(0);
    }

    public Application getApplication(long applicationId)  throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_APPLICATIONS_BY_ID);
        stmt.setLong(1, applicationId);
        return getApplications(stmt).get(0);
    }
    
    public List<Application> getApplicationsByUser(long userId)  throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(SQL_GET_APPLICATIONS_BY_USER_ID);
        stmt.setLong(1, userId);
        return getApplications(stmt);
   }

}
