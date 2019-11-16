/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.login;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@XmlRootElement
public class Credential implements Serializable{
    
    private String username ;
    
    private String password ;
    
    private String anneescolaire;

    /**
     * 
     */
    public Credential() {
    }

    /**
     * 
     * @param username
     * @param password 
     */
    public Credential(String username, String password, String anneescolaire) {
        this.username = username;
        this.password = password;
        this.anneescolaire=anneescolaire;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getAnneescolaire() {
		return anneescolaire;
	}

	public void setAnneescolaire(String anneescolaire) {
		this.anneescolaire = anneescolaire;
	}

	public void setPassword(String password) {
        this.password = password;
    }
    
    
}
