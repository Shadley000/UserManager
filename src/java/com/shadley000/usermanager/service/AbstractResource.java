/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.service;

import com.shadley000.userManagerClient.beans.Application;
import com.shadley000.userManagerClient.beans.Role;
import com.shadley000.usermanager.controllers.ApplicationController;
import com.shadley000.usermanager.controllers.RoleController;
import com.shadley000.usermanager.SQLConnectionFactory;
import com.shadley000.usermanager.controllers.UsersController;
import com.shadley000.usermanager.tokenmanager.Token;
import com.shadley000.usermanager.tokenmanager.TokenManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author shadl
 */
public abstract class AbstractResource {
    
    
    UsersController usersController;
    ApplicationController applicationController;
    RoleController roleController;
    TokenManager tokenManager;
    Application myApplication;

    public static final String APPLICATION_NAME = "User Manager";
    
    public static final String ROLE_USER = "User";
    public static final String ROLE_SUPERUSER = "Super User";
    public static final String ROLE_ADMINISTRATOR = "Administrator";

    protected Logger logger() {
        return Logger.getLogger(AbstractResource.class.getName());
    }

    
    protected boolean securityCheck(String tokenStr, String roleName) throws ForbiddenException {
        Token token = tokenManager.getToken(tokenStr);
        List<Role> roleList = null;

        try {
            roleList = roleController.getRolesByApplicationAndUser(myApplication.getApplicationId(), new Long(token.getUserId()));
        } catch (SQLException ex) {
            Logger.getLogger(UsersResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Role role : roleList) {
            if (roleName.equals(ROLE_ADMINISTRATOR)) {
                if (role.getRole_type_name().equals(ROLE_ADMINISTRATOR)) {
                    return true;
                }
            } else if (roleName.equals(ROLE_SUPERUSER)) {
                if (role.getRole_type_name().equals(ROLE_ADMINISTRATOR) || role.getRole_type_name().equals(ROLE_SUPERUSER)) {
                    return true;
                }
            } else if (roleName.equals(ROLE_USER)) {
                if (role.getRole_type_name().equals(ROLE_ADMINISTRATOR)
                        || role.getRole_type_name().equals(ROLE_SUPERUSER)
                        || role.getRole_type_name().equals(ROLE_USER)) {
                    return true;
                }
            }
        }
        throw new ForbiddenException();
    }
    public AbstractResource() {

        Connection connection = null;
        try {
            connection = SQLConnectionFactory.getConnection();
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);
        }
        usersController = new UsersController(connection);
        applicationController = new ApplicationController(connection);
        roleController = new RoleController(connection);
        tokenManager = TokenManager.getTokenManager();

        try {
            myApplication = applicationController.getApplication(APPLICATION_NAME);
        } catch (SQLException ex) {
            Logger.getLogger(UsersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected Response okResponse(Object obj) {
        return Response.ok(obj, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }

    protected Response noContentResponse() {
        return Response.noContent().header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }
    
    @GET
    @Path("/health")
    public Response getHealth() {
        logger().log(Level.INFO, "getHealth ");
        return Response.ok("good").build();
    }
}
