
package com.core.langues;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue May 08 12:34:36 GMT+01:00 2018
 * 
 */
public interface TermeDAO
    extends GenericDAO<Terme, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "TermeDAO";

}
