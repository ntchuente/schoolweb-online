
package com.core.menus;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sat Nov 11 06:50:36 WAT 2017
 * 
 */
@Path("/menuaction")
public class MenuActionRSImpl
    extends AbstractGenericService<MenuAction, Long>
    implements MenuActionRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "MenuActionManagerImpl", interf = MenuActionManagerRemote.class)
    protected MenuActionManagerRemote manager;

    public MenuActionRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<MenuAction, Long> getManager() {
        return manager;
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            MetaDataUtil.resetShareCache();
            //To change body of generated methods, choose Tools | Templates.
            MetaData meta = MetaDataUtil.getMetaData(new MenuAction(),MetaDataUtil.shareCache(),new ArrayList<String>());
            return meta;
        } catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }       
    }


}
