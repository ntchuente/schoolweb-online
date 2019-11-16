
package com.core.importexport;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.kerem.commons.DateHelper;
import com.kerem.core.FileHelper;
import com.megatimgroup.mgt.commons.command.MysqlBDWinExporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TimedObject;
import javax.ejb.Timer;

@TransactionAttribute
@Stateless(mappedName = "ExportManager")
public class ExportManagerImpl
    extends AbstractGenericManager<Export, Long>
    implements ExportManagerLocal, ExportManagerRemote,TimedObject
{

    @EJB(name = "ExportDAO")
    protected ExportDAOLocal dao;    
     
   @Resource
   SessionContext context ;

    public ExportManagerImpl() {
    }

    @Override
    public GenericDAO<Export, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public Export find(String propertyName, Long entityID) {
        Export data = super.find(propertyName, entityID); //To change body of generated methods, choose Tools | Templates.
        Export entity = new Export(data);
        return entity;
    }

    
    
    @Override
    public void processBeforeUpdate(Export entity) {        
        super.processBeforeUpdate(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processBeforeSave(Export entity) {        
        super.processBeforeSave(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public Export save(Export entity) { 
        RestrictionsContainer container =  RestrictionsContainer.newInstance();
        List<Export> datas = dao.filter(container.getPredicats(), null, null, 0, -1);       
        if(datas==null||datas.isEmpty()){
            return super.save(entity); 
        }else{
            Export data = datas.get(0);
            data.setCode(entity.getCode());
            data.setCycle(entity.getCycle());
            data.setExecDay(entity.getExecDay());
            data.setHeure(entity.getHeure());
            return super.update(data.getId(), data);
        }//end if(datas==null||datas.isEmpty()){
        //To change body of generated methods, choose Tools | Templates.
    }
    
   /**
     * 
     * @param entity
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InterruptedException 
     */
    public void exportFileManager(Export entity,Date day) throws FileNotFoundException, IOException, InterruptedException{
           InputStream input = null;
            String confg_file = FileHelper.getConfigDirectory()+File.separator+"config.properties";
            //Load file properties
            Properties config = new Properties();
            input = new FileInputStream(confg_file);
            //load a properties file
            config.load(input);
            String script_file = FileHelper.getConfigDirectory()+File.separator+config.getProperty("script");
            if(config.getProperty("system").equalsIgnoreCase("mysql")){
                 MysqlBDWinExporter exporter = new MysqlBDWinExporter(config.getProperty("program"),script_file,""+FileHelper.getConfigDirectory(), config.getProperty("user")
                        ,config.getProperty("password"), config.getProperty("database"));
                boolean result = exporter.execute();
                if(result){
                    String temp_file = FileHelper.getTemporalDirectory()+File.separator+config.getProperty("filename");
                    File file = new File(temp_file);
                    if(file.exists()){
                       String journe = DateHelper.convertToString(day, "ddMMyyyy");
                       File rep = new File(entity.getCode()+File.separator+journe);
                       if(!rep.exists()){
                           rep.mkdir();
                       }//end if(!rep.exists()){
                       String dest = entity.getCode()+File.separator+journe+File.separator+config.getProperty("filename");
                       //Deplacement du fichoer
                       FileHelper.moveFile(file, new File(dest));
                    }//end if(file.exists()){
                }//end if(result){
            }//end if(config.getProperty("system").equalsIgnoreCase("mysql")){
    }

    @Override
    public void scheduleDBExporterManager(Date initialExpiration, long duration) {
        //To change body of generated methods, choose Tools | Templates.
        RestrictionsContainer container =  RestrictionsContainer.newInstance();
        List<Export> datas = dao.filter(container.getPredicats(), null, null, 0, -1);  
        if(!datas.isEmpty()){
            Date today = new Date();
            int nbreday =  DateHelper.numberOfDays(today, datas.get(0).getExecDay());
            if(nbreday>=0){
                long  duree = nbreday*24*60*60*1000;
                context.getTimerService().createTimer(today, duree, "Event schulder ...");
            }else{
                 context.getTimerService().createTimer(initialExpiration, duration, "Event schulder ...");
            }//end if(nbreday>=0){
        }else{
           context.getTimerService().createTimer(initialExpiration, duration, "Event schulder ...");
        } //end if(!datas.isEmpty()){      
        
    }

    @Override
    public void ejbTimeout(Timer timer) {
        //To change body of generated methods, choose Tools | Templates.
        RestrictionsContainer container =  RestrictionsContainer.newInstance();
        List<Export> datas = dao.filter(container.getPredicats(), null, null, 0, -1);   
        if(!datas.isEmpty()){
            try {
                Export entity = datas.get(0);
                Date today = new Date();
                exportFileManager(entity, today);
                entity.setExecDay(DateHelper.next(today, entity.getCycle()));
            } //end if(!datas.isEmpty()){
            catch (Exception ex) {
                Logger.getLogger(ExportManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
