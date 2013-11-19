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
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2Ensembl;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2GenomicNucleotide;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2PMID;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2ProteinAccession;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2RNANucleotide;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2STS;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2UniGene;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneGroup;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfoDBXrefs;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfoSynonyms;
import org.jbiowhpersistence.datasets.gene.gene.search.SearchGeneInfo;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.gene.genome.entities.GeneRNT;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
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
    @Path("{id}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfoSynonyms> findSynonymByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGeneInfoSynonyms() != null) {
            return new ArrayList(gene.getGeneInfoSynonyms());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/xref")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfoDBXrefs> findXrefByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGeneInfoDBXrefs() != null) {
            return new ArrayList(gene.getGeneInfoDBXrefs());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/group")
    @Produces({"application/xml", "application/json"})
    public List<GeneGroup> findGroupByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGeneGroup() != null) {
            return new ArrayList(gene.getGeneGroup());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/proteinaccession")
    @Produces({"application/xml", "application/json"})
    public List<Gene2ProteinAccession> findProteinAccessionByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2ProteinAccessions() != null) {
            return new ArrayList(gene.getGene2ProteinAccessions());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/genomicnucleotide")
    @Produces({"application/xml", "application/json"})
    public List<Gene2GenomicNucleotide> findGenomicNucleotideByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2GenomicNucleotides() != null) {
            return new ArrayList(gene.getGene2GenomicNucleotides());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/rnanucleotide")
    @Produces({"application/xml", "application/json"})
    public List<Gene2RNANucleotide> findRNANucleotideByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2RNANucleotides() != null) {
            return new ArrayList(gene.getGene2RNANucleotides());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/ensembl")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Ensembl> findEnsemblByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2Ensembl() != null) {
            return new ArrayList(gene.getGene2Ensembl());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<Gene2PMID> findPMIDByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2PMID() != null) {
            return new ArrayList(gene.getGene2PMID());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/sts")
    @Produces({"application/xml", "application/json"})
    public List<Gene2STS> findSTSByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2STS() != null) {
            return new ArrayList(gene.getGene2STS());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/unigene")
    @Produces({"application/xml", "application/json"})
    public List<Gene2UniGene> findUniGeneByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGene2UniGene() != null) {
            return new ArrayList(gene.getGene2UniGene());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/ptt")
    @Produces({"application/xml", "application/json"})
    public GenePTT findPTTBID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGenePTT() != null) {
            return gene.getGenePTT();
        }
        return null;
    }

    @GET
    @Path("{id}/rnt")
    @Produces({"application/xml", "application/json"})
    public List<GeneRNT> findRNTByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getGenePTT() != null) {
            return new ArrayList(gene.getGeneRNT());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/ontology")
    @Produces({"application/xml", "application/json"})
    public List<Ontology> findOntologyByID(@PathParam("id") Long id) {
        GeneInfo gene = super.find(id);
        if (gene != null && gene.getOntology() != null) {
            return new ArrayList(gene.getOntology());
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
    @Path("geneid/{id}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfoSynonyms> findSynonymByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGeneInfoSynonyms() != null) {
                return new ArrayList(gene.getGeneInfoSynonyms());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/xref")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfoDBXrefs> findXrefByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGeneInfoDBXrefs() != null) {
                return new ArrayList(gene.getGeneInfoDBXrefs());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/group")
    @Produces({"application/xml", "application/json"})
    public List<GeneGroup> findGroupByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGeneGroup() != null) {
                return new ArrayList(gene.getGeneGroup());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/proteinaccession")
    @Produces({"application/xml", "application/json"})
    public List<Gene2ProteinAccession> findProteinAccessionByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2ProteinAccessions() != null) {
                return new ArrayList(gene.getGene2ProteinAccessions());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/genomicnucleotide")
    @Produces({"application/xml", "application/json"})
    public List<Gene2GenomicNucleotide> findGenomicNucleotideByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2GenomicNucleotides() != null) {
                return new ArrayList(gene.getGene2GenomicNucleotides());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/rnanucleotide")
    @Produces({"application/xml", "application/json"})
    public List<Gene2RNANucleotide> findRNANucleotideByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2RNANucleotides() != null) {
                return new ArrayList(gene.getGene2RNANucleotides());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/ensembl")
    @Produces({"application/xml", "application/json"})
    public List<Gene2Ensembl> findEnsemblByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2Ensembl() != null) {
                return new ArrayList(gene.getGene2Ensembl());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<Gene2PMID> findPMIDByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2PMID() != null) {
                return new ArrayList(gene.getGene2PMID());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/sts")
    @Produces({"application/xml", "application/json"})
    public List<Gene2STS> findSTSByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2STS() != null) {
                return new ArrayList(gene.getGene2STS());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/unigene")
    @Produces({"application/xml", "application/json"})
    public List<Gene2UniGene> findUniGeneByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGene2UniGene() != null) {
                return new ArrayList(gene.getGene2UniGene());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/ptt")
    @Produces({"application/xml", "application/json"})
    public GenePTT findPTTByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGenePTT() != null) {
                return gene.getGenePTT();
            }
        } catch (NoResultException ex) {
        }
        return null;
    }

    @GET
    @Path("geneid/{id}/rnt")
    @Produces({"application/xml", "application/json"})
    public List<GeneRNT> findRNTByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getGenePTT() != null) {
                return new ArrayList(gene.getGeneRNT());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("geneid/{id}/ontology")
    @Produces({"application/xml", "application/json"})
    public List<Ontology> findOntologyByGeneID(@PathParam("id") Long id) {
        parm.clear();
        parm.put("geneID", id);
        try {
            GeneInfo gene = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getOntology() != null) {
                return new ArrayList(gene.getOntology());
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
