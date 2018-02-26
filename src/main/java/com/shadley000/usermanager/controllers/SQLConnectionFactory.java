/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.controllers;

import com.shadley000.usermanager.ConfigurationProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadl
 */
public class SQLConnectionFactory {
    
     public static void init()
     {
         try {
            Class.forName(ConfigurationProperties.DB_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Connection getConnection() throws SQLException {

       
        return DriverManager.getConnection(ConfigurationProperties.DB_URL, ConfigurationProperties.DB_USER, ConfigurationProperties.DB_PASSWORD);
    }
}
