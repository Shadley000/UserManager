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
@Table(name = "application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a")
    , @NamedQuery(name = "Application.findById", query = "SELECT a FROM Application a WHERE a.id = :id")
    , @NamedQuery(name = "Application.findByApplicationName", query = "SELECT a FROM Application a WHERE a.applicationName = :applicationName")
    , @NamedQuery(name = "Application.findByDescription", query = "SELECT a FROM Application a WHERE a.description = :description")})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 128)
    @Column(name = "APPLICATION_NAME")
    private String applicationName;
    @Size(max = 256)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "idApplication")
    private Collection<AppPermission> appPermissionCollection;
    @OneToMany(mappedBy = "idApplication")
    private Collection<AppRole> appRoleCollection;

    public Application() {
    }

    public Application(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<AppPermission> getAppPermissionCollection() {
        return appPermissionCollection;
    }

    public void setAppPermissionCollection(Collection<AppPermission> appPermissionCollection) {
        this.appPermissionCollection = appPermissionCollection;
    }

    @XmlTransient
    public Collection<AppRole> getAppRoleCollection() {
        return appRoleCollection;
    }

    public void setAppRoleCollection(Collection<AppRole> appRoleCollection) {
        this.appRoleCollection = appRoleCollection;
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
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shadley000.usermanager.entities.Application[ id=" + id + " ]";
    }
    
}
