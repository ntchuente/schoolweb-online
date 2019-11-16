
package com.core.referentiels;

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
import javax.ws.rs.core.Response;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sat Nov 11 07:16:13 WAT 2017
 * 
 */
@Path("/societe")
public class SocieteRSImpl
    extends AbstractGenericService<Societe, Long>
    implements SocieteRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "SocieteManagerImpl", interf = SocieteManagerRemote.class)
    protected SocieteManagerRemote manager;

    public SocieteRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Societe, Long> getManager() {
        return manager;
    }
    
    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            MetaData meta = MetaDataUtil.getMetaData(new Societe(),new HashMap<String, MetaData>(),new ArrayList<String>());
            return meta ;
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

    @Override
    protected void processBeforeUpdate(Societe entity) {
        if(entity.getSocieteMere()!=null && entity.getSocieteMere().compareTo(entity)==0){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED).header("keyerrors", "La socièté mere ne peut être la societe courante").build());
        }
        super.processBeforeUpdate(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void processBeforeSave(Societe entity) {
        if(entity.getSocieteMere()!=null && entity.getSocieteMere().compareTo(entity)==0){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED).header("keyerrors", "La socièté mere ne peut être la societe courante").build());
        }
        super.processBeforeSave(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
