
package com.core.securites;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "GroupeDAO")
public class GroupeDAOImpl
    extends AbstractGenericDAO<Groupe, Long>
    implements GroupeDAOLocal, GroupeDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public GroupeDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Groupe> getManagedEntityClass() {
        return (Groupe.class);
    }

}
