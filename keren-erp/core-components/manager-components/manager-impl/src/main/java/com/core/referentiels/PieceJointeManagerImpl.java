
package com.core.referentiels;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import java.util.HashSet;
import java.util.List;

@TransactionAttribute
@Stateless(mappedName = "PieceJointeManager")
public class PieceJointeManagerImpl
    extends AbstractGenericManager<PieceJointe, Long>
    implements PieceJointeManagerLocal, PieceJointeManagerRemote
{

    @EJB(name = "PieceJointeDAO")
    protected PieceJointeDAOLocal dao;

    public PieceJointeManagerImpl() {
    }

    @Override
    public GenericDAO<PieceJointe, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<PieceJointe> getPiecesJointe(String serial, long identifiant) {
        //To change body of generated methods, choose Tools | Templates.
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("entityserial", serial);
        container.addEq("entityid", identifiant);
        return dao.filter(container.getPredicats(), null, new HashSet<String>(), 0, -1);
    }

}
