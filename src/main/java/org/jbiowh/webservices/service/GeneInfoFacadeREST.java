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
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.gene.gene.controller.GeneInfoJpaController;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.gene.search.SearchGeneInfo;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the GeneInfo webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 9, 2013
 */
@Stateless
@Path("geneinfo")
public class GeneInfoFacadeREST extends AbstractFacade<GeneInfo> {

    public GeneInfoFacadeREST() {
        super(GeneInfo.class);

    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GeneInfo find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getProtein() != null) {
            return new ArrayList(gene.getProtein());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/omim")
    @Produces({"application/xml", "application/json"})
    public List<OMIM> findOmimByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getOmim() != null) {
            return new ArrayList(gene.getOmim());
        }
        return new ArrayList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("geneid/{id}")
    @Produces({"application/xml", "application/json"})
    public GeneInfo findByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            return (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        } catch (NoResultException ex) {
        }
        return null;
    }

    @GET
    @Path("geneid/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getProtein() != null) {
                return new ArrayList(gene.getProtein());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/omim")
    @Produces({"application/xml", "application/json"})
    public List<OMIM> findOMIMByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getOmim() != null) {
                return new ArrayList(gene.getOmim());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
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
        controllers.put(GeneInfoJpaController.class, new GeneInfoJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchGeneInfo();
    }

}
