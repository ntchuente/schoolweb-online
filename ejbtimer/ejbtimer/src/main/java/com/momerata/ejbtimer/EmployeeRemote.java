/**
 * 
 */
package com.momerata.ejbtimer;

import javax.ejb.Remote;

/**
 * @author PSE-Computer
 *
 */
@Remote
public interface EmployeeRemote {

	public Long addBid(String userId,Long itemId,Double bidPrice);
}
