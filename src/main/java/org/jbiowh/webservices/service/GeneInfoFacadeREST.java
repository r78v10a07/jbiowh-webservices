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
import org.jbiowhpersistence.datasets.gene.gene.controller.GeneInfoJpaController;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2Accession;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneGroup;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfoDBXrefs;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfoSynonyms;
import org.jbiowhpersistence.datasets.gene.gene.search.SearchGeneInfo;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the GeneInfo webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 9, 2013
 */
@Stateless
@Path("gene")
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
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("geneid/{id}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfoSynonyms> findSynonymByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGeneInfoSynonyms() != null) {
            return new ArrayList(gene.getGeneInfoSynonyms().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/xref")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfoDBXrefs> findXrefByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGeneInfoDBXrefs() != null) {
            return new ArrayList(gene.getGeneInfoDBXrefs().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/group")
    @Produces({"application/xml", "application/json"})
    public List<GeneGroup> findGroupByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGeneGroup() != null) {
            return new ArrayList(gene.getGeneGroup().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/accession")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Accession> findAccessionByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGene2Accession() != null) {
            return new ArrayList(gene.getGene2Accession().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/ensembl")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Accession> findEnsemblByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGene2Ensembl() != null) {
            return new ArrayList(gene.getGene2Ensembl());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Accession> findPMIDByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGene2PMID() != null) {
            return new ArrayList(gene.getGene2PMID().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/sts")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Accession> findSTSByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGene2STS() != null) {
            return new ArrayList(gene.getGene2STS().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/unigene")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Accession> findUniGeneByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        if (gene != null && gene.getGene2UniGene() != null) {
            return new ArrayList(gene.getGene2UniGene().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/ongenome")
    @Produces({"application/xml", "application/json"})
    public GenePTT findPTTByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGenePTT() != null) {
                gene.getGenePTT().setGeneInfo(null);
                return gene.getGenePTT();
            }
        } catch (NoResultException ex) {
        }
        return null;
    }

    @GET
    @Path("taxid/{id}")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findByTaxID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("taxID", id);
        return ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuery("GeneInfo.findByTaxID", parm);
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
