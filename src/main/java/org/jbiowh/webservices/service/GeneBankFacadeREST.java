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
import org.jbiowhpersistence.datasets.gene.genebank.controller.GeneBankJpaController;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBank;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBankCDS;
import org.jbiowhpersistence.datasets.gene.genebank.search.SearchGeneBank;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the GeneBank webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Feb 28, 2014
 */
@Stateless
@Path("genbank")
public class GeneBankFacadeREST extends AbstractFacade<GeneBank> {

    public GeneBankFacadeREST() {
        super(GeneBank.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GeneBank find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByID(@PathParam("id") Long id) {
        GeneBank g = super.find(id);
        List<Protein> ps = new ArrayList();
        if (g != null && g.getGeneBankCDS() != null) {
            for (GeneBankCDS cds : g.getGeneBankCDS()) {
                for (GeneInfo gg : cds.getGeneInfo()) {
                    ps.addAll(gg.getProtein());
                }
            }
        }
        return ps;
    }

    @GET
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByID(@PathParam("id") Long id) {
        GeneBank g = super.find(id);
        List<GeneInfo> gs = new ArrayList();
        if (g != null && g.getGeneBankCDS() != null) {
            for (GeneBankCDS cds : g.getGeneBankCDS()) {
                gs.addAll(cds.getGeneInfo());
            }
        }
        return gs;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GeneBank> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("locusname/{id}")
    @Produces({"application/xml", "application/json"})
    public GeneBank findByLocusName(@PathParam("id") String id) {
        parm.clear();
        parm.put("locusName", id);
        try {
            return (GeneBank) (new GeneBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneBank.findByLocusName", parm);
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findByLocusName(id);
            }
        }
        return null;
    }

    @GET
    @Path("locusname/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByLocusName(@PathParam("id") String id) {
        List<Protein> ps = new ArrayList();
        parm.clear();
        parm.put("locusName", id);
        try {
            GeneBank g = (GeneBank) (new GeneBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneBank.findByLocusName", parm);
            if (g != null && g.getGeneBankCDS() != null) {
                for (GeneBankCDS cds : g.getGeneBankCDS()) {
                    for (GeneInfo gg : cds.getGeneInfo()) {
                        ps.addAll(gg.getProtein());
                    }
                }
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findProteinByLocusName(id);
            }
        }
        return ps;
    }

    @GET
    @Path("locusname/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByLocusName(@PathParam("id") String id) {
        List<GeneInfo> ps = new ArrayList();
        parm.clear();
        parm.put("locusName", id);
        try {
            GeneBank g = (GeneBank) (new GeneBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneBank.findByLocusName", parm);
            if (g != null && g.getGeneBankCDS() != null) {
                for (GeneBankCDS cds : g.getGeneBankCDS()) {
                    ps.addAll(cds.getGeneInfo());
                }
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findGeneInfoByLocusName(id);
            }
        }
        return ps;
    }

    @GET
    @Path("gi/{id}")
    @Produces({"application/xml", "application/json"})
    public GeneBank findByGi(@PathParam("id") Long id) {
        parm.clear();
        parm.put("gi", id);
        try {
            return (GeneBank) (new GeneBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneBank.findByGi", parm);
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findByGi(id);
        }
        return null;
    }

    @GET
    @Path("gi/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByGi(@PathParam("id") Long id) {
        List<Protein> ps = new ArrayList();
        parm.clear();
        parm.put("gi", id);
        try {
            GeneBank g = (GeneBank) (new GeneBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneBank.findByGi", parm);
            if (g != null && g.getGeneBankCDS() != null) {
                for (GeneBankCDS cds : g.getGeneBankCDS()) {
                    for (GeneInfo gg : cds.getGeneInfo()) {
                        ps.addAll(gg.getProtein());
                    }
                }
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findProteinByGi(id);
            }
        }
        return ps;
    }

    @GET
    @Path("gi/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByGi(@PathParam("id") Long id) {
        List<GeneInfo> ps = new ArrayList();
        parm.clear();
        parm.put("gi", id);
        try {
            GeneBank g = (GeneBank) (new GeneBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("GeneBank.findByGi", parm);
            if (g != null && g.getGeneBankCDS() != null) {
                for (GeneBankCDS cds : g.getGeneBankCDS()) {
                    ps.addAll(cds.getGeneInfo());
                }
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findGeneInfoByGi(id);
            }
        }
        return ps;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchGeneBank();
    }

}
