
package com.core.calendar;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import java.util.Date;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Sat Nov 18 09:29:25 WAT 2017
 * 
 */
public interface EventManager 
    extends GenericManager<Event, Long> 
{

    public final static String SERVICE_NAME = "EventManager";
    
    /**
     * Permet de definir la frequence de traitement des 
     * evenements 
     * @param initialExpiration
     * @param duration
     */
    public void scheduleEventManager(Date initialExpiration , long duration);

}
