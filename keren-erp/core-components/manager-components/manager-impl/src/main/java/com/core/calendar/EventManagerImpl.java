
package com.core.calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.discussions.SMessage;
import com.core.discussions.SMessageDAOLocal;
import com.core.securites.Utilisateur;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TimedObject;
import javax.ejb.Timer;

@TransactionAttribute
@Stateless(mappedName = "EventManager")
public class EventManagerImpl
    extends AbstractGenericManager<Event, Long>
    implements EventManagerLocal, EventManagerRemote,TimedObject
{

    @EJB(name = "EventDAO")
    protected EventDAOLocal dao;
    
    @EJB(name = "SMessageDAO")
    protected SMessageDAOLocal smessagedao;
    
    @Resource
    SessionContext context ;

    public EventManagerImpl() {
    }

    @Override
    public GenericDAO<Event, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public Event find(String propertyName, Long entityID) {
        Event event = super.find(propertyName, entityID); //To change body of generated methods, choose Tools | Templates.
         Event result = new Event(event);
         result.setParticipants(new ArrayList<Utilisateur>());
        if(event.getParticipants()!=null){
            for(Utilisateur u : event.getParticipants()){
                result.getParticipants().add(new Utilisateur(u));                
            }//end for(Utilisateur u : event.getParticipants()){
        }//end if(event.getParticipants()!=null){
        return result;
    }

    
    
    @Override
    public List<Event> findAll() {
       List<Event> datas = super.findAll(); //To change body of generated methods, choose Tools | Templates.
       List<Event> results = new ArrayList<Event>();
       if(datas!=null){
           for(Event ev : datas){
               results.add(new Event(ev));
           }
       }
       //dao.getEntityManager().clear();
       return results;
    }

    @Override
    public void ejbTimeout(Timer timer) {
        //To change body of generated methods, choose Tools | Templates.
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addLe("start", new Date());
        container.addEq("notify",Boolean.TRUE);
        List<Event> evenements = dao.filter(container.getPredicats(), null, null, 0, -1);
//        System.out.println(EventManagerImpl.class+" : Debut de traitement des evenement   :::: Nbres evenement trouvés : "+evenements.size());
        //Debut du traitement
        for(Event evt:evenements){
            Calendar c = Calendar.getInstance();
            c.setTime(evt.getStart());
            if(evt.getRappel().getUnite()==0){//minute
                c.add(Calendar.MINUTE, evt.getRappel().getQuantite());
            }else if(evt.getRappel().getUnite()==1){//heures
                 c.add(Calendar.HOUR, evt.getRappel().getQuantite());
            }else if(evt.getRappel().getUnite()==2){//jours
                 c.add(Calendar.DAY_OF_YEAR, evt.getRappel().getQuantite());
            }
            Date notify = c.getTime();
            StringBuilder body = new StringBuilder("Titre : "+evt.getTitle());
            if(evt.getLieu()!=null&&!evt.getLieu().trim().isEmpty()){
                body.append("\nLieu:"+evt.getLieu());
            }
            if(evt.getDescription()!=null&&!evt.getDescription().trim().isEmpty()){
                body.append("\nDétail:"+evt.getDescription());
            }
            if(notify.compareTo(new Date())>=0){
                SMessage message = new SMessage(new Date(), body.toString(), null, null, null);
                message.setRecievers(evt.getParticipants());
                smessagedao.innerNotes(message);
            }//end if(notify.compareTo(new Date())>=0)
            //Marquer l'evenement comme trité
            evt.setNotify(false);
            dao.update(evt.getId(), evt);
        }//end for(Event evt:evenements)
    }

    @Override
    public void scheduleEventManager(Date initialExpiration, long duration) {
         //To change body of generated methods, choose Tools | Templates.
        context.getTimerService().createTimer(initialExpiration, duration, "Event schulder ...");
    }

  

    
}
