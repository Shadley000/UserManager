/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.shadley000.usermanager.autogen.entities.AppRole;
import com.shadley000.usermanager.autogen.entities.AppPermission;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.controller.exceptions.NonexistentEntityException;
import com.shadley000.usermanager.autogen.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author shadl
 */
public class AppRoleToPermissionJpaController implements Serializable {

    public AppRoleToPermissionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AppRoleToPermission appRoleToPermission) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppRole idAppRole = appRoleToPermission.getIdAppRole();
            if (idAppRole != null) {
                idAppRole = em.getReference(idAppRole.getClass(), idAppRole.getId());
                appRoleToPermission.setIdAppRole(idAppRole);
            }
            AppPermission idAppPermission = appRoleToPermission.getIdAppPermission();
            if (idAppPermission != null) {
                idAppPermission = em.getReference(idAppPermission.getClass(), idAppPermission.getId());
                appRoleToPermission.setIdAppPermission(idAppPermission);
            }
            em.persist(appRoleToPermission);
            if (idAppRole != null) {
                idAppRole.getAppRoleToPermissionCollection().add(appRoleToPermission);
                idAppRole = em.merge(idAppRole);
            }
            if (idAppPermission != null) {
                idAppPermission.getAppRoleToPermissionCollection().add(appRoleToPermission);
                idAppPermission = em.merge(idAppPermission);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AppRoleToPermission appRoleToPermission) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppRoleToPermission persistentAppRoleToPermission = em.find(AppRoleToPermission.class, appRoleToPermission.getId());
            AppRole idAppRoleOld = persistentAppRoleToPermission.getIdAppRole();
            AppRole idAppRoleNew = appRoleToPermission.getIdAppRole();
            AppPermission idAppPermissionOld = persistentAppRoleToPermission.getIdAppPermission();
            AppPermission idAppPermissionNew = appRoleToPermission.getIdAppPermission();
            if (idAppRoleNew != null) {
                idAppRoleNew = em.getReference(idAppRoleNew.getClass(), idAppRoleNew.getId());
                appRoleToPermission.setIdAppRole(idAppRoleNew);
            }
            if (idAppPermissionNew != null) {
                idAppPermissionNew = em.getReference(idAppPermissionNew.getClass(), idAppPermissionNew.getId());
                appRoleToPermission.setIdAppPermission(idAppPermissionNew);
            }
            appRoleToPermission = em.merge(appRoleToPermission);
            if (idAppRoleOld != null && !idAppRoleOld.equals(idAppRoleNew)) {
                idAppRoleOld.getAppRoleToPermissionCollection().remove(appRoleToPermission);
                idAppRoleOld = em.merge(idAppRoleOld);
            }
            if (idAppRoleNew != null && !idAppRoleNew.equals(idAppRoleOld)) {
                idAppRoleNew.getAppRoleToPermissionCollection().add(appRoleToPermission);
                idAppRoleNew = em.merge(idAppRoleNew);
            }
            if (idAppPermissionOld != null && !idAppPermissionOld.equals(idAppPermissionNew)) {
                idAppPermissionOld.getAppRoleToPermissionCollection().remove(appRoleToPermission);
                idAppPermissionOld = em.merge(idAppPermissionOld);
            }
            if (idAppPermissionNew != null && !idAppPermissionNew.equals(idAppPermissionOld)) {
                idAppPermissionNew.getAppRoleToPermissionCollection().add(appRoleToPermission);
                idAppPermissionNew = em.merge(idAppPermissionNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = appRoleToPermission.getId();
                if (findAppRoleToPermission(id) == null) {
                    throw new NonexistentEntityException("The appRoleToPermission with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppRoleToPermission appRoleToPermission;
            try {
                appRoleToPermission = em.getReference(AppRoleToPermission.class, id);
                appRoleToPermission.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appRoleToPermission with id " + id + " no longer exists.", enfe);
            }
            AppRole idAppRole = appRoleToPermission.getIdAppRole();
            if (idAppRole != null) {
                idAppRole.getAppRoleToPermissionCollection().remove(appRoleToPermission);
                idAppRole = em.merge(idAppRole);
            }
            AppPermission idAppPermission = appRoleToPermission.getIdAppPermission();
            if (idAppPermission != null) {
                idAppPermission.getAppRoleToPermissionCollection().remove(appRoleToPermission);
                idAppPermission = em.merge(idAppPermission);
            }
            em.remove(appRoleToPermission);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AppRoleToPermission> findAppRoleToPermissionEntities() {
        return findAppRoleToPermissionEntities(true, -1, -1);
    }

    public List<AppRoleToPermission> findAppRoleToPermissionEntities(int maxResults, int firstResult) {
        return findAppRoleToPermissionEntities(false, maxResults, firstResult);
    }

    private List<AppRoleToPermission> findAppRoleToPermissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AppRoleToPermission as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AppRoleToPermission findAppRoleToPermission(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AppRoleToPermission.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppRoleToPermissionCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from AppRoleToPermission as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
