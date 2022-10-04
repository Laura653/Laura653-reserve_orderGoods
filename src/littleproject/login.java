package littleproject;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import utils.BCrypt;
import utils.member;


public class login extends JFrame {
	JFrame jf = this;
	public JButton btn1;
	public JLabel accountchi = new JLabel("帳號");
	public JLabel passwdchi = new JLabel("密碼");
	public static JTextField accountLog;
	public static JPasswordField passwdLog;
	public static String ac;
	public String pw;
	
	public login() {
		super("登入");
		
		//按鈕&輸入
		btn1 = new JButton("登入");
		accountLog = new JTextField(17);
		passwdLog = new JPasswordField(17);

		accountchi.setFont(new Font("新細明體",Font.PLAIN,16));
		passwdchi.setFont(new Font("新細明體",Font.PLAIN,16));
		btn1.setFont(new Font("新細明體",Font.PLAIN,14));
		
		setLayout(new FlowLayout());
		add(accountchi);add(accountLog);
		add(passwdchi);add(passwdLog);
		add(btn1);
		
		setSize(250, 200);
		setVisible(true);
		//無法調整大小
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//按鈕事件
		btn1.addActionListener(new action()); 
	}
	
	
	
	public static void main(String[] args) {
		new login();
	}

	class action implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ac= accountLog.getText();
			pw=passwdLog.getText();
			//System.out.println(ac);
			//System.out.println(pw);
			
			System.out.println(loginCheck(ac,pw));
			
			if(loginCheck(ac,pw)==1) {
				if(checkGrade()) {
					new saleStart();
					jf.dispose();
				}else {
					Timer timer = new Timer();
					MyTask m1 = new MyTask();
					StopTask s1 = new StopTask(timer);
					timer.schedule(m1, 0, 4000);
					timer.schedule(s1, 2000);
					jf.dispose();
					
				}
			}else {
				  JOptionPane.showMessageDialog(null, "帳號或密碼有誤", "錯誤", 
		    			  JOptionPane.CLOSED_OPTION);
			}
		}

		private int loginCheck(String ac, String pw) {
			//連線
			Properties prop = new Properties();
			prop.put("user", "root");
			prop.put("password", "root");
			
			try {
				Connection conn =DriverManager.getConnection
						("jdbc:mysql://localhost:3306/yucu", prop);
				member member=login(ac, pw, conn);
				if(member!=null) {
						return 1;
					
				}else {
					System.out.println("GET OUT HERE");
			    	
				}
				
			
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return 0; 
					
			
		}
	
		static member login(String account,String password,Connection conn)throws Exception{
			String sql = "SELECT * FROM member WHERE account = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String hashPw = rs.getString("password");
				if(BCrypt.checkpw(password, hashPw)) {
					member member = new member(rs.getInt("id"), 
							rs.getString("account"),
							rs.getString("realname"),
							rs.getInt("grade"));
					return member;
					
			
					
				}else {
					return null;
				}
			}else {
				return null;
			}
		}
		
		
		public static boolean checkGrade() {
			if (ac.contains("yucu")) {
				return true;
			}else {
				return false;
			}
			
		}
		
		
		
		//Thread-視窗
		public  static  class MyTask extends TimerTask{
			public void run() {
				  JOptionPane pane = new JOptionPane("共查詢到 27 筆資料",
				          JOptionPane.INFORMATION_MESSAGE);
				      JDialog dialog = pane.createDialog(null, "查詢");
				      dialog.setModal(false);
				      dialog.setVisible(true);

			}
		}
		
		//Thread-結束動作
		public static class StopTask extends TimerTask{
			private Timer timer;
			public StopTask(Timer timer) {this.timer=timer;}
			@Override
			public void run() {
				new productTable();
				timer.cancel();		
			}
		}
		
		
		
		
	
	}
}
