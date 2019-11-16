
package com.core.email;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.kerem.core.KerenExecption;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import javax.mail.MessagingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Thu May 03 15:12:05 GMT+01:00 2018
 * 
 */
@Path("/email")
public class EmailRSImpl
    extends AbstractGenericService<Email, Long>
    implements EmailRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "EmailManagerImpl", interf = EmailManagerLocal.class)
    protected EmailManagerLocal manager;

    public EmailRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Email, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public Email confirmer(HttpHeaders headers, Email mail) {
        try {
            //To change body of generated methods, choose Tools | Templates.
            if(mail.getCible()==null||mail.getCible().trim().isEmpty()){
                throw new KerenExecption("Adresse du destinataire introuvable!");
            }else if(mail.getSubject()==null||mail.getSubject().trim().isEmpty()){
                 throw new KerenExecption("L'objet du mail introuvable!");
            }//end if(mail.getCible()==null||mail.getCible().trim().isEmpty()){
            manager.sendmail(mail);
            return mail;
        } catch (MessagingException ex) {
            throw new WebApplicationException(ex);
        }
    }

}
