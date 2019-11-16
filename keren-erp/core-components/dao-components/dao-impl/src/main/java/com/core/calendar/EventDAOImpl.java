
package com.core.calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;
import com.core.securites.Utilisateur;
import java.util.Date;

@Stateless(mappedName = "EventDAO")
public class EventDAOImpl
    extends AbstractGenericDAO<Event, Long>
    implements EventDAOLocal, EventDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public EventDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Event> getManagedEntityClass() {
        return (Event.class);
    }

    @Override
    public Event createEvent(String title, String description, Date start, Date end, String duree, boolean recurrent, short confidentialite, short disponibilite, String lieu
            , boolean allDay, Long rappelID, boolean notify, Long ownerid, Long[] participantsID) {
        //To change body of generated methods, choose Tools | Templates.
        Event event = new Event(title, description, start, end, duree, lieu, allDay);
        event.setRecurrent(recurrent);event.setConfidentialite(confidentialite);
        event.setDisponibilite(disponibilite);event.setAllDay(allDay);
        event.setAllDay(allDay);event.setNotify(notify);        
        //Recherche des rappel
        if(rappelID!=null && rappelID>0){
            Rappel rappel = em.find(Rappel.class, rappelID);
            event.setRappel(rappel);
        }//end if(rappelID!=null && rappelID>0){
        //Recherche du owner
        if(ownerid!=null && ownerid>0){
            Utilisateur user = em.find(Utilisateur.class, ownerid);
            event.setOwner(user);
        }//end if(ownerid!=null && ownerid>0){
        //Liste des participant
        if(participantsID!=null && participantsID.length>0){
            for(Long id : participantsID){
                Utilisateur user = em.find(Utilisateur.class, id);
                event.getParticipants().add(user);
            }//end for(Long id : participantsID){
        }//end if(participantsID!=null && participantsID.length>0){
        em.persist(event);
        return event;
    }

}
