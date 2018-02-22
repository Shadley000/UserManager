package com.shadley000.usermanager.myrest.service;

import com.shadley000.usermanager.tokenmanager.Token;
import com.shadley000.usermanager.tokenmanager.TokenManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PublicAuthentication {

    @GET
    @Path("/user/{login}")
    public Response getToken(@PathParam("login") String login, @QueryParam("password") String password)
    {
        TokenManager tokenManager = TokenManager.getTokenManager();

        Token token = tokenManager.getToken(login, password);
        if(token!=null){
            return Response.ok().build();
        }
        else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/application/{application}/{token}")
    public Response getPermissions(@PathParam("id") int id, @PathParam("roleid") int roleId)
    {

        return Response.ok().build();
    }
}
