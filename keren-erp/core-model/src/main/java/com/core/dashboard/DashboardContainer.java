/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.dashboard;

import com.core.base.BaseElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Commercial_2
 */
public class DashboardContainer extends BaseElement implements Serializable ,Comparable<DashboardContainer>{
    
    /**
     * Label de la fenetre
     */
    private String label ;

    private String code ;
    
    private String method ;
    
    private String entity;
    
    private List<Dashboard> dashboards = new ArrayList<Dashboard>();
    
    public DashboardContainer() {
    }    
    
    /**
     * 
     * @param label 
     */
    public DashboardContainer(String label) {
        this.label = label;
    }

    /**
     * 
     * @param label
     * @param id
     * @param designation
     * @param moduleName 
     */
    public DashboardContainer(String label, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.label = label;
    }

    
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    

    @Override
    public int compareTo(DashboardContainer o) {
        //To change body of generated methods, choose Tools | Templates.
        return  code.compareTo(o.code);
    }
    
    
    
}
