
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue Jan 30 08:25:09 GMT+01:00 2018
 * 
 */
public interface KMessageDAO
    extends GenericDAO<KMessage, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "KMessageDAO";

}
