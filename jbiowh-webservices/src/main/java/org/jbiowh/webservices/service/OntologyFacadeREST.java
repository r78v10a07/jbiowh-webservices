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
import org.jbiowhpersistence.datasets.ontology.entities.OntologyAlternativeId;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyIsA;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyPMID;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyRelation;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyToConsider;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyXRef;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyhasOntologySynonym;
import org.jbiowhpersistence.datasets.ontology.search.SearchOntology;
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
    @Path("{id}/altid")
    @Produces({"application/xml", "application/json"})
    public List<OntologyAlternativeId> findAlternativeIdById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyAlternativeId() != null) {
            return new ArrayList(ont.getOntologyAlternativeId().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/isa")
    @Produces({"application/xml", "application/json"})
    public List<OntologyIsA> findIsAById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyIsA() != null) {
            return new ArrayList(ont.getOntologyIsA().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<OntologyPMID> findPMIDById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyPMID() != null) {
            return new ArrayList(ont.getOntologyPMID().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/relation")
    @Produces({"application/xml", "application/json"})
    public List<OntologyRelation> findRelationById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyRelation() != null) {
            return new ArrayList(ont.getOntologyRelation().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<OntologyhasOntologySynonym> findSynonymById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyhasOntologySynonym() != null) {
            return new ArrayList(ont.getOntologyhasOntologySynonym().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/toconsider")
    @Produces({"application/xml", "application/json"})
    public List<OntologyToConsider> findToConsiderById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyToConsider() != null) {
            return new ArrayList(ont.getOntologyToConsider().values());
        }
        return new ArrayList();
    }

    @GET
    @Path("{id}/xref")
    @Produces({"application/xml", "application/json"})
    public List<OntologyXRef> findXRefById(@PathParam("id") Long id) {
        Ontology ont = super.find(id);
        if (ont != null && ont.getOntologyXRef() != null) {
            return new ArrayList(ont.getOntologyXRef());
        }
        return new ArrayList();
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
    @Path("goid/{id}/altid")
    @Produces({"application/xml", "application/json"})
    public List<OntologyAlternativeId> findAlternativeIdByGOId(@PathParam("id") String id) {
        List<OntologyAlternativeId> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyAlternativeId() != null) {
                listR.addAll(o.getOntologyAlternativeId().values());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/isa")
    @Produces({"application/xml", "application/json"})
    public List<OntologyIsA> findIsAByGOId(@PathParam("id") String id) {
        List<OntologyIsA> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyIsA() != null) {
                listR.addAll(o.getOntologyIsA().values());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/pmid")
    @Produces({"application/xml", "application/json"})
    public List<OntologyPMID> findPMIDByGOId(@PathParam("id") String id) {
        List<OntologyPMID> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyPMID() != null) {
                listR.addAll(o.getOntologyPMID().values());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/relation")
    @Produces({"application/xml", "application/json"})
    public List<OntologyRelation> findRelationByGOId(@PathParam("id") String id) {
        List<OntologyRelation> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyRelation() != null) {
                listR.addAll(o.getOntologyRelation().values());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/synonym")
    @Produces({"application/xml", "application/json"})
    public List<OntologyhasOntologySynonym> findSynonymByGOId(@PathParam("id") String id) {
        List<OntologyhasOntologySynonym> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyhasOntologySynonym() != null) {
                listR.addAll(o.getOntologyhasOntologySynonym().values());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/toconsider")
    @Produces({"application/xml", "application/json"})
    public List<OntologyToConsider> findToConsiderByGOId(@PathParam("id") String id) {
        List<OntologyToConsider> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyToConsider() != null) {
                listR.addAll(o.getOntologyToConsider().values());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/xref")
    @Produces({"application/xml", "application/json"})
    public List<OntologyXRef> findXRefByGOId(@PathParam("id") String id) {
        List<OntologyXRef> listR = new ArrayList();
        parm.clear();
        parm.put("id", id);
        List<Ontology> onts = ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuery("Ontology.findById", parm);
        for (Ontology o : onts) {
            if (o.getOntologyXRef() != null) {
                listR.addAll(o.getOntologyXRef());
            }
        }
        return listR;
    }

    @GET
    @Path("goid/{id}/genes")
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
