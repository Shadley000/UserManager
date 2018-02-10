/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.shadley000.usermanager.autogen.entities.AppPermission;
import java.util.ArrayList;
import java.util.Collection;
import com.shadley000.usermanager.autogen.entities.AppRole;
import com.shadley000.usermanager.autogen.entities.Application;
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
public class ApplicationJpaController implements Serializable {

    public ApplicationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Application application) throws RollbackFailureException, Exception {
        if (application.getAppPermissionCollection() == null) {
            application.setAppPermissionCollection(new ArrayList<AppPermission>());
        }
        if (application.getAppRoleCollection() == null) {
            application.setAppRoleCollection(new ArrayList<AppRole>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<AppPermission> attachedAppPermissionCollection = new ArrayList<AppPermission>();
            for (AppPermission appPermissionCollectionAppPermissionToAttach : application.getAppPermissionCollection()) {
                appPermissionCollectionAppPermissionToAttach = em.getReference(appPermissionCollectionAppPermissionToAttach.getClass(), appPermissionCollectionAppPermissionToAttach.getId());
                attachedAppPermissionCollection.add(appPermissionCollectionAppPermissionToAttach);
            }
            application.setAppPermissionCollection(attachedAppPermissionCollection);
            Collection<AppRole> attachedAppRoleCollection = new ArrayList<AppRole>();
            for (AppRole appRoleCollectionAppRoleToAttach : application.getAppRoleCollection()) {
                appRoleCollectionAppRoleToAttach = em.getReference(appRoleCollectionAppRoleToAttach.getClass(), appRoleCollectionAppRoleToAttach.getId());
                attachedAppRoleCollection.add(appRoleCollectionAppRoleToAttach);
            }
            application.setAppRoleCollection(attachedAppRoleCollection);
            em.persist(application);
            for (AppPermission appPermissionCollectionAppPermission : application.getAppPermissionCollection()) {
                Application oldIdApplicationOfAppPermissionCollectionAppPermission = appPermissionCollectionAppPermission.getIdApplication();
                appPermissionCollectionAppPermission.setIdApplication(application);
                appPermissionCollectionAppPermission = em.merge(appPermissionCollectionAppPermission);
                if (oldIdApplicationOfAppPermissionCollectionAppPermission != null) {
                    oldIdApplicationOfAppPermissionCollectionAppPermission.getAppPermissionCollection().remove(appPermissionCollectionAppPermission);
                    oldIdApplicationOfAppPermissionCollectionAppPermission = em.merge(oldIdApplicationOfAppPermissionCollectionAppPermission);
                }
            }
            for (AppRole appRoleCollectionAppRole : application.getAppRoleCollection()) {
                Application oldIdApplicationOfAppRoleCollectionAppRole = appRoleCollectionAppRole.getIdApplication();
                appRoleCollectionAppRole.setIdApplication(application);
                appRoleCollectionAppRole = em.merge(appRoleCollectionAppRole);
                if (oldIdApplicationOfAppRoleCollectionAppRole != null) {
                    oldIdApplicationOfAppRoleCollectionAppRole.getAppRoleCollection().remove(appRoleCollectionAppRole);
                    oldIdApplicationOfAppRoleCollectionAppRole = em.merge(oldIdApplicationOfAppRoleCollectionAppRole);
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

    public void edit(Application application) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Application persistentApplication = em.find(Application.class, application.getId());
            Collection<AppPermission> appPermissionCollectionOld = persistentApplication.getAppPermissionCollection();
            Collection<AppPermission> appPermissionCollectionNew = application.getAppPermissionCollection();
            Collection<AppRole> appRoleCollectionOld = persistentApplication.getAppRoleCollection();
            Collection<AppRole> appRoleCollectionNew = application.getAppRoleCollection();
            Collection<AppPermission> attachedAppPermissionCollectionNew = new ArrayList<AppPermission>();
            for (AppPermission appPermissionCollectionNewAppPermissionToAttach : appPermissionCollectionNew) {
                appPermissionCollectionNewAppPermissionToAttach = em.getReference(appPermissionCollectionNewAppPermissionToAttach.getClass(), appPermissionCollectionNewAppPermissionToAttach.getId());
                attachedAppPermissionCollectionNew.add(appPermissionCollectionNewAppPermissionToAttach);
            }
            appPermissionCollectionNew = attachedAppPermissionCollectionNew;
            application.setAppPermissionCollection(appPermissionCollectionNew);
            Collection<AppRole> attachedAppRoleCollectionNew = new ArrayList<AppRole>();
            for (AppRole appRoleCollectionNewAppRoleToAttach : appRoleCollectionNew) {
                appRoleCollectionNewAppRoleToAttach = em.getReference(appRoleCollectionNewAppRoleToAttach.getClass(), appRoleCollectionNewAppRoleToAttach.getId());
                attachedAppRoleCollectionNew.add(appRoleCollectionNewAppRoleToAttach);
            }
            appRoleCollectionNew = attachedAppRoleCollectionNew;
            application.setAppRoleCollection(appRoleCollectionNew);
            application = em.merge(application);
            for (AppPermission appPermissionCollectionOldAppPermission : appPermissionCollectionOld) {
                if (!appPermissionCollectionNew.contains(appPermissionCollectionOldAppPermission)) {
                    appPermissionCollectionOldAppPermission.setIdApplication(null);
                    appPermissionCollectionOldAppPermission = em.merge(appPermissionCollectionOldAppPermission);
                }
            }
            for (AppPermission appPermissionCollectionNewAppPermission : appPermissionCollectionNew) {
                if (!appPermissionCollectionOld.contains(appPermissionCollectionNewAppPermission)) {
                    Application oldIdApplicationOfAppPermissionCollectionNewAppPermission = appPermissionCollectionNewAppPermission.getIdApplication();
                    appPermissionCollectionNewAppPermission.setIdApplication(application);
                    appPermissionCollectionNewAppPermission = em.merge(appPermissionCollectionNewAppPermission);
                    if (oldIdApplicationOfAppPermissionCollectionNewAppPermission != null && !oldIdApplicationOfAppPermissionCollectionNewAppPermission.equals(application)) {
                        oldIdApplicationOfAppPermissionCollectionNewAppPermission.getAppPermissionCollection().remove(appPermissionCollectionNewAppPermission);
                        oldIdApplicationOfAppPermissionCollectionNewAppPermission = em.merge(oldIdApplicationOfAppPermissionCollectionNewAppPermission);
                    }
                }
            }
            for (AppRole appRoleCollectionOldAppRole : appRoleCollectionOld) {
                if (!appRoleCollectionNew.contains(appRoleCollectionOldAppRole)) {
                    appRoleCollectionOldAppRole.setIdApplication(null);
                    appRoleCollectionOldAppRole = em.merge(appRoleCollectionOldAppRole);
                }
            }
            for (AppRole appRoleCollectionNewAppRole : appRoleCollectionNew) {
                if (!appRoleCollectionOld.contains(appRoleCollectionNewAppRole)) {
                    Application oldIdApplicationOfAppRoleCollectionNewAppRole = appRoleCollectionNewAppRole.getIdApplication();
                    appRoleCollectionNewAppRole.setIdApplication(application);
                    appRoleCollectionNewAppRole = em.merge(appRoleCollectionNewAppRole);
                    if (oldIdApplicationOfAppRoleCollectionNewAppRole != null && !oldIdApplicationOfAppRoleCollectionNewAppRole.equals(application)) {
                        oldIdApplicationOfAppRoleCollectionNewAppRole.getAppRoleCollection().remove(appRoleCollectionNewAppRole);
                        oldIdApplicationOfAppRoleCollectionNewAppRole = em.merge(oldIdApplicationOfAppRoleCollectionNewAppRole);
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
                Integer id = application.getId();
                if (findApplication(id) == null) {
                    throw new NonexistentEntityException("The application with id " + id + " no longer exists.");
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
            Application application;
            try {
                application = em.getReference(Application.class, id);
                application.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The application with id " + id + " no longer exists.", enfe);
            }
            Collection<AppPermission> appPermissionCollection = application.getAppPermissionCollection();
            for (AppPermission appPermissionCollectionAppPermission : appPermissionCollection) {
                appPermissionCollectionAppPermission.setIdApplication(null);
                appPermissionCollectionAppPermission = em.merge(appPermissionCollectionAppPermission);
            }
            Collection<AppRole> appRoleCollection = application.getAppRoleCollection();
            for (AppRole appRoleCollectionAppRole : appRoleCollection) {
                appRoleCollectionAppRole.setIdApplication(null);
                appRoleCollectionAppRole = em.merge(appRoleCollectionAppRole);
            }
            em.remove(application);
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

    public List<Application> findApplicationEntities() {
        return findApplicationEntities(true, -1, -1);
    }

    public List<Application> findApplicationEntities(int maxResults, int firstResult) {
        return findApplicationEntities(false, maxResults, firstResult);
    }

    private List<Application> findApplicationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Application as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Application findApplication(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Application.class, id);
        } finally {
            em.close();
        }
    }

    public int getApplicationCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Application as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
