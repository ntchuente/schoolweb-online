
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.core.securites.Utilisateur;
import java.util.List;


/**
 * Interface etendue par les interfaces locale et remote de la DAO

 * @since Sat Jan 27 08:28:43 GMT+01:00 2018
 * 
 */
public interface CanalDAO
    extends GenericDAO<Canal, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "CanalDAO";

    
    /**
     * 
     * @param username
     * @return 
     */
    public List<Canal> getCanaux(String username);
    
    /**
     * 
     * @param userid
     * @return 
     */
    public List<Canal> getCanaux(long userid);
    
    /**
     * 
     * @param username
     * @return 
     */
    public List<Utilisateur> getConnectUsers(String username);
    
    /**
     * 
     * @param userid
     * @return 
     */
    public List<Utilisateur> getConnectUsers(long userid);
}
