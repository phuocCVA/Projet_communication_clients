package chat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.TextField;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.TextArea;
import java.rmi.RemoteException;

import javax.swing.JButton;

import Server._Serveur;
import Server._ServeurMail;
import Client._Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client_Interface_CreeMail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private _Client client;
    private	_Serveur serveur;
    private _ServeurMail mail;
	private JComboBox<String> destinataire;
	private TextField sujet;
	private TextArea contenu_area;

	/**
	 * Create the frame.
	 */
	public Client_Interface_CreeMail(_Client client, _Serveur serveur, _ServeurMail mail) throws RemoteException {
		this.client=client;
		this.serveur=serveur;
		this.mail=mail;
		initComponents();
		Client_Interface_CreeMail.this.setTitle(client.getSurNomClient());
	   	Client_Interface_CreeMail.this.setLocationRelativeTo(null);
	   	Client_Interface_CreeMail.this.setVisible(true);	
	}
	
	public void initComponents() throws RemoteException{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 504, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		destinataire = new JComboBox <String>();
		destinataire.setBounds(72, 20, 390, 27);
		for(int i=0;i<serveur.getClients().size();i++){
			if (!serveur.getClients().get(i).getSurNomClient().equals(client.getSurNomClient())) {
				destinataire.addItem(serveur.getClients().get(i).getSurNomClient());
			}
		}
		contentPane.add(destinataire);
		
		JLabel lblTo = new JLabel("\u00C0:");
		lblTo.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblTo.setBounds(20, 22, 57, 36);
		contentPane.add(lblTo);
		
		JLabel lblSujet = new JLabel("Sujet:");
		lblSujet.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSujet.setBounds(20, 64, 57, 27);
		contentPane.add(lblSujet);
		
		sujet = new TextField();
		sujet.setBounds(72, 64, 390, 27);
		contentPane.add(sujet);
		
		contenu_area = new TextArea();
		contenu_area.setBounds(20, 139, 442, 184);
		contentPane.add(contenu_area);
		
		JLabel lblNewLabel = new JLabel("Contenu");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(20, 110, 117, 27);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("ENVOYER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionEnvoye(e);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(172, 339, 126, 27);
		contentPane.add(btnNewButton);
	}
	
	public void actionEnvoye(ActionEvent e)throws RemoteException {		
		mail.envoyerMail(client.getSurNomClient(), this.sujet.getText(), this.destinataire.getSelectedItem().toString(), this.contenu_area.getText());
		JOptionPane.showMessageDialog(null, "Mail a ete envoye");
		this.setVisible(false);
	}
}
