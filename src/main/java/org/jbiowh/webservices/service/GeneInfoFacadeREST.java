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
            return (GeneInfo) (new GeneInfoJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findByGeneID(id);
            }
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
            GeneInfo gene = (GeneInfo) (new GeneInfoJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getProtein() != null) {
                return new ArrayList(gene.getProtein());
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findProteinByGeneID(id);
            }
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
            GeneInfo gene = (GeneInfo) (new GeneInfoJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (gene != null && gene.getOmim() != null) {
                return new ArrayList(gene.getOmim());
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findOMIMByGeneID(id);
            }
        }
        return new ArrayList();
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchGeneInfo();
    }

}
