
package com.core.langues;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "TermeDAO")
public class TermeDAOImpl
    extends AbstractGenericDAO<Terme, Long>
    implements TermeDAOLocal, TermeDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public TermeDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Terme> getManagedEntityClass() {
        return (Terme.class);
    }

}
