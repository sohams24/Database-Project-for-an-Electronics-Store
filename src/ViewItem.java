import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class ViewItem {
	
//	String prod_id;
//	String prod_name;
//	String description;
//	String prod_manu;
//	String prod_price;
//	String prod_stock;

	private JFrame frmItemDetails;
	private JTextField text_Quantity;


	public ViewItem(String prod_id, String prod_name, String description, String prod_manu, String prod_price, String prod_stock) {
		System.out.println("New item window called");
		initialize(prod_id, prod_name, description, prod_manu, prod_price, prod_stock);
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}


	private void initialize(String prod_id, String prod_name, String description, String prod_manu, String prod_price, String prod_stock) {
		
		System.out.println("Initializing");
		
		frmItemDetails = new JFrame();
		frmItemDetails.setTitle("Item Details");
		frmItemDetails.getContentPane().setBackground(SystemColor.window);
		frmItemDetails.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel(prod_name);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(55, 23, 292, 37);
		frmItemDetails.getContentPane().add(lblName);
		
		JLabel lblManufacturer = new JLabel("Manufacturer:");
		lblManufacturer.setBounds(66, 94, 113, 14);
		frmItemDetails.getContentPane().add(lblManufacturer);
		
		JLabel lblManufacturer1 = new JLabel(prod_manu);
		lblManufacturer1.setBounds(201, 94, 86, 14);
		frmItemDetails.getContentPane().add(lblManufacturer1);
		frmItemDetails.setBounds(100, 100, 414, 366);
		//frmItemDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(66, 133, 113, 14);
		frmItemDetails.getContentPane().add(lblDescription);
		
		JLabel lblDescription1 = new JLabel(description);
		lblDescription1.setBounds(201, 133, 86, 14);
		frmItemDetails.getContentPane().add(lblDescription1);
		
		JLabel lblAvailableQuant = new JLabel("Available Quantity:");
		lblAvailableQuant.setBounds(66, 172, 113, 14);
		frmItemDetails.getContentPane().add(lblAvailableQuant);
		
		JLabel lblAvailableQuant1 = new JLabel(prod_stock);
		lblAvailableQuant1.setBounds(201, 172, 86, 14);
		frmItemDetails.getContentPane().add(lblAvailableQuant1);
		
		JLabel lblOrderQuantity = new JLabel("Enter Order Quantity:");
		lblOrderQuantity.setBounds(66, 213, 113, 14);
		frmItemDetails.getContentPane().add(lblOrderQuantity);
		
		text_Quantity = new JTextField();
		text_Quantity.setBounds(201, 210, 46, 20);
		frmItemDetails.getContentPane().add(text_Quantity);
		text_Quantity.setColumns(10);
		
		JButton btnAddToCart = new JButton("Place Order");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int order_quantity = Integer.parseInt(text_Quantity.getText());
				int newOrderNo = LoginUser.lastOrder + 1;
				String newOrderId = LoginUser.cid + "O" + newOrderNo;				
				System.out.println("The new order id is:"+newOrderId);
				try {
	
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
					
					PreparedStatement ps1 = con.prepareStatement("insert into online_order(oid, cid,order_date) values(?,?,?)");
					ps1.setString(1, newOrderId);
					ps1.setString(2, LoginUser.cid);
					ps1.setDate(3, getCurrentDate());
					
					int rs1 = ps1.executeUpdate();
					
					PreparedStatement ps2 = con.prepareStatement("insert into order_details(oid, pid, quantity, wh_id, shipper_name) values(?,?,?,?,?)");
					ps2.setString(1, newOrderId);
					ps2.setString(2, prod_id);
					ps2.setInt(3, order_quantity);
					ps2.setString(4, "WH1");
					ps2.setString(5, "FEDEX");
					int rs2 = ps2.executeUpdate();
					if(rs2 == 0) {
						JOptionPane.showMessageDialog(frmItemDetails, "Order not placed");
					}
					if(rs2 == 1) {
						JOptionPane.showMessageDialog(frmItemDetails, "Order has been placed and billed to your account number.");
						frmItemDetails.dispose();
						LoginUser.lastOrder++;
					}
					
					PreparedStatement ps3 = con.prepareStatement("update customer set last_order = ? where cid = ?");
					ps3.setInt(1, newOrderNo);
					ps3.setString(2, LoginUser.cid);
					int rs3 = ps3.executeUpdate();
					
					PreparedStatement ps4 = con.prepareStatement("update warehouse_inventory \r\n" + 
							"set quantity = quantity - ?\r\n" + 
							"where wh_id = ? AND pid = ?");
					
					System.out.println("Order quantity is "+order_quantity);
					ps4.setInt(1, order_quantity);
					ps4.setString(2, "WH1");
					ps4.setString(3, prod_id);
					
					ps4.execute();
					
					
					
					
//					System.out.println("rs4: "+rs4);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnAddToCart.setBounds(140, 262, 129, 37);
		frmItemDetails.getContentPane().add(btnAddToCart);
		
		frmItemDetails.setVisible(true);
	}
}
