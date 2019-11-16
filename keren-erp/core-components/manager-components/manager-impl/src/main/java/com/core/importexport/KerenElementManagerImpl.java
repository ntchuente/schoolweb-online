
package com.core.importexport;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.importexport.KerenElement;

@TransactionAttribute
@Stateless(mappedName = "KerenElementManager")
public class KerenElementManagerImpl
    extends AbstractGenericManager<KerenElement, Long>
    implements KerenElementManagerLocal, KerenElementManagerRemote
{

    @EJB(name = "KerenElementDAO")
    protected KerenElementDAOLocal dao;

    public KerenElementManagerImpl() {
    }

    @Override
    public GenericDAO<KerenElement, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
