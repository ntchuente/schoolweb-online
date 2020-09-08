package com.momerata.ejbtimer;

import javax.ejb.Local;

@Local
public interface EmployeeLocal {
	
	public Long addBid(String userId,Long itemId,Double bidPrice);

}
