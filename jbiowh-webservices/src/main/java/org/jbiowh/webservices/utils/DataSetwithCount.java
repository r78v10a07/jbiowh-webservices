package org.jbiowh.webservices.utils;

import org.jbiowhpersistence.datasets.dataset.entities.DataSet;

/**
 * This Class is
 *
 * $Author:$ $LastChangedDate:$ $LastChangedRevision:$
 *
 * @since Jun 10, 2014
 */
public class DataSetwithCount extends DataSet {

    int count; 

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DataSetwithCount{" + "count=" + count + "dataset: " + super.toString();
    }
}
