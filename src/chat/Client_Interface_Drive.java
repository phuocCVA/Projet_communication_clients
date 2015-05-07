package chat;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import Client._Client;
import Server.Fichier;
import Server.ServeurDrive;
import Server._Serveur;
import Server._ServeurDrive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.awt.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Font;

public class Client_Interface_Drive extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private File file;
	private _Client client;
	private _Serveur serveur;
	private LinkedList<Fichier> listeFichier ;
	private LinkedList<Fichier> listePartagerFichier;
	private JComboBox<String> list_user;
	private _ServeurDrive drive;
	private List mon_driver;
	private List partager_driver;

	/**
	 * Launch the application.
	 */
	public Client_Interface_Drive(_Client client, _Serveur serveur) throws RemoteException {
		this.serveur = serveur;
		this.client = client;
		this.drive = new ServeurDrive(serveur);
		this.listeFichier = new LinkedList<Fichier>();
		this.listePartagerFichier = new LinkedList<Fichier>();
		initComponents();		
	   	Client_Interface_Drive.this.setTitle(client.getSurNomClient());
	   	Client_Interface_Drive.this.setLocationRelativeTo(null);
	   	Client_Interface_Drive.this.setVisible(true);	   	
	}

	/**
	 * Create the frame.
	 */
	public void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		mon_driver = new List();
		mon_driver.setBounds(20, 50, 278, 205);
		contentPane.add(mon_driver);
		
		/**
		 * DEPOSER fichier
		 */
		final JButton deposer = new JButton("DEPOSER");
		deposer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionDeposer(e);
			}
		});
		deposer.setBounds(210, 272, 104, 31);
		contentPane.add(deposer);
		
		/**
		 * PARTAGER Fichier
		 */
		final JButton partager = new JButton("PARTAGER");		
		partager.setBounds(322, 272, 104, 31);		
		contentPane.add(partager);
		partager.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				actionPartager(e);
			}		
		});
		
		/**
		 * COMPOSER Fichier
		 */
		final JButton composer = new JButton("COMPOSER");
		composer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionComposer(e);
			}
		});
		
		
		composer.setBounds(428, 272, 104, 31);
		contentPane.add(composer);
		
	    list_user = new JComboBox<String>();
		list_user.setBounds(416, 62, 165, 28);
		try {
			for(_Client cl:this.serveur.getClients()) {
				list_user.addItem(cl.getSurNomClient());				
			}
		} catch (RemoteException e1) {			
			e1.printStackTrace();
		}
		contentPane.add(list_user);
		
		partager_driver = new List();
		partager_driver.setBounds(319, 96, 262, 159);
		contentPane.add(partager_driver);
	
		JLabel lb_toUser = new JLabel("\u00C0 Utilisateur");
		lb_toUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lb_toUser.setBounds(332, 61, 117, 28);
		contentPane.add(lb_toUser);	
		
		JLabel lb_MonDriver = new JLabel("Mon Driver");
		lb_MonDriver.setFont(new Font("Tahoma", Font.BOLD, 12));
		lb_MonDriver.setBounds(138, 11, 96, 21);
		contentPane.add(lb_MonDriver);
		
		JLabel lblPartager = new JLabel("Partager");
		lblPartager.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPartager.setBounds(416, 12, 89, 18);
		contentPane.add(lblPartager);
		
		JButton actualiser = new JButton("ACTUALISER");
		actualiser.setBounds(85, 272, 117, 31);		
		contentPane.add(actualiser);
		actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionActualiser(e);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	
	/**
	 * ACTUALIER le drive
	 */
	private void actionActualiser(ActionEvent e) throws RemoteException {
		
		this.listeFichier = serveur.getStockageFichier().get(client.getSurNomClient());
		mon_driver.removeAll();
		for(int i=0; i<listeFichier.size();i++)
	       {
			mon_driver.add(listeFichier.get(i).getNom());
	       }
		
		
		this.listePartagerFichier = serveur.getPartagerFichier().get(client.getSurNomClient());			
		partager_driver.removeAll();
		for(int i=0;i<this.listePartagerFichier.size();i++){
			String info = this.listePartagerFichier.get(i).getNom() + "_Avec " + this.listePartagerFichier.get(i).getPartager();
			System.out.println(info);
			partager_driver.add(info);
		}						
	}
	
	
	/**
	 * DEPOSER les fichiers
	 */
	private void actionDeposer(ActionEvent e){
		
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();        				
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("");
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			System.out.println("Error!");
		}       						
		
		try {             			
			drive.deposerFichier(file.getName(),file.getPath(),client.getSurNomClient());				
			this.listeFichier = drive.consulterFichier(client.getSurNomClient());
			mon_driver.removeAll();
			for(int i=0; i<listeFichier.size();i++)
		       {
				mon_driver.add(listeFichier.get(i).getNom());
		       }
		} catch (RemoteException e1) {				
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * PARTAGER les fichiers
	 */
	private void actionPartager(ActionEvent e) {
		
		try {
			drive.partagerFichier(list_user.getSelectedItem().toString(), mon_driver.getSelectedItem(), client.getSurNomClient());
			this.listePartagerFichier = drive.consulterPartagerFichier(client.getSurNomClient());			
				partager_driver.removeAll();
				for(int i=0;i<this.listePartagerFichier.size();i++){
					String info = this.listePartagerFichier.get(i).getNom() + "_Avec " + this.listePartagerFichier.get(i).getPartager();
					System.out.println(info);
					partager_driver.add(info);
				}			
		} catch (RemoteException e1) {						
			e1.printStackTrace();
		}
		
	}
	
	
	/**
	 * COMPOSER les fichiers
	 */
	private void actionComposer(ActionEvent e) {
		String path = null;
		
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			 File fileToSave = fc.getSelectedFile();
			 path = fileToSave.getAbsolutePath();        				
			
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("");
		} else if (returnVal == JFileChooser.ERROR_OPTION) {
			System.out.println("Error!");
		}   
		
		try {	
			System.out.println("Save as file: " + path);
			drive.recomposerFichier(path, client.getSurNomClient(),mon_driver.getSelectedItem().toString());
			System.out.println("Save file: "+mon_driver.getSelectedItem().toString() );
		} catch (RemoteException e1) {					
			e1.printStackTrace();
		}
		
	}
}
