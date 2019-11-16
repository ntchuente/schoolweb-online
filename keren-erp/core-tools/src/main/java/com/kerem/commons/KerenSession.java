/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.commons;

import com.core.securites.Utilisateur;
import java.util.HashMap;
import java.util.Map;

/**
 *Keren Memory data
 * @author BEKO
 */
public class KerenSession {
    
    private static Utilisateur currentUser ;
    
    private static Map<String,String> traductMap = new HashMap<String,String>();

    /**
     * 
     * @return 
     */
    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    /**
     * 
     * @param currentUser 
     */
    public static void setCurrentUser(Utilisateur currentUser) {
        KerenSession.currentUser = currentUser;
    }

    /**
     * 
     * @param traductMap 
     */
    public static void setTraductMap(Map<String, String> traductMap) {
        KerenSession.traductMap = traductMap;
        System.out.println(KerenSession.class.toString()+" ==================================== "+traductMap);
    }
    
    /**
     * Ajoute une entre dans la correspondance
     * @param key
     * @param value 
     */
    public static void addEntry(String key , String value){
        KerenSession.traductMap.put(key, value);
    }
   
    /**
     * Retourne la traduction correspondante
     * @param key
     * @return 
     */
    public static String getEntry(String key){
        return KerenSession.traductMap.get(key);
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    public static Boolean containKey(String key){
        return KerenSession.traductMap.containsKey(key);
    }
}
