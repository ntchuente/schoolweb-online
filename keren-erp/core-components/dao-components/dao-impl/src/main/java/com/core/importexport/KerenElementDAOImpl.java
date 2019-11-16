
package com.core.importexport;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;
import com.core.importexport.KerenElement;

@Stateless(mappedName = "KerenElementDAO")
public class KerenElementDAOImpl
    extends AbstractGenericDAO<KerenElement, Long>
    implements KerenElementDAOLocal, KerenElementDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public KerenElementDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<KerenElement> getManagedEntityClass() {
        return (KerenElement.class);
    }

}
