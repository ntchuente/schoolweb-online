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
public class Dashboard extends BaseElement implements Serializable,Comparable<Dashboard>{

    private String label ;
    
    private String code ;
    
    private List<DashboardEntry> entries = new ArrayList<DashboardEntry>();

    /**
     * 
     * @param label
     * @param code 
     */
    public Dashboard(String label, String code) {
        this.label = label;
        this.code = code;
    }

    /**
     * 
     * @param label
     * @param code
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Dashboard(String label, String code, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.label = label;
        this.code = code;
    }

    /**
     * 
     */
    public Dashboard() {
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

    public List<DashboardEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DashboardEntry> entries) {
        this.entries = entries;
    }
    
    
    
    @Override
    public int compareTo(Dashboard o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }
    
}
