/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.myrest.service;

import com.shadley000.usermanager.autogen.controller.AppPermissionJpaController;
import com.shadley000.usermanager.autogen.controller.AppRoleJpaController;
import com.shadley000.usermanager.autogen.controller.AppUserJpaController;
import com.shadley000.usermanager.autogen.entities.AppRole;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
import com.shadley000.usermanager.autogen.entities.AppUser;
import com.shadley000.usermanager.myrest.beans.AppPermissionBean;
import com.shadley000.usermanager.myrest.beans.AppRoleBean;
import com.shadley000.usermanager.myrest.beans.AppUserBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author shadl
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private AppUserJpaController getUserJpaController() {
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

   
    @GET
    @Path("/{login}")
    public Set<Integer> getPermissionsByLogin(@PathParam("login") int login,
            @QueryParam("password") String from) {

        Set<Integer> permissionSet = new HashSet<>();
        
        int id = 0;//search for matching login password
        
        AppUser appUserEntity = getUserJpaController().findAppUser(id);
   
        Collection<AppRoleToUsers> appRoleToUsers = appUserEntity.getAppRoleToUsersCollection();
        for (AppRoleToUsers appRoleToUser : appRoleToUsers) {
            AppRole appRole = appRoleToUser.getIdAppRole();
            for(AppRoleToPermission appRoleToPermission :appRole.getAppRoleToPermissionCollection())
            {
                permissionSet.add(appRoleToPermission.getId());
            }
        }
        
        return permissionSet;
    }
}
