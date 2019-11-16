
package com.core.langues;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;

@TransactionAttribute
@Stateless(mappedName = "LangueManager")
public class LangueManagerImpl
    extends AbstractGenericManager<Langue, Long>
    implements LangueManagerLocal, LangueManagerRemote
{

    @EJB(name = "LangueDAO")
    protected LangueDAOLocal dao;

    public LangueManagerImpl() {
    }

    @Override
    public GenericDAO<Langue, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
