package Server;

import java.rmi.RemoteException;
import java.util.LinkedList;


public interface _ServeurMail {
    
    /** Envoi un mail
     * @param clients : liste des clients authentifiés
     * @param auteur : auteur du mail
     * @param objet : objet du mail
     * @param destinataire : destinataire du mail
     * @param message : message du mail
     * @throws RemoteException 
     */
    public void envoyerMail(String auteur, String  objet, String destinataire, String message) throws RemoteException;
    
    /** Verifie que le nom de destinataire est valide
     * @param destinataire : nom du destinataire
     * @return vrai ssi le nom du destinataire est valide
     * @throws RemoteException 
     */
    public boolean destinataireValide(String destinataire) throws RemoteException;
    
    /** Affiche les mails reçu de la personne souhaitée
     * @param clients : liste des clients authentifiés
     * @param destinataire : personne dont on veut connaitre les mails
     * @return : la liste des mails de la personne
     * @throws RemoteException 
     */
    public LinkedList <Mail> ConsulterMail(String destinataire) throws RemoteException;
}
