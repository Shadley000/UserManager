/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.myrest.service;

import com.shadley000.usermanager.autogen.entities.AppRole;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
import com.shadley000.usermanager.autogen.entities.AppUser;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorizationResource {

    protected Logger logger() {
        return Logger.getLogger(UsersResource.class.getName());
    }

    

    @GET
    @Path("/token")
    public Response getToken(@PathParam("login") String login,
            @QueryParam("password") String password,
            @Context HttpServletRequest requestContext) {
        
      

        return Response.ok(token, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }

    @GET
    @Path("/{login}")
    public Response getPermissionsByLogin(@PathParam("login") String login,
            @QueryParam("password") String password,
            @Context HttpServletRequest requestContext) {

        logger().log(Level.INFO, "getRolePermissions " + login + " " + password);
        Set<Integer> permissionSet = new HashSet<>();

        //search for matching login password
        List<AppUser> appUserEntityList = getUserJpaController().findAppUseIDByLogin(login);

        if (appUserEntityList.size() > 0) {
            AppUser appUserEntity = appUserEntityList.get(0);
            if (appUserEntity.getUserPassword().equals(password)) {
                Collection<AppRoleToUsers> appRoleToUsers = appUserEntity.getAppRoleToUsersCollection();
                for (AppRoleToUsers appRoleToUser : appRoleToUsers) {
                    AppRole appRole = appRoleToUser.getIdAppRole();
                    for (AppRoleToPermission appRoleToPermission : appRole.getAppRoleToPermissionCollection()) {
                        permissionSet.add(appRoleToPermission.getId());
                    }
                }
            } else {
                logger().log(Level.WARNING, "Failed password from " + requestContext.getRemoteAddr());
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            logger().log(Level.WARNING, "Failed user from " + requestContext.getRemoteAddr());
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(permissionSet, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();

    }
}
