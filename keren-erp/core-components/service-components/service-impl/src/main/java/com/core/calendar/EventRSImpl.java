
package com.core.calendar;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.discussions.KMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.FilterPredicat;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sat Nov 18 09:29:25 WAT 2017
 * 
 */
@Path("/event")
public class EventRSImpl
    extends AbstractGenericService<Event, Long>
    implements EventRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "EventManagerImpl", interf = EventManagerRemote.class)
    protected EventManagerRemote manager;

    public EventRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Event, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(@Context HttpHeaders headers) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            return MetaDataUtil.getMetaData(new Event(),new HashMap<String, MetaData>(),new ArrayList<String>());
        }catch (Exception ex) {          
           throw new WebApplicationException(ex);
        }
    }

    @Override
    protected void processBeforeUpdate(Event entity) {
        if(entity.getTitle()==null||entity.getTitle().trim().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez saisir le titre")).build()); 
        }
        if(entity.getStart()==null){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez saisir la date de debut")).build()); 
        }
        if(entity.getParticipants()==null||entity.getParticipants().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez selectionner au moins un participant")).build()); 
        }
        if(entity.getDuree()==null||entity.getDuree().trim().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez saisir la durée")).build());  
        }
        if(entity.getRappel()==null){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez selection la periode de rappel")).build());            
        }
        
        super.processBeforeUpdate(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void processBeforeSave(Event entity) {
        if(entity.getTitle()==null||entity.getTitle().trim().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez saisir le titre")).build()); 
        }
        if(entity.getStart()==null){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez saisir la date de debut")).build()); 
        }
        if(entity.getParticipants()==null||entity.getParticipants().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez selectionner au moins un participant")).build()); 
        }
        if(entity.getDuree()==null||entity.getDuree().trim().isEmpty()){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez saisir la durée")).build());  
        }
        if(entity.getRappel()==null){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED)
                    .header("cause" , new String("Veuillez selection la periode de rappel")).build());            
        }
        
        super.processBeforeSave(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Event> getevents(HttpHeaders headers, long userid) {
        //To change body of generated methods, choose Tools | Templates.
         Gson gson =new Gson();
         
        List ids = new ArrayList();
        if(headers.getRequestHeader("usersid")!=null){
            ids = gson.fromJson(headers.getRequestHeader("usersid").get(0),new TypeToken<List<Long>>(){}.getType());
        }
        String requete = "SELECT DISTINCT c FROM Event c , IN(c.participants) p WHERE c.owner.id="+userid;
         String subquery = "(";
        int index = 0 ;
        for(Object obj : ids){
            Long id = (Long) obj;
            if(index==0){
                subquery+=" p.id="+id;
            }else{
                subquery+=" OR p.id="+id;
            }
            index++;
        }//end for(Object obj : ids)
        if(ids.size()>0){
            subquery+=") AND (c.confidentialite=0 OR c.confidentialite=2)";
            requete+=" OR "+subquery;
        }//end if(ids.size()>0)
        List<Event> datas = manager.getDao().getEntityManager().createQuery(requete).getResultList();
        List<Event> output = new ArrayList<Event>();
//        System.out.println(EventRSImpl.class.toString()+" =========================== "+datas.size()+" === "+requete);
        for(Event evt:datas){
            output.add(new Event(evt));
        }//end for(Event evt:datas){
        return output;
    }

   

}
