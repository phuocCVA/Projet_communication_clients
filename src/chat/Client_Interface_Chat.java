
package chat;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import Server.ServeurChat;
import Server.ServeurEnvoyeFichier;
import Server._Serveur;
import Client._Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;


/**
 *
 */
public class Client_Interface_Chat extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> list_user;
    private JLabel lb_user_online;
    private JLabel select_user;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea user_online;
    private JTextArea chat_area;
    private JTextField message;
    private JButton sendFile;

    private _Client client;
    private _Serveur server;  
    private String nickname;
    private File file;
    private int nbClients = 0;
    private JMenuBar menuBar;
    private JMenu mnDrive;
    private JMenu mnMail;
	
    
    /** Créer une nouvelle interface 
    */
    
    public Client_Interface_Chat(_Client client, _Serveur server) throws RemoteException {
    	 this.client=client;
    	 this.server=server;
    	 this.nickname=client.getSurNomClient();
    	 initComponents();
    	 Client_Interface_Chat.this.setTitle(client.getSurNomClient());
    	 Client_Interface_Chat.this.setLocationRelativeTo(null);
    	 Client_Interface_Chat.this.setVisible(true);
		 javax.swing.Timer t = new javax.swing.Timer(1000, new majChat());
		 t.start();    	 
    }
    
    public  void initComponents() {
        
        lb_user_online = new JLabel();       
        list_user = new JComboBox<String>();
        select_user = new JLabel();
        message = new JTextField();
        chat_area = new JTextArea();
        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        
        user_online = new JTextArea();
        user_online.setEditable(false);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lb_user_online.setFont(new java.awt.Font("Tahoma", 1, 14));
        lb_user_online.setText("Utilisateur en ligne");

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        user_online.setBackground(new java.awt.Color(240, 240, 240));
        user_online.setColumns(20);
        user_online.setRows(5);
        jScrollPane1.setViewportView(user_online);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
	 
        mnDrive = new JMenu("Drive");
        mnDrive.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount()>=1) {        				
    			    try {    			    
						new Client_Interface_Drive(client, server);							
					} catch (RemoteException e1) {							
						e1.printStackTrace();
					}        			   
    			
    			}
        	}
        });      	   
        menuBar.add(mnDrive);
        
        mnMail = new JMenu("Mail");
        mnMail.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount()>=1) {        				
    			    try {    			    
						new Client_Interface_Mail(client, server);							
					} catch (RemoteException e1) {							
						e1.printStackTrace();
					}        			   
        		}
        	}
        });
        menuBar.add(mnMail);
	 
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lb_user_online, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_user_online, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
        );

        list_user.setModel(new DefaultComboBoxModel<String>(new String[] {"Tous utilisateurs"}));
        select_user.setFont(new java.awt.Font("Tahoma", 1, 14));
        select_user.setText("Utilisateur");

        message.setText("Bonjour a tous...");
        message.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                messageKeyReleased(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        chat_area.setBackground(new java.awt.Color(240, 240, 240));
        chat_area.setColumns(20);
        chat_area.setEditable(false);
        chat_area.setLineWrap(true);
        chat_area.setRows(5);
        jScrollPane2.setViewportView(chat_area);
        
        // Choisir fichier a envoyer
        sendFile = new JButton("File");
        sendFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource()== sendFile) {
        			JFileChooser fc = new JFileChooser();
        			int returnVal = fc.showOpenDialog(null);
        			if (returnVal == JFileChooser.APPROVE_OPTION) {
        				file = fc.getSelectedFile();
        			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
        					System.out.println("");
        			} else if (returnVal == JFileChooser.ERROR_OPTION) {
        				System.out.println("Error!");
        			}        					
        		}
        		try {
					new ServeurEnvoyeFichier().FichierToServer(server, client.getSurNomClient(), list_user.getSelectedItem().toString(), file.getPath() );
				} catch (RemoteException e1) {				
					e1.printStackTrace();
				}    
        	}	       		
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(13)
        					.addComponent(select_user, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(list_user, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
        					.addGap(56))
        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(message, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(sendFile, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
        						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(11)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(list_user, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(select_user, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(message, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        				.addComponent(sendFile, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
        );
        getContentPane().setLayout(layout);

      pack();
    }

	/** Utiliser le bouton ENTER pour envoyer un message */
    
    private void messageKeyReleased(KeyEvent evt) {
	    if (evt.getKeyCode() == KeyEvent.VK_ENTER && !message.getText().equals(""))  {
	  	    try {
	                new ServeurChat().MsgToServer(server, message.getText(), nickname, list_user.getSelectedItem().toString(),false);
	                message.setText("");
	        } catch (RemoteException e) {
	                e.printStackTrace();
	        }
	    }
	}

	
    /** Fermer la fenêtre chat pour deconnecter à serveur*/
    
    private void formWindowClosing(WindowEvent evt) {
	     try {
	            if(server!=null)
	            {
	                server.LogoutToServer(client, nickname);
	            }
	        } catch (RemoteException ex) {
	            Logger.getLogger(Client_Interface_Chat.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}

   
   class majChat implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	try {
	    		
	    		Client_Interface_Chat.this.actualiser_Liste_Utilisateur();	 
	    		
				Client_Interface_Chat.this.actualiserChat();
				
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}	    
    	
	    }
    }
    
   /**
    * Mettre a jour liste d'utilisateur en ligne
    * @throws RemoteException
    */
    public void actualiser_Liste_Utilisateur() throws RemoteException {
        //
    	if( this.nbClients < server.getClients().size() ) {
	        try {	          	
	            user_online.setText("");
	            list_user.removeAllItems();
	            list_user.addItem("Tous utilisateurs");
	            
	            for (int i = 0; i < server.getClients().size(); i++) {	
	            	if (!server.getClients().get(i).getSurNomClient().equals(client.getSurNomClient()))
	            		list_user.addItem(server.getClients().get(i).getSurNomClient());	
	                user_online.append(server.getClients().get(i).getSurNomClient()+"\n");
	                                  
	            }
	            
	            this.nbClients = server.getClients().size();
		            
	        } catch (RemoteException ex) {
	           
	        }
    		}
    }
    
    /**
     * Mettre a jour chat area
     * @throws IOException 
     */
    private void actualiserChat() throws IOException {
    	
    	if (!client.getPrive()) { 
    		chat_area.setText(client.getMessage());    		
    	}
    	
    	if (client.AvoirFichier()) {    	
        
    		String msg = "<html> <b>"+ client.getFichier().getFromUser() + "</b> a vous envoyé le fichier: <b>" + client.getFichier().getNom() + "</b> <br>" + " Est ce que vous voulez télécharger ? </html>";
    		JLabel label = new JLabel(msg);
            label.setFont(new Font("serif", Font.PLAIN, 16));
            
            int choisir = JOptionPane.showConfirmDialog(null, label); 
    		if (choisir==0){
    			File file=new File(client.getFichier().getNom());
    		  	BufferedOutputStream outputFile=new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
    		    outputFile.write(client.getFichier().getData(),0,client.getFichier().getData().length);
    		    outputFile.flush();
    		    outputFile.close();
    		    JOptionPane.showMessageDialog(null, "Fichier a été télécharger à " + file.getAbsolutePath());
    		}
    		client.setFichier();
    	}    
    }
       
}
    



    

