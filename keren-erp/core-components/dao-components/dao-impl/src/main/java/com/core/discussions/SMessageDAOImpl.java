
package com.core.discussions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;
import com.core.referentiels.PieceJointe;
import com.core.securites.Utilisateur;
import com.kerem.core.FileHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Stateless(mappedName = "SMessageDAO")
public class SMessageDAOImpl
    extends AbstractGenericDAO<SMessage, Long>
    implements SMessageDAOLocal, SMessageDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public SMessageDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<SMessage> getManagedEntityClass() {
        return (SMessage.class);
    }

    @Override
    public List<RMessage> send(long userid, SMessage message) {
        //To change body of generated methods, choose Tools | Templates.
//        System.out.println(SMessageDAOImpl.class.toString()+" ===== "+message+" === "+message.getSender());
        Utilisateur user = message.getSender();
        List<RMessage> output = new ArrayList<RMessage>();        
//                message.setSender(new Utilisateur(user));
        message.setDate(new Date());
        if(message.getCanal()!=null){
            Canal can = em.find(Canal.class, message.getCanal().getId());
            for(Utilisateur mem : can.getMembres()){
                if(mem.compareTo(user)!=0){//exclusion de l'emeteur du message
                    RMessage rmess = new RMessage(message);
                    rmess.setSender(new Utilisateur(user));
                    rmess.setReciever(new Utilisateur(mem));
                    rmess.setCanal(new Canal(message.getCanal()));
                    em.persist(rmess);
                    output.add(rmess);
                }//end if(!mem.equals(user))
            }//end for(Utilisateur mem : message.getCanal().getMembres())
        }//end if(message.getCanal()!=null)
        if(message.getReciever()!=null){
            RMessage rmess = new RMessage(message);
            rmess.setReciever(new Utilisateur(message.getReciever()));
            rmess.setSender(new Utilisateur(user));
            em.persist(rmess);
            output.add(rmess);
        }//end if(message.getReciever()!=null)
        //Traitement de lite recieve
        if(message.getRecievers()!=null){
            for(Utilisateur mem : message.getRecievers()){
                if(mem.compareTo(user)!=0){//exclusion de l'emeteur du message
                    RMessage rmess = new RMessage(message);
                    rmess.setSender(new Utilisateur(user));
                    rmess.setReciever(new Utilisateur(mem));
                    em.persist(rmess);
                    output.add(rmess);
                }//end if(!mem.equals(user))
            }//end for(Utilisateur mem : message.getCanal().getMembres())
        }//end if(message.getRecievers()!=null){
        //Traitement des canaux
        if(message.getCanaux()!=null){
            for(Canal can:message.getCanaux()){
                can = em.find(Canal.class, can.getId());
                for(Utilisateur mem : can.getMembres()){
                    if(mem.compareTo(user)!=0){//exclusion de l'emeteur du message
                        RMessage rmess = new RMessage(message);
                        rmess.setSender(new Utilisateur(user));
                        rmess.setReciever(mem);
                        em.persist(rmess);
                        output.add(rmess);
                    }//end if(!mem.equals(user))
                }//end for(Utilisateur mem : message.getCanal().getMembres())
            }//end for(Canal can:message.getCanaux()){
        }//end if(message.getCanaux()!=null){
//                dao.save(message);
        //Transfert des pieces jointes
        try {
                //Traitement des pieces jointes
                if(message.getPiecesjointe()!=null){
                    for(PieceJointe pj:message.getPiecesjointe()){

                            String sourceName = FileHelper.getTemporalDirectory()+File.separator+pj.getFilename();
                            String ciblename = FileHelper.getStaticDirectory()+File.separator+pj.getFilename();
                            FileHelper.moveFile(new File(sourceName), new File(ciblename));                  
                    }
                }//end if(message.getPiecesjointe()!=null)
       } catch (IOException ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(Response.serverError().build());
        }
        
        return output;
    }

    /**
     * It is RMessage where sender==null and reciever!= null
     * @param userid
     * @param message
     * @return 
     */
    @Override
    public List<RMessage> innerNotes(SMessage message) {
        //To change body of generated methods, choose Tools | Templates.
        List<RMessage> output = new ArrayList<RMessage>();        
//                message.setSender(new Utilisateur(user));
        message.setDate(new Date());
        if(message.getCanal()!=null){
            Canal can = em.find(Canal.class, message.getCanal().getId());
            for(Utilisateur mem : can.getMembres()){
                //exclusion de l'emeteur du message
                RMessage rmess = new RMessage(message);
                rmess.setSender(null);
                rmess.setReciever(mem);
                rmess.setCanal(new Canal(message.getCanal()));
                em.persist(rmess);
                output.add(rmess);                
            }//end for(Utilisateur mem : message.getCanal().getMembres())
        }//end if(message.getCanal()!=null)
        if(message.getReciever()!=null){
            RMessage rmess = new RMessage(message);
            rmess.setReciever(message.getReciever());
            rmess.setSender(null);
            em.persist(rmess);
            output.add(rmess);
        }//end if(message.getReciever()!=null)
        //Traitement de lite recieve
        if(message.getRecievers()!=null){
            for(Utilisateur mem : message.getRecievers()){
                //exclusion de l'emeteur du message
                RMessage rmess = new RMessage(message);
                rmess.setSender(null);
                rmess.setReciever(mem);
                em.persist(rmess);
                output.add(rmess);                
            }//end for(Utilisateur mem : message.getCanal().getMembres())
        }//end if(message.getRecievers()!=null){
        //Traitement des canaux
        if(message.getCanaux()!=null){
            for(Canal can:message.getCanaux()){
                can = em.find(Canal.class, can.getId());
                for(Utilisateur mem : can.getMembres()){
                    //exclusion de l'emeteur du message
                    RMessage rmess = new RMessage(message);
                    rmess.setSender(null);
                    rmess.setReciever(mem);
                    em.persist(rmess);
                    output.add(rmess);                    
                }//end for(Utilisateur mem : message.getCanal().getMembres())
            }//end for(Canal can:message.getCanaux()){
        }//end if(message.getCanaux()!=null){
//                dao.save(message);
        //Transfert des pieces jointes
        try {
                //Traitement des pieces jointes
                if(message.getPiecesjointe()!=null){
                    for(PieceJointe pj:message.getPiecesjointe()){
                        String sourceName = FileHelper.getTemporalDirectory()+File.separator+pj.getFilename();
                        String ciblename = FileHelper.getStaticDirectory()+File.separator+pj.getFilename();
                        FileHelper.moveFile(new File(sourceName), new File(ciblename));                  
                    }//end for(PieceJointe pj:message.getPiecesjointe()){
                }//end if(message.getPiecesjointe()!=null)
       } catch (IOException ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(Response.serverError().build());
        }
        
        return output;
    }

}
