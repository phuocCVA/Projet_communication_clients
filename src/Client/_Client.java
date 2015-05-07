
package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Server.Fichier;

/**

 */
public interface _Client extends Remote{  

    /**
     * Donner le surnom du client
     * @return
     * @throws RemoteException
     */
    public String getSurNomClient() throws RemoteException;
	
    /** 
     * Afficher le message que l'un d'utilisateur connecter a envoyée au client courant  	
     * 
     */
    public void  MsgArrived(String msg, String FromUser, Boolean prive) throws RemoteException;    
    
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public String getMessage() throws RemoteException;    
    
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public Boolean getPrive() throws RemoteException;
    
    /**
     * 
     */
    public void FichierArrived(String nomFichier, byte[] data, String FromUser)throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public boolean AvoirFichier() throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public Fichier getFichier() throws RemoteException;
    
    /**
     * 
     * @throws RemoteException
     */
    public void setFichier() throws RemoteException;
    
}
