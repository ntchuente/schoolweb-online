
package com.core.importexport;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.commons.DateHelper;
import com.kerem.core.FileHelper;
import com.kerem.core.KerenExecption;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import com.megatimgroup.mgt.commons.command.MysqlBDWinExporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Wed Jul 04 15:20:27 GMT+01:00 2018
 * 
 */
@Path("/export")
public class ExportRSImpl
    extends AbstractGenericService<Export, Long>
    implements ExportRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "ExportManagerImpl", interf = ExportManagerRemote.class)
    protected ExportManagerRemote manager;

    public ExportRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Export, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new Export(), new HashMap<String, MetaData>(), new ArrayList<String>());
        } catch (InstantiationException ex) {
            Logger.getLogger(ExportRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ExportRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  null;
    }

    @Override
    protected void processBeforeUpdate(Export entity) {
        if(entity.getCode()==null|| entity.getCode().trim().isEmpty()){
            throw new KerenExecption("Le repertoire de sauvegarde est obligatoire");
        }else if(entity.getCycle()==null || entity.getCycle()<=0){
            throw new KerenExecption("Le Cycle de sauvegarde est obligatoire et positif");
        }else if(entity.getHeure()==null){
            throw new KerenExecption("L'heure de sauvegarde est obligatoire");
        }
        File rep = new File(entity.getCode());
        if(!rep.exists()){
            throw new KerenExecption("Le Repertoire de sauvegarde "+entity.getCode()+" est introuvable");
        }
        if(entity.getExecDay()==null){
            try {          
                Date today = new Date();
                manager.exportFileManager(entity, today);
                entity.setExecDay(DateHelper.next(today, entity.getCycle()));
            } //end if(entity.getExecDay()==null){
            catch (Exception ex) {
                Logger.getLogger(ExportManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new KerenExecption(ex.getMessage());
            }
        }//end if(entity.getExecDay()==null){
        super.processBeforeUpdate(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void processBeforeSave(Export entity) {
        if(entity.getCode()==null|| entity.getCode().trim().isEmpty()){
            throw new KerenExecption("Le repertoire de sauvegarde est obligatoire");
        }else if(entity.getCycle()==null || entity.getCycle()<=0){
            throw new KerenExecption("Le Cycle de sauvegarde est obligatoire et positif");
        }else if(entity.getHeure()==null){
            throw new KerenExecption("L'heure de sauvegarde est obligatoire");
        }
        File rep = new File(entity.getCode());
        if(!rep.exists()){
            throw new KerenExecption("Le Repertoire de sauvegarde "+entity.getCode()+" est introuvable");
        }
        if(entity.getExecDay()==null){
            try {          
                Date today = new Date();
                manager.exportFileManager(entity, today);
                entity.setExecDay(DateHelper.next(today, entity.getCycle()));
            } //end if(entity.getExecDay()==null){
            catch (Exception ex) {
                Logger.getLogger(ExportManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new KerenExecption(ex.getMessage());
            }
        }//end if(entity.getExecDay()==null){
        super.processBeforeSave(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
     
    

}
