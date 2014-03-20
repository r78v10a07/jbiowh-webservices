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
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protgroup.pirsf.controller.PirsfJpaController;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.Pirsf;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.PirsfhasProtein;
import org.jbiowhpersistence.datasets.protgroup.pirsf.search.SearchPirsf;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is The PIrSF webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Dec 12, 2013
 */
@Stateless
@Path("pirsf")
public class PirsfFacadeREST extends AbstractFacade<Pirsf> {

    public PirsfFacadeREST() {
        super(Pirsf.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Pirsf find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProtein(@PathParam("id") Long id) {
        List<Protein> p = new ArrayList();
        Pirsf pirsf = super.find(id);
        if (pirsf != null && pirsf.getpIRSFhasProtein() != null && !pirsf.getpIRSFhasProtein().isEmpty()) {
            for (PirsfhasProtein hp : pirsf.getpIRSFhasProtein().values()) {
                p.add(hp.getProtein());
            }
        }
        return p;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Pirsf> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("pirsfnumber/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Pirsf> findByName(@PathParam("id") String id) {
        try {
            parm.clear();
            parm.put("pIRSFnumber", id);
            return (new PirsfJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Pirsf.findByPIRSFnumber", parm);
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findByName(id);
            }
        }
        return new ArrayList<Pirsf>();
    }

    @GET
    @Path("pirsfnumber/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByName(@PathParam("id") String id) {
        try {
            List<Protein> p = new ArrayList();
            parm.clear();
            parm.put("pIRSFnumber", id);
            List<Pirsf> ps = (new PirsfJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuery("Pirsf.findByPIRSFnumber", parm);
            for (Pirsf pirsf : ps) {
                if (pirsf != null && pirsf.getpIRSFhasProtein() != null && !pirsf.getpIRSFhasProtein().isEmpty()) {
                    for (PirsfhasProtein hp : pirsf.getpIRSFhasProtein().values()) {
                        p.add(hp.getProtein());
                    }
                }
            }
            return p;
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return findProteinByName(id);
            }
        }
        return new ArrayList<Protein>();
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchPirsf();
    }

}
