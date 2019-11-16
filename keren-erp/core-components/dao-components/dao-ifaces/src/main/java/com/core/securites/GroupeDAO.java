
package com.core.securites;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue Nov 21 10:34:43 WAT 2017
 * 
 */
public interface GroupeDAO
    extends GenericDAO<Groupe, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "GroupeDAO";

}
