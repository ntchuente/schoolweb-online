
package com.core.views;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Mon Dec 18 20:36:02 WAT 2017
 * 
 */
public interface ReportRecordDAO
    extends GenericDAO<ReportRecord, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "ReportRecordDAO";

}
