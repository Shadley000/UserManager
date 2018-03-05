package com.shadley000.usermanager.service;

import com.shadley000.usermanager.tokenmanager.Token;
import com.shadley000.usermanager.tokenmanager.TokenManager;
import java.sql.SQLException;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

@Path("/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenResource {

    protected Logger logger() {
        return Logger.getLogger(TokenResource.class.getName());
    }
    TokenManager tokenManager;

    public TokenResource() {
        tokenManager = TokenManager.getTokenManager();
    }

    private Response okResponse(Object obj) {
        return Response.ok(obj, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }

    private Response notFoundResponse() {
        return Response.status(Status.NOT_FOUND)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }

    @GET
    @Path("/health")
    public Response getHealth() {
        logger().log(Level.INFO, "getHealth ");
        tokenManager.clean();
        return Response.ok(tokenManager.getUsersCount()).build();
    }

    @GET
    @Path("/users/{login}")
    public Response getToken(@PathParam("login") String login,
            @QueryParam("password") String password,
            @Context HttpServletRequest requestContext,
            @Context SecurityContext context) throws SQLException {
        Token token = tokenManager.getToken(requestContext.getRemoteAddr(), login, password);
        if (token != null) {
            logger().log(Level.INFO, "successful login from " + login);
            return okResponse(token);
        } else {
            logger().log(Level.INFO, "failed login from " + login);

            return notFoundResponse();
        }
    }

    @GET
    @Path("/userIDs/{token}")
    public Response getUserId(@PathParam("token") String tokenStr) {
        Token token = tokenManager.getToken(tokenStr);
        if (token != null) {
            logger().log(Level.INFO, "successful userID retrieval " + token);
            return okResponse(token);
        } else {
            logger().log(Level.INFO, "failed userID retrieval " + token);
            return notFoundResponse();
        }
    }

}
