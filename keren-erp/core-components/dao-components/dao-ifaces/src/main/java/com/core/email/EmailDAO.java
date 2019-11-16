
package com.core.email;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Thu May 03 15:12:05 GMT+01:00 2018
 * 
 */
public interface EmailDAO
    extends GenericDAO<Email, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "EmailDAO";

}
