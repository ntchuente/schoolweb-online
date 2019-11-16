
package com.core.langues;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "LangueDAO")
public class LangueDAOImpl
    extends AbstractGenericDAO<Langue, Long>
    implements LangueDAOLocal, LangueDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public LangueDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Langue> getManagedEntityClass() {
        return (Langue.class);
    }

}
