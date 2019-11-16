
package com.core.menus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "ActionItemDAO")
public class ActionItemDAOImpl
    extends AbstractGenericDAO<ActionItem, Long>
    implements ActionItemDAOLocal, ActionItemDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public ActionItemDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<ActionItem> getManagedEntityClass() {
        return (ActionItem.class);
    }

}
