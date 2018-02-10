/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.myrest.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shadl
 */
@XmlRootElement
public class AppPermissionBean {
    private int id;
    private String permissionName;
    private String description;

    public AppPermissionBean() {
    }

    public AppPermissionBean(int id, String permissionName, String description) {
        this.id = id;
        this.permissionName = permissionName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
