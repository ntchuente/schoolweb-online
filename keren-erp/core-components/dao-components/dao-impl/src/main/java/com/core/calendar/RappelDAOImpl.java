
package com.core.calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "RappelDAO")
public class RappelDAOImpl
    extends AbstractGenericDAO<Rappel, Long>
    implements RappelDAOLocal, RappelDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public RappelDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Rappel> getManagedEntityClass() {
        return (Rappel.class);
    }

}
