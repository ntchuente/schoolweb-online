
package com.core.referentiels;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue Nov 14 13:20:52 WAT 2017
 * 
 */
public interface DeviseDAO
    extends GenericDAO<Devise, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "DeviseDAO";

}
