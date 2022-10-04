package littleproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import utils.BCrypt;

public class orderInsert {
	static final String sqlAppendAccount = "INSERT INTO porder (account,proid,quantity) VALUES (?,?,?)";
	static PreparedStatement checkStatement, appendStatement;
	public static String proid;
	public static String quantity;
	public static String account;

	public orderInsert(String account ,String proid,String quantity) {
		account=orderInsert.account;
		proid=orderInsert.proid;
		quantity=orderInsert.quantity;
		
		
		//account = "tex";
		//proid = productTable.aa;
		//quantity = productTable.qLog.getText();
	
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		try {
			Connection conn =DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/yucu",prop);
			
			appendStatement = conn.prepareStatement(sqlAppendAccount);
		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		try {
			if (appendData(account, proid, quantity)) {
				System.out.println("Success");}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public static void main(String[] args) {

		
	}
	

	
	static boolean appendData(String account,String proid, String quantity) throws Exception {
		appendStatement.setString(1, account);
		appendStatement.setString(2, proid);
		appendStatement.setString(3, quantity);
		int n =appendStatement.executeUpdate();
		return n!=0;
	}

}
