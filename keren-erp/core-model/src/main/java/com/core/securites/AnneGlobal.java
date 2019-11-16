package com.core.securites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;

/**
 * @author ntchuente
 *
 */
@Table
@Entity(name = "e_annee")
@XmlRootElement
public class AnneGlobal extends BaseElement implements Serializable, Comparable<AnneGlobal> {
	
	
		
	@Predicate(label="CODE",optional=false,updatable=false,search=true , sequence=1, colsequence=1)
	protected String code;

	


	public AnneGlobal(String code) {
		super();
		this.code = code;
	}



	public AnneGlobal() {
		super();
		// TODO Auto-generated constructor stub
	}



	public AnneGlobal(AnneGlobal annee) {
		super(annee.id, annee.designation, annee.moduleName,annee.compareid);
		this.code = annee.code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	 @Override
    public String getModuleName() {
    return "kerencore"; //To change body of generated methods, choose Tools | Templates.
	    }

	@Override
	public boolean isCreateonfield() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int compareTo(AnneGlobal arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}