
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

 * @since Mon Jan 29 12:08:25 GMT+01:00 2018
 * 
 */
public interface RMessageRS
    extends GenericService<RMessage, Long>
{
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("nonlus/{userid}")
    public MessageState getUnReadMessages(@PathParam("userid")long userid);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("nonluscourriel/{username}")
    public MessageState getUnReadMessages(@PathParam("username")String username);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("inbox/{userid}/{first}/{max}")
    public List<RMessage> getmessages(@PathParam("userid") long userid,@PathParam("first") int firstResult, @PathParam("max") int maxResult);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("marquer/{messageid}/{userid}/{index}/{max}")
    public List<RMessage> marqueMessage(@PathParam("messageid")long messageid,@PathParam("userid")long userid,@PathParam("index")int index,@PathParam("max")int max);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("inboxcount/{userid}")
    public  long getInboxCount(@PathParam("userid") long userid);
}
