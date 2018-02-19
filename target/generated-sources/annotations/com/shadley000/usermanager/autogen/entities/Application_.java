package com.shadley000.usermanager.autogen.entities;

import com.shadley000.usermanager.autogen.entities.AppPermission;
import com.shadley000.usermanager.autogen.entities.AppRole;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-18T22:00:46")
@StaticMetamodel(Application.class)
public class Application_ { 

    public static volatile SingularAttribute<Application, String> description;
    public static volatile CollectionAttribute<Application, AppPermission> appPermissionCollection;
    public static volatile SingularAttribute<Application, Integer> id;
    public static volatile SingularAttribute<Application, String> applicationName;
    public static volatile CollectionAttribute<Application, AppRole> appRoleCollection;

}