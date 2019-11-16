
package com.core.calendar;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sun Nov 19 14:21:00 WAT 2017
 * 
 */
@Path("/rappel")
public class RappelRSImpl
    extends AbstractGenericService<Rappel, Long>
    implements RappelRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "RappelManagerImpl", interf = RappelManagerRemote.class)
    protected RappelManagerRemote manager;

    public RappelRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Rappel, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    /**
     * 
     * @param headers
     * @return 
     */
    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new Rappel(),new HashMap<String , MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

    
}
