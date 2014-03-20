package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.dataset.controller.DataSetJpaController;
import org.jbiowhpersistence.datasets.dataset.entities.DataSet;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the DataSet webservice
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 8, 2013
 */
@Stateless
@Path("dataset")
public class DataSetFacadeREST extends AbstractFacade<DataSet> {

    public DataSetFacadeREST() {
        super(DataSet.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public DataSet find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<DataSet> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<DataSet> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("name/{name}")
    @Produces({"application/xml", "application/json"})
    public List<DataSet> findName(@PathParam("name") String name) {
        try {
            parm.clear();
            parm.put("name", name);
            return (new DataSetJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DataSet.findByName", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findName(name);
            }
        }
        return new ArrayList<DataSet>();
    }

    @GET
    @Path("noname/{name}")
    @Produces({"application/xml", "application/json"})
    public List<DataSet> findNotName(@PathParam("name") String name) {
        try {
            parm.clear();
            parm.put("name", name);
            return (new DataSetJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DataSet.findByNoName", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findNotName(name);
            }
        }
        return new ArrayList<DataSet>();
    }

    @GET
    @Path("status/{status}")
    @Produces({"application/xml", "application/json"})
    public List<DataSet> findStatus(@PathParam("status") String name) {
        try {
            parm.clear();
            parm.put("status", name);
            return (new DataSetJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DataSet.findByStatus", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findStatus(name);
            }
        }
        return new ArrayList<DataSet>();
    }

    @GET
    @Path("nostatus/{status}")
    @Produces({"application/xml", "application/json"})
    public List<DataSet> findNotStatus(@PathParam("status") String name) {
        try {
            parm.clear();
            parm.put("status", name);
            return (new DataSetJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DataSet.findByNoStatus", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findNotStatus(name);
            }
        }
        return new ArrayList<DataSet>();
    }

    @Override
    protected JBioWHSearch getSearch() {
        return null;
    }

}
