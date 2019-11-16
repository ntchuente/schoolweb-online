/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.securites;

import com.core.base.BaseElement;
import com.core.base.State;
import com.core.langues.Langue;
import com.core.referentiels.Societe;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_USER")
@XmlRootElement
public class Utilisateur extends BaseElement implements Serializable,Comparable<Utilisateur>{

    @Predicate(label = "utilisateur.image",target = "image",sequence = 1)
    private String image ="img\\photo.png";  
    
    @Predicate(label = "utilisateur.actif",colsequence = 100,sequence = 2)
    private Boolean actif = Boolean.FALSE; 
    
    @Predicate(label = "utilisateur.login" ,nullable = false ,optional = false,min = 4,unique = true,updatable = false,search = true,sequence = 3)
    private String intitule ;     
    
    @Predicate(label = "utilisateur.email",target = "email",unique = false,optional = false,search = true,sequence = 4)
    private String courriel ;
    
    //@Predicate(label = "MOT DE PASSE",target = "password" ,optional = false,search = false)
    private String password = "ISMvKXpXpadDiUoOSoAfww==" ; 
    
    private String name = "admin";
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastconfirme = null;
    
    @Predicate(label = "utilisateur.societe.courante" , type = Societe.class,optional = false,sequence = 5,search = true,searchfields ="code" )
    @ManyToOne
    @JoinColumn(name = "SC_ID")
    private Societe societeCourante ;
    
    @Predicate(label = "utilisateur.societes.autorisees" ,type = Societe.class,sequence = 6)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_USER_SCT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SCT_ID"))
    private List<Societe> societeAutorisees = new ArrayList<Societe>();
    
    @Predicate(label = "utilisateur.niveau.administration" ,target = "combobox" ,values = "Aucun;Applications;Configuration;Applications & Configuration",search = false,sequence = 7)
    private String adminlevel = "0" ;   
    
    @Predicate(label = "utilisateur.droits" ,type = Groupe.class,group = true,groupName = "group1",groupLabel = "PROFIL UTILISATEUR",target = "many-to-many-list",searchfields = "code")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_USER_RIGTH",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "RIGTH_ID"))
    private List<Groupe> autorisations = new ArrayList<Groupe>();
    
    @ManyToOne
    @JoinColumn(name = "LANG_ID")
    @Predicate(label = "utilisateur.langue",type = Langue.class,target = "many-to-one",group = true,groupName = "group2",groupLabel = "Préférences")
    private Langue langue ;
    
    private Boolean uniqueuser;
    private String state ="etabli";
    
    /**
     * 
     */
    public Utilisateur() {
    }

    /**
     * 
     * @param image
     * @param intitule
     * @param courriel
             * @param societeCourante
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Utilisateur(String image, String intitule, String courriel, Societe societeCourante, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.image = image;
        this.intitule = intitule;
        this.courriel = courriel;
        //this.societeCourante = societeCourante;
    }

    /**
     * 
     * @param user 
     */
   public Utilisateur(Utilisateur user) {
        super(user.id, user.designation, user.moduleName,user.compareid);
        this.image = user.image;
        this.intitule = user.intitule;
        this.courriel = user.courriel;
        this.password = user.password;
        this.state = user.state;
        this.name = user.name;
        this.actif = user.actif;
        this.societeCourante = user.societeCourante;
        this.adminlevel = user.adminlevel;
        this.langue = user.langue;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getDesignation() {
        return intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminlevel() {
        return adminlevel;
    }

    public void setAdminlevel(String adminlevel) {
        this.adminlevel = adminlevel;
    }   

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }
    
    public Societe getSocieteCourante() {
        return societeCourante;
    }

    public void setSocieteCourante(Societe societeCourante) {
        this.societeCourante = societeCourante;
    }

    public List<Societe> getSocieteAutorisees() {
        return societeAutorisees;
    }

    public void setSocieteAutorisees(List<Societe> societeAutorisees) {
        this.societeAutorisees = societeAutorisees;
    }

    public List<Groupe> getAutorisations() {
        return autorisations;
    }

    public void setAutorisations(List<Groupe> autorisations) {
        this.autorisations = autorisations;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }    

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }    

    public Boolean getUniqueuser() {
        return uniqueuser;
    }

    public void setUniqueuser(Boolean uniqueuser) {
        this.uniqueuser = uniqueuser;
    }
    
    @Override
    public String getListTitle() {
        return "utilisateur.liste.title"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "utilisateur.dialogue.title"; //To change body of generated methods, choose Tools | Templates.
    }

    public Date getLastconfirme() {
        return lastconfirme;
    }

    public void setLastconfirme(Date lastconfirme) {
        this.lastconfirme = lastconfirme;
    }

       
    
    @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<State> getStates() {
        //To change body of generated methods, choose Tools | Templates.
        List<State> etats = new ArrayList<State>();
        State etat = new State("etabli", "Non Confirmé");
        etats.add(etat);
        etat = new State("confirme", "Confirmé");
        etats.add(etat);
        return etats; 
    }

    
    
    @Override
    public int compareTo(Utilisateur o) {
        //To change body of generated methods, choose Tools | Templates.
        return courriel.compareTo(o.courriel);
    }
    
}
