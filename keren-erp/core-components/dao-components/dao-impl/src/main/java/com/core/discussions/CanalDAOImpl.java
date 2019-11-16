
package com.core.discussions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bekosoftware.genericdaolayer.dao.impl.AbstractGenericDAO;
import com.core.securites.Utilisateur;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless(mappedName = "CanalDAO")
public class CanalDAOImpl
    extends AbstractGenericDAO<Canal, Long>
    implements CanalDAOLocal, CanalDAORemote
{

    @PersistenceContext(unitName = "keren")
    protected EntityManager em;

    public CanalDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Canal> getManagedEntityClass() {
        return (Canal.class);
    }

    @Override
    public List<Canal> getCanaux(String username) {
        //To change body of generated methods, choose Tools | Templates.
        String query="SELECT c FROM Canal c ,IN(c.membres)m WHERE  m.courriel='"+username+"' AND c.active="+Boolean.TRUE;
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Canal> getCanaux(long userid) {
        //To change body of generated methods, choose Tools | Templates.
        String query="SELECT c FROM Canal c , IN(c.membres)m WHERE  m.id ="+userid+" AND c.active="+Boolean.TRUE;
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Utilisateur> getConnectUsers(String username) {
        //To change body of generated methods, choose Tools | Templates.
        String query="SELECT c FROM Canal c ,IN(c.membres)m WHERE  m.courriel='"+username+"' AND c.active="+Boolean.TRUE;
        List<Canal> canaux = em.createQuery(query).getResultList();
        List<Utilisateur> users = new ArrayList<Utilisateur>();
        for(Canal can:canaux){
            for(Utilisateur mem:can.getMembres()){
                if(!users.contains(mem)&&!mem.getCourriel().equalsIgnoreCase(username)&&mem.getLastconfirme()!=null){
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
//                    c.add(Calendar.MILLISECOND, 180000);
                    long duration = c.getTimeInMillis()-mem.getLastconfirme().getTime();
                    if(duration<25000){
                        users.add(mem);
                    }//end if(duration<180000)
                }//end if(!users.contains(mem)){
            }//end for(Utilisateur mem:can.getMembres())
        }//end for(Canal can:canaux){
        return users;
    }

    @Override
    public List<Utilisateur> getConnectUsers(long userid) {
        //To change body of generated methods, choose Tools | Templates.
        String query="SELECT m FROM Canal c ,IN(c.membres)m WHERE  m.id ="+userid+" AND c.active="+Boolean.TRUE;       
        List<Canal> canaux = em.createQuery(query).getResultList();
        List<Utilisateur> users = new ArrayList<Utilisateur>();
        for(Canal can:canaux){
            for(Utilisateur mem:can.getMembres()){
                if(!users.contains(mem)&&mem.getId()!=userid&&mem.getLastconfirme()!=null){
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
//                    c.add(Calendar.MILLISECOND, 180000);
                    long duration = c.getTimeInMillis()-mem.getLastconfirme().getTime();
                    if(duration<180000){
                        users.add(mem);
                    }//end if(duration<180000)
                }//end if(!users.contains(mem)){
            }//end for(Utilisateur mem:can.getMembres())
        }//end for(Canal can:canaux){
        return users;
    }

  
}
