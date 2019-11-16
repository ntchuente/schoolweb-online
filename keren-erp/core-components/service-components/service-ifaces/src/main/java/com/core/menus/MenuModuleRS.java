
package com.core.menus;

import com.core.application.Manifest;
import com.megatimgroup.generic.jax.rs.layer.ifaces.GenericService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Interface du service JAX-RS

 * @since Tue Nov 21 22:36:56 WAT 2017
 * 
 */
public interface MenuModuleRS
    extends GenericService<MenuModule, Long>
{

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("manifest")
    public Manifest getManifest();
    
    @POST
    @Path("refresh")
    public void updateApplications();
    
    @POST
    @Path("install")
    @Consumes({MediaType.APPLICATION_JSON})
    public void installApplication(MenuModule module);
    
    @POST
    @Path("uninstall")
    @Consumes({MediaType.APPLICATION_JSON})
    public void uninstallApplication(MenuModule module);
}
