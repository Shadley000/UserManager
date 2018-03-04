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
import com.shadley000.usermanager.SQLConnectionFactory;
import com.shadley000.usermanager.controllers.UsersController;
import static com.shadley000.usermanager.service.ApplicationResource.ROLE_USER;
import com.shadley000.usermanager.tokenmanager.Token;
import com.shadley000.usermanager.tokenmanager.TokenManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author shadl
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource extends AbstractResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("token") String token) throws SQLException {
        logger().log(Level.INFO, "UsersResource.getUsers");
        securityCheck(token, ROLE_USER);
        return okResponse(usersController.getUsers());
    }

    @GET
    @Path("/{userid}")
    public Response getUsersById(@PathParam("userid") long userId,
            @QueryParam("token") String token) throws SQLException {
        logger().log(Level.INFO, "UsersResource.getUsersById {0}", userId);
        securityCheck(token, ROLE_USER);
        Users users = usersController.getUsers(userId);
        if (users != null) {
            return okResponse(users);
        } else {
            return noContentResponse();
        }
    }
    
    @GET
    @Path("/{userid}/applications")
    public Response getApplicationsByUsers(@PathParam("userid") long userId,
            @QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "UsersResource.getApplicationsByUsers {0}", userId);
        securityCheck(tokenStr, ROLE_USER);
        return okResponse(applicationController.getApplicationsByUser(userId));
    }

    @GET
    @Path("/{userid}/roles")
    public Response getUserRoles(@PathParam("userid") long userId,
            @QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "UsersResource.getUserRoles " + userId);
        securityCheck(tokenStr, ROLE_USER);
        return okResponse(usersController.getUsersRoles(userId));
    }
    
    @GET
    @Path("/{userid}/applications/{applicationid}/roles")
    public Response getUserApplicationRoles(@PathParam("userid") long userId,
            @PathParam("applicationid") long applicationId,
            @QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "UsersResource.getApplicationRoles {0}", userId);
        securityCheck(tokenStr, ROLE_USER);
        
        return okResponse(roleController.getRolesByApplicationAndUser(applicationId, userId));
    }
   
}
