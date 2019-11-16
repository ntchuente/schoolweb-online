/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.login;

import java.security.Principal;

/**
 *
 * @author BEKO
 */
public class KerenPrincipal implements Principal{

     /**
     * Login de l'utilisateur
     */
    private String name ;

    /**
     * 
     * @param name 
     */
    public KerenPrincipal(String name) {
        this.name = name;
    }
    
    
    @Override
    public String getName() {
        //To change body of generated methods, choose Tools | Templates.
        return name;
    }
    
}
