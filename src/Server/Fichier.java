
package Server;


public class Fichier implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Nom du fichier
    private String nom;
    //Nom d'utilisateur qui ete partage    
    private String partager;
    //Nom d'utilisateur qui est l'auter du fichier
    private String fromUser;
    //Données sous forme de bytes du fichier
    private byte[] data;
    
    /**
     * Constructor d'un fichier
     * @param nom: nom de fichier
     * @param fromUser : auteur du fichier
     * @param partager
     * @param data : contenu du fichier
     */
    public Fichier(String nom, String fromUser, String partager, byte[] data) {
        this.nom = nom;
        this.fromUser = fromUser;
        this.partager = partager;
        this.data = data;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

	public String getPartager() {
		return partager;
	}

	public void setPartager(String partager) {
		this.partager = partager;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
    
    
}
