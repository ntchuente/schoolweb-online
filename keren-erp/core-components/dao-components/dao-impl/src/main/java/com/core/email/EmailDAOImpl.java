
package com.core.email;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "EmailDAO")
public class EmailDAOImpl
    extends AbstractGenericDAO<Email, Long>
    implements EmailDAOLocal, EmailDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public EmailDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Email> getManagedEntityClass() {
        return (Email.class);
    }

}
