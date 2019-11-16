
package com.core.views;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Sat Feb 17 07:50:34 GMT+01:00 2018
 * 
 */
public interface CalendarRecordDAO
    extends GenericDAO<CalendarRecord, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "CalendarRecordDAO";

}
