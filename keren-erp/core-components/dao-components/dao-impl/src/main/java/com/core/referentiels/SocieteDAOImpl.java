
package com.core.referentiels;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "SocieteDAO")
public class SocieteDAOImpl
    extends AbstractGenericDAO<Societe, Long>
    implements SocieteDAOLocal, SocieteDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public SocieteDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Societe> getManagedEntityClass() {
        return (Societe.class);
    }

}
