import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import javax.swing.JScrollBar;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class Register {

	private JFrame frmNewCustomerRegistration;
	private JTextField txt_fname;
	private JTextField txt_mname;
	private JTextField txt_lname;
	private JTextField txt_emailId;
	private JPasswordField pwd_pass;
	private JPasswordField confirmPass;
	private JTextField txt_apartment;
	private JTextField txt_street;
	private JTextField txt_city;
	private JTextField txt_bankName;
	private JTextField txt_accNo;
	
	public Register() {
		initialize();
	}
	
	private void initialize() {
		frmNewCustomerRegistration = new JFrame();
		frmNewCustomerRegistration.setTitle("New Customer Registration");
		frmNewCustomerRegistration.getContentPane().setBackground(SystemColor.window);
		frmNewCustomerRegistration.setBounds(100, 100, 762, 412);
		frmNewCustomerRegistration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNewCustomerRegistration.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setBounds(10, 39, 62, 14);
		frmNewCustomerRegistration.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Email Id");
		lblNewLabel_1.setBounds(10, 71, 62, 14);
		frmNewCustomerRegistration.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Enter Password");
		lblNewLabel_2.setBounds(10, 144, 89, 14);
		frmNewCustomerRegistration.getContentPane().add(lblNewLabel_2);
		
		txt_fname = new JTextField();
		txt_fname.setBounds(92, 36, 146, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_fname);
		txt_fname.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Middle Name");
		lblNewLabel_3.setBounds(256, 39, 73, 14);
		frmNewCustomerRegistration.getContentPane().add(lblNewLabel_3);
		
		txt_mname = new JTextField();
		txt_mname.setBounds(339, 36, 148, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_mname);
		txt_mname.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Last Name");
		lblNewLabel_4.setBounds(503, 39, 62, 14);
		frmNewCustomerRegistration.getContentPane().add(lblNewLabel_4);
		
		txt_lname = new JTextField();
		txt_lname.setBounds(575, 36, 161, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_lname);
		txt_lname.setColumns(10);
		
		txt_emailId = new JTextField();
		txt_emailId.setBounds(92, 68, 247, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_emailId);
		txt_emailId.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 76, 506, -13);
		frmNewCustomerRegistration.getContentPane().add(separator);
		
		pwd_pass = new JPasswordField();
		pwd_pass.setBounds(109, 141, 232, 20);
		frmNewCustomerRegistration.getContentPane().add(pwd_pass);
		
		JLabel lblSetPassword = new JLabel("Set Password");
		lblSetPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSetPassword.setBounds(10, 116, 232, 17);
		frmNewCustomerRegistration.getContentPane().add(lblSetPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(405, 144, 89, 14);
		frmNewCustomerRegistration.getContentPane().add(lblConfirmPassword);
		
		confirmPass = new JPasswordField();
		confirmPass.setBounds(504, 141, 232, 20);
		frmNewCustomerRegistration.getContentPane().add(confirmPass);
		frmNewCustomerRegistration.setVisible(true);
		
		JLabel lblApartment = new JLabel("Apartment");
		lblApartment.setBounds(10, 229, 62, 14);
		frmNewCustomerRegistration.getContentPane().add(lblApartment);
		
		txt_apartment = new JTextField();
		txt_apartment.setBounds(92, 226, 158, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_apartment);
		txt_apartment.setColumns(10);
		
		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(292, 229, 47, 14);
		frmNewCustomerRegistration.getContentPane().add(lblStreet);
		
		txt_street = new JTextField();
		txt_street.setBounds(338, 226, 162, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_street);
		txt_street.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("City");
		lblNewLabel_5.setBounds(535, 229, 30, 14);
		frmNewCustomerRegistration.getContentPane().add(lblNewLabel_5);
		
		txt_city = new JTextField();
		txt_city.setBounds(575, 226, 161, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_city);
		txt_city.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(49, 86, -38, 2);
		frmNewCustomerRegistration.getContentPane().add(separator_1);
		
		JLabel lblPersonalInformation = new JLabel("Personal Information");
		lblPersonalInformation.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPersonalInformation.setBounds(10, 11, 232, 17);
		frmNewCustomerRegistration.getContentPane().add(lblPersonalInformation);
		
		JLabel lblAddressInformation = new JLabel("Address Information");
		lblAddressInformation.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblAddressInformation.setBounds(10, 198, 232, 17);
		frmNewCustomerRegistration.getContentPane().add(lblAddressInformation);
		
		JLabel lblBankAccountDetails = new JLabel("Bank Account Details");
		lblBankAccountDetails.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBankAccountDetails.setBounds(10, 281, 232, 17);
		frmNewCustomerRegistration.getContentPane().add(lblBankAccountDetails);
		
		JLabel lblBank = new JLabel("Bank Name");
		lblBank.setBounds(10, 309, 76, 14);
		frmNewCustomerRegistration.getContentPane().add(lblBank);
		
		txt_bankName = new JTextField();
		txt_bankName.setBounds(82, 306, 222, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_bankName);
		txt_bankName.setColumns(10);
		
		JLabel lblAccountNumber = new JLabel("Account Number");
		lblAccountNumber.setBounds(361, 309, 106, 14);
		frmNewCustomerRegistration.getContentPane().add(lblAccountNumber);
		
		txt_accNo = new JTextField();
		txt_accNo.setBounds(473, 306, 263, 20);
		frmNewCustomerRegistration.getContentPane().add(txt_accNo);
		txt_accNo.setColumns(10);
		
		JButton btnAlreadyReg = new JButton("Login if already registered");
		btnAlreadyReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login();
				frmNewCustomerRegistration.dispose();
			}
		});
		btnAlreadyReg.setBounds(535, 5, 201, 23);
		frmNewCustomerRegistration.getContentPane().add(btnAlreadyReg);

		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String fname = txt_fname.getText();
				String mname = txt_mname.getText();
				String lname = txt_lname.getText();
				String email = txt_emailId.getText();
				String password = pwd_pass.getText();
				String conformPassword = confirmPass.getText();
				String apartment = txt_apartment.getText();
				String street = txt_street.getText();
				String city = txt_city.getText();
				String bankName = txt_bankName.getText();
				String accNo = txt_accNo.getText();
				
				if(!password.equals(conformPassword)) {
					JOptionPane.showMessageDialog(frmNewCustomerRegistration, "Passwords do not match");
				}
				else {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
					PreparedStatement ps1 = con.prepareStatement("SELECT email FROM customer WHERE email = ?");
					ps1.setString(1, email);
					ResultSet rs1 = ps1.executeQuery();
					if(rs1.next()) {
						JOptionPane.showMessageDialog(frmNewCustomerRegistration, "Given email id already register");
					}
					else {
					PreparedStatement ps = con.prepareStatement("insert into customer(email, first_name, middle_name, last_name, password, apartment, street, city, bank_name, account_number) values(?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1, email);
					ps.setString(2, fname);
					ps.setString(3, mname);
					ps.setString(4, lname);
					ps.setString(5, password);
					ps.setString(6, apartment);
					ps.setString(7, street);
					ps.setString(8, city);
					ps.setString(9, bankName);
					ps.setString(10, accNo);
					
					int rs = ps.executeUpdate();
					if(rs == 0) {
						JOptionPane.showMessageDialog(frmNewCustomerRegistration, "Customer not Registered");
					}
					if(rs == 1) {
						JOptionPane.showMessageDialog(frmNewCustomerRegistration, "Customer Successfully Registered");
						new Login();
						frmNewCustomerRegistration.dispose();
					}	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}}
			}
		});
		btnSubmit.setBounds(340, 339, 89, 23);
		frmNewCustomerRegistration.getContentPane().add(btnSubmit);
	}
}
