/**
 * 
 */
package com.momerata.ejbtimer;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 * @author PSE-Computer
 *
 */
//@Singleton
public class SmsService {
	/**
	 * le traitement s'executera le 12 decembre 2012 a 12h00
	 */
	//@Schedule(hour='12', dayOfMonth='12', month='12', year='2012') 
	//	 @Schedule(minute='*/10')  tous les 10 minute
	// @Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	
	  @Schedule( minute = "*/2", hour = "*", persistent = false)
	    public void periodic() {
	        System.out.println("bonjour je suis la period...");
	    }
		
	  @Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
	    public void schedule() {
	        System.out.println(" bonsoir je suis schedule....");
	    }

}
