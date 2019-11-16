
package com.core.referentiels;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;

@TransactionAttribute
@Stateless(mappedName = "PaysManager")
public class PaysManagerImpl
    extends AbstractGenericManager<Pays, Long>
    implements PaysManagerLocal, PaysManagerRemote
{

    @EJB(name = "PaysDAO")
    protected PaysDAOLocal dao;

    public PaysManagerImpl() {
    }

    @Override
    public GenericDAO<Pays, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
