/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.login;

import com.kerem.security.DESEncrypter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.Path;

/**
 *
 * @author Commercial_2
 */
@Path("/login")
public class LoginRSImpl implements LoginRS{
    
    private LoginContext lc = null;    
    
    
    /**
     * 
     * @param auth 
     * @throws javax.security.auth.login.LoginException 
     */
     @Override
    public void login(Credential auth)  throws LoginException{
             if(auth.getUsername()==null||auth.getPassword()==null||auth.getAnneescolaire()==null){
                 throw new LoginException("Unknow username or password or annee scolaire");
             }//end if(auth.getUsername()==null||auth.getPassword()==null){
             // verifier que l'anné existe dejà
             
             
//             auth.setPassword(DESEncrypter.getInstance().encryptText(auth.getPassword()));
             CurrentUser.setAuth(auth);
             //To change body of generated methods, choose Tools | Templates.
             KerenCallbackHandler handler = new KerenCallbackHandler();
             //handler.setAuth(auth);
             LoginContext lc = new LoginContext("keren-auth", handler);
             lc.login();
             //Mise a jour du champs
//             System.out.println("YOUPI VOUS ETES AUTHENTIFIE EN TANT QUE "+auth.getUsername()+" ==== Password : "+auth.getPassword()+""
//             		+ " === Crypte Password : "+DESEncrypter.getInstance().encryptText(auth.getPassword())+" annee scolaire "+auth.getAnneescolaire());
//             
    }

    /**
     * 
     * @param aut 
     */
    @Override
    public void logout(Credential aut) {
         //To change body of generated methods, choose Tools | Templates.
//        System.out.println("Good By Mr  : "+aut.getUsername());
        if(lc!=null){
            try {
                lc.logout();
            } catch (LoginException ex) {
                Logger.getLogger(LoginRSImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//end if(lc!=null){
    }

    @Override
    public String getPassword(Credential auth) {
        //To change body of generated methods, choose Tools | Templates.
        return DESEncrypter.getInstance().encryptText(auth.getPassword());
    }
    
   
}
