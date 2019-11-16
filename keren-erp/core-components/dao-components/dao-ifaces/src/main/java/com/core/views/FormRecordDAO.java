
package com.core.views;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue Nov 21 21:56:28 WAT 2017
 * 
 */
public interface FormRecordDAO
    extends GenericDAO<FormRecord, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "FormRecordDAO";

}
