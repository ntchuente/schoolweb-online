
package com.core.email;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@TransactionAttribute
@Stateless(mappedName = "EmailManager")
public class EmailManagerImpl
    extends AbstractGenericManager<Email, Long>
    implements EmailManagerLocal, EmailManagerRemote,TimedObject
{

    
    @EJB(name = "EmailDAO")
    protected EmailDAOLocal dao;

   @Resource(name = "java:/Mail")
    private Session mailSeesion ;
    
   @Resource
    SessionContext context ;
    
    public EmailManagerImpl() {
    }

    @Override
    public GenericDAO<Email, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public void sendmail(Email mail) throws AddressException, MessagingException {
            //To change body of generated methods, choose Tools | Templates.
//            System.out.println(EmailManagerImpl.class.toString()+" ========================== "+mail+" ========== Session : "+mailSeesion);
            MimeMessage m = new MimeMessage(mailSeesion);
            InternetAddress[] to = new InternetAddress[]{
                new InternetAddress(mail.getCible())
            }; 
            
            m.setFrom(new InternetAddress(mail.getSource()));
            m.setRecipients(Message.RecipientType.TO, to);
            m.setSubject(mail.getSubject());
            m.setSentDate(new Date());
            m.setText(mail.getText(), "utf-8", "html");
            //Traitement des pieces jointes
            if(mail.getPiecesjointes()!=null && !mail.getPiecesjointes().isEmpty()){
                Multipart multipart = new MimeMultipart();
                for(String fichier:mail.getPiecesjointes()){
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    File file = new File(fichier);
                    if(file.exists()){
                        DataSource source = new FileDataSource(fichier);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(file.getName());
                        multipart.addBodyPart(messageBodyPart);
                    }//end if(file.exists()){
                }//end for(String fichier:mail.getPiecesjointes
                m.setContent(multipart);
            }//end if(mail.getPiecesjointes()!=null){
            Transport.send(m);
    }

    @Override
    public void sendmailFromDB() {
        //To change body of generated methods, choose Tools | Templates.
        
        List<Email> datas = dao.filter(RestrictionsContainer.newInstance().getPredicats(), null, null, 0, -1);
        for(Email mail:datas){
            try {
                sendmail(mail);
                dao.delete(mail.getId());
            } catch (MessagingException ex) {
                
            }
        }//end for(Email mail:datas){
    }

    @Override
    public void ejbTimeout(Timer timer) {
        //To change body of generated methods, choose Tools | Templates.      
//        System.out.println(EmailManagerImpl.class.toString()+" =================================== Demarrage du Timer =====================");
        sendmailFromDB();
    }

    @Override
    public void scheduleEventManager(Date initialExpiration, long duration) {
         context.getTimerService().createTimer(initialExpiration, duration, "Event schulder ...");
    }
    
    

}
