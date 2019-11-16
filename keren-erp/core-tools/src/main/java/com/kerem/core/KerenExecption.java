/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.core;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author BEKO
 */
public class KerenExecption extends WebApplicationException{

    /**
     * 
     * @param message 
     */
    public KerenExecption(String message) {
        super(Response.status(Response.Status.PRECONDITION_FAILED).entity(message).build());
    }
    
    
    
}
