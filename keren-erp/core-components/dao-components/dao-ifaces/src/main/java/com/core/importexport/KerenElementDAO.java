
package com.core.importexport;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.core.importexport.KerenElement;


/**
 * Interface etendue par les interfaces locale et remote de la DAO

 * @since Mon Apr 30 13:23:43 GMT+01:00 2018
 * 
 */
public interface KerenElementDAO
    extends GenericDAO<KerenElement, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "KerenElementDAO";

}
