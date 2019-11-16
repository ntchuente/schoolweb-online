
package com.core.langues;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Sat Nov 11 21:58:35 WAT 2017
 * 
 */
public interface LangueDAO
    extends GenericDAO<Langue, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "LangueDAO";

}
