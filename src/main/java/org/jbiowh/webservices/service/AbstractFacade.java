package org.jbiowh.webservices.service;

import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;

/**
 * This class is
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @param <T>
 * @since Nov 6, 2013
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private HashMap<Class, Object> controllers = null;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Creates the JPA controller
     *
     * @return the JPA controller
     */
    protected abstract HashMap<Class, Object> createController();

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

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
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

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
