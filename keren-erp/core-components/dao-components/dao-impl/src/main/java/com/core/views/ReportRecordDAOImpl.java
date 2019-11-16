
package com.core.views;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "ReportRecordDAO")
public class ReportRecordDAOImpl
    extends AbstractGenericDAO<ReportRecord, Long>
    implements ReportRecordDAOLocal, ReportRecordDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public ReportRecordDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<ReportRecord> getManagedEntityClass() {
        return (ReportRecord.class);
    }

}
