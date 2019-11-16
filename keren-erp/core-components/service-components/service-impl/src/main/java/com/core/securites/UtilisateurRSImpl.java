
package com.core.securites;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.menus.MenuModule;
import com.google.gson.Gson;
import com.kerem.core.MetaDataUtil;
import com.kerem.security.DESEncrypter;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaColumn;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Tue Nov 14 21:34:40 WAT 2017
 * 
 */
@Path("/utilisateur")
public class UtilisateurRSImpl
    extends AbstractGenericService<Utilisateur, Long>
    implements UtilisateurRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "UtilisateurManagerImpl", interf = UtilisateurManagerRemote.class)
    protected UtilisateurManagerRemote manager;

    public UtilisateurRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Utilisateur, Long> getManager() {
        return manager;
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            MetaColumn pwd = new MetaColumn("button", "updatpassword", "Modifier le mot de passe", false, "action", null);
            pwd.setValue("{'name':'update_pwd'}");
            MetaColumn btn2 = new MetaColumn("button", "sendmessaged", "Envoyer les instructions de réinitialisation de mot de passe", false, "object", null);
            btn2.setValue("{'model':'kerencore','entity':'email','method':'directsent'"
                    + ",template:{'subject':'Modification de mot de passe','source':'user.courriel','cible':'object.courriel'"
                    + ",'text':'votre mot de passe est @object.password'}}");
            MetaColumn work1 = new MetaColumn("button", "work1", "Confirmer", false, "workflow", null);
            work1.setStates(new String[]{"etabli"});
            work1.setValue("{'model':'kerencore','entity':'utilisateur','method':'confirme'}");
            MetaColumn status = new MetaColumn("workflow", "state", "State", false, "statusbar", null);            
            //To change body of generated methods, choose Tools | Templates.
            MetaData meta = MetaDataUtil.getMetaData(new Utilisateur(),new HashMap<String, MetaData>(),new ArrayList<String>());
            meta.getHeader().add(pwd);
            meta.getHeader().add(btn2);
            meta.getHeader().add(work1);
            meta.getHeader().add(status);            
            return meta;
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

        

    public String getModuleName() {
        return ("kerencore");
    }

    /**
     * 
     * @param headers
     * @param user
     * @return 
     */
    @Override
    public List<MenuModule> loadUserApplications(@Context HttpHeaders headers) {
        //To change body of generated methods, choose Tools | Templates.
	    Gson gson = new Gson();
	    Long id = gson.fromJson(headers.getRequestHeader("userid").get(0),Long.class);
	    Utilisateur user = manager.find("id", id);
        return manager.loadUserApplications(user);
    }

    @Override
    public List<Utilisateur> findByUniqueProperty(@Context HttpHeaders headers , String propertyName, String propertyValue) {
         //To change body of generated methods, choose Tools | Templates.
        List<Utilisateur> datas = super.findByUniqueProperty(headers,propertyName, propertyValue);
         List<Utilisateur> result = new ArrayList<Utilisateur>();
         for(Utilisateur user : datas){
             result.add(new Utilisateur(user));
         }
         return result;
    }

    @Override
    public Utilisateur confirmer(@Context HttpHeaders headers,Utilisateur user) {
        //To change body of generated methods, choose Tools | Templates.
        Gson gson = new Gson();
        List<String> states = gson.fromJson(headers.getRequestHeader("states").get(0),List.class);
//        System.out.println("Vous avez modiifer l'etat de l'utlisateur == "+states.size());
        if(user.getState().equals(user.getStates().get(0))){
            user.setState(user.getStates().get(1).getCode());
        }else{
            user.setState(user.getStates().get(0).getCode());
        }
        manager.update(user.getId(), user);
        return user;
    }

    
    @Override
	protected void processBeforeSave(Utilisateur entity) {
    	entity.setPassword(DESEncrypter.getInstance().encryptText("admin"));
		super.processBeforeSave(entity);
	}

	@Override
    public List<MenuModule> loadUserApplicationsByCourriel(HttpHeaders headers, String courriel) {
        
        //On initialise
        List<Utilisateur> users = manager.findByUniqueProperty("courriel", courriel, null);
        
        return manager.loadUserApplications(users.get(0));
    }
}
