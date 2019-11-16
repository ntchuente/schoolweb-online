
package com.core.referentiels;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;

@TransactionAttribute
@Stateless(mappedName = "DeviseManager")
public class DeviseManagerImpl
    extends AbstractGenericManager<Devise, Long>
    implements DeviseManagerLocal, DeviseManagerRemote
{

    @EJB(name = "DeviseDAO")
    protected DeviseDAOLocal dao;

    public DeviseManagerImpl() {
    }

    @Override
    public GenericDAO<Devise, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
