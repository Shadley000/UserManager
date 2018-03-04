/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager;

/**
 *
 * @author shadl
 */
public interface ConfigurationProperties {
    public final static String DB_DRIVER = "com.mysql.jdbc.Driver";
     //public final static String DB_URL = "jdbc:mysql://localhost:3306/a6alarms";
    public final static String DB_URL = "jdbc:mysql://a4alarms.ccbaz5k8ib32.us-east-2.rds.amazonaws.com:3306/usermanager";
    public final static String DB_USER = "user"; 
    public final static String DB_PASSWORD = "0verl00k";
    public final static String TOKENMANAGER_URL = "";
}
