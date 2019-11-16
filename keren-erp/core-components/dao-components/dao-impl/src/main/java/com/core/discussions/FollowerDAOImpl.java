
package com.core.discussions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "FollowerDAO")
public class FollowerDAOImpl
    extends AbstractGenericDAO<Follower, Long>
    implements FollowerDAOLocal, FollowerDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public FollowerDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Follower> getManagedEntityClass() {
        return (Follower.class);
    }

}
