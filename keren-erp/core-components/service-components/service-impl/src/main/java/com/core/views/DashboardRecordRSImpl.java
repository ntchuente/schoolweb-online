
package com.core.views;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.menus.MenuActionManagerRemote;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Wed Jan 24 08:06:02 WAT 2018
 * 
 */
@Path("/dashboardrecord")
public class DashboardRecordRSImpl
    extends AbstractGenericService<DashboardRecord, Long>
    implements DashboardRecordRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "DashboardRecordManagerImpl", interf = DashboardRecordManagerRemote.class)
    protected DashboardRecordManagerRemote manager;
    
     @Manager(application = "kerencore", name = "MenuActionManagerImpl", interf = MenuActionManagerRemote.class)
    protected MenuActionManagerRemote actionmanager;
     

    public DashboardRecordRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<DashboardRecord, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }
    
    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {              
        try {       
            return MetaDataUtil.getMetaData(new DashboardRecord(),new HashMap<String, MetaData>(),new ArrayList<String>());
        } catch (InstantiationException ex) {
            Logger.getLogger(DashboardRecordRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DashboardRecordRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

}
