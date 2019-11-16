
package com.core.menus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "MenuGroupActionsDAO")
public class MenuGroupActionsDAOImpl
    extends AbstractGenericDAO<MenuGroupActions, Long>
    implements MenuGroupActionsDAOLocal, MenuGroupActionsDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public MenuGroupActionsDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<MenuGroupActions> getManagedEntityClass() {
        return (MenuGroupActions.class);
    }

}
