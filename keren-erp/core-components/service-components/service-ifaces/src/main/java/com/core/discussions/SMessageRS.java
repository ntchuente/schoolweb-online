
package com.core.discussions;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Mon Jan 29 12:08:25 GMT+01:00 2018
 * 
 */
public interface SMessageRS
    extends GenericService<SMessage, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("inbox/{userid}/{first}/{max}")
    public List<SMessage> getmessages(@PathParam("userid") long userid,@PathParam("first") int firstResult, @PathParam("max") int maxResult);
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})  
    @Path("send/{userid}")
    public List<RMessage> send(@PathParam("userid") long userid,SMessage message);

}
