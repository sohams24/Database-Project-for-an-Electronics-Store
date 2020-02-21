import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class LandingPage {

	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	//static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	private JFrame frame;
	JComboBox comboBox = new JComboBox();
	private final JLabel lblNewLabel = new JLabel("Shop by Category");
	private final JButton btnLogout = new JButton("Logout");
	String cat_select;
	private final JTable product_table = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();

	public LandingPage() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//device.setFullScreenWindow(frame);
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBackground(SystemColor.window);
		frame.setBounds(100, 100, 782, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblWelcome = new JLabel("Welcome "+LoginUser.fname+"!");
		lblWelcome.setBounds(128, 0, 376, 43);
		lblWelcome.setBackground(SystemColor.window);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblWelcome);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cat_select = (String) comboBox.getSelectedItem();
				updateTable();
			}
		});
		comboBox.setToolTipText("Select Categories");
		comboBox.setBounds(29, 72, 169, 20);
		frame.getContentPane().add(comboBox);
		fillComboBox();
		lblNewLabel.setBounds(29, 54, 105, 14);
		frame.getContentPane().add(lblNewLabel);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginUser.email=null;
				LoginUser.fname=null;
				new Login();
				frame.dispose();
			}
		});
		btnLogout.setBounds(550, 20, 89, 23);
		
		frame.getContentPane().add(btnLogout);
		scrollPane.setBounds(10, 103, 746, 323);
		
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(product_table);
		product_table.setBackground(Color.WHITE);
		product_table.setFillsViewportHeight(true);
		product_table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		JButton btnViewItem = new JButton("View Item");
		btnViewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = product_table.getSelectedRow();
				//TableModel model = product_table.getModel();
				String pid = product_table.getValueAt(index, 0).toString();
				String pname = product_table.getValueAt(index, 1).toString();
				String description = product_table.getValueAt(index, 2).toString();
				String manu = product_table.getValueAt(index, 3).toString();
				String price = product_table.getValueAt(index, 4).toString();
				String stock = product_table.getValueAt(index, 5).toString();
				
				System.out.println("Row selected is : "+pid+" "+pname+" "+" "+manu+" "+" "+price+" "+stock);

				new ViewItem(pid, pname, description, manu, price, stock);
				
			}
		});
		btnViewItem.setBounds(517, 71, 122, 23);
		frame.getContentPane().add(btnViewItem);
		
		JButton btnMyOrders = new JButton("My Orders");
		btnMyOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MyOrders();
			}
		});
		btnMyOrders.setBounds(286, 71, 139, 23);
		frame.getContentPane().add(btnMyOrders);
		updateTable();
		frame.setVisible(true);
	}
	
	public void fillComboBox() {
		try {
			comboBox.addItem("All");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
			String query = "Select distinct cat_name from product_categories";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				comboBox.addItem(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateTable() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
			String query;
			if(cat_select.equals("All")) {
				 query = "WITH availibility AS\r\n" + 
						"(SELECT DISTINCT pid , SUM(Quantity) stock\r\n" + 
						"FROM warehouse_inventory\r\n" + 
						"GROUP BY pid)\r\n" + 
						"SELECT DISTINCT pid, name, discription, manufacturer, price, stock\r\n" + 
						"FROM product NATURAL JOIN availibility";
				 		ps = con.prepareStatement(query);
			} else {
				query = "WITH availibility AS\r\n" + 
					"(SELECT DISTINCT pid , SUM(Quantity) stock\r\n" + 
					"FROM warehouse_inventory\r\n" + 
					"GROUP BY pid)\r\n" + 
					"SELECT DISTINCT pid, name, discription, manufacturer, price, stock\r\n" + 
					"FROM product NATURAL JOIN availibility NATURAL JOIN product_categories\r\n" + 
					"WHERE cat_name = ?" ;
				ps = con.prepareStatement(query);
				ps.setString(1, cat_select);
			}
			rs = ps.executeQuery();
			product_table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
