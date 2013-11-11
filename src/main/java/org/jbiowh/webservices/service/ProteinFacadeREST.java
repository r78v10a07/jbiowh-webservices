package org.jbiowh.webservices.service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.protein.controller.ProteinJpaController;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protein.search.SearchProtein;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the Protein webservice
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 9, 2013
 */
@Stateless
@Path("protein")
public class ProteinFacadeREST extends AbstractFacade<Protein> {

    public ProteinFacadeREST() {
        super(Protein.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Protein find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected HashMap<Class, Object> createController() {
        HashMap<Class, Object> controllers = new HashMap();
        controllers.put(ProteinJpaController.class, new ProteinJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchProtein();
    }
}
