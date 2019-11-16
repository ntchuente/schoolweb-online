
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Mon Jan 29 12:08:24 GMT+01:00 2018
 * 
 */
public interface RMessageDAO
    extends GenericDAO<RMessage, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "RMessageDAO";

}
