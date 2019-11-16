
package com.core.menus;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.application.Manifest;
import com.kerem.core.FileHelper;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Tue Nov 21 22:36:56 WAT 2017
 * 
 */
@Path("/menumodule")
public class MenuModuleRSImpl
    extends AbstractGenericService<MenuModule, Long>
    implements MenuModuleRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "MenuModuleManagerImpl", interf = MenuModuleManagerRemote.class)
    protected MenuModuleManagerRemote manager;

    public MenuModuleRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<MenuModule, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new MenuModule(),new HashMap<String, MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

//    /**
//     * 
//     * @param headers
//     * @param firstResult
//     * @param maxResult
//     * @return 
//     */
    @Override
    public List<MenuModule> filter(HttpHeaders headers, int firstResult, int maxResult) {
        List<MenuModule> datas= super.filter(headers, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
        List<MenuModule> results = new ArrayList<MenuModule>();
        if(datas!=null){
            for(MenuModule mod : datas){
                results.add(new MenuModule(mod));
            }
        }
        return results;
    } 

    @Override
    public Manifest getManifest() {
        try {
            //To change body of generated methods, choose Tools | Templates.
            System.out.println("Repertoire courant === "+FileHelper.getAvailableModules());
        } catch (IOException ex) {
            throw new WebApplicationException(ex);
        }
        return new Manifest();
    }

    @Override
    public void updateApplications() {
        List<MenuModule> modules = new ArrayList<MenuModule>();
        try {
            //To change body of generated methods, choose Tools | Templates.
            List<Manifest> availableModules = FileHelper.getAvailableModules();
            for(Manifest manif : availableModules){
                List<MenuModule> mods = manager.findByUniqueProperty("name", manif.getFilename(),new HashSet<String>());
                if(mods==null||mods.isEmpty()){
                    MenuModule mod = new MenuModule(manif);                    
                    manager.save(mod);
                }else{
                    mods.get(0).update(manif);
                    manager.update(mods.get(0).getId(), mods.get(0));
                }
            }
            //Chargement de la liste des modules
            //return manager.findAll();
        } catch (IOException ex) {
            throw new WebApplicationException(ex);
        }
        //return modules;
    }

    /**
     * Installation d'un nouveau module
     * @param module 
     */
    @Override
    public void installApplication(MenuModule module) {
        try {
            manager.installApplication(module);
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Override
    public void uninstallApplication(MenuModule module) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            manager.uninstallApplication(module);
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }
    
   

}
