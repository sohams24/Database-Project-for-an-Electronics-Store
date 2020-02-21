import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Dimension;

public class Login {

	private JFrame frame;
	private JTextField email;
	private JPasswordField password;

	public Login() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(false);
		frame.setSize(new Dimension(673, 448));
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBackground(Color.WHITE);
		frame.setForeground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEmailId = new JLabel("Email Id");
		lblEmailId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmailId.setBounds(178, 150, 56, 14);
		frame.getContentPane().add(lblEmailId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(172, 198, 58, 14);
		frame.getContentPane().add(lblPassword);
		
		email = new JTextField();
		email.setBounds(244, 147, 173, 20);
		frame.getContentPane().add(email);
		email.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(244, 197, 173, 20);
		frame.getContentPane().add(password);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int flag = 0;
				
				String emailID = email.getText();
				String pass = password.getText();

				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select email, password from customer");
					while(rs.next()) {
						if(rs.getString(1).equals(emailID) && rs.getString(2).equals(pass)) {
							flag = 1;
							break;
						}
					}				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(flag == 1) {
					LoginUser.setParameters(emailID);
					LandingPage window = new LandingPage();
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(frame, "Invalid email or password");
				}
			}
		});
		btnLogin.setBounds(276, 248, 72, 23);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblWelcomeToSmart = new JLabel("Welcome to Smart Buy");
		lblWelcomeToSmart.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 52));
		lblWelcomeToSmart.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToSmart.setBounds(73, 28, 461, 48);
		frame.getContentPane().add(lblWelcomeToSmart);
		
		JLabel lblSignIn = new JLabel("Sign-In");
		lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignIn.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 40));
		lblSignIn.setBounds(258, 87, 107, 48);
		frame.getContentPane().add(lblSignIn);
		
		JLabel lblNewCustomer = new JLabel("New Customer?");
		lblNewCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewCustomer.setFont(new Font("Microsoft Himalaya", Font.PLAIN, 38));
		lblNewCustomer.setBounds(73, 313, 196, 41);
		frame.getContentPane().add(lblNewCustomer);
		
		JButton btnRegister = new JButton("Register yourself");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Register();
				frame.dispose();
			}
		});
		btnRegister.setBounds(276, 313, 242, 33);
		frame.getContentPane().add(btnRegister);
		frame.setVisible(true);
	}
}
