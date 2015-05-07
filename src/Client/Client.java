
package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Server.Fichier;

public class Client  extends UnicastRemoteObject  implements _Client{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
		message envoye par le client connecte 
	 */
	private String message = "";
	private String surnom;
	private Boolean prive = false;
	private Fichier fichier;

	public Client(String surnom) throws RemoteException {
		this.surnom = surnom;		
	} 
	
	public String getSurNomClient() throws RemoteException{
    	return surnom;
    }
	
    public void MsgArrived(String msg, String FromUser, Boolean prive) throws RemoteException {    	
    	this.message = message + FromUser + ": " + msg + "\n" ;
    	this.prive = prive;
    }
    
    public void FichierArrived(String nomFichier, byte[] data, String FromUser) throws RemoteException {    	
          this.fichier = new Fichier(nomFichier,FromUser,surnom,data);                  
    }
    
    public boolean AvoirFichier() throws RemoteException {
    	if (this.fichier!=null)  {
    		return true; 
    	}
    	else return false;
    }
    	
    public String getMessage(){
    	return message;
    }
    
    public Boolean getPrive() throws RemoteException{
    	return prive;
    }   
    
	public Fichier getFichier() {
		return fichier;
	}
	
	public void setFichier() {
		this.fichier = null;
	}
   
}
