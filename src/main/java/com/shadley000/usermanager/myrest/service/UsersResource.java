/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.myrest.service;

import com.shadley000.usermanager.myrest.beans.AppPermissionBean;
import com.shadley000.usermanager.myrest.beans.AppRoleBean;
import com.shadley000.usermanager.myrest.beans.AppUserBean;
import com.shadley000.usermanager.autogen.controller.AppPermissionJpaController;
import com.shadley000.usermanager.autogen.controller.AppRoleJpaController;
import com.shadley000.usermanager.autogen.controller.AppUserJpaController;
import com.shadley000.usermanager.autogen.entities.AppPermission;
import com.shadley000.usermanager.autogen.entities.AppRole;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
import com.shadley000.usermanager.autogen.entities.AppUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author shadl
 */

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {
    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private AppUserJpaController getJpaController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new AppUserJpaController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
       
    private AppRoleJpaController getAppRoleJpaController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new AppRoleJpaController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private AppPermissionJpaController getAppPermissionJpaController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new AppPermissionJpaController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private AppUserBean createAppUserBean(AppUser appUserEntity) {
        AppUserBean appUserBean = new AppUserBean();
        appUserBean.setId(appUserEntity.getId());
        appUserBean.setLogin(appUserEntity.getLogin());
        appUserBean.setFirstName(appUserEntity.getFirstName());
        
        appUserBean.setLastName(appUserEntity.getLastName());        
        appUserBean.setEmail(appUserEntity.getEmail());

        Collection<AppRoleToUsers> appRoleToUsers = appUserEntity.getAppRoleToUsersCollection();
        for (AppRoleToUsers appRoleToUser : appRoleToUsers) {
            AppRole appRole = appRoleToUser.getIdAppRole();
            appUserBean.getRoleBeans().add(createAppRoleBean(appRole));
        }
        return appUserBean;
    }

    private AppRoleBean createAppRoleBean(AppRole appRole) {
        AppRoleBean appRoleBean = new AppRoleBean();
        appRoleBean.setId(appRole.getId());
        appRoleBean.setIdApplication(appRole.getIdApplication().getId());
        appRoleBean.setRoleName(appRole.getRoleName());
        appRoleBean.setDescription(appRole.getDescription());

        Collection<AppRoleToPermission> appRoleToPermissions = appRole.getAppRoleToPermissionCollection();
        for (AppRoleToPermission appRoleToPermission : appRoleToPermissions) {
            AppPermission appPermission = appRoleToPermission.getIdAppPermission();
            appRoleBean.getAppPermissionBeans().add(createAppPermissionBean(appPermission));
        }
        return appRoleBean;
    }

    private AppPermissionBean createAppPermissionBean(AppPermission appPermission) {
        AppPermissionBean permissionBean = new AppPermissionBean();
        permissionBean.setId(appPermission.getId());

        permissionBean.setPermissionName(appPermission.getPermissionName());
        permissionBean.setDescription(appPermission.getDescription());
        return permissionBean;
    }

    public UsersResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppUserBean> get() {
        List<AppUser> appUsers = getJpaController().findAppUserEntities();
        List<AppUserBean> appUserBeans = new ArrayList<>();
        for (AppUser appUserEntity : appUsers) {
            appUserBeans.add(createAppUserBean(appUserEntity));
        }
        return appUserBeans;
    }

    @GET
    @Path("/{id}")
    public AppUserBean getById(@PathParam("id") int id) {
        return createAppUserBean(getJpaController().findAppUser(id));
    }

    @GET
    @Path("/{id}/roles")
    public List<AppRoleBean> getRoles(@PathParam("id") int id) {
        AppUser appUserEntity = getJpaController().findAppUser(id);
        AppUserBean appUserBean = createAppUserBean(appUserEntity);
        return appUserBean.getRoleBeans();
    }

    @GET
    @Path("/{id}/roles/{roleid}")
    public AppRoleBean getRole(@PathParam("id") int id, @PathParam("roleid") int roleId) {
        AppRole appRole = getAppRoleJpaController().findAppRole(roleId);
        return createAppRoleBean(appRole);
    }

    @GET
    @Path("/{id}/roles/{roleid}/permissions")
    public List<AppPermissionBean> getRolePermissions(@PathParam("id") int id, @PathParam("roleid") int roleId) {
        AppRole appRole = getAppRoleJpaController().findAppRole(roleId);
        return createAppRoleBean(appRole).getAppPermissionBeans();
    }

    @GET
    @Path("/{id}/roles/{roleid}/permissions/{idpermissions}")
    public AppPermissionBean getRolePermissions(@PathParam("id") int id, @PathParam("roleid") int roleId, @PathParam("idpermissions") int permissionsId) {
        AppPermission appPermission = getAppPermissionJpaController().findAppPermission(permissionsId);
        return createAppPermissionBean(appPermission);
    }
    
}