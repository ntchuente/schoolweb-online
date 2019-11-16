/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.discussions;

import com.core.base.BaseElement;
import com.core.securites.Groupe;
import com.core.securites.Utilisateur;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_CANAL")
public class Canal extends BaseElement implements Serializable,Comparable<Canal>{

    @Predicate(label = "image" ,target = "image")
    private String image ;
    
    @Predicate(label = "Nom",optional = false,updatable = false,search = true)
    private String code ;
    
    @Predicate(label = "Envoyer un e-mail",type = Boolean.class)
    private Boolean sendMail = false ;
    
    @Predicate(label = "Description",search = true)
    private String description ;
    
    //Politique de publication
    @Predicate(label = "Qui peut publier ?",search = true,target = "combobox",group = true,groupLabel = "Confidentialité",groupName = "confid",values = "Tout le monde;Personnes authentifiées;Abonnés seulement")
    private String publication ;
    
    @Predicate(label = "Qui peut suivre?",search = true,target = "combobox",group = true,groupLabel = "Confidentialité",groupName = "confid",values = "Tout le monde ; Personnes invitées")
    private String confidentialite ;
    
    private boolean active = true ;
    
//    @Predicate(label = "Abonnement automatique",group = true,groupLabel = "Confidentialité",groupName = "confid",type = Groupe.class,target = "many-to-many")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "canal_grp",
            joinColumns = @JoinColumn(name="CAN_ID"),inverseJoinColumns = @JoinColumn(name = "GRP_ID"))
    private List<Groupe> groupes = new ArrayList<Groupe>();
    
    @Predicate(label = "membres",group = true,groupLabel = "Membres",groupName = "membres",type = Utilisateur.class,target = "many-to-many-list")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "canal_user",
            joinColumns = @JoinColumn(name="CAN_ID"),inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<Utilisateur> membres = new ArrayList<Utilisateur>();
    
    

    /**
     * 
     * @param image
     * @param code
     * @param description
     * @param publication
     * @param confidentialite 
     */
    public Canal(String image, String code, String description, String publication, String confidentialite) {
        this.image = image;
        this.code = code;
        this.description = description;
        this.publication = publication;
        this.confidentialite = confidentialite;
        
    }

    /**
     * 
     * @param image
     * @param code
     * @param description
     * @param publication
     * @param confidentialite
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Canal(String image, String code, String description, String publication, String confidentialite, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.image = image;
        this.code = code;
        this.description = description;
        this.publication = publication;
        this.confidentialite = confidentialite;
    }
    
     public Canal(Canal canal) {
        super(canal.id, canal.designation, canal.moduleName,canal.compareid);
        this.image = canal.image;
        this.code = canal.code;
        this.description = canal.description;
        this.publication = canal.publication;
        this.confidentialite = canal.confidentialite;
        this.groupes = new ArrayList<Groupe>();
    }

    public Canal() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getConfidentialite() {
        return confidentialite;
    }

    public void setConfidentialite(String confidentialite) {
        this.confidentialite = confidentialite;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public List<Utilisateur> getMembres() {
        return membres;
    }

    public void setMembres(List<Utilisateur> membres) {
        this.membres = membres;
    }

    @Override
    public String getDesignation() {
        return code; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "CANAUX"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "CANAL"; //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
    @Override
    public int compareTo(Canal o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }
    
}
