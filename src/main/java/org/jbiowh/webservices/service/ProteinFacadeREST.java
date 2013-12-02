package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
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
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfo(@PathParam("id") Long id) {
        Protein p = super.find(id);
        if (p != null) {
            return new ArrayList(p.getGeneInfo());
        }
        return new ArrayList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("name/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findByName(@PathParam("id") String id) {
        parm.clear();
        parm.put("name", id);
        return ((ProteinJpaController) getController(ProteinJpaController.class)).useNamedQuery("Protein.findProteinByName", parm);
    }

    @GET
    @Path("name/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByName(@PathParam("id") String id) {
        List<GeneInfo> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = ((ProteinJpaController) getController(ProteinJpaController.class)).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getGeneInfo() != null) {
                    g.addAll(p.getGeneInfo());
                }
            }
        }
        return g;
    }

    @GET
    @Path("accession/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findByAccession(@PathParam("id") String id) {
        parm.clear();
        parm.put("accessionNumber", id);
        try {
            return ((ProteinJpaController) getController(ProteinJpaController.class)).useNamedQuery("Protein.findProteinByAccessionNumber", parm);
        } catch (NoResultException ex) {
        }
        return null;
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
