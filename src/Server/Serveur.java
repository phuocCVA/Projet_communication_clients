
package Server;

import Client._Client;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**

 */
public class Serveur extends UnicastRemoteObject implements _Serveur
{
	private static final long serialVersionUID = 1L;
	static Registry reg = null;    
	
	/**
	 * Liste d'utilisateur
	 */
    public List<_Client> clients;   
    
    /**
     *  Liste de surnom d'utilisateur
     */
    public List<String> clientsName; 
    
    /**
     * Stockage des fichiers stocker sur le serveur ou String est l'auteur des fichiers
     */
    public Hashtable<String, LinkedList <Fichier>> StockageFichier ;  
    
    /**
     * Stockage des fichiers partage sur le serveur ou String est l'auteur des fichiers
     */
    public Hashtable<String, LinkedList <Fichier>> PartagerFichier;
    
    /**
     * Stockage des mails sur le serveur ou String est le destinataire
     */
    private Hashtable<String, LinkedList <Mail>> StockageMail;
    
    
    /**
     * Constructor du serveur
     * @throws RemoteException
     */
    public Serveur() throws RemoteException {
    	clients = new ArrayList<_Client>();
    	clientsName = new ArrayList<String>(); 
    	StockageFichier = new Hashtable<String, LinkedList <Fichier>>();  
    	PartagerFichier = new Hashtable<String, LinkedList <Fichier>>();
    	StockageMail = new Hashtable<String, LinkedList <Mail>>();
    }
    
    /**
     * liste des utilisateurs
     */
    public List<_Client> getClients() throws RemoteException{
    	return clients;
    }
    
    /**
     * liste des surnoms des utilisateurs     
     */
    public List<String> getClientsName() throws RemoteException {		
		return this.clientsName;
	}
    
    /**
     * Lancer serveur
     */    
    public static void StartServer()	{
    	try {
    		reg = LocateRegistry.createRegistry(1099);                        
    		reg.bind("Server", new Serveur());    	
    		System.out.println("Server Ready");
    		} 
    		catch (AlreadyBoundException ex) { 
    			ex.printStackTrace(); 
    		} 
    		catch (RemoteException ex) { 
    			ex.printStackTrace(); 
    		}                
    	}
    
    /**
     *  Arrête le serveur
     */
    public static void StopServer()
    {
        try {
            reg.unbind("Server");
            UnicastRemoteObject.unexportObject(reg, true);
            System.out.println("Server Stopped");
        }
        
        catch (RemoteException ex)  {
        	ex.printStackTrace();
        }
        catch (NotBoundException ex) {
            ex.printStackTrace();
        }        
    }
    
    /**
     *  Quand un client connecter à serveur
     */
    public boolean RegisterToServer(_Client client, String Name) throws RemoteException
    {
        boolean registe = false;
        try {    
            if(clientsName.indexOf(Name)>-1) // Le surnom du client a été registé  
            {
            }
            
            else {
                 clients.add(client);      // 
                 clientsName.add(Name); 
                 StockageFichier.put(Name, new LinkedList <Fichier>());
                 PartagerFichier.put(Name, new LinkedList <Fichier>());
                 StockageMail.put(Name, new LinkedList <Mail>());
                 registe = true;   
             }
                     
         }
         catch(Exception e) {
        	
         }        
         
         return registe;
    }

    /**
     * Client disconnect au serveur
     */
    public void LogoutToServer(_Client client, String Name) throws RemoteException {
        clients.remove(client);
        clientsName.remove(Name);
    }
    
    /**
     * 
     */
	public Hashtable<String, LinkedList <Fichier>> getStockageFichier() {	
		return StockageFichier;
	}
	
	/**
	 * 
	 */
	public void setStockageFichier(Hashtable<String, LinkedList <Fichier>> stockageFichier) {		
		StockageFichier = stockageFichier;
	}
	
	/**
	 * 
	 */
	public Hashtable<String, LinkedList <Fichier>> getPartagerFichier() {		
		return PartagerFichier;
	}
	
	/**
	 * 
	 */
	public void setPartagerFichier(Hashtable<String, LinkedList <Fichier>> partagerFichier) {		
		PartagerFichier = partagerFichier;
	}
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<String, LinkedList <Mail>> getStockageMail() {
		return StockageMail;
	}

	/**
	 * 
	 * @param stockageMail
	 */
	public void setStockageMail(Hashtable<String, LinkedList <Mail>> stockageMail) {
		StockageMail = stockageMail;
	}
	
    
}
