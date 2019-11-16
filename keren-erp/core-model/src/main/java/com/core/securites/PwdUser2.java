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
public class PwdUser2 extends BaseElement implements Serializable,Comparable<PwdUser2>{

    private long cle ;
    
    @Predicate(label = "pwduser2.ancien.password",target = "password",optional = false)
    private String oldpassword ;    
    
    @Predicate(label = "pwduser2.nouveau.password",target = "password",optional = false)
    private String newpassword ;

    
    /**
     * 
     */
    public PwdUser2() {
    }

    /**
     * 
     * @param user
     * @param password
     * @param id
     * @param designation
     * @param moduleName 
     */
    public PwdUser2(String user, String password, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.oldpassword = user;
        this.newpassword = password;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
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
        return "Modifier Mot de passe / "+oldpassword; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "PwdUser{" + "cle=" + cle + ", user=" + oldpassword + ", password=" + newpassword + '}';
    }
    
    
    
    
    @Override
    public int compareTo(PwdUser2 o) {
        //To change body of generated methods, choose Tools | Templates.
        return oldpassword.compareTo(o.oldpassword);
    }
    
}
