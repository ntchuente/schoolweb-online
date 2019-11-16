/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.securites;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@XmlRootElement
public class PwdUser extends BaseElement implements Serializable,Comparable<PwdUser>{

    private long cle ;
    
    @Predicate(label = "pwduser.utilisateur",updatable = false,optional = false)
    private String user ;    
    
    @Predicate(label = "pwduser.password",target = "password",optional = false)
    private String password ;

    
    /**
     * 
     */
    public PwdUser() {
    }

    /**
     * 
     * @param user
     * @param password
     * @param id
     * @param designation
     * @param moduleName 
     */
    public PwdUser(String user, String password, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCle() {
        return cle;
    }

    public void setCle(long cle) {
        this.cle = cle;
    }
    
    

    @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "Mise a jour Mot de passe"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "Mise a jour Mot de passe"; //To change body of generated methods, choose Tools | Templates.
    }

    
    
    

    @Override
    public String getDesignation() {
        return "Modifier Mot de passe / "+user; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "PwdUser{" + "cle=" + cle + ", user=" + user + ", password=" + password + '}';
    }
    
    
    
    
    @Override
    public int compareTo(PwdUser o) {
        //To change body of generated methods, choose Tools | Templates.
        return user.compareTo(o.user);
    }
    
}
