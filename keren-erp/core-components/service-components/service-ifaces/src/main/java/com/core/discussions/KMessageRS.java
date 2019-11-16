
package com.core.discussions;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Tue Jan 30 08:25:11 GMT+01:00 2018
 * 
 */
public interface KMessageRS
    extends GenericService<KMessage, Long> 
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("canal/{userid}/{canalid}/{first}/{max}")
    public List<KMessage> getmessages(@PathParam("userid") long userid,@PathParam("canalid") long canalid,@PathParam("first") int firstResult, @PathParam("max") int maxResult);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("direct/{userid}/{connectuserid}/{first}/{max}")
    public List<KMessage> getUsermessages(@PathParam("userid") long userid,@PathParam("connectuserid") long canalid,@PathParam("first") int firstResult, @PathParam("max") int maxResult);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("countcanal/{userid}/{canalid}")
    public long countCanalMsge(@PathParam("userid") long userid,@PathParam("canalid") long canalid);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("countdirect/{userid}/{connectuserid}")
    public long countDirectMsge(@PathParam("userid") long userid,@PathParam("connectuserid") long connectuserid);
}
