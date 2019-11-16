/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.securites;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.core.MetaDataUtil;
import com.kerem.security.DESEncrypter;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Commercial_2
 */
@Path("/pwduser")
public class PwdUserRSImpl extends AbstractGenericService<PwdUser, Long> implements PwdUserRS{

    public PwdUserRSImpl() {
        super();
    }

    
    
     /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "UtilisateurManagerImpl", interf = UtilisateurManagerRemote.class)
    protected UtilisateurManagerRemote usermanager;

    /**
     * 
     * @param entity
     * @return 
     */
    @Override
    public PwdUser save(@Context HttpHeaders headers , PwdUser entity) {
//        System.out.println(PwdUserRSImpl.class.toString()+" ========================================= pwd : "+entity.getPassword()+" ===== encrypte : "+DESEncrypter.getInstance().encryptText(entity.getPassword().trim()));
        //To change body of generated methods, choose Tools | Templates.
        Utilisateur user = usermanager.find("id", entity.getCle());    
        if(!user.getPassword().equalsIgnoreCase(entity.getPassword())){
            user.setPassword(DESEncrypter.getInstance().encryptText(entity.getPassword()));
            user = usermanager.update(user.getId(), user);           
        }//end if(user.getPassword().equalsIgnoreCase(entity.getPassword())){
        
       return entity;
    }
    
    
   /** 
    @Override
    public Utilisateur save(PwdUser entity) {
        System.out.println(PwdUserRSImpl.class.toString()+" ===== "+entity);
        //To change body of generated methods, choose Tools | Templates.
        Utilisateur user = manager.find("id", entity.getCle());
        user.setPassword(entity.getPassword());
        user = manager.update(user.getId(), user);
        return user;
    }**/
    
    /**
     *
     * @param headers
     * @return 
     * @Override public Utilisateur save(PwdUser entity) {
        System.out.println(PwdUserRSImpl.class.toString()+" ===== "+entity);
        //To change body of generated methods, choose Tools | Templates.
        Utilisateur user = manager.find("id", entity.getCle());
        user.setPassword(entity.getPassword());
        user = manager.update(user.getId(), user);
        return user;
    }
     */
    @Override
    public MetaData getMetaData(HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new PwdUser(), new HashMap<String,MetaData>(),new ArrayList<String>());
        } catch (InstantiationException ex) {
            Logger.getLogger(PwdUserRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PwdUserRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public GenericManager<PwdUser, Long> getManager() {
        return null; //To change body of generated methods, choose Tools | Templates.
    }
    
}
