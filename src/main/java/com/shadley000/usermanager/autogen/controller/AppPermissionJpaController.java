/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.controller;

import com.shadley000.usermanager.autogen.entities.AppPermission;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.shadley000.usermanager.autogen.entities.Application;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import com.shadley000.usermanager.autogen.controller.exceptions.NonexistentEntityException;
import com.shadley000.usermanager.autogen.controller.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author shadl
 */
public class AppPermissionJpaController implements Serializable {

    public AppPermissionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AppPermission appPermission) throws RollbackFailureException, Exception {
        if (appPermission.getAppRoleToPermissionCollection() == null) {
            appPermission.setAppRoleToPermissionCollection(new ArrayList<AppRoleToPermission>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application idApplication = appPermission.getIdApplication();
            if (idApplication != null) {
                idApplication = em.getReference(idApplication.getClass(), idApplication.getId());
                appPermission.setIdApplication(idApplication);
            }
            Collection<AppRoleToPermission> attachedAppRoleToPermissionCollection = new ArrayList<AppRoleToPermission>();
            for (AppRoleToPermission appRoleToPermissionCollectionAppRoleToPermissionToAttach : appPermission.getAppRoleToPermissionCollection()) {
                appRoleToPermissionCollectionAppRoleToPermissionToAttach = em.getReference(appRoleToPermissionCollectionAppRoleToPermissionToAttach.getClass(), appRoleToPermissionCollectionAppRoleToPermissionToAttach.getId());
                attachedAppRoleToPermissionCollection.add(appRoleToPermissionCollectionAppRoleToPermissionToAttach);
            }
            appPermission.setAppRoleToPermissionCollection(attachedAppRoleToPermissionCollection);
            em.persist(appPermission);
            if (idApplication != null) {
                idApplication.getAppPermissionCollection().add(appPermission);
                idApplication = em.merge(idApplication);
            }
            for (AppRoleToPermission appRoleToPermissionCollectionAppRoleToPermission : appPermission.getAppRoleToPermissionCollection()) {
                AppPermission oldIdAppPermissionOfAppRoleToPermissionCollectionAppRoleToPermission = appRoleToPermissionCollectionAppRoleToPermission.getIdAppPermission();
                appRoleToPermissionCollectionAppRoleToPermission.setIdAppPermission(appPermission);
                appRoleToPermissionCollectionAppRoleToPermission = em.merge(appRoleToPermissionCollectionAppRoleToPermission);
                if (oldIdAppPermissionOfAppRoleToPermissionCollectionAppRoleToPermission != null) {
                    oldIdAppPermissionOfAppRoleToPermissionCollectionAppRoleToPermission.getAppRoleToPermissionCollection().remove(appRoleToPermissionCollectionAppRoleToPermission);
                    oldIdAppPermissionOfAppRoleToPermissionCollectionAppRoleToPermission = em.merge(oldIdAppPermissionOfAppRoleToPermissionCollectionAppRoleToPermission);
                }
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

    public void edit(AppPermission appPermission) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppPermission persistentAppPermission = em.find(AppPermission.class, appPermission.getId());
            Application idApplicationOld = persistentAppPermission.getIdApplication();
            Application idApplicationNew = appPermission.getIdApplication();
            Collection<AppRoleToPermission> appRoleToPermissionCollectionOld = persistentAppPermission.getAppRoleToPermissionCollection();
            Collection<AppRoleToPermission> appRoleToPermissionCollectionNew = appPermission.getAppRoleToPermissionCollection();
            if (idApplicationNew != null) {
                idApplicationNew = em.getReference(idApplicationNew.getClass(), idApplicationNew.getId());
                appPermission.setIdApplication(idApplicationNew);
            }
            Collection<AppRoleToPermission> attachedAppRoleToPermissionCollectionNew = new ArrayList<AppRoleToPermission>();
            for (AppRoleToPermission appRoleToPermissionCollectionNewAppRoleToPermissionToAttach : appRoleToPermissionCollectionNew) {
                appRoleToPermissionCollectionNewAppRoleToPermissionToAttach = em.getReference(appRoleToPermissionCollectionNewAppRoleToPermissionToAttach.getClass(), appRoleToPermissionCollectionNewAppRoleToPermissionToAttach.getId());
                attachedAppRoleToPermissionCollectionNew.add(appRoleToPermissionCollectionNewAppRoleToPermissionToAttach);
            }
            appRoleToPermissionCollectionNew = attachedAppRoleToPermissionCollectionNew;
            appPermission.setAppRoleToPermissionCollection(appRoleToPermissionCollectionNew);
            appPermission = em.merge(appPermission);
            if (idApplicationOld != null && !idApplicationOld.equals(idApplicationNew)) {
                idApplicationOld.getAppPermissionCollection().remove(appPermission);
                idApplicationOld = em.merge(idApplicationOld);
            }
            if (idApplicationNew != null && !idApplicationNew.equals(idApplicationOld)) {
                idApplicationNew.getAppPermissionCollection().add(appPermission);
                idApplicationNew = em.merge(idApplicationNew);
            }
            for (AppRoleToPermission appRoleToPermissionCollectionOldAppRoleToPermission : appRoleToPermissionCollectionOld) {
                if (!appRoleToPermissionCollectionNew.contains(appRoleToPermissionCollectionOldAppRoleToPermission)) {
                    appRoleToPermissionCollectionOldAppRoleToPermission.setIdAppPermission(null);
                    appRoleToPermissionCollectionOldAppRoleToPermission = em.merge(appRoleToPermissionCollectionOldAppRoleToPermission);
                }
            }
            for (AppRoleToPermission appRoleToPermissionCollectionNewAppRoleToPermission : appRoleToPermissionCollectionNew) {
                if (!appRoleToPermissionCollectionOld.contains(appRoleToPermissionCollectionNewAppRoleToPermission)) {
                    AppPermission oldIdAppPermissionOfAppRoleToPermissionCollectionNewAppRoleToPermission = appRoleToPermissionCollectionNewAppRoleToPermission.getIdAppPermission();
                    appRoleToPermissionCollectionNewAppRoleToPermission.setIdAppPermission(appPermission);
                    appRoleToPermissionCollectionNewAppRoleToPermission = em.merge(appRoleToPermissionCollectionNewAppRoleToPermission);
                    if (oldIdAppPermissionOfAppRoleToPermissionCollectionNewAppRoleToPermission != null && !oldIdAppPermissionOfAppRoleToPermissionCollectionNewAppRoleToPermission.equals(appPermission)) {
                        oldIdAppPermissionOfAppRoleToPermissionCollectionNewAppRoleToPermission.getAppRoleToPermissionCollection().remove(appRoleToPermissionCollectionNewAppRoleToPermission);
                        oldIdAppPermissionOfAppRoleToPermissionCollectionNewAppRoleToPermission = em.merge(oldIdAppPermissionOfAppRoleToPermissionCollectionNewAppRoleToPermission);
                    }
                }
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
                Integer id = appPermission.getId();
                if (findAppPermission(id) == null) {
                    throw new NonexistentEntityException("The appPermission with id " + id + " no longer exists.");
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
            AppPermission appPermission;
            try {
                appPermission = em.getReference(AppPermission.class, id);
                appPermission.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appPermission with id " + id + " no longer exists.", enfe);
            }
            Application idApplication = appPermission.getIdApplication();
            if (idApplication != null) {
                idApplication.getAppPermissionCollection().remove(appPermission);
                idApplication = em.merge(idApplication);
            }
            Collection<AppRoleToPermission> appRoleToPermissionCollection = appPermission.getAppRoleToPermissionCollection();
            for (AppRoleToPermission appRoleToPermissionCollectionAppRoleToPermission : appRoleToPermissionCollection) {
                appRoleToPermissionCollectionAppRoleToPermission.setIdAppPermission(null);
                appRoleToPermissionCollectionAppRoleToPermission = em.merge(appRoleToPermissionCollectionAppRoleToPermission);
            }
            em.remove(appPermission);
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

    public List<AppPermission> findAppPermissionEntities() {
        return findAppPermissionEntities(true, -1, -1);
    }

    public List<AppPermission> findAppPermissionEntities(int maxResults, int firstResult) {
        return findAppPermissionEntities(false, maxResults, firstResult);
    }

    private List<AppPermission> findAppPermissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AppPermission as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AppPermission findAppPermission(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AppPermission.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppPermissionCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from AppPermission as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
