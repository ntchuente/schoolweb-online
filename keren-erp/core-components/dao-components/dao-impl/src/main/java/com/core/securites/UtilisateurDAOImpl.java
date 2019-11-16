
package com.core.securites;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;

@Stateless(mappedName = "UtilisateurDAO")
public class UtilisateurDAOImpl
    extends AbstractGenericDAO<Utilisateur, Long>
    implements UtilisateurDAOLocal, UtilisateurDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public UtilisateurDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Utilisateur> getManagedEntityClass() {
        return (Utilisateur.class);
    }

}
