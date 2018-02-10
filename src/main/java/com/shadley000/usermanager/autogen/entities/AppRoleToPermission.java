/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shadl
 */
@Entity
@Table(name = "app_role_to_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppRoleToPermission.findAll", query = "SELECT a FROM AppRoleToPermission a")
    , @NamedQuery(name = "AppRoleToPermission.findById", query = "SELECT a FROM AppRoleToPermission a WHERE a.id = :id")})
public class AppRoleToPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ID_APP_ROLE", referencedColumnName = "ID")
    @ManyToOne
    private AppRole idAppRole;
    @JoinColumn(name = "ID_APP_PERMISSION", referencedColumnName = "ID")
    @ManyToOne
    private AppPermission idAppPermission;

    public AppRoleToPermission() {
    }

    public AppRoleToPermission(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppRole getIdAppRole() {
        return idAppRole;
    }

    public void setIdAppRole(AppRole idAppRole) {
        this.idAppRole = idAppRole;
    }

    public AppPermission getIdAppPermission() {
        return idAppPermission;
    }

    public void setIdAppPermission(AppPermission idAppPermission) {
        this.idAppPermission = idAppPermission;
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
        if (!(object instanceof AppRoleToPermission)) {
            return false;
        }
        AppRoleToPermission other = (AppRoleToPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shadley000.usermanager.entities.AppRoleToPermission[ id=" + id + " ]";
    }
    
}
