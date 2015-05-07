package chat;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.List;
import java.awt.Label;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.LinkedList;

import javax.swing.JButton;

import Server.Mail;
import Server.ServeurMail;
import Server._Serveur;
import Server._ServeurMail;
import Client._Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

public class Client_Interface_Mail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private _Serveur serveur;
	private _Client client;
	private _ServeurMail mail;
	private LinkedList<Mail> listeMail;
	private List mesMail;
	private List mailEnvoyer;
	private JEditorPane contenu_area;
	private JButton bt_actualiser;
	private JButton bt_envoye;

	/**
	 * Create the frame.
	 */
	public Client_Interface_Mail(_Client client, _Serveur serveur) throws RemoteException {
		this.serveur = serveur;
		this.client = client;
		this.mail  = new ServeurMail(serveur);
		this.listeMail = new LinkedList<Mail>(); 
		initComponents();
		Client_Interface_Mail.this.setTitle(client.getSurNomClient());
		Client_Interface_Mail.this.setLocationRelativeTo(null);
		Client_Interface_Mail.this.setVisible(true);
		
		
	}
	
	public void initComponents(){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 541, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contenu_area = new JEditorPane("text/html", "");
		contenu_area.setBackground(SystemColor.controlHighlight);
		contenu_area.setBounds(10, 165, 502, 143);
		contentPane.add(contenu_area);
		contenu_area.setBorder(BorderFactory.createLineBorder(Color.black));
		
		mesMail = new List();
		mesMail.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) { 
					try {
						listeMail = serveur.getStockageMail().get(client.getSurNomClient());
					} catch (RemoteException e1) {						
						e1.printStackTrace();
					}
					for(int i=0;i<listeMail.size();i++) {
						if (listeMail.get(i).getObjet().equals(mesMail.getSelectedItem().toString())) {
							String from = listeMail.get(i).getAuteur();
							String sujet =  listeMail.get(i).getObjet();
							String contenu = listeMail.get(i).getMessage();
							contenu_area.setText("<b>From:</b> "+ from +"<br> <b>Sujet:</b> "+ sujet +"<br> <b>Contenu:</b> <br>" +contenu);
						}
					}	
				}
			}				
		});
		mesMail.setBounds(10, 38, 242, 111);
		contentPane.add(mesMail);
		
		mailEnvoyer = new List();
		mailEnvoyer.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) { 
					
					try {
						listeMail = serveur.getStockageMail().get(client.getSurNomClient());
					} catch (RemoteException e1) {						
						e1.printStackTrace();
					}
					for(int i=0;i<listeMail.size();i++) {
						if (listeMail.get(i).getObjet().equals(mailEnvoyer.getSelectedItem().toString())) {
							String to = listeMail.get(i).getDestinataire();
							String sujet =  listeMail.get(i).getObjet();
							String contenu = listeMail.get(i).getMessage();
							contenu_area.setText("<b>To:</b> "+ to +"<br> <b>Sujet:</b> "+ sujet +"<br> <b>Contenu:</b> <br>" +contenu);
						}
					}	
				}
			}				
		});
		mailEnvoyer.setBounds(273, 38, 242, 111);
		contentPane.add(mailEnvoyer);

		Label mail_recu = new Label("Recevoir");
		mail_recu.setFont(new Font("Courier New", Font.BOLD, 14));
		mail_recu.setBounds(10, 10, 105, 22);
		contentPane.add(mail_recu);
		
		Label label = new Label("Envoyer");
		label.setFont(new Font("Cordia New", Font.BOLD, 14));
		label.setBounds(273, 10, 95, 22);
		contentPane.add(label);
		
		bt_actualiser = new JButton("ACTUALISER");
		bt_actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionActualiser(e);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		bt_actualiser.setBounds(127, 319, 125, 30);
		contentPane.add(bt_actualiser);
		
		bt_envoye = new JButton("ENVOYER");
		bt_envoye.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionEnvoyerMail(e);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		bt_envoye.setBounds(261, 319, 125, 30);
		contentPane.add(bt_envoye);
	}
	
	/**
	 * 
	 */
	public void actionEnvoyerMail(ActionEvent e) throws RemoteException{
		new Client_Interface_CreeMail(client,serveur,mail);
	}
	
	/**
	 * 
	 */
	public void actionActualiser(ActionEvent e) throws RemoteException{
		this.listeMail = serveur.getStockageMail().get(client.getSurNomClient());
		this.mesMail.removeAll();
		this.mailEnvoyer.removeAll();
		
		for (int i=0;i<this.listeMail.size();i++) {
			
			if (this.listeMail.get(i).getAuteur().equals(client.getSurNomClient())) {
				this.mailEnvoyer.add(this.listeMail.get(i).getObjet());
			}
			else {
				this.mesMail.add(this.listeMail.get(i).getObjet());
			}
			
		}
		
	}
}
