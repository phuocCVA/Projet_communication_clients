package chat;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class ApplicationTemoin {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException {
            new Connexion().setVisible(true);
    }
}
