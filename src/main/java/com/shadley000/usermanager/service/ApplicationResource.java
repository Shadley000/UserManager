package com.shadley000.usermanager.service;

import com.shadley000.userManagerClient.beans.Application;
import com.shadley000.userManagerClient.beans.Role;
import com.shadley000.usermanager.controllers.ApplicationController;
import com.shadley000.usermanager.controllers.RoleController;
import com.shadley000.usermanager.SQLConnectionFactory;
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

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationResource extends AbstractResource {

    //list all applications
    @GET
    public Response getApplications(@QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "ApplicationResource.getApplications ");
        securityCheck(tokenStr, ROLE_USER);
        return okResponse(applicationController.getApplications());

    }

    //specific application
    @GET
    @Path("/{applicationid}")
    public Response getApplicationById(@PathParam("applicationid") String applicationId,
            @QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "ApplicationResource.getApplicationById " + applicationId);
        securityCheck(tokenStr, ROLE_USER);

        Application application = applicationController.getApplication(applicationId);
        if (application != null) {
            return okResponse(application);
        } else {
            application = applicationController.getApplication(applicationId);
            if (application != null) {
                return okResponse(application);
            } else {
                return noContentResponse();
            }
        }
    }

//get all of an applications roles
    @GET
    @Path("/{applicationid}/roles")
    public Response getApplicationRoles(@PathParam("applicationid") long applicationId,
            @QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "ApplicationResource.getApplicationRoles " + applicationId);
        securityCheck(tokenStr, ROLE_USER);
        return okResponse(roleController.getRolesByApplication(applicationId));
    }

//get all of an applications roles
    @GET
    @Path("/{applicationid}/users")
    public Response getApplicationUsers(@PathParam("applicationid") long applicationId,
            @QueryParam("token") String tokenStr) throws SQLException {
        logger().log(Level.INFO, "ApplicationResource.getApplicationUsers " + applicationId);
        securityCheck(tokenStr, ROLE_USER);
        return okResponse(usersController.getUsersByApplication(applicationId));
    }
}
