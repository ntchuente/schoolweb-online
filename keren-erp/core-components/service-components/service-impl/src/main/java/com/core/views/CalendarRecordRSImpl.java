
package com.core.views;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sat Feb 17 07:50:36 GMT+01:00 2018
 * 
 */
@Path("/calendarrecord")
public class CalendarRecordRSImpl
    extends AbstractGenericService<CalendarRecord, Long>
    implements CalendarRecordRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "CalendarRecordManagerImpl", interf = CalendarRecordManagerRemote.class)
    protected CalendarRecordManagerRemote manager;

    public CalendarRecordRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<CalendarRecord, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }
    
     @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new CalendarRecord(),new HashMap<String, MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

}
