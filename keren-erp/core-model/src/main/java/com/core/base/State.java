/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.base;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@XmlRootElement
public class State implements Serializable{
    
    private String code ;
    
    private String intitule ;

    /**
     * 
     * @param code
     * @param intitule 
     */
    public State(String code, String intitule) {
        this.code = code;
        this.intitule = intitule;
    }

    public State() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
    
    
    
}
