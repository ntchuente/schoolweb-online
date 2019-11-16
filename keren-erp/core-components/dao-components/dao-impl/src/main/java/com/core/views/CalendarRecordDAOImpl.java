
package com.core.views;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "CalendarRecordDAO")
public class CalendarRecordDAOImpl
    extends AbstractGenericDAO<CalendarRecord, Long>
    implements CalendarRecordDAOLocal, CalendarRecordDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public CalendarRecordDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<CalendarRecord> getManagedEntityClass() {
        return (CalendarRecord.class);
    }

}
