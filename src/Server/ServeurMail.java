
package Server;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServeurMail extends Serveur implements _ServeurMail {
    
    private static final long serialVersionUID = 1L;
    private _Serveur serveur;
    
    //Stockage des mails sur le serveur où String est le destinataire
    private Hashtable<String, LinkedList <Mail>> StockageMail;
    //Fichier de log    

    public ServeurMail(_Serveur serveur) throws RemoteException{
    	this.serveur = serveur;
        StockageMail = serveur.getStockageMail();
    }
    
    /**
     * 
     */
    public void envoyerMail(String auteur, String  objet, String destinataire, String message) throws RemoteException{
        try {
        	StockageMail = serveur.getStockageMail();        
            if(destinataireValide(destinataire)){            
                //Creation du mail
                Mail mail = new Mail(auteur, objet, destinataire, message);
                
                //Recuperation de la liste des mails du destinataire
                LinkedList <Mail> destiMail = StockageMail.get(destinataire); 
                //Recuperation de la liste des mails d'auteur
                LinkedList <Mail> auteurMail = StockageMail.get(auteur);
                
                //Ajout en queue du nouveau mail
                destiMail.add(mail);
                auteurMail.add(mail);
                
                //Actualisation de la table des mails de l'utilisateur
                StockageMail.put(destinataire, destiMail);
                StockageMail.put(auteur, auteurMail);
                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ServeurMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        serveur.setStockageMail(StockageMail);      
        
    }
    
    /**
     * 
     */
    public boolean destinataireValide(String destinataire) throws RemoteException {
        boolean valide = false;       
       
        for (int i = 0; i < serveur.getClients().size(); i++){    	 	
            if(serveur.getClients().get(i).getSurNomClient().equals(destinataire)){            	
                valide = true;
                break;
            }
        }
        
        if (valide == false) 
        	try {
                 throw new Exception("Destinataire invalide");
                } 
                catch (Exception ex) {
                    Logger.getLogger(ServeurMail.class.getName()).log(Level.SEVERE, null, ex);
                } 
        return valide;
    }
    
    /**
     * 
     */
    public LinkedList<Mail> ConsulterMail(String destinataire) throws RemoteException {
        LinkedList<Mail> MailDestinataire = null;
        if(destinataireValide(destinataire)){
            MailDestinataire = StockageMail.get(destinataire);
        }
        return MailDestinataire;
    }

   
}
