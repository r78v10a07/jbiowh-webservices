/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbiowh.webservices.beans;

import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.dataset.controller.DataSetJpaController;
import org.jbiowhpersistence.datasets.dataset.entities.DataSet;
import org.jbiowhpersistence.utils.controller.AbstractController;

/**
 * This is the DataSet bean
 *
 * @author roberto
 */
@ManagedBean
@SessionScoped
public class DataSetBean extends AbstractController {

    private List<DataSet> datasets;

    /**
     * Creates a new instance of DataSetBean
     */
    public DataSetBean() {
    }

    public List<DataSet> getDatasets() {
        return ((DataSetJpaController) getController(DataSetJpaController.class)).findDataSetEntities();
    }
    
    public String getServer(){
        return JBioWHWebservicesSingleton.getInstance().getServer();
    }

    @Override
    protected HashMap<Class, Object> createController() {
        HashMap<Class, Object> controllers = new HashMap();
        controllers.put(DataSetJpaController.class, new DataSetJpaController(
                JBioWHWebservicesSingleton.getInstance().getWHEntityManager().createEntityManager().getEntityManagerFactory()));
        return controllers;
    }

}
