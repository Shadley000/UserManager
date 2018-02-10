package com.shadley000.usermanager.autogen.entities;

import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.entities.Application;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-15T14:36:11")
@StaticMetamodel(AppPermission.class)
public class AppPermission_ { 

    public static volatile CollectionAttribute<AppPermission, AppRoleToPermission> appRoleToPermissionCollection;
    public static volatile SingularAttribute<AppPermission, String> description;
    public static volatile SingularAttribute<AppPermission, Application> idApplication;
    public static volatile SingularAttribute<AppPermission, Integer> id;
    public static volatile SingularAttribute<AppPermission, String> permissionName;

}