
package com.core.referentiels;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import java.util.List;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Wed Jan 10 21:34:02 WAT 2018
 * 
 */
@Path("/piecejointe")
public class PieceJointeRSImpl
    extends AbstractGenericService<PieceJointe, Long>
    implements PieceJointeRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "PieceJointeManagerImpl", interf = PieceJointeManagerRemote.class)
    protected PieceJointeManagerRemote manager;

    public PieceJointeRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<PieceJointe, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public List<PieceJointe> getPiecesJointe(String serial, long identifiant) {
        //To change body of generated methods, choose Tools | Templates.
        return manager.getPiecesJointe(serial, identifiant);
    }

}
