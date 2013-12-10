package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.protein.controller.ProteinJpaController;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protein.search.SearchProtein;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the Protein webservice
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 9, 2013
 */
@Stateless
@Path("protein")
public class ProteinFacadeREST extends AbstractFacade<Protein> {

    public ProteinFacadeREST() {
        super(Protein.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Protein find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfo(@PathParam("id") Long id) {
        Protein p = super.find(id);
        if (p != null && p.getGeneInfo() != null && !p.getGeneInfo().isEmpty()) {
            return new ArrayList(p.getGeneInfo());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/drugbank")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBank(@PathParam("id") Long id) {
        Protein p = super.find(id);
        if (p != null && p.getDrugBank() != null && !p.getDrugBank().isEmpty()) {
            return new ArrayList(p.getDrugBank());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/drugbankascarriers")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankAsCarriers(@PathParam("id") Long id) {
        Protein p = super.find(id);
        if (p != null && p.getDrugBankAsCarriers() != null && !p.getDrugBankAsCarriers().isEmpty()) {
            return new ArrayList(p.getDrugBankAsCarriers());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/drugbankastransporters")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankAsTransporters(@PathParam("id") Long id) {
        Protein p = super.find(id);
        if (p != null && p.getDrugBankAsTransporters() != null && !p.getDrugBankAsTransporters().isEmpty()) {
            return new ArrayList(p.getDrugBankAsTransporters());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/drugbankasenzyme")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankAsEnzyme(@PathParam("id") Long id) {
        Protein p = super.find(id);
        if (p != null && p.getDrugBankAsEnzyme() != null && !p.getDrugBankAsEnzyme().isEmpty()) {
            return new ArrayList(p.getDrugBankAsEnzyme());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/alldrugbank")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findAllDrugBank(@PathParam("id") Long id) {
        List<DrugBank> d = new ArrayList<DrugBank>();
        Protein p = super.find(id);
        if (p != null) {
            if (p.getDrugBank() != null && !p.getDrugBank().isEmpty()) {
                d.addAll(p.getDrugBank());
            }
            if (p.getDrugBankAsCarriers()!= null && !p.getDrugBankAsCarriers().isEmpty()) {
                d.addAll(p.getDrugBankAsCarriers());
            }
            if (p.getDrugBankAsTransporters()!= null && !p.getDrugBankAsTransporters().isEmpty()) {
                d.addAll(p.getDrugBank());
            }
            if (p.getDrugBankAsEnzyme()!= null && !p.getDrugBankAsEnzyme().isEmpty()) {
                d.addAll(p.getDrugBankAsEnzyme());
            }
        }
        return d;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("name/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findByName(@PathParam("id") String id) {
        parm.clear();
        parm.put("name", id);
        return (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
    }

    @GET
    @Path("name/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByName(@PathParam("id") String id) {
        List<GeneInfo> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getGeneInfo() != null && !p.getGeneInfo().isEmpty()) {
                    g.addAll(p.getGeneInfo());
                }
            }
        }
        return g;
    }

    @GET
    @Path("name/{id}/drugbank")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankByName(@PathParam("id") String id) {
        List<DrugBank> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getDrugBank() != null && !p.getDrugBank().isEmpty()) {
                    g.addAll(p.getDrugBank());
                }
            }
        }
        return g;
    }

    @GET
    @Path("name/{id}/drugbankascarriers")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankAsCarriersByName(@PathParam("id") String id) {
        List<DrugBank> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getDrugBankAsCarriers() != null && !p.getDrugBankAsCarriers().isEmpty()) {
                    g.addAll(p.getDrugBankAsCarriers());
                }
            }
        }
        return g;
    }

    @GET
    @Path("name/{id}/drugbankastransporters")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankAsTransportersByName(@PathParam("id") String id) {
        List<DrugBank> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getDrugBankAsTransporters() != null && !p.getDrugBankAsTransporters().isEmpty()) {
                    g.addAll(p.getDrugBankAsTransporters());
                }
            }
        }
        return g;
    }

    @GET
    @Path("name/{id}/drugbankasenzyme")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findDrugBankAsEnzymeByName(@PathParam("id") String id) {
        List<DrugBank> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getDrugBankAsEnzyme() != null && !p.getDrugBankAsEnzyme().isEmpty()) {
                    g.addAll(p.getDrugBankAsEnzyme());
                }
            }
        }
        return g;
    }

    @GET
    @Path("name/{id}/alldrugbank")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findAllDrugBankByName(@PathParam("id") String id) {
        List<DrugBank> g = new ArrayList();
        parm.clear();
        parm.put("name", id);
        List<Protein> ps = (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByName", parm);
        if (ps != null) {
            for (Protein p : ps) {
                if (p.getDrugBank() != null && !p.getDrugBank().isEmpty()) {
                    g.addAll(p.getDrugBank());
                }
                if (p.getDrugBankAsCarriers() != null && !p.getDrugBankAsCarriers().isEmpty()) {
                    g.addAll(p.getDrugBankAsCarriers());
                }
                if (p.getDrugBankAsTransporters() != null && !p.getDrugBankAsTransporters().isEmpty()) {
                    g.addAll(p.getDrugBankAsTransporters());
                }
                if (p.getDrugBankAsEnzyme() != null && !p.getDrugBankAsEnzyme().isEmpty()) {
                    g.addAll(p.getDrugBankAsEnzyme());
                }
            }
        }
        return g;
    }

    @GET
    @Path("accession/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findByAccession(@PathParam("id") String id) {
        parm.clear();
        parm.put("accessionNumber", id);
        return (new ProteinJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Protein.findProteinByAccessionNumber", parm);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchProtein();
    }
}
