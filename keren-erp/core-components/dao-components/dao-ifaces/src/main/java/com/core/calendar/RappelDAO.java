
package com.core.calendar;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Sun Nov 19 14:21:00 WAT 2017
 * 
 */
public interface RappelDAO
    extends GenericDAO<Rappel, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "RappelDAO";

}
