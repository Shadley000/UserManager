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
public class AppRoleBean {
    private int id;
    private int idApplication;
    private String roleName;
    private String description;
    private List<AppPermissionBean>  appPermissionBeans = new ArrayList<>();;

    public AppRoleBean() {
    }

    public AppRoleBean(int id, int idApplication, String roleName, String description) {
        this.id = id;
        this.idApplication = idApplication;
        this.roleName = roleName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(int idApplication) {
        this.idApplication = idApplication;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AppPermissionBean> getAppPermissionBeans() {
        return appPermissionBeans;
    }

    public void setAppPermissionBeans(List<AppPermissionBean> appPermissionBeans) {
        this.appPermissionBeans = appPermissionBeans;
    }
    
}
