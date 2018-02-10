/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.controller;

import com.shadley000.usermanager.autogen.entities.AppRole;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.shadley000.usermanager.autogen.entities.Application;
import com.shadley000.usermanager.autogen.entities.AppRoleToPermission;
import java.util.ArrayList;
import java.util.Collection;
import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
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
public class AppRoleJpaController implements Serializable {

    public AppRoleJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AppRole appRole) throws RollbackFailureException, Exception {
        if (appRole.getAppRoleToPermissionCollection() == null) {
            appRole.setAppRoleToPermissionCollection(new ArrayList<AppRoleToPermission>());
        }
        if (appRole.getAppRoleToUsersCollection() == null) {
            appRole.setAppRoleToUsersCollection(new ArrayList<AppRoleToUsers>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application idApplication = appRole.getIdApplication();
            if (idApplication != null) {
                idApplication = em.getReference(idApplication.getClass(), idApplication.getId());
                appRole.setIdApplication(idApplication);
            }
            Collection<AppRoleToPermission> attachedAppRoleToPermissionCollection = new ArrayList<AppRoleToPermission>();
            for (AppRoleToPermission appRoleToPermissionCollectionAppRoleToPermissionToAttach : appRole.getAppRoleToPermissionCollection()) {
                appRoleToPermissionCollectionAppRoleToPermissionToAttach = em.getReference(appRoleToPermissionCollectionAppRoleToPermissionToAttach.getClass(), appRoleToPermissionCollectionAppRoleToPermissionToAttach.getId());
                attachedAppRoleToPermissionCollection.add(appRoleToPermissionCollectionAppRoleToPermissionToAttach);
            }
            appRole.setAppRoleToPermissionCollection(attachedAppRoleToPermissionCollection);
            Collection<AppRoleToUsers> attachedAppRoleToUsersCollection = new ArrayList<AppRoleToUsers>();
            for (AppRoleToUsers appRoleToUsersCollectionAppRoleToUsersToAttach : appRole.getAppRoleToUsersCollection()) {
                appRoleToUsersCollectionAppRoleToUsersToAttach = em.getReference(appRoleToUsersCollectionAppRoleToUsersToAttach.getClass(), appRoleToUsersCollectionAppRoleToUsersToAttach.getId());
                attachedAppRoleToUsersCollection.add(appRoleToUsersCollectionAppRoleToUsersToAttach);
            }
            appRole.setAppRoleToUsersCollection(attachedAppRoleToUsersCollection);
            em.persist(appRole);
            if (idApplication != null) {
                idApplication.getAppRoleCollection().add(appRole);
                idApplication = em.merge(idApplication);
            }
            for (AppRoleToPermission appRoleToPermissionCollectionAppRoleToPermission : appRole.getAppRoleToPermissionCollection()) {
                AppRole oldIdAppRoleOfAppRoleToPermissionCollectionAppRoleToPermission = appRoleToPermissionCollectionAppRoleToPermission.getIdAppRole();
                appRoleToPermissionCollectionAppRoleToPermission.setIdAppRole(appRole);
                appRoleToPermissionCollectionAppRoleToPermission = em.merge(appRoleToPermissionCollectionAppRoleToPermission);
                if (oldIdAppRoleOfAppRoleToPermissionCollectionAppRoleToPermission != null) {
                    oldIdAppRoleOfAppRoleToPermissionCollectionAppRoleToPermission.getAppRoleToPermissionCollection().remove(appRoleToPermissionCollectionAppRoleToPermission);
                    oldIdAppRoleOfAppRoleToPermissionCollectionAppRoleToPermission = em.merge(oldIdAppRoleOfAppRoleToPermissionCollectionAppRoleToPermission);
                }
            }
            for (AppRoleToUsers appRoleToUsersCollectionAppRoleToUsers : appRole.getAppRoleToUsersCollection()) {
                AppRole oldIdAppRoleOfAppRoleToUsersCollectionAppRoleToUsers = appRoleToUsersCollectionAppRoleToUsers.getIdAppRole();
                appRoleToUsersCollectionAppRoleToUsers.setIdAppRole(appRole);
                appRoleToUsersCollectionAppRoleToUsers = em.merge(appRoleToUsersCollectionAppRoleToUsers);
                if (oldIdAppRoleOfAppRoleToUsersCollectionAppRoleToUsers != null) {
                    oldIdAppRoleOfAppRoleToUsersCollectionAppRoleToUsers.getAppRoleToUsersCollection().remove(appRoleToUsersCollectionAppRoleToUsers);
                    oldIdAppRoleOfAppRoleToUsersCollectionAppRoleToUsers = em.merge(oldIdAppRoleOfAppRoleToUsersCollectionAppRoleToUsers);
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

    public void edit(AppRole appRole) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppRole persistentAppRole = em.find(AppRole.class, appRole.getId());
            Application idApplicationOld = persistentAppRole.getIdApplication();
            Application idApplicationNew = appRole.getIdApplication();
            Collection<AppRoleToPermission> appRoleToPermissionCollectionOld = persistentAppRole.getAppRoleToPermissionCollection();
            Collection<AppRoleToPermission> appRoleToPermissionCollectionNew = appRole.getAppRoleToPermissionCollection();
            Collection<AppRoleToUsers> appRoleToUsersCollectionOld = persistentAppRole.getAppRoleToUsersCollection();
            Collection<AppRoleToUsers> appRoleToUsersCollectionNew = appRole.getAppRoleToUsersCollection();
            if (idApplicationNew != null) {
                idApplicationNew = em.getReference(idApplicationNew.getClass(), idApplicationNew.getId());
                appRole.setIdApplication(idApplicationNew);
            }
            Collection<AppRoleToPermission> attachedAppRoleToPermissionCollectionNew = new ArrayList<AppRoleToPermission>();
            for (AppRoleToPermission appRoleToPermissionCollectionNewAppRoleToPermissionToAttach : appRoleToPermissionCollectionNew) {
                appRoleToPermissionCollectionNewAppRoleToPermissionToAttach = em.getReference(appRoleToPermissionCollectionNewAppRoleToPermissionToAttach.getClass(), appRoleToPermissionCollectionNewAppRoleToPermissionToAttach.getId());
                attachedAppRoleToPermissionCollectionNew.add(appRoleToPermissionCollectionNewAppRoleToPermissionToAttach);
            }
            appRoleToPermissionCollectionNew = attachedAppRoleToPermissionCollectionNew;
            appRole.setAppRoleToPermissionCollection(appRoleToPermissionCollectionNew);
            Collection<AppRoleToUsers> attachedAppRoleToUsersCollectionNew = new ArrayList<AppRoleToUsers>();
            for (AppRoleToUsers appRoleToUsersCollectionNewAppRoleToUsersToAttach : appRoleToUsersCollectionNew) {
                appRoleToUsersCollectionNewAppRoleToUsersToAttach = em.getReference(appRoleToUsersCollectionNewAppRoleToUsersToAttach.getClass(), appRoleToUsersCollectionNewAppRoleToUsersToAttach.getId());
                attachedAppRoleToUsersCollectionNew.add(appRoleToUsersCollectionNewAppRoleToUsersToAttach);
            }
            appRoleToUsersCollectionNew = attachedAppRoleToUsersCollectionNew;
            appRole.setAppRoleToUsersCollection(appRoleToUsersCollectionNew);
            appRole = em.merge(appRole);
            if (idApplicationOld != null && !idApplicationOld.equals(idApplicationNew)) {
                idApplicationOld.getAppRoleCollection().remove(appRole);
                idApplicationOld = em.merge(idApplicationOld);
            }
            if (idApplicationNew != null && !idApplicationNew.equals(idApplicationOld)) {
                idApplicationNew.getAppRoleCollection().add(appRole);
                idApplicationNew = em.merge(idApplicationNew);
            }
            for (AppRoleToPermission appRoleToPermissionCollectionOldAppRoleToPermission : appRoleToPermissionCollectionOld) {
                if (!appRoleToPermissionCollectionNew.contains(appRoleToPermissionCollectionOldAppRoleToPermission)) {
                    appRoleToPermissionCollectionOldAppRoleToPermission.setIdAppRole(null);
                    appRoleToPermissionCollectionOldAppRoleToPermission = em.merge(appRoleToPermissionCollectionOldAppRoleToPermission);
                }
            }
            for (AppRoleToPermission appRoleToPermissionCollectionNewAppRoleToPermission : appRoleToPermissionCollectionNew) {
                if (!appRoleToPermissionCollectionOld.contains(appRoleToPermissionCollectionNewAppRoleToPermission)) {
                    AppRole oldIdAppRoleOfAppRoleToPermissionCollectionNewAppRoleToPermission = appRoleToPermissionCollectionNewAppRoleToPermission.getIdAppRole();
                    appRoleToPermissionCollectionNewAppRoleToPermission.setIdAppRole(appRole);
                    appRoleToPermissionCollectionNewAppRoleToPermission = em.merge(appRoleToPermissionCollectionNewAppRoleToPermission);
                    if (oldIdAppRoleOfAppRoleToPermissionCollectionNewAppRoleToPermission != null && !oldIdAppRoleOfAppRoleToPermissionCollectionNewAppRoleToPermission.equals(appRole)) {
                        oldIdAppRoleOfAppRoleToPermissionCollectionNewAppRoleToPermission.getAppRoleToPermissionCollection().remove(appRoleToPermissionCollectionNewAppRoleToPermission);
                        oldIdAppRoleOfAppRoleToPermissionCollectionNewAppRoleToPermission = em.merge(oldIdAppRoleOfAppRoleToPermissionCollectionNewAppRoleToPermission);
                    }
                }
            }
            for (AppRoleToUsers appRoleToUsersCollectionOldAppRoleToUsers : appRoleToUsersCollectionOld) {
                if (!appRoleToUsersCollectionNew.contains(appRoleToUsersCollectionOldAppRoleToUsers)) {
                    appRoleToUsersCollectionOldAppRoleToUsers.setIdAppRole(null);
                    appRoleToUsersCollectionOldAppRoleToUsers = em.merge(appRoleToUsersCollectionOldAppRoleToUsers);
                }
            }
            for (AppRoleToUsers appRoleToUsersCollectionNewAppRoleToUsers : appRoleToUsersCollectionNew) {
                if (!appRoleToUsersCollectionOld.contains(appRoleToUsersCollectionNewAppRoleToUsers)) {
                    AppRole oldIdAppRoleOfAppRoleToUsersCollectionNewAppRoleToUsers = appRoleToUsersCollectionNewAppRoleToUsers.getIdAppRole();
                    appRoleToUsersCollectionNewAppRoleToUsers.setIdAppRole(appRole);
                    appRoleToUsersCollectionNewAppRoleToUsers = em.merge(appRoleToUsersCollectionNewAppRoleToUsers);
                    if (oldIdAppRoleOfAppRoleToUsersCollectionNewAppRoleToUsers != null && !oldIdAppRoleOfAppRoleToUsersCollectionNewAppRoleToUsers.equals(appRole)) {
                        oldIdAppRoleOfAppRoleToUsersCollectionNewAppRoleToUsers.getAppRoleToUsersCollection().remove(appRoleToUsersCollectionNewAppRoleToUsers);
                        oldIdAppRoleOfAppRoleToUsersCollectionNewAppRoleToUsers = em.merge(oldIdAppRoleOfAppRoleToUsersCollectionNewAppRoleToUsers);
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
                Integer id = appRole.getId();
                if (findAppRole(id) == null) {
                    throw new NonexistentEntityException("The appRole with id " + id + " no longer exists.");
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
            AppRole appRole;
            try {
                appRole = em.getReference(AppRole.class, id);
                appRole.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appRole with id " + id + " no longer exists.", enfe);
            }
            Application idApplication = appRole.getIdApplication();
            if (idApplication != null) {
                idApplication.getAppRoleCollection().remove(appRole);
                idApplication = em.merge(idApplication);
            }
            Collection<AppRoleToPermission> appRoleToPermissionCollection = appRole.getAppRoleToPermissionCollection();
            for (AppRoleToPermission appRoleToPermissionCollectionAppRoleToPermission : appRoleToPermissionCollection) {
                appRoleToPermissionCollectionAppRoleToPermission.setIdAppRole(null);
                appRoleToPermissionCollectionAppRoleToPermission = em.merge(appRoleToPermissionCollectionAppRoleToPermission);
            }
            Collection<AppRoleToUsers> appRoleToUsersCollection = appRole.getAppRoleToUsersCollection();
            for (AppRoleToUsers appRoleToUsersCollectionAppRoleToUsers : appRoleToUsersCollection) {
                appRoleToUsersCollectionAppRoleToUsers.setIdAppRole(null);
                appRoleToUsersCollectionAppRoleToUsers = em.merge(appRoleToUsersCollectionAppRoleToUsers);
            }
            em.remove(appRole);
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

    public List<AppRole> findAppRoleEntities() {
        return findAppRoleEntities(true, -1, -1);
    }

    public List<AppRole> findAppRoleEntities(int maxResults, int firstResult) {
        return findAppRoleEntities(false, maxResults, firstResult);
    }

    private List<AppRole> findAppRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AppRole as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AppRole findAppRole(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AppRole.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppRoleCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from AppRole as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
