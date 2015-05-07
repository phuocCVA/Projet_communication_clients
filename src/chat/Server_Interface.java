package chat;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import Server.Serveur;

public class Server_Interface extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton bt_start = new JButton("Lancer Server");
	private JButton bt_stop = new JButton("Terminer Server");
	
	private JLabel message ;
	/**
	 * .
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_Interface frame = new Server_Interface();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Créer l'interface du serveur.
	 */
	
	public Server_Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 384, 142);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		bt_start.setBounds(41, 11, 134, 33);
		bt_start.addActionListener(this);
		contentPane.add(bt_start);
		
		bt_stop.setBounds(197, 13, 143, 31);
		bt_stop.addActionListener(this);
		contentPane.add(bt_stop);

		message = new JLabel("");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setBounds(84, 56, 143, 31);
		contentPane.add(message);
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		Object o=e.getSource();
		
		/**	Lancer serveur	*/
		
		if(o.equals(bt_start)){
			System.out.println("ready");
			message.setText("Serveur exécute");
			Serveur.StartServer();
			
		}
		
		/**	Terminer serveur	*/
		
		else if(o.equals(bt_stop)){
			message.setText("Server arrête");
			Serveur.StopServer();
			
		} 
	}
}

