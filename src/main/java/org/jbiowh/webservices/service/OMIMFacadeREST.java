/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbiowh.webservices.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jbiowhpersistence.datasets.disease.omim.controller.OMIMJpaController;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.disease.omim.search.SearchOMIM;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.utils.search.JBioWHSearch;

/**
 * This class is the OMIM Facade REST webservices
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Dec 9, 2013
 */
@Stateless
@Path("omim")
public class OMIMFacadeREST extends AbstractFacade<OMIM> {

    public OMIMFacadeREST() {
        super(OMIM.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public OMIM find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfo(@PathParam("id") Long id) {
        OMIM o = super.find(id);
        if (o != null && o.getGeneInfo() != null) {
            return new ArrayList(o.getGeneInfo());
        }
        return new ArrayList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<OMIM> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("id/{id}")
    @Produces({"application/xml", "application/json"})
    public OMIM findById(@PathParam("id") Long id) {
        parm.clear();
        parm.put("omimId", id);
        try {
            return (OMIM) (new OMIMJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("OMIM.findByOmimId", parm);
        } catch (NoResultException ex) {
        }
        return null;
    }

    @GET
    @Path("id/{id}/geneinfo")
    @Produces({"application/xml", "application/json"})
    public List<GeneInfo> findGeneInfoById(@PathParam("id") Long id) {
        parm.clear();
        parm.put("omimId", id);
        try {
            OMIM o = (OMIM) (new OMIMJpaController(getEntityManager().getEntityManagerFactory())).useNamedQuerySingleResult("OMIM.findByOmimId", parm);
            if (o != null && o.getGeneInfo() != null) {
                return new ArrayList(o.getGeneInfo());
            }
        } catch (NoResultException ex) {
        }
        return new ArrayList();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected JBioWHSearch getSearch() {
        return new SearchOMIM();
    }

}
