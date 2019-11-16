
package com.core.securites;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.google.gson.Gson;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Tue Nov 21 10:34:44 WAT 2017
 * 
 */
@Path("/groupe")
public class GroupeRSImpl
    extends AbstractGenericService<Groupe, Long>
    implements GroupeRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "GroupeManagerImpl", interf = GroupeManagerRemote.class)
    protected GroupeManagerRemote manager;

    public GroupeRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Groupe, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new Groupe(),new HashMap<String, MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

    @Override
    public List<GroupeDetail> getHabilitation(HttpHeaders headers) {
        //To change body of generated methods, choose Tools | Templates.
        Gson gson =new Gson();
        long id = gson.fromJson(headers.getRequestHeader("id").get(0),Long.class);
//        System.out.println(GroupeRSImpl.class.toString()+" =========================== ID:"+id);
        return  manager.getHabilitations(id);
    }
    
    

}
