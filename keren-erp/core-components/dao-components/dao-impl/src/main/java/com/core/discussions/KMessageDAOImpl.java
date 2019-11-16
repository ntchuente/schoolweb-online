
package com.core.discussions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "KMessageDAO")
public class KMessageDAOImpl
    extends AbstractGenericDAO<KMessage, Long>
    implements KMessageDAOLocal, KMessageDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public KMessageDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<KMessage> getManagedEntityClass() {
        return (KMessage.class);
    }

}
