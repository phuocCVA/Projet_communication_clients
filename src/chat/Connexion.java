package chat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import Client.Client;
import Client._Client;
import Server._Serveur;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Connexion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nickname;
	private JTextField serveur;

	private String host;
	private _Serveur server;
	private _Client client;
	/**
	 * Create the frame.
	 */
	
	public Connexion() {
		initComponents();
		Connexion.this.setLocationRelativeTo(null);
		Connexion.this.setVisible(true);
	}
	
	public void initComponents(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nickname = new JTextField();
		nickname.setBounds(120, 82, 211, 33);
		contentPane.add(nickname);
		nickname.setColumns(10);
		addWindowListener( new WindowAdapter() {
			   public void windowOpened( WindowEvent e ){
			        nickname.requestFocus();
			     }
		} ); 
		
		JLabel lb_nickname = new JLabel("Votre surnom");
		lb_nickname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lb_nickname.setBounds(26, 80, 112, 33);
		contentPane.add(lb_nickname);
		
		JLabel lb_serveur = new JLabel("Serveur");
		lb_serveur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lb_serveur.setBounds(26, 20, 106, 33);
		contentPane.add(lb_serveur);
		
		serveur = new JTextField();
		serveur.setBounds(120, 22, 209, 33);
		contentPane.add(serveur);
		serveur.setText("localhost");
		serveur.setColumns(10);
		
		JButton bt_connexion = new JButton("Connexion");
		bt_connexion.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1) {
					   try {					 
						  host = serveur.getText();
						  server = (_Serveur)Naming.lookup("//" + host + "/Server");						 
						  if(nickname.getText()==null)
						    	{
				                	JOptionPane.showInputDialog("Il faut entre votre surnom.", " ");
						    	}
				          else 
				            	{	
				        	  		client = new Client(nickname.getText());
				                    if(server.RegisterToServer(client, nickname.getText()))
				                    {
				                    	new Client_Interface_Chat(client,server);
				                    	Connexion.this.setVisible(false);
				                    }
				                    else
				                    {
				                        JOptionPane.showInputDialog(" Ce surnom a déja utilisé ", "Surnom");
				                    }
				            }
							
						} catch (MalformedURLException e1) {						
							e1.printStackTrace();
						} catch (RemoteException e1) {							
							e1.printStackTrace();
						} catch (NotBoundException e1) {							
							e1.printStackTrace();
						}
					   
				
				}
			}
		});
		
	
		bt_connexion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		bt_connexion.setBounds(100, 135, 119, 29);
		contentPane.add(bt_connexion);
		
		
	}
}
