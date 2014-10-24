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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.jbiowh.webservices.utils.DataSetwithCount;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.dataset.controller.DataSetJpaController;
import org.jbiowhpersistence.datasets.dataset.entities.DataSet;
import org.jbiowhpersistence.datasets.disease.omim.controller.OMIMJpaController;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.drug.drugbank.controller.DrugBankJpaController;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.gene.gene.controller.GeneInfoJpaController;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.genebank.controller.GeneBankJpaController;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBank;
import org.jbiowhpersistence.datasets.gene.genome.controller.GenePTTJpaController;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.ontology.controller.OntologyJpaController;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
import org.jbiowhpersistence.datasets.protein.controller.ProteinJpaController;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protgroup.pirsf.controller.PirsfJpaController;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.Pirsf;
import org.jbiowhpersistence.datasets.taxonomy.controller.TaxonomyJpaController;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;

/**
 * This is the DataSet bean
 *
 * @author roberto
 */
@Named(value = "dataSets")
@SessionScoped
public class DataSetBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(DataSetBean.class.getName());

    private HashMap parm;

    /**
     * Creates a new instance of DataSetBean
     */
    public DataSetBean() {
        parm = new HashMap();
    }

    @Produces
    public List<DataSetwithCount> getDatasets() {
        try {
            LOG.info("INFO: Loading datasets");
            DataSetJpaController ctrl = new DataSetJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false));
            JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).getCache().evict(DataSet.class);
            List<DataSet> ds = ctrl.findDataSetEntities();

            List<DataSetwithCount> dsc = new ArrayList();
            for (DataSet d : ds) {
                DataSetwithCount c = new DataSetwithCount();
                c.setName(d.getName());
                c.setReleaseDate(d.getReleaseDate());
                c.setLoadDate(d.getLoadDate());
                c.setChangeDate(d.getChangeDate());
                c.setHomeURL(d.getHomeURL());
                c.setApplication(d.getApplication());
                c.setStatus(d.getStatus());
                c.setCount(getDataSetCount(EntityParserFieldProxy.getInstance().getClassType(d.getApplication().replace("Loader", ""))));
                dsc.add(c);
            }

            return dsc;
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getDatasets();
            }
        }
        return new ArrayList<DataSetwithCount>();
    }

    /**
     * Count all DataSet entities objects
     *
     * @return a int with the amount of Dataset entities inserted
     */
    private int getDataSetCount(Class theClass) {
        EntityManager em = JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false).createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DataSet> rt = cq.from(theClass);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
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
            Taxonomy t = (Taxonomy) new TaxonomyJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("Taxonomy.findByTaxId", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getTaxonomyWID();
            }
        }
        return "";
    }

    @Produces
    public String getOntologyWID() {
        try {
            parm.clear();
            parm.put("id", "GO:0000011");
            Ontology t = (Ontology) new OntologyJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("Ontology.findById", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getOntologyWID();
            }
        }
        return "";
    }

    @Produces
    public String getGeneInfoWID() {
        try {
            parm.clear();
            parm.put("geneID", 2947819L);
            GeneInfo t = (GeneInfo) new GeneInfoJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("GeneInfo.findByGeneID", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getGeneInfoWID();
            }
        }
        return "";
    }

    @Produces
    public String getGeneBankWID() {
        try {
            parm.clear();
            parm.put("locusName", "CP002312");
            GeneBank t = (GeneBank) new GeneBankJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("GeneBank.findByLocusName", parm);
            if (t != null) {
                return t.getWid().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getGeneInfoWID();
            }
        }
        return "";
    }

    @Produces
    public String getGeneBankGi() {
        try {
            parm.clear();
            parm.put("locusName", "CP002312");
            GeneBank t = (GeneBank) new GeneBankJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("GeneBank.findByLocusName", parm);
            if (t != null) {
                return Integer.toString(t.getGi());
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getGeneInfoWID();
            }
        }
        return "";
    }

    @Produces
    public String getGeneBankProteinGi() {
        try {
            parm.clear();
            parm.put("locusName", "CP002312");
            GeneBank t = (GeneBank) new GeneBankJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("GeneBank.findByLocusName", parm);
            if (t != null) {
                if (t.getGeneBankCDS() != null && t.getGeneBankCDS().isEmpty()) {
                    return Integer.toString(t.getGeneBankCDS().iterator().next().getProteinGi());
                }
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getGeneInfoWID();
            }
        }
        return "";
    }

    @Produces
    public String getGenePTTProteinGi() {
        try {
            parm.clear();
            parm.put("proteinGi", 148558178L);
            GenePTT t = (GenePTT) new GenePTTJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).useNamedQuerySingleResult("GenePTT.findByProteinGi", parm);
            if (t != null) {
                return t.getProteinGi().toString();
            }
        } catch (NoResultException ex) {
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getGenePTTProteinGi();
            }
        }
        return "";
    }

    @Produces
    public String getProteinWID() {
        try {
            List<Protein> t = new ProteinJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).findProteinEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getProteinWID();
            }
        }
        return "";
    }

    @Produces
    public String getOMIMWID() {
        try {
            List<OMIM> t = new OMIMJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).findOMIMEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getOMIMWID();
            }
        }
        return "";
    }

    @Produces
    public String getDrugBankWID() {
        try {
            List<DrugBank> t = new DrugBankJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).findDrugBankEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getDrugBankWID();
            }
        }
        return "";
    }

    @Produces
    public String getPIRSFWID() {
        try {
            List<Pirsf> t = new PirsfJpaController(JBioWHWebservicesSingleton.getInstance().getWHEntityManager(false)).findPirsfEntities(1, 1);
            if (t != null && !t.isEmpty()) {
                return t.get(0).getWid().toString();
            }
        } catch (PersistenceException ex) {
            if (JBioWHWebservicesSingleton.getInstance().getWHEntityManager(true).isOpen()) {
                return getPIRSFWID();
            }
        }
        return "";
    }
}
