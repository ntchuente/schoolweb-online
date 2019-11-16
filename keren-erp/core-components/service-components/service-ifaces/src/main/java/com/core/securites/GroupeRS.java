
package com.core.securites;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Tue Nov 21 10:34:43 WAT 2017
 * 
 */
public interface GroupeRS
    extends GenericService<Groupe, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("detail")
    public List<GroupeDetail> getHabilitation(@Context HttpHeaders headers);
}
