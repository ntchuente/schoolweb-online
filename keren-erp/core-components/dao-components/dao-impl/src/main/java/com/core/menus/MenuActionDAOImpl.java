
package com.core.menus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "MenuActionDAO")
public class MenuActionDAOImpl
    extends AbstractGenericDAO<MenuAction, Long>
    implements MenuActionDAOLocal, MenuActionDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public MenuActionDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<MenuAction> getManagedEntityClass() {
        return (MenuAction.class);
    }

}
