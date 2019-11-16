
package com.core.menus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "MenuModuleDAO")
public class MenuModuleDAOImpl
    extends AbstractGenericDAO<MenuModule, Long>
    implements MenuModuleDAOLocal, MenuModuleDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public MenuModuleDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<MenuModule> getManagedEntityClass() {
        return (MenuModule.class);
    }

}
