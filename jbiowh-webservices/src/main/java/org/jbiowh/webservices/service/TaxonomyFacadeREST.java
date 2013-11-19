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
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.taxonomy.controller.TaxonomyJpaController;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomyPMID;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomySynonym;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomyUnParseCitation;
import org.jbiowhpersistence.datasets.taxonomy.search.SearchTaxonomy;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the Taxonomy webservice
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 8, 2013
 */
@Stateless
@Path("taxonomy")
public class TaxonomyFacadeREST extends AbstractFacade<Taxonomy> {

    public TaxonomyFacadeREST() {
        super(Taxonomy.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Taxonomy find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Taxonomy> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("{id}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomySynonym> findSynonymById(@PathParam("id") Long id) {
        Taxonomy tax = super.find(id);
        if (tax != null) {
            if (tax.getSynonym() != null) {
                return new ArrayList(tax.getSynonym().values());
            }
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomyPMID> findPMIDById(@PathParam("id") Long id) {
        Taxonomy tax = super.find(id);
        if (tax != null) {
            if (tax.getPmid() != null) {
                return new ArrayList(tax.getPmid().values());
            }
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/unparse")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomyUnParseCitation> findUnParseById(@PathParam("id") Long id) {
        Taxonomy tax = super.find(id);
        if (tax != null) {
            if (tax.getUnparse() != null) {
                return new ArrayList(tax.getUnparse());
            }
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/gene")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoById(@PathParam("id") Long id) {
        Taxonomy tax = super.find(id);
        if (tax != null) {
            parm.clear();
            parm.put("taxID", tax.getTaxId());
            return ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuery("GeneInfo.findByTaxID", parm);
        }
        return new ArrayList();
    }

    @GET
    @Path("taxid/{taxid}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomySynonym> findSynonymByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        try {
            Taxonomy taxs = (Taxonomy) ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            if (taxs.getSynonym() != null) {
                return new ArrayList(taxs.getSynonym().values());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("taxid/{taxid}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomyPMID> findPMIDByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        try {
            Taxonomy taxs = (Taxonomy) ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            if (taxs.getPmid() != null) {
                return new ArrayList(taxs.getPmid().values());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("taxid/{taxid}/unparse")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomyUnParseCitation> findUnParseByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        try {
            Taxonomy taxs = (Taxonomy) ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            if (taxs != null && taxs.getUnparse() != null) {
                return new ArrayList(taxs.getUnparse());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("taxid/{taxid}/gene")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxID", taxid);
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
        controllers.put(TaxonomyJpaController.class, new TaxonomyJpaController(getEntityManager().getEntityManagerFactory()));
        controllers.put(GeneInfoJpaController.class, new GeneInfoJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchTaxonomy();
    }

}
