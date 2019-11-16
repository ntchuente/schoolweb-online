/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEKO
 */
public class MessageState implements Serializable,Comparable<MessageState>{

    private long unreadMessages = 0;
    
    private long inboxUnreadMessaes = 0 ;
    
    private List<MessageStateDetail> canaux =new ArrayList<MessageStateDetail>();
    
    private List<MessageStateDetail> directes =new ArrayList<MessageStateDetail>();

    public MessageState() {
    }

    public long getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(long unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public long getInboxUnreadMessaes() {
        return inboxUnreadMessaes;
    }

    public void setInboxUnreadMessaes(long inboxUnreadMessaes) {
        this.inboxUnreadMessaes = inboxUnreadMessaes;
    }

    public List<MessageStateDetail> getCanaux() {
        return canaux;
    }

    public void setCanaux(List<MessageStateDetail> canaux) {
        this.canaux = canaux;
    }

    public List<MessageStateDetail> getDirectes() {
        return directes;
    }

    public void setDirectes(List<MessageStateDetail> directes) {
        this.directes = directes;
    }
    
    
    @Override
    public int compareTo(MessageState o) {
        return 0; //To change body of generated methods, choose Tools | Templates.
    }
    
}
