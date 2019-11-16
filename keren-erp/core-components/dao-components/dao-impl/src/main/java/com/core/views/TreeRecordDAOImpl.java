
package com.core.views;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "TreeRecordDAO")
public class TreeRecordDAOImpl
    extends AbstractGenericDAO<TreeRecord, Long>
    implements TreeRecordDAOLocal, TreeRecordDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public TreeRecordDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<TreeRecord> getManagedEntityClass() {
        return (TreeRecord.class);
    }

}
