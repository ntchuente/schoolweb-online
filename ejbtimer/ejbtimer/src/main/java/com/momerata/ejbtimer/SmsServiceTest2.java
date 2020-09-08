/**
 * 
 */
package com.momerata.ejbtimer;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 * @author PSE-Computer
 *
 */
@Singleton
public class SmsServiceTest2 {
	
	@Resource
	TimerService timerservice;
	
	@Schedule(minute="*/2", hour="*", persistent=false)
	public void monTraitement(Timer timer) {
	 System.out.println("SmsServiceTest2.monTraitement() test sms service 2222------");
	} 
	
	@Timeout
	public void handleTimeout(Timer timer) {
	    System.out.println("Handle timeour event here...");
	}

}
