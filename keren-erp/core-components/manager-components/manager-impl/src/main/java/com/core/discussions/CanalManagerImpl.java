
package com.core.discussions;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.securites.Utilisateur;
import com.core.securites.UtilisateurDAOLocal;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "CanalManager")
public class CanalManagerImpl
    extends AbstractGenericManager<Canal, Long>
    implements CanalManagerLocal, CanalManagerRemote
{

    @EJB(name = "CanalDAO")
    protected CanalDAOLocal dao;
    
    @EJB(name = "UtilisateurDAO")
    protected UtilisateurDAOLocal userdao;

    public CanalManagerImpl() {
    }

    @Override
    public GenericDAO<Canal, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<Canal> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
         //To change body of generated methods, choose Tools | Templates.
        List<Canal> datas = super.filter(predicats, orders, properties, firstResult, maxResult); 
        List<Canal> result = new ArrayList<Canal>();
        for(Canal c:datas){
            result.add(new Canal(c));
        }
        return result;
    }

    @Override
    public List<Canal> findAll() {
        List<Canal> datas = super.findAll();
        List<Canal> result = new ArrayList<Canal>();
        for(Canal c:datas){
            result.add(new Canal(c));
        }
        return result;
    }

    @Override
    public Canal find(String propertyName, Long entityID) {
        Canal data = super.find(propertyName, entityID); //To change body of generated methods, choose Tools | Templates.
        Canal result = new Canal(data);
        if(data.getMembres()!=null){
            for(Utilisateur user:data.getMembres()){
                result.getMembres().add(new Utilisateur(user));
            }
        }
        return result;
    }

    @Override
    public Canal delete(Long id) {
        Canal data =  super.delete(id); //To change body of generated methods, choose Tools | Templates.
        return new Canal(data);
    }

    @Override
    public List<Canal> getCanaux(String username) {
        //To change body of generated methods, choose Tools | Templates.
        List<Canal> datas =  dao.getCanaux(username);
         List<Canal> result = new ArrayList<Canal>();
        for(Canal c:datas){
            result.add(new Canal(c));
        }
        return result;
    }

    @Override
    public List<Canal> getCanaux(long userid) {
       //To change body of generated methods, choose Tools | Templates.
         List<Canal> datas =   dao.getCanaux(userid);
         List<Canal> result = new ArrayList<Canal>();
         for(Canal c:datas){
            result.add(new Canal(c));
         }
         return result;
    }

    @Override
    public List<Utilisateur> getConnectUsers(String username) {
        //To change body of generated methods, choose Tools | Templates.
        List<Utilisateur> users = dao.getConnectUsers(username);
        List<Utilisateur> output = new ArrayList<Utilisateur>();
        for(Utilisateur user:users){
            output.add(new Utilisateur(user));
        }//end for(Utilisateur user:users)
        return output;
    }

    @Override
    public List<Utilisateur> getConnectUsers(long userid) {
        //To change body of generated methods, choose Tools | Templates.
        List<Utilisateur> users = dao.getConnectUsers(userid);
        List<Utilisateur> output = new ArrayList<Utilisateur>();
        for(Utilisateur user:users){
            output.add(new Utilisateur(user));
        }//end for(Utilisateur user:users)
        return output;
    }
    
    

}
