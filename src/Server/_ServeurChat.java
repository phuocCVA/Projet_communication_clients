package Server;

import java.rmi.RemoteException;

public interface _ServeurChat {
	 /**
     * message à envoye sur le serveur
     * @param msg
     * @param FrmUser
     * @param ToName
     * @param flag
     * @throws RemoteException
     */
    public void MsgToServer(_Serveur serveur, String msg, String FromUser, String ToUser, Boolean flag) throws RemoteException;
    
}
