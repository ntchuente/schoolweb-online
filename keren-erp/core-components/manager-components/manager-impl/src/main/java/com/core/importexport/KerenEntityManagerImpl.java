
package com.core.importexport;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.importexport.KerenEntity;

@TransactionAttribute
@Stateless(mappedName = "KerenEntityManager")
public class KerenEntityManagerImpl
    extends AbstractGenericManager<KerenEntity, Long>
    implements KerenEntityManagerLocal, KerenEntityManagerRemote
{

    @EJB(name = "KerenEntityDAO")
    protected KerenEntityDAOLocal dao;

    public KerenEntityManagerImpl() {
    }

    @Override
    public GenericDAO<KerenEntity, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
