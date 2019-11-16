
package com.core.views;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "FormRecordDAO")
public class FormRecordDAOImpl
    extends AbstractGenericDAO<FormRecord, Long>
    implements FormRecordDAOLocal, FormRecordDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public FormRecordDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<FormRecord> getManagedEntityClass() {
        return (FormRecord.class);
    }

}
