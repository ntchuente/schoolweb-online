/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.login;

/**
 *
 * @author Commercial_2
 */
public class CurrentUser {
    
    private static Credential auth;

    public static Credential getAuth() {
        return auth;
    }

    public static void setAuth(Credential auth) {
        CurrentUser.auth = auth;
    }
    
    
    
}
