
package com.core.securites;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue Nov 14 21:34:39 WAT 2017
 * 
 */
public interface UtilisateurDAO
    extends GenericDAO<Utilisateur, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "UtilisateurDAO";

}
