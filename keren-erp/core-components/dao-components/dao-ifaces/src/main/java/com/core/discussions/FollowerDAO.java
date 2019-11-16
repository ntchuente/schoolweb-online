
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Wed Jan 31 16:04:27 GMT+01:00 2018
 * 
 */
public interface FollowerDAO
    extends GenericDAO<Follower, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "FollowerDAO";

}
