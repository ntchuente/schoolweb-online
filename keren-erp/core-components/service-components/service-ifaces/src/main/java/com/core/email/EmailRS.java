
package com.core.email;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Thu May 03 15:12:05 GMT+01:00 2018
 * 
 */
public interface EmailRS
    extends GenericService<Email, Long>
{

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("directsent")
    public Email confirmer(@Context HttpHeaders headers,Email mail);

}
