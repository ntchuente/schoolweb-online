
package com.core.securites;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "AnneGlobalDAO")
public class AnneGlobalDAOImpl
    extends AbstractGenericDAO<AnneGlobal, Long>
    implements AnneGlobalDAOLocal, AnneGlobalDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public AnneGlobalDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<AnneGlobal> getManagedEntityClass() {
        return (AnneGlobal.class);
    }

}
