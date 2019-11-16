
package com.core.referentiels;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Wed Jan 10 21:34:02 WAT 2018
 * 
 */
public interface PieceJointeRS
    extends GenericService<PieceJointe, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("pj/{serial}/{id}")
    public List<PieceJointe> getPiecesJointe(@PathParam("serial")String serial , @PathParam("id")long identifiant);
}
