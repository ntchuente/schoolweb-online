/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.application;

import com.core.calendar.EventManagerLocal;
import com.core.email.EmailManagerLocal;
import com.core.importexport.ExportManagerLocal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author BEKO
 */
@Startup
@Singleton
public class StartupBean {
    
    @EJB(name = "EventManager")
    protected EventManagerLocal eventManager;
    
    @EJB(name = "ExportManager")
    protected ExportManagerLocal exportManager;
    
    @EJB(name = "EmailManager")
    protected EmailManagerLocal emailManager;
    
    
    
    public final long EVENTDURATION=10000;//10 seconds 
    
    public final long EMAILDURATION = 10000;
    
    public enum States {BEFORESTARTED, STARTED, PAUSED, SHUTTINGDOWN};
    private States state;
    
    @PostConstruct
    @SuppressWarnings("empty-statement")
    public void initialize() {
        state = States.BEFORESTARTED;
        // Perform intialization
        state = States.STARTED;
       //Demarrage du timer des evenement
        Date  today = new Date();
        //Lancement du timer du gestionnaire des evenements
        eventManager.scheduleEventManager(today, EVENTDURATION);
        
        //Lancement du timer de traitement de mail
        try{
            emailManager.scheduleEventManager(today, EMAILDURATION);
        }catch(Exception ex){;}
        
        exportManager.scheduleDBExporterManager(today, EVENTDURATION);
    }
    @PreDestroy
    public void terminate() {
        state = States.SHUTTINGDOWN;
        // Perform termination
//        System.out.println(StartupBean.class+"=============================================================================================Shut down in progress");
    }
    public States getState() {
        return state;
    }
    public void setState(States state) {
        this.state = state;
    }
}
