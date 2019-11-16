/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.securites;

import com.core.base.BaseElement;
import com.core.menus.MenuModule;
import com.megatim.common.annotations.Observer;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_GROUPE")
public class Groupe extends BaseElement implements Serializable,Comparable<Groupe>{

    @Predicate(label = "nom.groupe" ,optional = false,unique = true,updatable = false,search = true)
    private String code ;
    
    @ManyToOne
    @JoinColumn(name = "M_ID")
    @Predicate(label = "groupe.module.concerne",target = "many-to-one",optional = false,updatable = false,type = MenuModule.class,search = true,observable = true)
    private MenuModule module ;
    
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "GROUP_ID")
    @Predicate(label = "groupe.habilitations",group = true,groupName = "group_1",groupLabel = "groupe.habilitations",updatable = false,target = "one-to-many",type = GroupeDetail.class,edittable = true)
    @Observer(observable = "module",source = "method:detail",parameters = "code,module")
    private List<GroupeDetail> droits = new ArrayList<GroupeDetail>();

    /**
     * 
     */
    public Groupe() {
    }

    /**
     * 
     * @param code
     * @param module 
     */
    public Groupe(String code, MenuModule module) {
        this.code = code;
        this.module = module;
    }

    /**
     * 
     * @param code
     * @param module
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Groupe(String code, MenuModule module, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.code = code;
        this.module = module;
    }
    
     public Groupe(Groupe groupe) {
        super(groupe.id, groupe.designation, groupe.moduleName,groupe.compareid);
        this.code = groupe.code;
        //this.module = groupe.module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MenuModule getModule() {
        return module;
    }

    public void setModule(MenuModule module) {
        this.module = module;
    }

    public List<GroupeDetail> getDroits() {
        return droits;
    }

    public void setDroits(List<GroupeDetail> droits) {
        this.droits = droits;
    }

    @Override
    public String getDesignation() {
        String value = code;
//        
//        if(module!=null){
//            value = module.getDesignation()+" / ";
//        }
//        value +=code;
        return value; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getCompareid() {
//        if(module!=null){
//            return module.getId();
//        }
        return super.getCompareid(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "GROUPES"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "GROUPE"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public int compareTo(Groupe o) {
       //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }

    @Override
    public String[] searchFields() {
        return new String[]{"code"}; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
