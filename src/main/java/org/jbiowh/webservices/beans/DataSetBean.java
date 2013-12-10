/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbiowh.webservices.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.dataset.controller.DataSetJpaController;
import org.jbiowhpersistence.datasets.dataset.entities.DataSet;
import org.jbiowhpersistence.datasets.disease.omim.controller.OMIMJpaController;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.drug.drugbank.controller.DrugBankJpaController;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
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

/**
 * This is the DataSet bean
 *
 * @author roberto
 */
@Named(value = "dataSets")
@SessionScoped
public class DataSetBean implements Serializable {

    private HashMap parm;

    /**
     * Creates a new instance of DataSetBean
     */
    public DataSetBean() {
        parm = new HashMap();
    }

    @Produces
    public List<DataSet> getDatasets() {
        try {
            return new DataSetJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).findDataSetEntities();
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getDatasets();
        }
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
            Taxonomy t = (Taxonomy) new TaxonomyJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getTaxonomyWID();
        }
        return "";
    }

    @Produces
    public String getOntologyWID() {
        try {
            parm.clear();
            parm.put("id", "GO:0000011");
            Ontology t = (Ontology) new OntologyJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).useNamedQuerySingleResult("Ontology.findById", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getOntologyWID();
        }
        return "";
    }

    @Produces
    public String getGeneInfoWID() {
        try {
            parm.clear();
            parm.put("geneID", 2947819L);
            GeneInfo t = (GeneInfo) new GeneInfoJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getGeneInfoWID();
        }
        return "";
    }

    @Produces
    public String getGenePTTProteinGi() {
        try {
            parm.clear();
            parm.put("proteinGi", 148558178L);
            GenePTT t = (GenePTT) new GenePTTJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).useNamedQuerySingleResult("GenePTT.findByProteinGi", parm);
            if (t != null) {
                return t.getProteinGi().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getGenePTTProteinGi();
        }
        return "";
    }

    @Produces
    public String getProteinWID() {
        try {
            List<Protein> t = new ProteinJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).findProteinEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getProteinWID();
        }
        return "";
    }

    @Produces
    public String getOMIMWID() {
        try {
            List<OMIM> t = new OMIMJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).findOMIMEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getOMIMWID();
        }
        return "";
    }

    @Produces
    public String getDrugBankWID() {
        try {
            List<DrugBank> t = new DrugBankJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager().getEntityManagerFactory()).findDrugBankEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true);
            return getDrugBankWID();
        }
        return "";
    }
}
