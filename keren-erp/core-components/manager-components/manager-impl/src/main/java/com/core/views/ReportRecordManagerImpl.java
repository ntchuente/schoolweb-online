
package com.core.views;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.menus.MenuAction;
import com.megatim.common.annotations.OrderType;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "ReportRecordManager")
public class ReportRecordManagerImpl
    extends AbstractGenericManager<ReportRecord, Long>
    implements ReportRecordManagerLocal, ReportRecordManagerRemote
{

    @EJB(name = "ReportRecordDAO")
    protected ReportRecordDAOLocal dao;

    public ReportRecordManagerImpl() {
    }

    @Override
    public GenericDAO<ReportRecord, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<ReportRecord> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<ReportRecord> datas = super.filter(predicats, orders, properties, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
        if(datas!=null){
            for(ReportRecord data : datas){
                data.setAction(new MenuAction(data.getAction()));
            }
        }
        return datas;
    }

    @Override
    public List<ReportRecord> findAll() {
        //To change body of generated methods, choose Tools | Templates.
        List<ReportRecord> datas = super.findAll(); 
        if(datas!=null){
            for(ReportRecord data : datas){
                data.setAction(new MenuAction(data.getAction()));
            }
        }
        return datas;
    }

    @Override
    public ReportRecord find(String propertyName, Long entityID) {
         //To change body of generated methods, choose Tools | Templates.
        ReportRecord data = super.find(propertyName, entityID);
        if(data!=null&&data.getScript()!=null){
            data.getScript().length();
        }
        if(data!=null&&data.getAction()!=null){
            data.setAction(new MenuAction(data.getAction()));            
        }
        return data;
    }
    
    

}
