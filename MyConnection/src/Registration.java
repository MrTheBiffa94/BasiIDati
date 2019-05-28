

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.PreparedStatement;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;

import java.util.Random;

public class Registration extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration frame = new Registration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Registration() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 47, 86, 30);
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblUsername);
		
		JLabel lblRegistrazione = new JLabel("Registration");
		lblRegistrazione.setBounds(126, 11, 117, 21);
		lblRegistrazione.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrazione.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPane.add(lblRegistrazione);
		
		textField = new JTextField();
		textField.setBounds(126, 55, 117, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 88, 86, 30);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPane.add(lblPassword);
		
		JLabel lblConfpass = new JLabel("Conf_Pass");
		lblConfpass.setBounds(10, 129, 86, 30);
		lblConfpass.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfpass.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPane.add(lblConfpass);
		
		textField_1 = new JTextField();
		textField_1.setBounds(126, 96, 117, 20);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(126, 137, 117, 20);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/esame","root","");
					String username = textField.getText();
					String password = textField_1.getText();
					String password_2 = textField_2.getText();
					Random rand = new Random();
					int Cod_Utente = rand.nextInt(1000000);
					
					//CONTROLLO PASSWORD
					String result = "Password valida";
					int length = 0;
					int numCount = 0;
					int capCount = 0;
					
					for (int x = 0; x < password.length(); x++) {
						if((password.charAt(x) >= 47 && password.charAt(x)<=58) || (password.charAt(x)>=64 && password.charAt(x)<=91) ||
								(password.charAt(x) >= 97 && password.charAt(x) <= 122)) {
								//keep the password
						}else {
							JOptionPane.showMessageDialog(null, "Password non corretta");
						}
						

						if ((password.charAt(x) > 47 && password.charAt(x) < 58)) {			// conta quanti numeri sono stati inseriti
							numCount ++;
						}

						if ((password.charAt(x) > 64 && password.charAt(x) < 91)) {			// conta il numero di lettere maiuscole
							capCount ++;
						}

						length = (x + 1);
					}//fine ciclo for
					
					if (numCount < 2){	
						JOptionPane.showMessageDialog(null,"Non contiene 2 numeri!");// Controlla se la password contiene 2 numeri
						
					}

					if (capCount < 1) {									// Controlla se la password contiene almeno 1 lettera maiuscola
						JOptionPane.showMessageDialog(null,"Non contiene lettere maiuscole");
					}

					if (length < 8){									// controlla la lunghezza della password
						JOptionPane.showMessageDialog(null,"Password troppo corta");
					}
				//FINE CONTROLLO INSERIMENTO PASSWORD
					
				//TRASFORMAZIONE IN MD5
					String passwordToHash = password;
					
				    String generatedPassword = null;
				    
				    try {
				        // Create MessageDigest instance for MD5
				        MessageDigest md = MessageDigest.getInstance("MD5");
				        //Add password bytes to digest
				        md.update(passwordToHash.getBytes());
				        //Get the hash's bytes
				        byte[] bytes = md.digest();
				        //This bytes[] has bytes in decimal format;
				        //Convert it to hexadecimal format
				        StringBuilder sb = new StringBuilder();
				        for(int i=0; i< bytes.length ;i++)
				        {
				            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				        }
				        //Get complete hashed password in hex format
				        generatedPassword = sb.toString();
				        
				    }
				    catch (NoSuchAlgorithmException e)
				    {
				        e.printStackTrace();
				    }
					
					//if (generatedPassword.equals(password_2)) {
						//System.out.println(generatedPassword);
						//Query
						
						String query = "insert into utenti Username,Password,Cod_Utenti values(?,?,?)";
						PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
						stmt.setString(1, username);
						stmt.setString(2, generatedPassword);
						stmt.setInt(3, Cod_Utente);
				
						
					//}
					
				}catch (Exception e){
					System.out.println("Errore di connessione000"+ e);
				}
				
				
			}

			
		});
		btnSignUp.setBounds(126, 194, 117, 23);
		contentPane.add(btnSignUp);
	}
}



