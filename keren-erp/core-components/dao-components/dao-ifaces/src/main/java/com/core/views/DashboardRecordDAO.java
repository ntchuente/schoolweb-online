
package com.core.views;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Wed Jan 24 08:06:02 WAT 2018
 * 
 */
public interface DashboardRecordDAO
    extends GenericDAO<DashboardRecord, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "DashboardRecordDAO";

}
