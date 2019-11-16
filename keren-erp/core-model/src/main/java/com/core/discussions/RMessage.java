/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import com.core.referentiels.PieceJointe;
import com.core.securites.Utilisateur;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author BEKO
 */
@Entity
@DiscriminatorValue("R")
public class RMessage extends KMessage implements Serializable{

    /**
     * 
     * @param date
     * @param body
     * @param sender
     * @param canal
     * @param reciever 
     */
    public RMessage(Date date, String body, Utilisateur sender, Canal canal, Utilisateur reciever) {
        super(date, body, sender, canal, reciever);
        this.typeMessage = MessageOrientation.RECEPTION;
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
    public RMessage(Date date, String body, Utilisateur sender, Canal canal, Utilisateur reciever, long id, String designation, String moduleName) {
        super(date, body, sender, canal, reciever, id, designation, moduleName);
        this.typeMessage = MessageOrientation.RECEPTION;
    }
    /**
     * 
     * @param message 
     */
     public RMessage(RMessage message) {
        super(message.getDate(), message.getBody(), null, null, null, message.id, message.designation, message.moduleName);
        this.typeMessage = MessageOrientation.RECEPTION;
        this.status = message.isStatus();
        if(message.getSender()!=null){
            sender = new Utilisateur(message.getSender());
        }
        if(message.getReciever()!=null){
            reciever = new Utilisateur(message.getReciever());
        }
        if(message.getCanal()!=null){
            canal = new Canal(message.getCanal());
        }
        if(message.getPiecesjointe()!=null){
            for(PieceJointe pj:message.getPiecesjointe()){
                piecesjointe.add(new PieceJointe(pj));
            }
        }
    }
  public RMessage(SMessage message) {
        super(message.getDate(), message.getBody(), null, null, null, -1, message.getDesignation(), message.getModuleName());
        this.status = false;
        this.typeMessage = MessageOrientation.RECEPTION;        
        reciever = null;
        canal = null;        
        if(message.getPiecesjointe()!=null){
            for(PieceJointe pj:message.getPiecesjointe()){
                PieceJointe pjd = new PieceJointe(pj);
                pjd.setId(-1);
                piecesjointe.add(pjd);
            }
        }
    }
    /**
     * 
     */
    public RMessage() {
        this.typeMessage = MessageOrientation.RECEPTION; 
    }
    
}
