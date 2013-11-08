package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.taxonomy.controller.TaxonomyJpaController;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomyPMID;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomySynonym;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomyUnParseCitation;

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

    private final HashMap parm;

    public TaxonomyFacadeREST() {
        super(Taxonomy.class);
        parm = new HashMap();
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
    @Path("taxid/{taxid}")
    @Produces({"application/xml", "application/json"})
    public Taxonomy findTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        List<Taxonomy> taxs = ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuery("Taxonomy.findByTaxId", parm);
        if (!taxs.isEmpty()) {
            return taxs.get(0);
        }
        return null;
    }

    @GET
    @Path("taxid/{taxid}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomySynonym> findSynonymByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        List<Taxonomy> taxs = ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuery("Taxonomy.findByTaxId", parm);
        if (!taxs.isEmpty()) {
            if (taxs.get(0).getSynonym() != null) {
                return new ArrayList(taxs.get(0).getSynonym().values());
            }
        }
        return new ArrayList();
    }

    @GET
    @Path("taxid/{taxid}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomyPMID> findPMIDByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        List<Taxonomy> taxs = ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuery("Taxonomy.findByTaxId", parm);
        if (!taxs.isEmpty()) {
            if (taxs.get(0).getPmid() != null) {
                return new ArrayList(taxs.get(0).getPmid().values());
            }
        }
        return new ArrayList();
    }

    @GET
    @Path("taxid/{taxid}/unparse")
    @Produces({"application/xml", "application/json"})
    public List<TaxonomyUnParseCitation> findUnParseByTaxId(@PathParam("taxid") Long taxid) {
        parm.clear();
        parm.put("taxId", taxid);
        List<Taxonomy> taxs = ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuery("Taxonomy.findByTaxId", parm);
        if (!taxs.isEmpty()) {
            if (taxs.get(0).getUnparse() != null) {
                return new ArrayList(taxs.get(0).getUnparse());
            }
        }
        return new ArrayList();
    }

    @GET
    @Path("synonym/{synonym}")
    @Produces({"application/xml", "application/json"})
    public List<Taxonomy> findBySynonym(@PathParam("synonym") String synonym) {
        parm.clear();
        parm.put("synonym", synonym.toUpperCase());
        List<Long> taxsWID = ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuery("Taxonomy.findTaxonomyWIDBySynonym", parm);
        if (!taxsWID.isEmpty()) {
            List<Taxonomy> taxs = new ArrayList();
            for (Long id : taxsWID) {
                taxs.add(super.find(id));
            }
            return taxs;
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
        controllers.put(TaxonomyJpaController.class, new TaxonomyJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

}
