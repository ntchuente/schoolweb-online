
package com.core.views;

import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Mon Dec 18 20:36:03 WAT 2017
 * 
 */
public interface ReportRecordRS
    extends GenericService<ReportRecord, Long>
{
     @GET
     @Produces({MediaType.APPLICATION_JSON})
     @Path("meta/{id}")
     public MetaData getSearchMetaData(@Context HttpHeaders headers ,@PathParam("id") long id);
}
