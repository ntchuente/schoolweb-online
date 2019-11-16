
package com.core.calendar;

import com.core.discussions.KMessage;
import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Sat Nov 18 09:29:25 WAT 2017
 * 
 */
public interface EventRS
    extends GenericService<Event, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("event/{userid}")
    public List<Event> getevents(@Context HttpHeaders headers,@PathParam("userid") long userid);
      
}
