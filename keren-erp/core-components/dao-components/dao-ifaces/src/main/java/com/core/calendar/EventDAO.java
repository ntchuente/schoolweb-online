
package com.core.calendar;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import java.util.Date;


/**
 * Interface etendue par les interfaces locale et remote de la DAO

 * @since Sat Nov 18 09:29:25 WAT 2017
 * 
 */
public interface EventDAO
    extends GenericDAO<Event, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "EventDAO";
    
    /**
     * 
     * @param title
     * @param description
     * @param start
     * @param end
     * @param duree
     * @param recurrent
     * @param confidentialite
     * @param disponibilite
     * @param lieu
     * @param allDay
     * @param rappelID
     * @param notify
     * @param ownerid
     * @param participantsID
     * @return 
     */
    public Event createEvent(String title, String description, Date start, Date end, String duree,boolean recurrent,short confidentialite,short disponibilite, String lieu, boolean allDay,Long rappelID ,boolean notify,Long ownerid,Long[] participantsID);

}
