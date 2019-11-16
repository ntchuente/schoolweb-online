
package com.core.discussions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "RMessageDAO")
public class RMessageDAOImpl
    extends AbstractGenericDAO<RMessage, Long>
    implements RMessageDAOLocal, RMessageDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public RMessageDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<RMessage> getManagedEntityClass() {
        return (RMessage.class);
    }

}
