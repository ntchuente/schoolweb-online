
package com.core.views;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.menus.MenuAction;
import com.core.menus.MenuModule;
import com.megatim.common.annotations.OrderType;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "FormRecordManager")
public class FormRecordManagerImpl
    extends AbstractGenericManager<FormRecord, Long>
    implements FormRecordManagerLocal, FormRecordManagerRemote
{

    @EJB(name = "FormRecordDAO")
    protected FormRecordDAOLocal dao;

    public FormRecordManagerImpl() {
    }

    @Override
    public GenericDAO<FormRecord, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<FormRecord> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<FormRecord> datas =super.filter(predicats, orders, properties, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
        for(FormRecord rec : datas){
            if(rec.getAction()!=null){
                rec.setAction(new MenuAction(rec.getAction()));
            }           
        }
        return datas;
    }

    @Override
    public FormRecord find(String propertyName, Long entityID) {
         //To change body of generated methods, choose Tools | Templates.
        FormRecord data = super.find(propertyName, entityID);
        if(data.getScript()!=null){
            data.getScript().length();
        }
        if(data.getAction()!=null){
            data.setAction(new MenuAction(data.getAction()));            
        }
        
        return data;
    }

    @Override
    public FormRecord delete(Long id) {
         //To change body of generated methods, choose Tools | Templates.
        FormRecord data = super.delete(id);
        if(data.getScript()!=null){
            data.getScript().length();
        }
        if(data.getAction()!=null){
            data.setAction(new MenuAction(data.getAction()));            
        }
        
        return data;
    }
    
    

}
