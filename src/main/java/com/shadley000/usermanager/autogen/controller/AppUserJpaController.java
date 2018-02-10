/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.shadley000.usermanager.autogen.entities.AppRoleToUsers;
import com.shadley000.usermanager.autogen.entities.AppUser;
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
public class AppUserJpaController implements Serializable {

    public AppUserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AppUser appUser) throws RollbackFailureException, Exception {
        if (appUser.getAppRoleToUsersCollection() == null) {
            appUser.setAppRoleToUsersCollection(new ArrayList<AppRoleToUsers>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<AppRoleToUsers> attachedAppRoleToUsersCollection = new ArrayList<AppRoleToUsers>();
            for (AppRoleToUsers appRoleToUsersCollectionAppRoleToUsersToAttach : appUser.getAppRoleToUsersCollection()) {
                appRoleToUsersCollectionAppRoleToUsersToAttach = em.getReference(appRoleToUsersCollectionAppRoleToUsersToAttach.getClass(), appRoleToUsersCollectionAppRoleToUsersToAttach.getId());
                attachedAppRoleToUsersCollection.add(appRoleToUsersCollectionAppRoleToUsersToAttach);
            }
            appUser.setAppRoleToUsersCollection(attachedAppRoleToUsersCollection);
            em.persist(appUser);
            for (AppRoleToUsers appRoleToUsersCollectionAppRoleToUsers : appUser.getAppRoleToUsersCollection()) {
                AppUser oldIdAppUserOfAppRoleToUsersCollectionAppRoleToUsers = appRoleToUsersCollectionAppRoleToUsers.getIdAppUser();
                appRoleToUsersCollectionAppRoleToUsers.setIdAppUser(appUser);
                appRoleToUsersCollectionAppRoleToUsers = em.merge(appRoleToUsersCollectionAppRoleToUsers);
                if (oldIdAppUserOfAppRoleToUsersCollectionAppRoleToUsers != null) {
                    oldIdAppUserOfAppRoleToUsersCollectionAppRoleToUsers.getAppRoleToUsersCollection().remove(appRoleToUsersCollectionAppRoleToUsers);
                    oldIdAppUserOfAppRoleToUsersCollectionAppRoleToUsers = em.merge(oldIdAppUserOfAppRoleToUsersCollectionAppRoleToUsers);
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

    public void edit(AppUser appUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppUser persistentAppUser = em.find(AppUser.class, appUser.getId());
            Collection<AppRoleToUsers> appRoleToUsersCollectionOld = persistentAppUser.getAppRoleToUsersCollection();
            Collection<AppRoleToUsers> appRoleToUsersCollectionNew = appUser.getAppRoleToUsersCollection();
            Collection<AppRoleToUsers> attachedAppRoleToUsersCollectionNew = new ArrayList<AppRoleToUsers>();
            for (AppRoleToUsers appRoleToUsersCollectionNewAppRoleToUsersToAttach : appRoleToUsersCollectionNew) {
                appRoleToUsersCollectionNewAppRoleToUsersToAttach = em.getReference(appRoleToUsersCollectionNewAppRoleToUsersToAttach.getClass(), appRoleToUsersCollectionNewAppRoleToUsersToAttach.getId());
                attachedAppRoleToUsersCollectionNew.add(appRoleToUsersCollectionNewAppRoleToUsersToAttach);
            }
            appRoleToUsersCollectionNew = attachedAppRoleToUsersCollectionNew;
            appUser.setAppRoleToUsersCollection(appRoleToUsersCollectionNew);
            appUser = em.merge(appUser);
            for (AppRoleToUsers appRoleToUsersCollectionOldAppRoleToUsers : appRoleToUsersCollectionOld) {
                if (!appRoleToUsersCollectionNew.contains(appRoleToUsersCollectionOldAppRoleToUsers)) {
                    appRoleToUsersCollectionOldAppRoleToUsers.setIdAppUser(null);
                    appRoleToUsersCollectionOldAppRoleToUsers = em.merge(appRoleToUsersCollectionOldAppRoleToUsers);
                }
            }
            for (AppRoleToUsers appRoleToUsersCollectionNewAppRoleToUsers : appRoleToUsersCollectionNew) {
                if (!appRoleToUsersCollectionOld.contains(appRoleToUsersCollectionNewAppRoleToUsers)) {
                    AppUser oldIdAppUserOfAppRoleToUsersCollectionNewAppRoleToUsers = appRoleToUsersCollectionNewAppRoleToUsers.getIdAppUser();
                    appRoleToUsersCollectionNewAppRoleToUsers.setIdAppUser(appUser);
                    appRoleToUsersCollectionNewAppRoleToUsers = em.merge(appRoleToUsersCollectionNewAppRoleToUsers);
                    if (oldIdAppUserOfAppRoleToUsersCollectionNewAppRoleToUsers != null && !oldIdAppUserOfAppRoleToUsersCollectionNewAppRoleToUsers.equals(appUser)) {
                        oldIdAppUserOfAppRoleToUsersCollectionNewAppRoleToUsers.getAppRoleToUsersCollection().remove(appRoleToUsersCollectionNewAppRoleToUsers);
                        oldIdAppUserOfAppRoleToUsersCollectionNewAppRoleToUsers = em.merge(oldIdAppUserOfAppRoleToUsersCollectionNewAppRoleToUsers);
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
                Integer id = appUser.getId();
                if (findAppUser(id) == null) {
                    throw new NonexistentEntityException("The appUser with id " + id + " no longer exists.");
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
            AppUser appUser;
            try {
                appUser = em.getReference(AppUser.class, id);
                appUser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appUser with id " + id + " no longer exists.", enfe);
            }
            Collection<AppRoleToUsers> appRoleToUsersCollection = appUser.getAppRoleToUsersCollection();
            for (AppRoleToUsers appRoleToUsersCollectionAppRoleToUsers : appRoleToUsersCollection) {
                appRoleToUsersCollectionAppRoleToUsers.setIdAppUser(null);
                appRoleToUsersCollectionAppRoleToUsers = em.merge(appRoleToUsersCollectionAppRoleToUsers);
            }
            em.remove(appUser);
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

    public List<AppUser> findAppUserEntities() {
        return findAppUserEntities(true, -1, -1);
    }

    public List<AppUser> findAppUserEntities(int maxResults, int firstResult) {
        return findAppUserEntities(false, maxResults, firstResult);
    }

    private List<AppUser> findAppUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AppUser as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AppUser findAppUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AppUser.class, id);
        } finally {
            em.close();
        }
    }    
   
    public List findAppUseIDByLogin(String login) {

        EntityManager em = getEntityManager();
        return em.createNamedQuery("AppUser.findByLogin")
                .setParameter("login", login).getResultList();             
    }
    
    public List findAppUseIDByLoginPassword(String login, String password) {

        EntityManager em = getEntityManager();
        return em.createNamedQuery("AppUser.findByLoginUserPassword")
                .setParameter("login", login)
                .setParameter("userPassword", password).getResultList();             
    }
    
    public int getAppUserCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from AppUser as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
