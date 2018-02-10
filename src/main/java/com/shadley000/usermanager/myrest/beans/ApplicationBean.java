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
public class ApplicationBean {
    private int id;
    private String applicationName;
    private String description;
    private List<AppRoleBean> appRoleBeans = new ArrayList<>();
    
    public ApplicationBean() {
    }

    public ApplicationBean(int id, String applicationName, String description) {
        this.id = id;
        this.applicationName = applicationName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AppRoleBean> getRoleBeans() {
        return appRoleBeans;
    }

    public void setRoleBeans(List<AppRoleBean> roleBeans) {
        this.appRoleBeans = roleBeans;
    }
    
    
}
