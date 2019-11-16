/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import com.core.base.BaseElement;
import com.core.referentiels.PieceJointe;
import com.core.securites.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
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
@Table(name = "T_KMSGE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_MSGE",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("BASE")
public class KMessage extends BaseElement implements Serializable,Comparable<KMessage>{

    @Temporal(TemporalType.TIMESTAMP)
    protected Date date ;   
    
    protected String body ;
    
    protected boolean status = false;
    
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "KMSGE_ID")
    protected List<PieceJointe> piecesjointe = new ArrayList<PieceJointe>();
    
    @ManyToOne
    @JoinColumn(name = "SEND_ID")
    protected Utilisateur sender ;
    
    @ManyToOne
    @JoinColumn(name = "CAN_ID")
    protected Canal canal ;
    
    @ManyToOne
    @JoinColumn(name = "RECI_ID") 
    protected Utilisateur reciever ;
    
    //Numero de serie de l'entite
    protected String entityserial ;
    
    //Identifiant de l'entite
    protected long entityid ;
    
    protected MessageOrientation typeMessage = MessageOrientation.INTERNE;

    /**
     * 
     * @param date
     * @param body
     * @param sender
     * @param canal
     * @param reciever 
     */
    public KMessage(Date date, String body, Utilisateur sender, Canal canal, Utilisateur reciever) {
        this.date = date;
        this.body = body;
        this.sender = sender;
        this.canal = canal;
        this.reciever = reciever;
    }
    
    /**
     * 
     * @param message 
     */
    public KMessage(KMessage message) {
        super(message.id, message.designation, message.moduleName,message.compareid);
        this.date = message.date;
        this.body = message.body;
        this.status = message.isStatus();
        this.typeMessage = message.typeMessage;
        if(message.getSender()!=null){
            this.sender = new Utilisateur(message.getSender());
        }
        if(message.getReciever()!=null){
            this.reciever = new Utilisateur(message.getReciever());
        }
        if(message.getCanal()!=null){
            this.canal = new Canal(message.getCanal());
        }
        if(message.getPiecesjointe()!=null){
            for(PieceJointe pj:message.getPiecesjointe()){
                this.piecesjointe.add(new PieceJointe(pj));
            }
        }
    }
    
    /**
     * 
     * @param date
     * @param body
     * @param sender
     * @param canal
     * @param reciever
     * @param id
     * @param designation
     * @param moduleName 
     */
    public KMessage(Date date, String body, Utilisateur sender, Canal canal, Utilisateur reciever, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.date = date;
        this.body = body;
        this.sender = sender;
        this.canal = canal;
        this.reciever = reciever;
    }

    public KMessage() {
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

    public List<PieceJointe> getPiecesjointe() {
        return piecesjointe;
    }

    public void setPiecesjointe(List<PieceJointe> piecesjointe) {
        this.piecesjointe = piecesjointe;
    }

    public Utilisateur getSender() {
        return sender;
    }

    public void setSender(Utilisateur sender) {
        this.sender = sender;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public Utilisateur getReciever() {
        return reciever;
    }

    public void setReciever(Utilisateur reciever) {
        this.reciever = reciever;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public MessageOrientation getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(MessageOrientation typeMessage) {
        this.typeMessage = typeMessage;
    }
    
    
    
    @Override
    public int compareTo(KMessage o) {
       return date.compareTo(o.date); //To change body of generated methods, choose Tools | Templates.
    }
    
}
