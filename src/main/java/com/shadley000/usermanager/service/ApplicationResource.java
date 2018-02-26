package com.shadley000.usermanager.service;

import com.shadley000.userManagerClient.beans.Application;
import com.shadley000.userManagerClient.beans.Role;
import com.shadley000.usermanager.controllers.ApplicationController;
import com.shadley000.usermanager.controllers.RoleController;
import com.shadley000.usermanager.controllers.SQLConnectionFactory;
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

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    ApplicationController applicationController;
    RoleController roleController;
     Application myApplication;
    TokenManager tokenManager;
    public static final String APPLICATION_NAME = "User Manager";

    protected Logger logger() {
        return Logger.getLogger(ApplicationResource.class.getName());
    }

    protected boolean securityCheck(long token, String roleName) throws SecurityException {
        Long userID = tokenManager.getUserId(token);
        try {
            List<Role> roleList = roleController.getRolesByApplicationAndUser(myApplication.getApplicationId(), userID);

            for (Role role : roleList) {
                if (role.getRole_type_name().equals(roleName)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ApplicationResource() {

        Connection connection = null;
        try {
            connection = SQLConnectionFactory.getConnection();
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);
        }
        applicationController = new ApplicationController(connection);
        tokenManager = TokenManager.getTokenManager();
        roleController = new RoleController(connection);
        
         try {
            myApplication = applicationController.getApplication(APPLICATION_NAME);
        } catch (SQLException ex) {
            Logger.getLogger(UsersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    public Response get(@PathParam("token") int token) {
        logger().log(Level.INFO, "get ");
        Long userID = tokenManager.getUserId(token);
        try {
            return Response.ok(applicationController.getApplications(), MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                    .build();
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{applicationid}")
    public Response getById(@PathParam("applicationid") int applicationId,
            @PathParam("token") int token) {
        logger().log(Level.INFO, "getById " + applicationId);

        Long userID = tokenManager.getUserId(token);
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

    @GET
    @Path("/name/{applicationName}")
    public Response getByName(@PathParam("applicationname") String applicationName,
            @PathParam("token") int token) {
        logger().log(Level.INFO, "getByName " + applicationName);
        Long userID = tokenManager.getUserId(token);
        try {
            return Response.ok(applicationController.getApplication(applicationName), MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                    .build();
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, null, ex);

            return Response.serverError().build();
        }
    }

}
