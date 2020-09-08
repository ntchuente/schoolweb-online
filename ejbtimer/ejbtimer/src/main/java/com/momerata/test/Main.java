/**
 * 
 */
package com.momerata.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.momerata.ejbtimer.EmployeeRemote;

/**
 * @author PSE-Computer
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 EmployeeRemote service = null;

		    // Context compEnv = (Context) new InitialContext().lookup("java:comp/env");

		    // service = (HelloService)new
		    // InitialContext().lookup("java:comp/env/ejb/HelloService");
		    try {
				service = (EmployeeRemote) new InitialContext().lookup("EmployeeBean/remote");
				
				   service.addBid("userId",1L,0.1);
				   
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		 
		    


		  }

}
