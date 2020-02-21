import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MyOrders {

	private JFrame frame;
	private JTable orders_table;
	String currentUser;

	public MyOrders() {
		initialize();
		updateOrdersTable();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.window);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 640, 355);
		frame.getContentPane().add(scrollPane);
		
		orders_table = new JTable();
		scrollPane.setViewportView(orders_table);
		orders_table.setFillsViewportHeight(true);
		orders_table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		JLabel lblNewLabel = new JLabel("My Orders");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(259, 11, 147, 27);
		frame.getContentPane().add(lblNewLabel);
		frame.setBounds(100, 100, 674, 449);
		frame.setVisible(true);
	}
	
	private void updateOrdersTable() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
			
			String query = "SELECT oid, name, quantity, tracking_id, shipper_name, price Unit_Price, price*quantity amount\r\n" + 
					"FROM online_order natural join order_details natural join product\r\n" + 
					"WHERE cid = ?";
			PreparedStatement ps  = con.prepareStatement(query);
			currentUser = LoginUser.cid;
			
			
			System.out.println("Current user: "+currentUser);
			ps.setString(1, currentUser);
			ResultSet rs = ps.executeQuery();
			orders_table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
