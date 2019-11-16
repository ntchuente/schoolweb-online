
package com.core.menus;

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

 * @since Tue Nov 21 22:36:56 WAT 2017
 * 
 */
@Path("/menugroupactions")
public class MenuGroupActionsRSImpl
    extends AbstractGenericService<MenuGroupActions, Long>
    implements MenuGroupActionsRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "MenuGroupActionsManagerImpl", interf = MenuGroupActionsManagerRemote.class)
    protected MenuGroupActionsManagerRemote manager;

    public MenuGroupActionsRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<MenuGroupActions, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new MenuGroupActions(),new HashMap<String, MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

    /**
     * 
     * @param headers
     * @param firstResult
     * @param maxResult
     * @return 
     */
//    @Override
//    public List<MenuGroupActions> filter(HttpHeaders headers, int firstResult, int maxResult) {
//        List<MenuGroupActions> datas = super.filter(headers, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
//        List<MenuGroupActions> results = new ArrayList<MenuGroupActions>();
//        if(datas!=null){
//            for(MenuGroupActions mod : datas){
//                results.add(new MenuGroupActions(mod));
//            }
//        }
//        return results;
//    }
    
    
}
