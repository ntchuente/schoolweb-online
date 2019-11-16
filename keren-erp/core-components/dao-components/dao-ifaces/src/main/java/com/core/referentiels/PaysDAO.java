
package com.core.referentiels;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Sat Nov 11 18:45:30 WAT 2017
 * 
 */
public interface PaysDAO
    extends GenericDAO<Pays, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "PaysDAO";

}
