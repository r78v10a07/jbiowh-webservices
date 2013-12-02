package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.ontology.controller.OntologyJpaController;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
import org.jbiowhpersistence.datasets.ontology.search.SearchOntology;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the Ontology webservice
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 8, 2013
 */
@Stateless
@Path("ontology")
public class OntologyFacadeREST extends AbstractFacade<Ontology> {

    public OntologyFacadeREST() {
        super(Ontology.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Ontology find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Ontology> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoById(@PathParam("id") String id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getGeneInfo() != null) {
            return new ArrayList(ont.getGeneInfo());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinById(@PathParam("id") String id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getProtein() != null) {
            return new ArrayList(ont.getProtein());
        }
        return new ArrayList();
    }

    @GET
    @Path("goid/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Ontology> findByGOId(@PathParam("id") String id) {
        List<Ontology> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        return ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
    }

    @GET
    @Path("goid/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoByGOId(@PathParam("id") String id) {
        List<GeneInfo> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getGeneInfo() != null) {
                listR.addAll(o.getGeneInfo());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/protein")
    @Produces({"application/xml", "application/json"})
    public List<Protein> findProteinByGOId(@PathParam("id") String id) {
        List<Protein> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getProtein() != null) {
                listR.addAll(o.getProtein());
            }
        }
        return listR;
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
        controllers.put(OntologyJpaController.class, new OntologyJpaController(getEntityManager().getEntityManagerFactory()));
        return controllers;
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchOntology();
    }

}
