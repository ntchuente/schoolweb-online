
package com.core.discussions;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Wed Jan 31 16:04:28 GMT+01:00 2018
 * 
 */
public interface FollowerManager
    extends GenericManager<Follower, Long>
{

    public final static String SERVICE_NAME = "FollowerManager";
    
    public Follower computeFollower(long userid , Follower follower);

}
