/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.application;


import java.util.Set;

import javax.ws.rs.core.Application;

/**
 *
 * @author Commercial_2
 */
@javax.ws.rs.ApplicationPath("/")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.core.calendar.EventRSImpl.class);
        resources.add(com.core.calendar.RappelRSImpl.class);
        resources.add(com.core.discussions.CanalRSImpl.class);
        resources.add(com.core.discussions.FollowerRSImpl.class);
        resources.add(com.core.discussions.KMessageRSImpl.class);
        resources.add(com.core.discussions.RMessageRSImpl.class);
        resources.add(com.core.discussions.SMessageRSImpl.class);
        resources.add(com.core.email.EmailRSImpl.class);
        resources.add(com.core.files.UploadFileRSImpl.class);
        resources.add(com.core.importexport.ExportRSImpl.class);
        resources.add(com.core.langues.ChargerLangueRSImpl.class);
        resources.add(com.core.langues.LangueRSImpl.class);
        resources.add(com.core.langues.TermeRSImpl.class);
        resources.add(com.core.menus.ActionItemRSImpl.class);
        resources.add(com.core.menus.MenuActionRSImpl.class);
        resources.add(com.core.menus.MenuGroupActionsRSImpl.class);
        resources.add(com.core.menus.MenuModuleRSImpl.class);
        resources.add(com.core.referentiels.DeviseRSImpl.class);
        resources.add(com.core.referentiels.PaysRSImpl.class);
        resources.add(com.core.referentiels.PieceJointeRSImpl.class);
        resources.add(com.core.referentiels.SocieteRSImpl.class);
        resources.add(com.core.securites.GroupeRSImpl.class);
        resources.add(com.core.securites.PwdUser2RSImpl.class);
        resources.add(com.core.securites.PwdUserRSImpl.class);
        resources.add(com.core.securites.UtilisateurRSImpl.class);
        resources.add(com.core.views.CalendarRecordRSImpl.class);
        resources.add(com.core.views.DashboardRecordRSImpl.class);
        resources.add(com.core.views.FormRecordRSImpl.class);
        resources.add(com.core.views.ReportRecordRSImpl.class);
        resources.add(com.core.views.TreeRecordRSImpl.class);
        resources.add(com.core.securites.AnneGlobalRSImpl.class);
    }
    
}
