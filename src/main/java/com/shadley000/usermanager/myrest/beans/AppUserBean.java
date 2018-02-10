/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.myrest.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shadl
 */
@XmlRootElement
public class AppUserBean {
    private int id;
    private String login;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String email;
    
    private List<AppRoleBean>  appRoleBeans = new ArrayList<>();;


    public AppUserBean() {
    }

    public AppUserBean(int id, String login, String userPassword, String firstName, String lastName, String email) {
        this.id = id;
        this.login = login;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public List<AppRoleBean> getRoleBeans() {
        return appRoleBeans;
    }

    public void setRoleBeans(List<AppRoleBean> roleBeans) {
        this.appRoleBeans = roleBeans;
    }
    
    
}
