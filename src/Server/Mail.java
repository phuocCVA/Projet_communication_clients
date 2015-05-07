package Server;


public final class Mail implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	//Auteur du mail
    private String auteur;
    //Texte à envoyer dans le mail
    private String message;
    //Destinataire du mail
    private String destinataire;
    //Objet du mail
    private String objet;
   

    /** Construit un mail avec un auteur, un objet, un destinataire et 
     * un message
     * @param auteur : auteur du mail
     * @param objet : objet du mail
     * @param destinataire : destinataire du mail
     * @param message : message du mail
     * @param pieceJointe : piece jointe du mail
     */
    public Mail(String auteur, String objet, String destinataire, String message) {
        setAuteur(auteur);
        setObjet(objet);
        setDestinataire(destinataire);
        setMessage(message);

    }
    
    /** Retourne l'auteur du mail
     * @return l'auteur du mail
     */
    public String getAuteur() {
        return auteur;
    }

    /** Affecte un auteur au mail à envoyer
     * @param auteur : le nom de l'auteur à affecter
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    
    /** Retourne le texte du mail
     * @return le texte du mail
     */
    public String getMessage() {
        return message;
    }

    /** Affecte un texte au corps du mail
     * @param message : le texte du mail
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Retourne le destinataire du mail
     * @return le destinataire du mail
     */
    public String getDestinataire() {
        return destinataire;
    }

    /** Affecte un destinataire au mail
     * @param destinataire : le destinataire du mail
     */
    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    /** Retourne l'objet du mail
     * @return l'objet du mail
     */
    public String getObjet() {
        return objet;
    }

    /** Affecte un objet au mail
     * @param objet : l'objet du mail
     */
    public void setObjet(String objet) {
        this.objet = objet;
    }
    
}
