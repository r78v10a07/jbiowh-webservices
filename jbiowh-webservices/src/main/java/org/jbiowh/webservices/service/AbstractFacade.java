package org.jbiowh.webservices.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
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
     * Creates the JPA controller
     *
     * @return the JPA controller
     */
    protected abstract HashMap<Class, Object> createController();

    /**
     * Get the JBioWHSearch search class
     *
     * @return the JBioWHSearch search class
     */
    protected abstract JBioWHSearch getSearch();

    /**
     * Get the JPA controller and check is it is created or not
     *
     * @param controllerClass
     * @return the JPA controller
     */
    protected Object getController(Class controllerClass) {
        if (controllers == null) {
            controllers = createController();
        }
        return controllers.get(controllerClass);
    }

    protected EntityManager getEntityManager() {
        return JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @GET
    @Path("search/{search}")
    @Produces({"application/xml", "application/json"})
    public List<T> findBySearch(@PathParam("search") String search) {
        try {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager();
            JBioWHSearch searchClass = getSearch();
            if (searchClass != null) {
                return searchClass.search(search, null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
