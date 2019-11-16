
package com.core.discussions;

import com.core.securites.Utilisateur;
import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Sat Jan 27 08:28:43 GMT+01:00 2018
 * 
 */
public interface CanalRS
    extends GenericService<Canal, Long>
{
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("canaux/{username}")
     public List<Canal> getCanaux(@PathParam("username")String username);
     
     @GET
     @Produces({MediaType.APPLICATION_JSON})
     @Path("directe/{username}")
     public List<Utilisateur> getConnectUsers(@PathParam("username")String username);

}
