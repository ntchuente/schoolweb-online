
package com.core.discussions;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
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

 * @since Wed Jan 31 16:04:28 GMT+01:00 2018
 * 
 */
public interface FollowerRS
    extends GenericService<Follower, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("entity/{serial}/{entityid}")
    public Follower getFollower(@PathParam("serial") String serial ,@PathParam("entityid")long entityid);

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})  
    @Path("send/{userid}")
    public Follower computeFollower(@PathParam("userid")long userid , Follower follower);
}
