package Server;

import Client._Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 */
public interface _Serveur extends Remote {   
	/**
	 * Enregistrement du client au serveur
	 * @param client
	 * @param Name: nom du client qui tente de se connecte sur le serveur
	 * @return
	 * @throws RemoteException
	 */
    public boolean RegisterToServer(_Client client, String Name) throws  RemoteException;
    
    /**
     * Déconnexion du client sur le serveur
     * @param client
     * @param Name
     * @throws RemoteException
     */
    public void LogoutToServer(_Client client, String Name) throws RemoteException;
    
    /**
     * Mettre à jour la liste de utilisateur en ligne
     * @param ClientsName
     * @throws RemoteException
     */
    public List<_Client> getClients() throws RemoteException;
    
    /**
     * Recuperation de la liste des fichier
     * @return
     * @throws RemoteException
     */
    public Hashtable<String, LinkedList <Fichier>> getStockageFichier() throws RemoteException;
    
    /**
     * Mettre a jour la liste des fichier 
     * @param stokageFichier
     * @throws RemoteException
     */
    public void setStockageFichier(Hashtable<String, LinkedList <Fichier>> stokageFichier) throws RemoteException;
    
    /**
     * Recuperation de la liste des fichier partager
     * @return
     * @throws RemoteException
     */
    public Hashtable<String, LinkedList <Fichier>> getPartagerFichier() throws RemoteException;
    
    /**
     * Mettre a jour la liste des fichier partager
     * @param partagerFichier
     * @throws RemoteException
     */
    public void setPartagerFichier(Hashtable<String, LinkedList <Fichier>> partagerFichier) throws RemoteException;
	
    /**
     * Recuperation de la liste des mail
     * @return
     * @throws RemoteException
     */
    public Hashtable<String, LinkedList <Mail>> getStockageMail() throws RemoteException;
    
    /**
     * Mettre a jour la liste des mail
     * @param stockageMail
     * @throws RemoteException
     */
    public void setStockageMail(Hashtable<String, LinkedList <Mail>> stockageMail) throws RemoteException;

}
