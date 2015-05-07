package Server;

import java.rmi.RemoteException;

public class ServeurChat extends Serveur implements _ServeurChat {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServeurChat() throws RemoteException {
		
	}

	// Client envoye un message
	 
    public void MsgToServer(_Serveur serveur, String msg, String FromUser, String ToUser, Boolean prive) throws RemoteException  {
        	
        if (prive==false) {
        	
	    	if(ToUser.equalsIgnoreCase("Tous utilisateurs")) { // Envoye un message a tous utilisateurs 
	    		
	            for(int i=0; i < serveur.getClients().size(); i++)
	             	{	            	
	                 try
	                 	{
	                     if(serveur.getClients().get(i).getSurNomClient().equals(FromUser)) 
	                     	{
	                    	 	serveur.getClients().get(i).MsgArrived(msg, "Moi", prive);
	                     	}
	                     
	                     else 
	                     	{	                    	
	                    	 serveur.getClients().get(i).MsgArrived(msg, FromUser, prive);
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
	            int count=0;
	            for(int i=0; i< serveur.getClients().size(); i++)                
	            {
	                if(serveur.getClients().get(i).getSurNomClient().equals(ToUser))
	                	{    
	                    count++;
	                    try
	                     {
	                         serveur.getClients().get(i).MsgArrived(msg, FromUser, prive);
	                     }
	                     catch(Exception e)
	                     {
	                         System.out.println(e);
	                         serveur.getClients().remove(i);	               
	                         MsgToServer(serveur, "user " + ToUser +" seems unavailable.", "Server", FromUser, prive);
	                         count++;
	                     }                                        
	                }
	                
	                else if(serveur.getClients().get(i).getSurNomClient().equals(FromUser))
	                	{
	                    count++;
	                    try
	                     {
	                         serveur.getClients().get(i).MsgArrived(msg, "Moi", prive);
	                     }
	                     catch(Exception e)
	                     {
	                         System.out.println(e);
	                         serveur.getClients().remove(i);                                          
	                     }
	                }
	                
	                if(count==2) break;
	            }
	        }        	
        }
        
        else {
        	for(int i=0; i< serveur.getClients().size(); i++)                
            	{
                if(serveur.getClients().get(i).getSurNomClient().equals(ToUser))
                	{ 
                	serveur.getClients().get(i).MsgArrived(msg, FromUser, prive); 
                	break;
                	}
                }                
        }
    
    }
		
}


