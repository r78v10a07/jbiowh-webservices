/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbiowh.webservices.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.dataset.controller.DataSetJpaController;
import org.jbiowhpersistence.datasets.dataset.entities.DataSet;
import org.jbiowhpersistence.datasets.gene.gene.controller.GeneInfoJpaController;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.genome.controller.GenePTTJpaController;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.ontology.controller.OntologyJpaController;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
import org.jbiowhpersistence.datasets.protein.controller.ProteinJpaController;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.taxonomy.controller.TaxonomyJpaController;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;
import org.jbiowhpersistence.utils.controller.AbstractController;

/**
 * This is the DataSet bean
 *
 * @author roberto
 */
@Named(value = "dataSets")
@SessionScoped
public class DataSetBean extends AbstractController implements Serializable {

    private HashMap parm;

    /**
     * Creates a new instance of DataSetBean
     */
    public DataSetBean() {
        parm = new HashMap();
    }

    @Produces
    public List<DataSet> getDatasets() {
        return ((DataSetJpaController) getController(DataSetJpaController.class)).findDataSetEntities();
    }

    @Produces
    public String getServer() {
        return JBioWHWebservicesSingleton.getInstance().getServer();
    }

    @Produces
    public String getTaxonomyWID() {
        try {
            parm.clear();
            parm.put("taxId", 9906L);
            Taxonomy t = (Taxonomy) ((TaxonomyJpaController) getController(TaxonomyJpaController.class)).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        }
        return "";
    }

    @Produces
    public String getOntologyWID() {
        try {
            parm.clear();
            parm.put("id", "GO:0000011");
            Ontology t = (Ontology) ((OntologyJpaController) getController(OntologyJpaController.class)).useNamedQuerySingleResult("Ontology.findById", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        }
        return "";
    }

    @Produces
    public String getGeneInfoWID() {
        try {
            parm.clear();
            parm.put("geneID", 2947819L);
            GeneInfo t = (GeneInfo) ((GeneInfoJpaController) getController(GeneInfoJpaController.class)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        }
        return "";
    }

    @Produces
    public String getGenePTTProteinGi() {
        try {
            parm.clear();
            parm.put("proteinGi", 148558178L);
            GenePTT t = (GenePTT) ((GenePTTJpaController) getController(GenePTTJpaController.class)).useNamedQuerySingleResult("GenePTT.findByProteinGi", parm);
            if (t != null) {
                return t.getProteinGi().toString();
            }
        } catch (NoResultException ex) {
        }
        return "";
    }

    @Produces
    public String getProteinWID() {
        List<Protein> t = ((ProteinJpaController) getController(ProteinJpaController.class)).findProteinEntities(1, 1);
        if (t != null && !t.isEmpty()) {
            return t.get(0).getWid().toString();
        }
        return "";
    }

    @Override
    protected HashMap<Class, Object> createController() {
        HashMap<Class, Object> controllers = new HashMap();
        controllers.put(DataSetJpaController.class, new DataSetJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        controllers.put(TaxonomyJpaController.class, new TaxonomyJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        controllers.put(OntologyJpaController.class, new OntologyJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        controllers.put(GeneInfoJpaController.class, new GeneInfoJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        controllers.put(GenePTTJpaController.class, new GenePTTJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        controllers.put(ProteinJpaController.class, new ProteinJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        return controllers;
    }

}
