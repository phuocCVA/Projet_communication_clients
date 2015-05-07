
package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServeurDrive extends Serveur implements _ServeurDrive {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Stockage des fichiers sur le serveur ou String est l'auteur des fichiers
     */
    private Hashtable<String, LinkedList <Fichier>> StockageFichier ;  
      
    /**
     * Stockage des fichiers partage sur le serveur ou String est l'auteur des fichiers
     */
    private Hashtable<String, LinkedList <Fichier>> PartagerFichier ;
    
    private _Serveur serveur;
    
    /**
     * Constructeur du serveur
     * @param serveur
     * @throws RemoteException
     */
    public ServeurDrive(_Serveur serveur) throws RemoteException {
    	this.serveur = serveur;
    	this.StockageFichier = this.serveur.getStockageFichier();      
    	this.PartagerFichier = this.serveur.getPartagerFichier();    
    }

    public void deposerFichier(String nomFichierAcreer, String path, String auteur) throws RemoteException {
    	
        BufferedInputStream data = null;
        byte[] buf;
        File nomFichier = new File(path);
        
        try {                    
            data = new BufferedInputStream(new FileInputStream(nomFichier));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServeurDrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        buf = new byte[(int)nomFichier.length()];	
            
        int ch;
        int i = 0;            
        try {
        	FileInputStream f = new FileInputStream(nomFichier); 
            try {
                //Creation du tableau de bytes
                while((ch = data.read()) != -1) { 
                    buf[i] = (byte)ch;
                    i++;						
                }
                Fichier fic = new Fichier(nomFichierAcreer,auteur,null,buf);
                
                if (this.StockageFichier.get(auteur)!=null) {                	
                	LinkedList <Fichier> stockageAuteur = StockageFichier.get(auteur);
                	stockageAuteur.add(fic);
                	StockageFichier.put(auteur, stockageAuteur);                	
                }
                
                else {                	
                	LinkedList <Fichier> stockageAuteur = new LinkedList<Fichier>();
                	stockageAuteur.add(fic);
                	StockageFichier.put(auteur, stockageAuteur);                     	
                } 
                this.serveur.setStockageFichier(this.StockageFichier);
            }
            finally {
                    f.close();
            }
        }

        catch(FileNotFoundException f){
            System.out.println("Le fichier est introuvable.");
        }
        catch(IOException e){
            System.out.println(e + "Erreur lors de la lecture du fichier.");		
        }
        
        try {
            data.close();
        } catch (IOException ex) {
            Logger.getLogger(ServeurDrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("size StockageFichier.get(" + auteur + ") :" + StockageFichier.get(auteur).size() );
    }   
    
    public LinkedList <Fichier> consulterFichier(String auteur) throws RemoteException {
        return StockageFichier.get(auteur);
    }  
    
    public LinkedList <Fichier> consulterPartagerFichier(String auteur) throws RemoteException {
        return PartagerFichier.get(auteur);
    }  
    

    public void partagerFichier(String destinataire, String nomFichier, String auteur) throws RemoteException {

            Fichier fic = new Fichier(nomFichier, auteur, destinataire, null);            
            int index = 0;
            //Recupere l'index du fichier correspondant au nom que je recherche
            for (int i=0;i<StockageFichier.get(auteur).size();i++){
            	if (StockageFichier.get(auteur).get(i).getNom().equals(nomFichier)) {
            		index = i;
            		break;
            	}            	
            }          
            
            //Recupere le tableau de bytes correspondant au fichier que je recherche
            byte[] buf = StockageFichier.get(auteur).get(index).getData();
            
            fic.setData(buf);
            
            LinkedList <Fichier> stockageDestinataire = StockageFichier.get(destinataire); 
            if (stockageDestinataire!=null) {
            	stockageDestinataire.add(fic);
            	}
            else {
            	stockageDestinataire = new LinkedList<Fichier>();
            	stockageDestinataire.add(fic);            	     
            }      
            StockageFichier.put(destinataire, stockageDestinataire);
            this.serveur.setStockageFichier(this.StockageFichier);
              
            
            LinkedList <Fichier> stockagePartager = PartagerFichier.get(auteur);
            if (stockagePartager!=null) {
            	stockagePartager.add(fic);
            	}
            else {
            	stockagePartager = new LinkedList<Fichier>();
            	stockagePartager.add(fic);            	     
            }  
            PartagerFichier.put(auteur,stockagePartager);
            this.serveur.setPartagerFichier(this.PartagerFichier);
    }
    
    
    public void recomposerFichier(String path, String auteur, String nomFichier) throws RemoteException {
        try {
            BufferedOutputStream bos; 
            int index = 0;
            File file = new File(path);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            new Fichier(nomFichier,auteur,null,null);
            
            //Recupere l'index du fichier correspondant au nom que je recherche
            for (int i=0;i<StockageFichier.get(auteur).size();i++){
            	if (StockageFichier.get(auteur).get(i).getNom().equals(nomFichier)) {
            		index = i;
            		break;
            	}            	
            }
            
            //Recupere le tableau de bytes correspondant au fichier que je recherche
            byte[] buf = StockageFichier.get(auteur).get(index).getData();
            for (int i = 0 ; i < buf.length ; i++) {
                bos.write((int)buf[i]);
            }	 
            System.out.println("Fichier cree.");
            System.out.println(file.getPath());
            bos.close();
        }
                
    catch(IOException e) {
             System.out.println(e + "Erreur lors de la lecture du fichier.");
        }
    }

}
