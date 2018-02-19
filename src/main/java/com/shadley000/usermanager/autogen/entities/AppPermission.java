/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shadl
 */
@Entity
@Table(name = "APP_PERMISSION")
//@Table(name = "app_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppPermission.findAll", query = "SELECT a FROM AppPermission a")
    , @NamedQuery(name = "AppPermission.findById", query = "SELECT a FROM AppPermission a WHERE a.id = :id")
    , @NamedQuery(name = "AppPermission.findByPermissionName", query = "SELECT a FROM AppPermission a WHERE a.permissionName = :permissionName")
    , @NamedQuery(name = "AppPermission.findByDescription", query = "SELECT a FROM AppPermission a WHERE a.description = :description")})
public class AppPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 32)
    @Column(name = "PERMISSION_NAME")
    private String permissionName;
    @Size(max = 256)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "ID_APPLICATION", referencedColumnName = "ID")
    @ManyToOne
    private Application idApplication;
    @OneToMany(mappedBy = "idAppPermission")
    private Collection<AppRoleToPermission> appRoleToPermissionCollection;

    public AppPermission() {
    }

    public AppPermission(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Application getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(Application idApplication) {
        this.idApplication = idApplication;
    }

    @XmlTransient
    public Collection<AppRoleToPermission> getAppRoleToPermissionCollection() {
        return appRoleToPermissionCollection;
    }

    public void setAppRoleToPermissionCollection(Collection<AppRoleToPermission> appRoleToPermissionCollection) {
        this.appRoleToPermissionCollection = appRoleToPermissionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppPermission)) {
            return false;
        }
        AppPermission other = (AppPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shadley000.usermanager.entities.AppPermission[ id=" + id + " ]";
    }
    
}
