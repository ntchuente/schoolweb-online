
package com.core.importexport;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.core.importexport.KerenEntity;


/**
 * Interface etendue par les interfaces locale et remote de la DAO

 * @since Mon Apr 30 13:23:43 GMT+01:00 2018
 * 
 */
public interface KerenEntityDAO
    extends GenericDAO<KerenEntity, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "KerenEntityDAO";

}
