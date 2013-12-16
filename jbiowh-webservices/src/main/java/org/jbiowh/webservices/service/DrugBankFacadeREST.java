package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.drug.drugbank.controller.DrugBankJpaController;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.drug.drugbank.search.SearchDrugBank;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the DrugBank Facade REST webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Dec 9, 2013
 */
@Stateless
@Path("drugbank")
public class DrugBankFacadeREST extends AbstractFacade<DrugBank> {

    public DrugBankFacadeREST() {
        super(DrugBank.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public DrugBank find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProtein(@PathParam("id") Long id) {
        DrugBank d = super.find(id);
        if (d != null && d.getProtein() != null && !d.getProtein().isEmpty()) {
            return new ArrayList(d.getProtein());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/proteinascarriers")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinAsCarriers(@PathParam("id") Long id) {
        DrugBank d = super.find(id);
        if (d != null && d.getProteinAsCarriers() != null && !d.getProteinAsCarriers().isEmpty()) {
            return new ArrayList(d.getProteinAsCarriers());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/proteinasenzyme")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinAsEnzyme(@PathParam("id") Long id) {
        DrugBank d = super.find(id);
        if (d != null && d.getProteinAsEnzyme() != null && !d.getProteinAsEnzyme().isEmpty()) {
            return new ArrayList(d.getProteinAsEnzyme());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/proteinastransporters")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinAsTransporters(@PathParam("id") Long id) {
        DrugBank d = super.find(id);
        if (d != null && d.getProteinAsTransporters() != null && !d.getProteinAsTransporters().isEmpty()) {
            return new ArrayList(d.getProteinAsTransporters());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/allprotein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findPAllrotein(@PathParam("id") Long id) {
        List<Protein> p = new ArrayList<Protein>();
        DrugBank d = super.find(id);
        if (d != null) {
            if (d.getProtein() != null && !d.getProtein().isEmpty()) {
                p.addAll(d.getProtein());
            }
            if (d.getProteinAsCarriers() != null && !d.getProteinAsCarriers().isEmpty()) {
                p.addAll(d.getProteinAsCarriers());
            }
            if (d.getProteinAsEnzyme() != null && !d.getProteinAsEnzyme().isEmpty()) {
                p.addAll(d.getProteinAsEnzyme());
            }
            if (d.getProteinAsTransporters() != null && !d.getProteinAsTransporters().isEmpty()) {
                p.addAll(d.getProteinAsTransporters());
            }
        }
        return p;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("id/{id}")
    @Produces({"application/xml", "application/json"})
    public List<DrugBank> findById(@PathParam("id") String id) {
        try {
            parm.clear();
            parm.put("id", id);
            return (new DrugBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DrugBank.findById", parm);
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findById(id);
        }
    }

    @GET
    @Path("id/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinById(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList<Protein>();
            parm.clear();
            parm.put("id", id);
            List<DrugBank> ds = (new DrugBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DrugBank.findById", parm);
            for (DrugBank d : ds) {
                if (d.getProtein() != null && !d.getProtein().isEmpty()) {
                    p.addAll(d.getProtein());
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findProteinById(id);
        }
    }

    @GET
    @Path("id/{id}/proteinascarriers")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinAsCarriersById(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList<Protein>();
            parm.clear();
            parm.put("id", id);
            List<DrugBank> ds = (new DrugBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DrugBank.findById", parm);
            for (DrugBank d : ds) {
                if (d.getProteinAsCarriers() != null && !d.getProteinAsCarriers().isEmpty()) {
                    p.addAll(d.getProteinAsCarriers());
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findProteinAsCarriersById(id);
        }
    }

    @GET
    @Path("id/{id}/proteinasenzyme")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinAsEnzymeById(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList<Protein>();
            parm.clear();
            parm.put("id", id);
            List<DrugBank> ds = (new DrugBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DrugBank.findById", parm);
            for (DrugBank d : ds) {
                if (d.getProteinAsEnzyme() != null && !d.getProteinAsEnzyme().isEmpty()) {
                    p.addAll(d.getProteinAsEnzyme());
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findProteinAsEnzymeById(id);
        }
    }

    @GET
    @Path("id/{id}/proteinastransporters")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinAsTransportersById(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList<Protein>();
            parm.clear();
            parm.put("id", id);
            List<DrugBank> ds = (new DrugBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DrugBank.findById", parm);
            for (DrugBank d : ds) {
                if (d.getProteinAsTransporters() != null && !d.getProteinAsTransporters().isEmpty()) {
                    p.addAll(d.getProteinAsTransporters());
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findProteinAsTransportersById(id);
        }
    }

    @GET
    @Path("id/{id}/allprotein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findAllProteinById(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList<Protein>();
            parm.clear();
            parm.put("id", id);
            List<DrugBank> ds = (new DrugBankJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("DrugBank.findById", parm);
            for (DrugBank d : ds) {
                if (d.getProtein() != null && !d.getProtein().isEmpty()) {
                    p.addAll(d.getProtein());
                }
                if (d.getProteinAsCarriers() != null && !d.getProteinAsCarriers().isEmpty()) {
                    p.addAll(d.getProteinAsCarriers());
                }
                if (d.getProteinAsEnzyme() != null && !d.getProteinAsEnzyme().isEmpty()) {
                    p.addAll(d.getProteinAsEnzyme());
                }
                if (d.getProteinAsTransporters() != null && !d.getProteinAsTransporters().isEmpty()) {
                    p.addAll(d.getProteinAsTransporters());
                }
            }
            return p;
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return findAllProteinById(id);
        }
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchDrugBank();
    }

}
