
package com.core.securites;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;

@TransactionAttribute
@Stateless(mappedName = "AnneGlobalManager")
public class AnneGlobalManagerImpl
    extends AbstractGenericManager<AnneGlobal, Long>
    implements AnneGlobalManagerLocal, AnneGlobalManagerRemote
{

    @EJB(name = "AnneGlobalDAO")
    protected AnneGlobalDAOLocal dao;

    public AnneGlobalManagerImpl() {
    }

    @Override
    public GenericDAO<AnneGlobal, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
