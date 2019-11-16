
package com.core.securites;

import com.core.menus.MenuModule;
import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Tue Nov 14 21:34:40 WAT 2017
 * 
 */
public interface UtilisateurRS
    extends GenericService<Utilisateur, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("application")
    public List<MenuModule> loadUserApplications(@Context HttpHeaders headers);
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("confirme")
    public Utilisateur confirmer(@Context HttpHeaders headers,Utilisateur user);
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("application/{courriel}")
    public List<MenuModule> loadUserApplicationsByCourriel(@Context HttpHeaders headers, @PathParam("courriel") String courriel);

}
