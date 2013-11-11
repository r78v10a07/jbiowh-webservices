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
import org.jbiowh.webservices.utils.JBioWHWebservicesSingleton;
import org.jbiowhpersistence.datasets.dataset.controller.DataSetJpaController;
import org.jbiowhpersistence.datasets.dataset.entities.DataSet;
import org.jbiowhpersistence.utils.controller.AbstractController;

/**
 * This is the DataSet bean
 *
 * @author roberto
 */
@Named(value = "dataSets")
@SessionScoped
public class DataSetBean extends AbstractController implements Serializable {

    /**
     * Creates a new instance of DataSetBean
     */
    public DataSetBean() {
    }

    @Produces
    public List<DataSet> getDatasets() {
        return ((DataSetJpaController) getController(DataSetJpaController.class)).findDataSetEntities();
    }

    @Produces
    public String getServer() {
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
