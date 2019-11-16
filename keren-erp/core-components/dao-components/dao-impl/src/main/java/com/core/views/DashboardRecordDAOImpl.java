
package com.core.views;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "DashboardRecordDAO")
public class DashboardRecordDAOImpl
    extends AbstractGenericDAO<DashboardRecord, Long>
    implements DashboardRecordDAOLocal, DashboardRecordDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public DashboardRecordDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<DashboardRecord> getManagedEntityClass() {
        return (DashboardRecord.class);
    }

}
