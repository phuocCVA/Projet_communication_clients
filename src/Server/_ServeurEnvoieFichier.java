package Server;

import java.rmi.RemoteException;

public interface _ServeurEnvoieFichier {
	/**
     * Fichier à envoye sur le serveur
     * @param msg
     * @param FrmUser
     * @param ToName
     * @param flag
     * @throws RemoteException
     */
    public void FichierToServer(_Serveur serveur, String FromUser, String ToUser, String path) throws RemoteException;

}
