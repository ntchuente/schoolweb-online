
package com.core.discussions;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import java.util.List;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Tue Jan 30 08:25:10 GMT+01:00 2018
 * 
 */
public interface KMessageManager
    extends GenericManager<KMessage, Long>
{

    public final static String SERVICE_NAME = "KMessageManager";
    
    
    public List<KMessage> getmessages(long userid, long canalid, int firstResult, int maxResult);
    
   public List<KMessage> getUsermessages(long userid, long canalid, int firstResult, int maxResult);
   
   public long countcanal(long userid, long canalid);
    
   public long countdirect(long userid, long canalid);

}
