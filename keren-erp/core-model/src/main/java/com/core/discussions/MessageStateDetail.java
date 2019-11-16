/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import com.core.securites.Utilisateur;
import java.io.Serializable;

/**
 *
 * @author BEKO
 */
public class MessageStateDetail implements Serializable, Comparable<MessageStateDetail>{

    private Utilisateur user ;
    
    private Canal canal ;
    
    private long unreadmessages = 0;

    public MessageStateDetail() {
    }

    public MessageStateDetail(Utilisateur user, Canal canal) {
        this.user = user;
        this.canal = canal;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public long getUnreadmessages() {
        return unreadmessages;
    }

    public void setUnreadmessages(long unreadmessages) {
        this.unreadmessages = unreadmessages;
    }
    
    
    
    
    @Override
    public int compareTo(MessageStateDetail o) {
       //To change body of generated methods, choose Tools | Templates.
        if(user!=null){
            return user.compareTo(user);
        }
        return canal.compareTo(canal);
    }
    
}
