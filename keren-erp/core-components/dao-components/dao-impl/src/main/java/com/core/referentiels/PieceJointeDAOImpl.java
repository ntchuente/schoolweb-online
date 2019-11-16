
package com.core.referentiels;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "PieceJointeDAO")
public class PieceJointeDAOImpl
    extends AbstractGenericDAO<PieceJointe, Long>
    implements PieceJointeDAOLocal, PieceJointeDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public PieceJointeDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<PieceJointe> getManagedEntityClass() {
        return (PieceJointe.class);
    }

}
