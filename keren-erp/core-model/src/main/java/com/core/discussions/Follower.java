/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import com.core.base.BaseElement;
import com.core.securites.Utilisateur;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author BEKO
 */
@Entity
@Table(name="T_Follow")
public class Follower extends BaseElement implements Serializable,Comparable<Follower>{

    @Predicate(label = "Date" , type = Date.class)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date date ;
    
     //Numero de serie de l'entite
    private String entityserial ;
    
    //Identifiant de l'entite
    private long entityid ;
    
    @Predicate(label = "body")
    protected String body ;
    
    @Predicate(label = "Sender" ,target = "many-to-one" ,type=Utilisateur.class )
    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    protected Utilisateur sender ;
    
    @Predicate(label = "Abonnées" ,type = Utilisateur.class,target = "many-to-many")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_FOL_ABAN" , joinColumns = @JoinColumn(name = "FOL_ID"),inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    protected List<Utilisateur> abonnes = new ArrayList<Utilisateur>();
    
    @Predicate(label = "Canaux" ,type = Canal.class,target = "many-to-many")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_FOL_CAN" , joinColumns = @JoinColumn(name = "FOL_ID"),inverseJoinColumns = @JoinColumn(name = "CAN_ID"))
    protected List<Canal> canaux = new ArrayList<Canal>();
    
    @Predicate(label = "Note" , type = Boolean.class)
    protected Boolean noteinterne =  false ;
    
    @Predicate(label = "Actif" , type = Boolean.class)
    protected Boolean actif =  true ;
    
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "SMES_ID")
    @Predicate(label = "MESSAGES",type = SMessage.class,target = "one-to-many",group = true,groupName = "group1",groupLabel = "Messages")
    protected List<SMessage> messages = new ArrayList<SMessage>();
    

    public Follower(Date date, String body, Utilisateur sender) {
        this.date = date;
        this.body = body;
        this.sender = sender;
    }

    public Follower(Date date, String body, Utilisateur sender, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.date = date;
        this.body = body;
        this.sender = sender;
    }

   /**
    * 
    * @param data 
    */
     public Follower(Follower data) {
        super(data.id, data.designation, data.moduleName,data.compareid);
        this.date = data.date;
        this.body = data.body;
        this.noteinterne = data.isNoteinterne();
        this.actif = data.actif;
        this.entityserial = data.entityserial;
        this.entityid = data.entityid;
        if(data.sender!=null){
            this.sender = new Utilisateur(data.sender);
        }//end if(data.sender!=null)
        if(data.getAbonnes()!=null){
            for(Utilisateur user:data.getAbonnes()){
                this.abonnes.add(new Utilisateur(user));
            }
        }//end if(data.getAbonnes()!=null)
        if(data.getCanaux()!=null){
            for(Canal can:data.getCanaux()){
                this.canaux.add(new Canal(can));
            }//end for(Canal can:data.getCanaux())
        }//end if(data.getCanaux()!=null){
        if(data.getMessages()!=null){
            for(SMessage msge:data.getMessages()){
                this.messages.add(new SMessage(msge));
            }//end for(SMessage msge:data.getMessages())
        }//end if(data.getMessages()!=null)
    }
    public Follower() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Utilisateur getSender() {
        return sender;
    }

    public void setSender(Utilisateur sender) {
        this.sender = sender;
    }

    public List<Utilisateur> getAbonnes() {
        return abonnes;
    }

    public void setAbonnes(List<Utilisateur> abonnes) {
        this.abonnes = abonnes;
    }

    public List<Canal> getCanaux() {
        return canaux;
    }

    public void setCanaux(List<Canal> canaux) {
        this.canaux = canaux;
    }

    public boolean isNoteinterne() {
        return noteinterne;
    }

    public void setNoteinterne(boolean noteinterne) {
        this.noteinterne = noteinterne;
    }

    public String getEntityserial() {
        return entityserial;
    }

    public void setEntityserial(String entityserial) {
        this.entityserial = entityserial;
    }

    public long getEntityid() {
        return entityid;
    }

    public void setEntityid(long entityid) {
        this.entityid = entityid;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<SMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<SMessage> messages) {
        this.messages = messages;
    }
    
    
    
    @Override
    public int compareTo(Follower o) {
        //To change body of generated methods, choose Tools | Templates.
        return 0;
    }

    @Override
    public String toString() {
        return "Follower{" + "date=" + date + ", entityserial=" + entityserial + ", entityid=" + entityid + ", body=" + body + ", sender=" + sender + ", abonnes=" + abonnes + ", canaux=" + canaux + ", noteinterne=" + noteinterne + ", actif=" + actif + ", messages=" + messages + '}';
    }
    
}
