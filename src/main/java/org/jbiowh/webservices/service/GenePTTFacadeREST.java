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
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.genome.controller.GenePTTJpaController;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.gene.genome.search.SearchGenePTT;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
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
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public Protein findProtein(@PathParam("id") Long id) {
        GenePTT g = super.find(id);
        if (g != null) {
            return g.getProtein();
        }
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("chromosome/{id}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findGenePTTByPTTFileName(@PathParam("id") String id) {
        parm.clear();
        parm.put("pTTFile", id);
        try {
            return (new GenePTTJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GenePTT.findByPTTFile", parm);
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findGenePTTByPTTFileName(id);
        }
        return new ArrayList();
    }

    @GET
    @Path("chromosome/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByPTTFileName(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList();
            parm.clear();
            parm.put("pTTFile", id);
            List<GenePTT> gs = (new GenePTTJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GenePTT.findByPTTFile", parm);
            if (gs != null) {
                for (GenePTT g : gs) {
                    if (g.getProtein() != null) {
                        p.add(g.getProtein());
                    }
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findProteinByPTTFileName(id);
        }

    }

    @GET
    @Path("chromosome/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneByPTTFileName(@PathParam("id") String id) {
        try {
            List<GeneInfo> genes = new ArrayList();
            parm.clear();
            parm.put("pTTFile", id);
            List<GenePTT> ptts = (new GenePTTJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GenePTT.findByPTTFile", parm);
            for (GenePTT p : ptts) {
                if (p.getGeneInfo() != null) {
                    genes.add(p.getGeneInfo());
                }
            }
            return genes;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findGeneByPTTFileName(id);
        }
    }

    @GET
    @Path("chromosome/{id}/{from}/{to}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneByFileNameRange(@PathParam("id") String id, @PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            List<GeneInfo> genes = new ArrayList();
            parm.clear();
            parm.put("pTTFile", id);
            parm.put("pFrom", from);
            parm.put("pTo", to);
            List<GenePTT> ptts = (new GenePTTJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GenePTT.findByPFromPToPTTFile", parm);
            for (GenePTT p : ptts) {
                if (p.getGeneInfo() != null) {
                    genes.add(p.getGeneInfo());
                }
            }
            return genes;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findGeneByFileNameRange(id, from, to);
        }
    }

    @GET
    @Path("chromosome/{id}/{from}/{to}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByFileNameRange(@PathParam("id") String id, @PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            List<Protein> p = new ArrayList();
            parm.clear();
            parm.put("pTTFile", id);
            parm.put("pFrom", from);
            parm.put("pTo", to);
            List<GenePTT> ptts = (new GenePTTJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GenePTT.findByPFromPToPTTFile", parm);
            for (GenePTT g : ptts) {
                if (g.getProtein() != null) {
                    p.add(g.getProtein());
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findProteinByFileNameRange(id, from, to);
        }
    }

    @GET
    @Path("chromosome/{id}/{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GenePTT> findGenePTTByFileNameRange(@PathParam("id") String id, @PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            parm.clear();
            parm.put("pTTFile", id);
            parm.put("pFrom", from);
            parm.put("pTo", to);
            return (new GenePTTJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("GenePTT.findByPFromPToPTTFile", parm);
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findGenePTTByFileNameRange(id, from, to);
        }
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchGenePTT();
    }
}
