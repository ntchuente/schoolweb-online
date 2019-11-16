
package com.core.calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;

@TransactionAttribute
@Stateless(mappedName = "RappelManager")
public class RappelManagerImpl
    extends AbstractGenericManager<Rappel, Long>
    implements RappelManagerLocal, RappelManagerRemote
{

    @EJB(name = "RappelDAO")
    protected RappelDAOLocal dao;

    public RappelManagerImpl() {
    }

    @Override
    public GenericDAO<Rappel, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
