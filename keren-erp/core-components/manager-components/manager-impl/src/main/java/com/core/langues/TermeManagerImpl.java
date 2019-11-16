
package com.core.langues;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;

@TransactionAttribute
@Stateless(mappedName = "TermeManager")
public class TermeManagerImpl
    extends AbstractGenericManager<Terme, Long>
    implements TermeManagerLocal, TermeManagerRemote
{

    @EJB(name = "TermeDAO")
    protected TermeDAOLocal dao;

    public TermeManagerImpl() {
    }

    @Override
    public GenericDAO<Terme, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

}
