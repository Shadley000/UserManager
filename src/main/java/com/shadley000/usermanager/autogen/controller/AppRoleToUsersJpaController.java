/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.usermanager.autogen.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.shadley000.usermanager.autogen.entities.AppUser;
import com.shadley000.usermanager.autogen.entities.AppRole;
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
public class AppRoleToUsersJpaController implements Serializable {

    public AppRoleToUsersJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AppRoleToUsers appRoleToUsers) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppUser idAppUser = appRoleToUsers.getIdAppUser();
            if (idAppUser != null) {
                idAppUser = em.getReference(idAppUser.getClass(), idAppUser.getId());
                appRoleToUsers.setIdAppUser(idAppUser);
            }
            AppRole idAppRole = appRoleToUsers.getIdAppRole();
            if (idAppRole != null) {
                idAppRole = em.getReference(idAppRole.getClass(), idAppRole.getId());
                appRoleToUsers.setIdAppRole(idAppRole);
            }
            em.persist(appRoleToUsers);
            if (idAppUser != null) {
                idAppUser.getAppRoleToUsersCollection().add(appRoleToUsers);
                idAppUser = em.merge(idAppUser);
            }
            if (idAppRole != null) {
                idAppRole.getAppRoleToUsersCollection().add(appRoleToUsers);
                idAppRole = em.merge(idAppRole);
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

    public void edit(AppRoleToUsers appRoleToUsers) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AppRoleToUsers persistentAppRoleToUsers = em.find(AppRoleToUsers.class, appRoleToUsers.getId());
            AppUser idAppUserOld = persistentAppRoleToUsers.getIdAppUser();
            AppUser idAppUserNew = appRoleToUsers.getIdAppUser();
            AppRole idAppRoleOld = persistentAppRoleToUsers.getIdAppRole();
            AppRole idAppRoleNew = appRoleToUsers.getIdAppRole();
            if (idAppUserNew != null) {
                idAppUserNew = em.getReference(idAppUserNew.getClass(), idAppUserNew.getId());
                appRoleToUsers.setIdAppUser(idAppUserNew);
            }
            if (idAppRoleNew != null) {
                idAppRoleNew = em.getReference(idAppRoleNew.getClass(), idAppRoleNew.getId());
                appRoleToUsers.setIdAppRole(idAppRoleNew);
            }
            appRoleToUsers = em.merge(appRoleToUsers);
            if (idAppUserOld != null && !idAppUserOld.equals(idAppUserNew)) {
                idAppUserOld.getAppRoleToUsersCollection().remove(appRoleToUsers);
                idAppUserOld = em.merge(idAppUserOld);
            }
            if (idAppUserNew != null && !idAppUserNew.equals(idAppUserOld)) {
                idAppUserNew.getAppRoleToUsersCollection().add(appRoleToUsers);
                idAppUserNew = em.merge(idAppUserNew);
            }
            if (idAppRoleOld != null && !idAppRoleOld.equals(idAppRoleNew)) {
                idAppRoleOld.getAppRoleToUsersCollection().remove(appRoleToUsers);
                idAppRoleOld = em.merge(idAppRoleOld);
            }
            if (idAppRoleNew != null && !idAppRoleNew.equals(idAppRoleOld)) {
                idAppRoleNew.getAppRoleToUsersCollection().add(appRoleToUsers);
                idAppRoleNew = em.merge(idAppRoleNew);
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
                Integer id = appRoleToUsers.getId();
                if (findAppRoleToUsers(id) == null) {
                    throw new NonexistentEntityException("The appRoleToUsers with id " + id + " no longer exists.");
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
            AppRoleToUsers appRoleToUsers;
            try {
                appRoleToUsers = em.getReference(AppRoleToUsers.class, id);
                appRoleToUsers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appRoleToUsers with id " + id + " no longer exists.", enfe);
            }
            AppUser idAppUser = appRoleToUsers.getIdAppUser();
            if (idAppUser != null) {
                idAppUser.getAppRoleToUsersCollection().remove(appRoleToUsers);
                idAppUser = em.merge(idAppUser);
            }
            AppRole idAppRole = appRoleToUsers.getIdAppRole();
            if (idAppRole != null) {
                idAppRole.getAppRoleToUsersCollection().remove(appRoleToUsers);
                idAppRole = em.merge(idAppRole);
            }
            em.remove(appRoleToUsers);
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

    public List<AppRoleToUsers> findAppRoleToUsersEntities() {
        return findAppRoleToUsersEntities(true, -1, -1);
    }

    public List<AppRoleToUsers> findAppRoleToUsersEntities(int maxResults, int firstResult) {
        return findAppRoleToUsersEntities(false, maxResults, firstResult);
    }

    private List<AppRoleToUsers> findAppRoleToUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AppRoleToUsers as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AppRoleToUsers findAppRoleToUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AppRoleToUsers.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppRoleToUsersCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from AppRoleToUsers as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
