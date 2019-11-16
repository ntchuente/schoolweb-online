/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.login;

import com.kerem.security.DESEncrypter;
import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author Commercial_2
 */
public class KerenCallbackHandler implements CallbackHandler{

    private Credential auth ;
    
    /**
     * 
     * @param callbacks
     * @throws IOException
     * @throws UnsupportedCallbackException 
     */
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        auth = CurrentUser.getAuth();
        //To change body of generated methods, choose Tools | Templates.
        if(auth!=null&&auth.getUsername()!=null&&auth.getPassword()!=null){
            for(int i=0;i<callbacks.length;i++){
                if(callbacks[i] instanceof NameCallback){
                    NameCallback nc = (NameCallback) callbacks[i];
                    nc.setName(auth.getUsername());
                }else if(callbacks[i] instanceof PasswordCallback){
                    PasswordCallback pc = (PasswordCallback) callbacks[i];  
//                    System.out.println(KerenCallbackHandler.class.toString()+" ===============================  password : "+auth.getPassword()+""
//                    		+ " ============== crypte : "+DESEncrypter.getInstance().encryptText(auth.getPassword())+
//                    		"Annee Scolaire =========================="+auth.getAnneescolaire());
                 pc.setPassword(DESEncrypter.getInstance().encryptText(auth.getPassword()).toCharArray());
                }
            }
        }
        
    }

    public KerenCallbackHandler() {
    }

    public Credential getAuth() {
        return auth;
    }

    public void setAuth(Credential auth) {
        this.auth = auth;
    }
    
    
    
}
