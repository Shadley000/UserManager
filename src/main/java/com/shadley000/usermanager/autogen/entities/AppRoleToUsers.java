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
/////@Table(name = "app_role_to_users")
@Table(name = "APP_ROLE_TO_USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppRoleToUsers.findAll", query = "SELECT a FROM AppRoleToUsers a")
    , @NamedQuery(name = "AppRoleToUsers.findById", query = "SELECT a FROM AppRoleToUsers a WHERE a.id = :id")})
public class AppRoleToUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ID_APP_USER", referencedColumnName = "ID")
    @ManyToOne
    private AppUser idAppUser;
    @JoinColumn(name = "ID_APP_ROLE", referencedColumnName = "ID")
    @ManyToOne
    private AppRole idAppRole;

    public AppRoleToUsers() {
    }

    public AppRoleToUsers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUser idAppUser) {
        this.idAppUser = idAppUser;
    }

    public AppRole getIdAppRole() {
        return idAppRole;
    }

    public void setIdAppRole(AppRole idAppRole) {
        this.idAppRole = idAppRole;
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
        if (!(object instanceof AppRoleToUsers)) {
            return false;
        }
        AppRoleToUsers other = (AppRoleToUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shadley000.usermanager.entities.AppRoleToUsers[ id=" + id + " ]";
    }
    
}
