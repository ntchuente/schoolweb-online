
package com.core.importexport;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "ExportDAO")
public class ExportDAOImpl
    extends AbstractGenericDAO<Export, Long>
    implements ExportDAOLocal, ExportDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public ExportDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Export> getManagedEntityClass() {
        return (Export.class);
    }

}
