
package com.core.securites;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Mon May 27 17:42:51 WAT 2019
 * 
 */
public interface AnneGlobalDAO
    extends GenericDAO<AnneGlobal, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "AnneGlobalDAO";

}
