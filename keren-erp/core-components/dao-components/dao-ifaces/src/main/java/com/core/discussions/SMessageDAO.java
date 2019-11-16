
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import java.util.List;


/**
 * Interface etendue par les interfaces locale et remote de la DAO

 * @since Mon Jan 29 12:08:24 GMT+01:00 2018
 * 
 */
public interface SMessageDAO
    extends GenericDAO<SMessage, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "SMessageDAO";
    
    /**
     * 
     * @param userid
     * @param message
     * @return 
     */
    public List<RMessage> send(long userid,SMessage message);
    
    /**
     * 
     * @param userid
     * @param message
     * @return 
     */
    public List<RMessage> innerNotes(SMessage message);

}
