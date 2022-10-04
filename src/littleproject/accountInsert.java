package littleproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import utils.BCrypt;

public class accountInsert {
	static final String sqlCheckAccount = "SELECT account FROM member WHERE account = ?";
	static final String sqlAppendAccount = "INSERT INTO member (account,password,realname,grade) VALUES (?,?,?,?)";
	static PreparedStatement checkStatement, appendStatement;

	public static void main(String[] args) {
		String account = "amy";
		String password = "123";
		String realname = "艾蜜莉";
		int grade = 1;
		
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		
		
		try {
			Connection conn =DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/yucu",prop);
			
			checkStatement = conn.prepareStatement(sqlCheckAccount);
			appendStatement = conn.prepareStatement(sqlAppendAccount);
			if (!isDataRepeat(account)) {
				if (appendData(account, password, realname,grade)) {
					System.out.println("Success");
				}else {
					System.out.println("E2");
				}
			}else {
				System.out.println("E1");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}
	
	static boolean isDataRepeat(String account) throws Exception {
		checkStatement.setString(1, account);
		ResultSet rs = checkStatement.executeQuery();
		return rs.next();
	}
	static boolean appendData(String account,String password,String realname, int grade) throws Exception {
		appendStatement.setString(1, account);
		appendStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
		appendStatement.setString(3, realname);
		appendStatement.setInt(4, grade);
		int n = appendStatement.executeUpdate();
		return n!= 0;
	}

}
