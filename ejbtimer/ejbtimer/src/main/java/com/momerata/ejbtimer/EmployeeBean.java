/**
 * 
 */
package com.momerata.ejbtimer;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 * @author PSE-Computer
 *
 */
@Stateless
public class EmployeeBean implements EmployeeLocal,EmployeeRemote{
	
	@Resource
	  private SessionContext ctx;

	  public EmployeeBean() {
	  }

	  public Long addBid(String userId, Long itemId, Double bidPrice) {
		    System.out.println("Bid for " + itemId + " received with price" + bidPrice);

		    TimerService timerService = ctx.getTimerService();
		    Timer timer = timerService.createTimer(123, 86400000, null);

		    return 0L;
		  }

		  @Timeout
		  public void handleTimeout(Timer timer) {
		    System.out.println(" handleTimeout called.");
		  }
	

}
