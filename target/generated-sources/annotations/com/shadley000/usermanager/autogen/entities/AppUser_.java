package com.shadley000.usermanager.autogen.entities;

import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-15T14:36:11")
@StaticMetamodel(AppUser.class)
public class AppUser_ { 

    public static volatile SingularAttribute<AppUser, String> firstName;
    public static volatile SingularAttribute<AppUser, String> lastName;
    public static volatile SingularAttribute<AppUser, String> userPassword;
    public static volatile CollectionAttribute<AppUser, AppRoleToUsers> appRoleToUsersCollection;
    public static volatile SingularAttribute<AppUser, Integer> id;
    public static volatile SingularAttribute<AppUser, String> login;
    public static volatile SingularAttribute<AppUser, String> email;

}