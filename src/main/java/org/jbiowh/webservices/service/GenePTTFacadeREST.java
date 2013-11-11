package org.jbiowh.webservices.service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.gene.genome.controller.GenePTTJpaController;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the GenePTT webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 9, 2013
 */
@Stateless
@Path("genome")
public class GenePTTFacadeREST extends AbstractFacade<GenePTT> {

    public GenePTTFacadeREST() {
        super(GenePTT.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GenePTT find(@PathParam("id") Long id) {
        GenePTT g = super.find(id);
        if (g != null) {
            g.setGeneInfo(null);
        }
        return g;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<GenePTT> gs = super.findRange(new int[]{from, to});
        for (GenePTT g : gs) {
            g.setGeneInfo(null);
        }
        return gs;
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
        controllers.put(GenePTTJpaController.class, new GenePTTJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return null;
    }
}
