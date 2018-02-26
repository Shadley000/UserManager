/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.service;

import com.shadley000.userManagerClient.beans.Application;
import com.shadley000.userManagerClient.beans.Role;
import com.shadley000.userManagerClient.beans.Users;
import com.shadley000.usermanager.controllers.ApplicationController;
import com.shadley000.usermanager.controllers.RoleController;
import com.shadley000.usermanager.controllers.SQLConnectionFactory;
import com.shadley000.usermanager.controllers.UsersController;
import com.shadley000.usermanager.tokenmanager.TokenManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author shadl
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    UsersController usersController;
    ApplicationController applicationController;
    RoleController roleController;
    TokenManager tokenManager;
    Application myApplication;

    public static final String ROLE_USER = "User";
    public static final String ROLE_SUPERUSER = "Super User";
    public static final String ROLE_ADMINISTRATOR = "Administrator";

    protected Logger logger() {
        return Logger.getLogger(UsersResource.class.getName());
    }

    public static final String APPLICATION_NAME = "User Manager";

    protected boolean securityCheck(long token, String roleName) throws SecurityException {
        Long userID = tokenManager.getUserId(token);
        List<Role> roleList = null;

        try {
            roleController.getRolesByApplicationAndUser(myApplication.getApplicationId(), userID);
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
        throw new SecurityException();       
    }
    

    public UsersResource() {

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

    //list of all users
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("token") int token) {

        securityCheck(token, ROLE_USER);

        try {
            List<Users> usersList = usersController.getUsers();

            return Response.ok(usersList, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                    .build();
        } catch (Exception ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }

    //specific user
    @GET
    @Path("/{userid}")
    public Response getById(@PathParam("userid") int userId,
            @PathParam("token") int token) {
        logger().log(Level.INFO, "getById {0}", userId);
        securityCheck(token, ROLE_USER);
        try {
            Users users = usersController.getUsers(userId);
            if (users != null) {

                return Response.ok(users, MediaType.APPLICATION_JSON)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                        .build();
            } else {
                return Response.noContent().header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                        .build();
            }
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }

    //all applications for a given user
    @GET
    @Path("/{userid}/applications")
    public Response getApplications(@PathParam("userid") int userId,
            @PathParam("token") int token) {
        logger().log(Level.INFO, "getRoles {0}", userId);
        securityCheck(token, ROLE_USER);
        try {
            return Response.ok(applicationController.getApplicationsByUser(userId), MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                    .build();
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }

    //get application for a given user in an application
    @GET
    @Path("/{userid}/applications/{applicationid}")
    public Response getApplication(@PathParam("userid") int userId,
            @PathParam("applicationid") int applicationId,
            @PathParam("token") int token) {
        logger().log(Level.INFO, "getRoles {0}", userId);

        securityCheck(token, ROLE_USER);
        try {
            return Response.ok(applicationController.getApplication(applicationId), MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                    .build();
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }

    //all roles for a given user in an application
    @GET
    @Path("/{userid}/applications/{applicationid}/roles")
    public Response getApplicationRoles(@PathParam("userid") int userId,
            @PathParam("applicationid") int applicationId,
            @PathParam("token") int token) {
        logger().log(Level.INFO, "getRoles {0}", userId);

        securityCheck(token, ROLE_USER);

        try {
            return Response.ok(roleController.getRolesByApplicationAndUser(applicationId, userId), MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                    .build();

        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }
}
