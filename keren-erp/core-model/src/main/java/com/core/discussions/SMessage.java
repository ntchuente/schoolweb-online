/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import com.core.referentiels.PieceJointe;
import com.core.securites.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author BEKO
 */
@Entity
@DiscriminatorValue("S")
public class SMessage extends KMessage implements Serializable{

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_SMES_USER",joinColumns = @JoinColumn(name = "SMESS_ID"),inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<Utilisateur> recievers = new ArrayList<Utilisateur>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_SMES_CAN",joinColumns = @JoinColumn(name = "SMESS_ID"),inverseJoinColumns = @JoinColumn(name = "CAN_ID"))
    private List<Canal> canaux = new ArrayList<Canal>();
    
    /**
     * 
     * @param date
     * @param body
     * @param sender
     * @param canal
     * @param reciever 
     */
    public SMessage(Date date, String body, Utilisateur sender, Canal canal, Utilisateur reciever) {
        super(date, body, sender, canal, reciever);
        this.typeMessage = MessageOrientation.ENVOI;
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
    public SMessage(Date date, String body, Utilisateur sender, Canal canal, Utilisateur reciever, long id, String designation, String moduleName) {
        super(date, body, sender, canal, reciever, id, designation, moduleName);
        this.typeMessage = MessageOrientation.ENVOI;
    }
    
     public SMessage(SMessage message) {
        super(message.date, message.body,null,null, null, message.id, message.designation, message.moduleName);
        this.typeMessage = MessageOrientation.ENVOI;
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
        if(message.getRecievers()!=null){
            for(Utilisateur user:message.getRecievers()){
                this.recievers.add(new Utilisateur(user));
            }
        }
        
        if(message.getCanaux()!=null){
            for(Canal can:message.getCanaux()){
                this.canaux.add(new Canal(can));
            }
        }
    }

     
    /**
     * 
     */
    public SMessage() {
         this.typeMessage = MessageOrientation.ENVOI;
    }

    public List<Utilisateur> getRecievers() {
        return recievers;
    }

    public void setRecievers(List<Utilisateur> recievers) {
        this.recievers = recievers;
    }

   

    public List<Canal> getCanaux() {
        return canaux;
    }

    public void setCanaux(List<Canal> canaux) {
        this.canaux = canaux;
    }

    @Override
    public String toString() {
        return "SMessage{" + "recievers=" + recievers + ", canaux=" + canaux + '}';
    }
    
    
    
    
}
