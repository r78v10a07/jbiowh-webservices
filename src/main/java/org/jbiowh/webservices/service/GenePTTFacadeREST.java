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
import org.jbiowhpersistence.datasets.gene.genebank.controller.GeneBankCDSJpaController;
import org.jbiowhpersistence.datasets.gene.genome.controller.GenePTTJpaController;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.gene.genome.search.SearchGenePTT;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the GenePTT webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 9, 2013
 */
@Stateless
@Path("geneptt")
public class GenePTTFacadeREST extends AbstractFacade<GenePTT> {

    public final String PROTEINGI = "ProteinGi";

    public GenePTTFacadeREST() {
        super(GenePTT.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GenePTT find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public GeneInfo findGeneInfoByProteinGi(@PathParam("id") Long id) {
        GenePTT g = super.find(id);
        if (g != null) {
            return g.getGeneInfo();
        }
        return null;
    }

    @GET
    @Path("chromosome/{id}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findGenePTTByPTTFileName(@PathParam("id") String id) {
        parm.clear();
        parm.put("pTTFile", id);
        try {
            return ((GenePTTJpaController) getController(GenePTTJpaController.class)).useNamedQuery("GenePTT.findByPTTFile", parm);
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("chromosome/{id}/gene")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneByPTTFileName(@PathParam("id") String id) {
        List<GeneInfo> genes = new ArrayList();
        parm.clear();
        parm.put("pTTFile", id);
        try {
            List<GenePTT> ptts = ((GenePTTJpaController) getController(GenePTTJpaController.class)).useNamedQuery("GenePTT.findByPTTFile", parm);
            for (GenePTT p : ptts) {
                if (p.getGeneInfo() != null) {
                    genes.add(p.getGeneInfo());
                }
            }
        } catch (NoResultException ex) {
        }
        return genes;
    }

    @GET
    @Path("chromosome/{id}/{from}/{to}/gene")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneByFileNameRange(@PathParam("id") String id, @PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<GeneInfo> genes = new ArrayList();
        parm.clear();
        parm.put("pTTFile", id);
        parm.put("pFrom", from);
        parm.put("pTo", to);
        try {
            List<GenePTT> ptts = ((GenePTTJpaController) getController(GenePTTJpaController.class)).useNamedQuery("GenePTT.findByPFromPToPTTFile", parm);
            for (GenePTT p : ptts) {
                if (p.getGeneInfo() != null) {
                    genes.add(p.getGeneInfo());
                }
            }
        } catch (NoResultException ex) {
        }
        return genes;
    }

    @GET
    @Path("chromosome/{id}/{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findGenePTTByFileNameRange(@PathParam("id") String id, @PathParam("from") Integer from, @PathParam("to") Integer to) {
        parm.clear();
        parm.put("pTTFile", id);
        parm.put("pFrom", from);
        parm.put("pTo", to);
        try {
            return ((GenePTTJpaController) getController(GenePTTJpaController.class)).useNamedQuery("GenePTT.findByPFromPToPTTFile", parm);
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
        controllers.put(GenePTTJpaController.class, new GenePTTJpaController(getEntityManager().getEntityManagerFactory()));
        controllers.put(GeneBankCDSJpaController.class, new GeneBankCDSJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchGenePTT();
    }
}
