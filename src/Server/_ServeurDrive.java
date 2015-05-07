
package Server;

import java.rmi.RemoteException;
import java.util.LinkedList;


public interface _ServeurDrive {
    
    /** Depose un fichier sur le serveur
     * @param nomFichierAcreer : nom du fichier à déposer
     * @param path : chemin local du fichier
     * @param auteur : auteur du fichier
     * @throws RemoteException 
     */
    public void deposerFichier(String nomFichierAcreer, String path, String auteur) throws RemoteException;
    
    /** Consulte les fichiers disponibles à un utilisateur
     * @param auteur : possesseur des fichiers
     * @return : liste des fichiers consultables
     * @throws RemoteException 
     */
    public LinkedList <Fichier> consulterFichier(String auteur) throws RemoteException;
    
    /** Consulte les fichiers partager a un utilisateur
     * @param auteur : possesseur des fichiers
     * @return : liste des fichiers consultables
     * @throws RemoteException 
     */
    public LinkedList <Fichier> consulterPartagerFichier(String auteur) throws RemoteException;
    
    /** Recompose le tableau de byte déposé sur le serveur en fichier local
     * @param path : endroit où créer le fichier
     * @param auteur : possesseur du fichier
     * @param nomFichier : nom du fichier déposé sur le serveur
     * @throws RemoteException 
     */
    public void recomposerFichier(String path, String auteur, String nomFichier) throws RemoteException;
    
    /** Partage un fichier avec un destinataire choisi existant parmi les utilisateurs
     * @param destinataire : utilisateur avec qui on désire partager le fichier
     * @param nomFichier : nom du fichier à partager
     * @param auteur : auteur du fichier à partater
     * @throws RemoteException 
     */
    public void partagerFichier(String destinataire, String nomFichier, String auteur) throws RemoteException;
    
}
