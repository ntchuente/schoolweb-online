
package com.core.referentiels;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sat Nov 11 18:45:30 WAT 2017
 * 
 */
@Path("/pays")
public class PaysRSImpl
    extends AbstractGenericService<Pays, Long>
    implements PaysRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "PaysManagerImpl", interf = PaysManagerRemote.class)
    protected PaysManagerRemote manager;
    
   // public final String moduleName="kerencore";

    public PaysRSImpl() {
        super();        
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Pays, Long> getManager() {
        return manager;
    }

    @Override
    protected void processBeforeSave(Pays entity) {
        Pays pays = (Pays) entity;
        if(pays.getDevise()!=null&&pays.getDevise().getCode()==null){
            pays.setDevise(null);
        }
        super.processBeforeSave(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void processBeforeUpdate(Map entity) {
         super.processBeforeUpdate(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new Pays(),new HashMap<String, MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }
    
    

}
