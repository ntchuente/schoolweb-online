
package com.core.views;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kerem.core.FileHelper;
import com.kerem.core.MetaDataUtil;
import com.kerem.genarated.Search;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.JAXBException;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Mon Dec 18 20:36:03 WAT 2017
 * 
 */
@Path("/reportrecord")
public class ReportRecordRSImpl
    extends AbstractGenericService<ReportRecord, Long>
    implements ReportRecordRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "ReportRecordManagerImpl", interf = ReportRecordManagerRemote.class)
    protected ReportRecordManagerRemote manager;

    public ReportRecordRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<ReportRecord, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            MetaDataUtil.resetShareCache();
            return MetaDataUtil.getMetaData(new ReportRecord(), MetaDataUtil.shareCache(),new ArrayList<String>());
        } catch (InstantiationException ex) {
            Logger.getLogger(ReportRecordRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReportRecordRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public MetaData getSearchMetaData(@Context HttpHeaders headers ,long id) {
        try {
            Gson gson =new Gson();
            //Type predType = ;
            MetaData meta = null;
            if(headers.getRequestHeader("meta")!=null){
                meta = gson.fromJson(headers.getRequestHeader("meta").get(0),new TypeToken<List<MetaData>>(){}.getType());
            }  
            if(meta==null){
                return meta;
            }
            //To change body of generated methods, choose Tools | Templates.
            ReportRecord record = manager.find("id", Long.valueOf(id));
            System.out.println(ReportRecordRSImpl.class.getName()+"===== "+id);            
            //Creation d'un instance de class
//            MetaData meta = MetaDataUtil.getMetaData(Class.forName(record.getClazz()), new HashMap<String, MetaData>());
            Search search = FileHelper.transformScriptToSearch(record.getSearch());
            if(search==null){
                return meta;
            }
            return MetaDataUtil.getMetaData(meta, search.getField());
        } catch (JAXBException ex) {
            Logger.getLogger(ReportRecordRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

}
