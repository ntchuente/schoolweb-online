
package com.core.menus;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.base.BaseElement;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaColumn;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sun Dec 17 10:38:04 WAT 2017
 * 
 */
@Path("/actionitem")
public class ActionItemRSImpl
    extends AbstractGenericService<ActionItem, Long>
    implements ActionItemRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "ActionItemManagerImpl", interf = ActionItemManagerRemote.class)
    protected ActionItemManagerRemote manager;

    public ActionItemRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<ActionItem, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    /**
     * 
     * @param headers
     * @return 
     */
    @Override
    public MetaData getMetaData(HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            MetaData meta =  MetaDataUtil.getMetaData(new ActionItem(), new HashMap<String, MetaData>(),new ArrayList<String>());
            MetaData metaData = new MetaData();            
            metaData.setEditTitle(meta.getEditTitle());
            metaData.setListTitle(meta.getListTitle());
            metaData.setEntityName(meta.getEntityName());
            metaData.setModuleName(meta.getModuleName());
            metaData.setCreateonfield(meta.isCreateonfield()); 
            metaData.setDesablecreate(meta.isDesablecreate());
            for(MetaColumn col : meta.getColumns()){
                if(col.getFieldName().trim().equalsIgnoreCase("id")){
                    metaData.getColumns().add(col);
                }else if(col.getFieldName().equalsIgnoreCase("designation")){
                    metaData.getColumns().add(col);
                }else if(col.getFieldName().equalsIgnoreCase("name")){
                    metaData.getColumns().add(col);
                }else if(col.getFieldName().equalsIgnoreCase("type")){
                    metaData.getColumns().add(col);
                }else if(col.getFieldName().equalsIgnoreCase("label")){
                    col.setFieldLabel("ACTION NAME");
                    metaData.getColumns().add(col);
                }else if(col.getFieldName().equalsIgnoreCase("value")){
                    metaData.getColumns().add(col);
                }
            }//end for(MetaColumn col : meta.getColumns())
            for(MetaGroup group:meta.getGroups()){
                if(group.getGroupName().equalsIgnoreCase("group1")){
                    metaData.getGroups().add(group);
                }
            }
            return metaData;
        } catch (InstantiationException ex) {
            Logger.getLogger(ActionItemRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActionItemRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

}
