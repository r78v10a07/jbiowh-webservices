package org.jbiowh.webservices.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the abstract facade class for the RESTful classes
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @param <T>
 * @since Nov 6, 2013
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private HashMap<Class, Object> controllers = null;
    protected final HashMap parm;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        parm = new HashMap();
    }

    /**
     * Get the JBioWHSearch search class
     *
     * @return the JBioWHSearch search class
     */
    protected abstract JBioWHSearch getSearch();

    protected EntityManager getEntityManager() {
        try {
            return JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager();
        } catch (PersistenceException ex) {
            return JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).createEntityManager();
        }
    }

    public T find(Object id) {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return find(id);
        }
    }

    public List<T> findAll() {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findAll();
        }
    }

    public List<T> findRange(int[] range) {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findRange(range);
        }
    }

    @GET
    @Path("search/{search}")
    @Produces({"application/xml", "application/json"})
    public List<T> findBySearch(@PathParam("search") String search) {
        try {
            JBioWHSearch searchClass = getSearch();
            if (searchClass != null) {
                return searchClass.search(search, null);
            }
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findBySearch(search);
        } catch (SQLException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult()).toString();
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return count();
        }
    }

}
