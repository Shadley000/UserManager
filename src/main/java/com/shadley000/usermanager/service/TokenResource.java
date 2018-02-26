package com.shadley000.usermanager.service;

import com.shadley000.usermanager.ErrorMessage;
import com.shadley000.usermanager.tokenmanager.TokenManager;
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
import javax.ws.rs.core.SecurityContext;

@Path("/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenResource {

     protected Logger logger() {
        return Logger.getLogger(TokenResource.class.getName());
    }
     TokenManager tokenManager;
     public TokenResource()
     {
          tokenManager = TokenManager.getTokenManager();
     }
     
    @GET
    @Path("/users/{login}")
    public Response getToken(@PathParam("login") String login, 
            @QueryParam("password") String password, 
            @Context HttpServletRequest requestContext,
            @Context SecurityContext context)
    {
        Long token = tokenManager.getToken(requestContext.getRemoteAddr(), login, password);
        if(token!=null){
            logger().log(Level.INFO, "successful login from "+login);
            return Response.ok(token).build();
        }
        else {
            logger().log(Level.INFO, "failed login from "+login);
            ErrorMessage message = new ErrorMessage("404","code","failed login from "+login,"try another password");
            return Response.noContent().entity("{'message':'No matching login password found'}")
    				.type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/userIDs/{token}")
    public Response getUserId(@PathParam("token") Long token)
    {
        Long userID = tokenManager.getUserId(token);
        
        if(userID !=null){
            logger().log(Level.INFO, "successful userID retrieval "+token);
            return Response.ok(userID).build();
        }else{
            logger().log(Level.INFO, "failed userID retrieval "+token);
            return Response.noContent().build();
        }
    }
    
}
