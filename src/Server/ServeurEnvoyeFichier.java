package Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;

public class ServeurEnvoyeFichier extends Serveur implements _ServeurEnvoieFichier{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServeurEnvoyeFichier() throws RemoteException {
		super();		
	}

	public void FichierToServer(_Serveur serveur, String FromUser, String ToUser, String source) throws RemoteException {		
		 try
         {
           File file=new File(source);
           byte buffer[]=new byte[(int)file.length()];
           BufferedInputStream inputFile=new BufferedInputStream( new FileInputStream(source));  
           inputFile.read(buffer,0,buffer.length);
           inputFile.close();
           
           if(ToUser.equalsIgnoreCase("Tous utilisateurs")) { // Envoye un fichier a tous utilisateurs 
	    		
	            for(int i=0; i < serveur.getClients().size(); i++)
	             	{	            	
	                 try
	                 	{
	                     if(serveur.getClients().get(i).getSurNomClient().equals(FromUser)) 
	                     	{
	                    	 	serveur.getClients().get(i).FichierArrived(file.getName(), buffer, "Moi");
	                     	}
	                     
	                     else 
	                     	{	                    	
	                    	 serveur.getClients().get(i).FichierArrived(file.getName(), buffer, FromUser);
	                     	}                     
	                 }
	                 
	                 // Si client disonnecter au serveur
	                 catch(Exception e) { 
	                     System.out.println(e);
	                     serveur.getClients().remove(i);
	                 }
	             }
	        }
	        else
	        {	 
	            for(int i=0; i< serveur.getClients().size(); i++)              
	            {
	                if(serveur.getClients().get(i).getSurNomClient().equals(ToUser)) {    
	                    try {
	                         serveur.getClients().get(i).FichierArrived(file.getName(), buffer, FromUser);
	                     }
	                     catch(Exception e){
	                         System.out.println(e);
	                         serveur.getClients().remove(i);	               
	                     }                                        
	                }
    
	            }
	        }        	
        
         }
         catch(Exception e)
         {
           e.printStackTrace();                      
         }
	}
		
}
		

	


