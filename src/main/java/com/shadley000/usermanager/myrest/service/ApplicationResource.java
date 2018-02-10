
package com.shadley000.usermanager.myrest.service;

import com.shadley000.usermanager.myrest.beans.AppPermissionBean;
import com.shadley000.usermanager.myrest.beans.AppRoleBean;
import com.shadley000.usermanager.myrest.beans.ApplicationBean;
import com.shadley000.usermanager.autogen.entities.Application;
import com.shadley000.usermanager.autogen.controller.AppPermissionJpaController;
import com.shadley000.usermanager.autogen.controller.AppRoleJpaController;
import com.shadley000.usermanager.autogen.controller.ApplicationJpaController;
import com.shadley000.usermanager.autogen.controller.exceptions.RollbackFailureException;
import com.shadley000.usermanager.autogen.entities.AppPermission;
import com.shadley000.usermanager.autogen.entities.AppRole;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author shadl
 */
@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private ApplicationJpaController getJpaController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new ApplicationJpaController(utx, getEntityManagerFactory());
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

    private ApplicationBean createApplicationBean(Application applicationEntity) {
        ApplicationBean applicationBean = new ApplicationBean();
        applicationBean.setId(applicationEntity.getId());
        applicationBean.setApplicationName(applicationEntity.getApplicationName());
        applicationBean.setDescription(applicationEntity.getDescription());

        Collection<AppRole> appRoles = applicationEntity.getAppRoleCollection();
        for (AppRole appRole : appRoles) {
            applicationBean.getRoleBeans().add(createAppRoleBean(appRole));
        }
        return applicationBean;
    }

    private AppRoleBean createAppRoleBean(AppRole appRole) {
        AppRoleBean appRoleBean = new AppRoleBean();
        appRoleBean.setId(appRole.getId());
        appRoleBean.setIdApplication(appRole.getIdApplication().getId());
        appRoleBean.setRoleName(appRole.getRoleName());
        appRoleBean.setDescription(appRole.getDescription());

        Collection<AppRoleToPermission> appRoleToPermissions = appRole.getAppRoleToPermissionCollection();
        for (AppRoleToPermission appRoleToPermission : appRoleToPermissions) {
            AppPermission appPermission = appRoleToPermission.getIdAppPermission();
            appRoleBean.getAppPermissionBeans().add(createAppPermissionBean(appPermission));
        }
        return appRoleBean;
    }

    private AppPermissionBean createAppPermissionBean(AppPermission appPermission) {
        AppPermissionBean permissionBean = new AppPermissionBean();
        permissionBean.setId(appPermission.getId());

        permissionBean.setPermissionName(appPermission.getPermissionName());
        permissionBean.setDescription(appPermission.getDescription());
        return permissionBean;
    }

    public ApplicationResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationBean> get() {
        List<Application> applications = getJpaController().findApplicationEntities();
        List<ApplicationBean> applicationBeans = new ArrayList<>();
        for (Application applicationEntity : applications) {
            applicationBeans.add(createApplicationBean(applicationEntity));
        }
        return applicationBeans;
    }

    @GET
    @Path("/{id}")
    public ApplicationBean getById(@PathParam("id") int id) {
        return createApplicationBean(getJpaController().findApplication(id));
    }

    @GET
    @Path("/{id}/roles")
    public List<AppRoleBean> getRoles(@PathParam("id") int id) {
        Application applicationEntity = getJpaController().findApplication(id);
        ApplicationBean applicationBean = createApplicationBean(applicationEntity);
        return applicationBean.getRoleBeans();
    }

    @GET
    @Path("/{id}/roles/{roleid}")
    public AppRoleBean getRole(@PathParam("id") int id, @PathParam("roleid") int roleId) {
        AppRole appRole = getAppRoleJpaController().findAppRole(roleId);
        return createAppRoleBean(appRole);
    }

    @GET
    @Path("/{id}/roles/{roleid}/permissions")
    public List<AppPermissionBean> getRolePermissions(@PathParam("id") int id, @PathParam("roleid") int roleId) {
        AppRole appRole = getAppRoleJpaController().findAppRole(roleId);
        return createAppRoleBean(appRole).getAppPermissionBeans();
    }

    @GET
    @Path("/{id}/roles/{roleid}/permissions/{idpermissions}")
    public AppPermissionBean getRolePermissions(@PathParam("id") int id, @PathParam("roleid") int roleId, @PathParam("idpermissions") int permissionsId) {
        AppPermission appPermission = getAppPermissionJpaController().findAppPermission(permissionsId);
        return createAppPermissionBean(appPermission);
    }

    @POST
    public void add(ApplicationBean b) {
        Application application = new Application();
        application.setApplicationName(b.getApplicationName());
        application.setDescription(b.getDescription());
        try {
            getJpaController().create(application);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") int id, ApplicationBean b) {
        Application application = new Application();
        application.setApplicationName(b.getApplicationName());
        application.setDescription(b.getDescription());
        application.setId(id);
        try {
            getJpaController().edit(application);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") int id) {
        try {
            getJpaController().destroy(id);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
