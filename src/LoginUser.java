import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


//this class stores the information of currently logged in user
public class LoginUser {
	
	static String cid = null;
	static String email = null;
	static String fname = null;
	static String lname = null;
	static int lastOrder = 0;
	//static String newOrderId = null;

	public static void setParameters(String emailID){
		email = emailID;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
			PreparedStatement ps = con.prepareStatement("select cid, first_name, last_name, last_order from customer where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
		
			while(rs.next()) {
				cid= rs.getString(1);
				fname = rs.getString(2);
				lname = rs.getString(3);
				lastOrder = rs.getInt(4);
				System.out.println("cid:"+rs.getString(1)+"\nfname "+rs.getString(2)+"\n"+"last order"+lastOrder);
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//int newOrderNo = Integer.parseInt(lastOrder.substring(lastOrder.indexOf('O')+1)) + 1;
		//newOrderId = cid + "O" + newOrderNo;
		System.out.println("The user currently logged in is: "+fname+" "+lname);
	}
}
