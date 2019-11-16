
package com.core.discussions;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import java.util.List;
import javax.ws.rs.PathParam;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Mon Jan 29 12:08:24 GMT+01:00 2018
 * 
 */
public interface SMessageManager
    extends GenericManager<SMessage, Long>
{

    public final static String SERVICE_NAME = "SMessageManager";
    
    public List<RMessage> send(long userid,SMessage message);

}
