
package com.core.referentiels;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Wed Jan 10 21:34:02 WAT 2018
 * 
 */
public interface PieceJointeDAO
    extends GenericDAO<PieceJointe, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "PieceJointeDAO";

}
