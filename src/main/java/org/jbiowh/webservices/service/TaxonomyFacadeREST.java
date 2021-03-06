package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.gene.gene.controller.GeneInfoJpaController;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.protein.controller.ProteinJpaController;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.taxonomy.controller.TaxonomyJpaController;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;
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
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoById(@PathParam("id") Long id) {
        try {
            Taxonomy tax = super.find(id);
            if (tax != null) {
                parm.clear();
                parm.put("taxID", tax.getTaxId());
                return (new GeneInfoJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GeneInfo.findByTaxID", parm);
            }
            return new ArrayList();
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findGeneInfoById(id);
            }
        }
        return new ArrayList<GeneInfo>();
    }

    @GET
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinById(@PathParam("id") Long id) {
        try {
            Taxonomy tax = super.find(id);
            if (tax != null) {
                parm.clear();
                parm.put("taxId", tax.getTaxId());
                return (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findByTaxId", parm);
            }
            return new ArrayList();
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findProteinById(id);
            }
        }
        return new ArrayList<Protein>();
    }

    @GET
    @Path("taxid/{taxid}")
    @Produces({"application/xml", "application/json"})
    public Taxonomy findByTaxId(@PathParam("taxid") Long taxid) {
        try {
            parm.clear();
            parm.put("taxId", taxid);
            try {
                return (Taxonomy) (new TaxonomyJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            } catch (NoResultException ex) {
            }
            return null;
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findByTaxId(taxid);
            }
        }
        return null;
    }

    @GET
    @Path("taxid/{taxid}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByTaxId(@PathParam("taxid") Long taxid) {
        try {
            parm.clear();
            parm.put("taxID", taxid);
            return (new GeneInfoJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GeneInfo.findByTaxID", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findGeneInfoByTaxId(taxid);
            }
        }
        return new ArrayList<GeneInfo>();
    }

    @GET
    @Path("taxid/{taxid}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByTaxId(@PathParam("taxid") Long taxid) {
        try {
            parm.clear();
            parm.put("taxId", taxid);
            return (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findByTaxId", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findProteinByTaxId(taxid);
            }
        }
        return new ArrayList<Protein>();
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchTaxonomy();
    }

}
