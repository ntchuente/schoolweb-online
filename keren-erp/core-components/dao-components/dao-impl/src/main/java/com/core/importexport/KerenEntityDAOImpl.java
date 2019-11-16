
package com.core.importexport;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;
import com.core.importexport.KerenEntity;

@Stateless(mappedName = "KerenEntityDAO")
public class KerenEntityDAOImpl
    extends AbstractGenericDAO<KerenEntity, Long>
    implements KerenEntityDAOLocal, KerenEntityDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public KerenEntityDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<KerenEntity> getManagedEntityClass() {
        return (KerenEntity.class);
    }

}
