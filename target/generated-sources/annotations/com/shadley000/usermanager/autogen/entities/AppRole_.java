package com.shadley000.usermanager.autogen.entities;

import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
import com.shadley000.usermanager.autogen.entities.Application;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-15T14:36:11")
@StaticMetamodel(AppRole.class)
public class AppRole_ { 

    public static volatile CollectionAttribute<AppRole, AppRoleToPermission> appRoleToPermissionCollection;
    public static volatile SingularAttribute<AppRole, String> roleName;
    public static volatile SingularAttribute<AppRole, String> description;
    public static volatile CollectionAttribute<AppRole, AppRoleToUsers> appRoleToUsersCollection;
    public static volatile SingularAttribute<AppRole, Application> idApplication;
    public static volatile SingularAttribute<AppRole, Integer> id;

}